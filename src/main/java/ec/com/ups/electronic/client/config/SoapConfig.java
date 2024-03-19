package ec.com.ups.electronic.client.config;

import ec.com.ups.electronic.client.AutorizacionDesaSoapClient;
import ec.com.ups.electronic.client.RecepcionDesaSoapClient;
import ec.com.ups.electronic.util.DatosUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerRecepcion(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ec.com.ups.electronic.util.wsdl.desarrollo.recepcion");
        return marshaller;
    }

    @Bean
    @Lazy
    public RecepcionDesaSoapClient getSoapClientRecepcion(Jaxb2Marshaller marshallerRecepcion){
        RecepcionDesaSoapClient soapClient = new RecepcionDesaSoapClient();
        soapClient.setDefaultUri(DatosUtil.WSDL_LOCATION_DESA_RECEPCION_COMPROBANTE);
        soapClient.setMarshaller(marshallerRecepcion);
        soapClient.setUnmarshaller(marshallerRecepcion);

        return soapClient;
    }

    @Bean
    public Jaxb2Marshaller marshallerAut(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion");
        return marshaller;
    }

    @Bean
    @Lazy
    public AutorizacionDesaSoapClient getSoapClientAut(Jaxb2Marshaller marshallerAut){
        AutorizacionDesaSoapClient soapClient = new AutorizacionDesaSoapClient();
        soapClient.setDefaultUri(DatosUtil.WSDL_LOCATION_DESA_AUTORIZACION_COMPROBANTE);
        soapClient.setMarshaller(marshallerAut);
        soapClient.setUnmarshaller(marshallerAut);

        return soapClient;
    }
}