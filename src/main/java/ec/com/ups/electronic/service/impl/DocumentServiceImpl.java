package ec.com.ups.electronic.service.impl;

import ec.com.ups.electronic.client.AutorizacionDesaSoapClient;
import ec.com.ups.electronic.client.RecepcionDesaSoapClient;
import ec.com.ups.electronic.repository.DocumentRepository;
import ec.com.ups.electronic.service.DocumentService;
import ec.com.ups.electronic.source.entity.*;
import ec.com.ups.electronic.source.mapper.DocumentMapper;
import ec.com.ups.electronic.source.model.request.DocumentModelRequest;
import ec.com.ups.electronic.source.model.response.DocumentDataResponse;
import ec.com.ups.electronic.source.model.response.SriReceiptDataResponse;
import ec.com.ups.electronic.source.xml.FacturaXml;
import ec.com.ups.electronic.source.xml.buils.FacturaBuild;
import ec.com.ups.electronic.util.DatosUtil;
import ec.com.ups.electronic.util.FirmarDocumentoXml;
import ec.com.ups.electronic.util.MetodosVarios;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.Autorizacion;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.AutorizacionComprobanteResponse;
import ec.com.ups.electronic.util.wsdl.desarrollo.recepcion.ValidarComprobanteResponse;
import jakarta.xml.ws.WebServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final RecepcionDesaSoapClient recepcionDesaSoapClient;
    private final AutorizacionDesaSoapClient autorizacionDesaSoapClient;
    private final MetodosVarios metodosVarios;
    private final FirmarDocumentoXml firmarDocumentoXml;
    private final FacturaBuild facturaBuild;
    private final DocumentMapper documentMapper;
    private final DetailDocumentServiceImpl detailDocumentService;
    private final PaymentDocumentServiceImpl paymentDocumentService;
    private final SriComprobanteServiceImpl sriComprobanteService;
    private final MensajeSriResponseServiceImpl mensajeSriResponseService;
    private final DocumentRepository documentRepository;
    @Override
    @Transactional
    public DocumentDataResponse createDocument(DocumentModelRequest request) {
        Document document = documentMapper.mapearDocumentoModelRequestADocumento(request);
        document = documentRepository.save(document);
        List<DetailDocument> listDetails = detailDocumentService.saveDocumentDetail(document, request);
        List<PaymentDocument> listPayment = paymentDocumentService.saveDocumentPayment(document, request);

        FacturaXml invoiceXml = facturaBuild.generaFacturaXml(document, listDetails, listPayment);
        String xmlUnsigned = metodosVarios.facturaXmlToText(invoiceXml);
        SriComprobante sriComprobante = sriComprobanteService.crearSriComprobantePorFactura(document, invoiceXml);
        String pathXmlSigned = DatosUtil.PATH_XML_SIGNED + invoiceXml.getInfoTributaria().getClaveAcceso() + ".xml";
        String resultSign = firmarDocumentoXml.generarXmlFirmado(metodosVarios.textToFile(xmlUnsigned), pathXmlSigned);

        String response = null;
        if(resultSign.equals(DatosUtil.PROCESO_CORRECTO)){
            String xmlSigned = metodosVarios.fileToText(pathXmlSigned);
            sriComprobante.setXml(xmlSigned);
            sriComprobante.setEstado(DatosUtil.ESTADO_FIRMADO);
            if(sriComprobante.getTipoAmbiente().equals(DatosUtil.AMBIENTE_DESARROLLO)){
                response = recepcionAutorizacionComprobante(sriComprobante);
            }
        }else{
            sriComprobante.setXml(xmlUnsigned);
            sriComprobante.setObservaciones(resultSign);
            sriComprobante.setEstado(DatosUtil.ESTADO_NO_FIRMADO);
        }
        sriComprobanteService.saveSriComprobante(sriComprobante);
        return documentMapper.documentToDocumentDataResponse(document, response);
    }

    public String recepcionAutorizacionComprobante(SriComprobante sriComprobante) throws WebServiceException {
        String mensajeResponse = null;
        try{
            File signedFile = metodosVarios.textToFile(sriComprobante.getXml());
            ValidarComprobanteResponse responseReception =  recepcionDesaSoapClient.validarComprobante(metodosVarios.readFileToBytes(signedFile));
            MensajeSriResponse mensajeRecepcion = new MensajeSriResponse();
            if (responseReception != null) {
                mensajeRecepcion = mensajeSriResponseService.saveMensajeRecepcionSriResponse(responseReception, sriComprobante);
            }
            if(mensajeRecepcion.getEstado().equals(DatosUtil.ESTADO_REPCION_RECIBIDA)){
                AutorizacionComprobanteResponse responseAuth = autorizacionDesaSoapClient.autorizacionComprobante(sriComprobante.getClaveAcceso());
                if(responseAuth != null && !responseAuth.getRespuestaAutorizacionComprobante().getNumeroComprobantes().equals(DatosUtil.NO_HAY_COMPROBANTES_AUTORIZADOS)){
                    MensajeSriResponse mensajeAut = mensajeSriResponseService.saveMensajeAutorizacionSriResponse(responseAuth, sriComprobante);
                    if(mensajeAut.getEstado().equals(DatosUtil.AUTORIZADO)){
                        for (Autorizacion aut : responseAuth.getRespuestaAutorizacionComprobante().getAutorizaciones().getAutorizacion()){
                            String comprobante = aut.getComprobante();
                            aut.setComprobante("<![CDATA[".concat(comprobante).concat("]]>"));
                            sriComprobante.setXml(metodosVarios.autorizacionXmlToText(aut));
                        }
                        sriComprobante.setNumeroAutorizacion(mensajeAut.getNumeroAutorizacion());
                        sriComprobante.setFechaAutorizacion(mensajeAut.getFechaAutorizacion());
                        sriComprobante.setEstado(mensajeAut.getEstado());
                        sriComprobante.setObservaciones(DatosUtil.MENSAJE_AUTORIZADO);
                        mensajeResponse = DatosUtil.MENSAJE_AUTORIZADO;
                    }else{
                        sriComprobante.setEstado(mensajeAut.getEstado());
                        sriComprobante.setObservaciones(mensajeAut.getMensajeAdcional());
                    }
                    sriComprobanteService.saveSriComprobante(sriComprobante);
                }
            }

        } catch (Exception ws){
            ws.printStackTrace();
        }
        return mensajeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public SriReceiptDataResponse consultDocument(String documentId) {
        Document document = documentRepository.findById(documentId).orElse(null);
        if(document == null)
            throw new RuntimeException("No existe Documento con el id: "+documentId);
        SriComprobante sriComprobante = sriComprobanteService.consultaPorDocumentoId(documentId);
        return documentMapper.sriComprobanteToSriReceiptDataResponse(sriComprobante);
    }

}
