package ec.com.ups.electronic.client;


import ec.com.ups.electronic.util.DatosUtil;
import ec.com.ups.electronic.util.wsdl.desarrollo.recepcion.ValidarComprobante;
import ec.com.ups.electronic.util.wsdl.desarrollo.recepcion.ValidarComprobanteResponse;
import jakarta.xml.bind.JAXBElement;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class RecepcionDesaSoapClient extends WebServiceGatewaySupport {

    public ValidarComprobanteResponse validarComprobante(byte[] xml) {

        ValidarComprobante request = new ValidarComprobante();
        request.setXml(xml);
        String uri = DatosUtil.WSDL_LOCATION_DESA_RECEPCION_COMPROBANTE;
        String soapAction = ""; // Valor del SOAPAction

        SoapActionCallback callback = new SoapActionCallback(soapAction);
        JAXBElement<ValidarComprobanteResponse> responseElement = (JAXBElement<ValidarComprobanteResponse>)
                getWebServiceTemplate().marshalSendAndReceive(uri, request, callback);
        ValidarComprobanteResponse response = responseElement.getValue();

        return response;
    }


}