package ec.com.ups.electronic.client;


import ec.com.ups.electronic.util.DatosUtil;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.AutorizacionComprobante;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.AutorizacionComprobanteResponse;
import jakarta.xml.bind.JAXBElement;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class AutorizacionDesaSoapClient extends WebServiceGatewaySupport {

    public AutorizacionComprobanteResponse autorizacionComprobante(String claveAcceso) {

        String uri = DatosUtil.WSDL_LOCATION_DESA_AUTORIZACION_COMPROBANTE;
        String soapAction = ""; // Valor del SOAPAction

        AutorizacionComprobante request = new AutorizacionComprobante();
        request.setClaveAccesoComprobante(claveAcceso);

        SoapActionCallback callback = new SoapActionCallback(soapAction);
        JAXBElement<AutorizacionComprobanteResponse> responseElement = (JAXBElement<AutorizacionComprobanteResponse>)
                getWebServiceTemplate().marshalSendAndReceive(uri, request, callback);
        AutorizacionComprobanteResponse response = responseElement.getValue();

        return response;
    }


}