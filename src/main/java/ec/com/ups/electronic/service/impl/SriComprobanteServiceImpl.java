package ec.com.ups.electronic.service.impl;


import ec.com.ups.electronic.repository.SriComprobanteRepository;
import ec.com.ups.electronic.source.entity.Document;
import ec.com.ups.electronic.source.entity.SriComprobante;
import ec.com.ups.electronic.source.xml.FacturaXml;
import ec.com.ups.electronic.util.DatosUtil;
import ec.com.ups.electronic.util.MetodosVarios;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SriComprobanteServiceImpl {
    private final MetodosVarios metodosVarios;
    private final SriComprobanteRepository sriComprobanteRepository;

    @Transactional
    public SriComprobante crearSriComprobantePorFactura(Document documento, FacturaXml facturaXml) {
        SriComprobante sriComprobante = new SriComprobante();
        sriComprobante.setDocumento(documento);
        sriComprobante.setTipoAmbiente(facturaXml.getInfoTributaria().getAmbiente());
        sriComprobante.setTipoEmision(facturaXml.getInfoTributaria().getTipoEmision());
        sriComprobante.setClaveAcceso(facturaXml.getInfoTributaria().getClaveAcceso());
        sriComprobante.setMetodoAutorizacion(DatosUtil.METODO_AUTORIZACION);
        return sriComprobanteRepository.save(sriComprobante);
    }

    @Transactional
    public SriComprobante saveSriComprobante(SriComprobante sriComprobante) {
        return sriComprobanteRepository.save(sriComprobante);
    }

    @Transactional(readOnly = true)
    public SriComprobante consultaPorDocumentoId(String documentId){
        return sriComprobanteRepository.findByDocumentoId(documentId);
    }


}
