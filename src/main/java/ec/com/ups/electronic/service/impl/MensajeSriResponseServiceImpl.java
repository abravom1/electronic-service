package ec.com.ups.electronic.service.impl;



import ec.com.ups.electronic.repository.MensajeSriResponseRepository;
import ec.com.ups.electronic.source.entity.MensajeSriResponse;
import ec.com.ups.electronic.source.entity.SriComprobante;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.Autorizacion;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.AutorizacionComprobanteResponse;
import ec.com.ups.electronic.util.wsdl.desarrollo.recepcion.Comprobante;
import ec.com.ups.electronic.util.wsdl.desarrollo.recepcion.Mensaje;
import ec.com.ups.electronic.util.wsdl.desarrollo.recepcion.ValidarComprobanteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class MensajeSriResponseServiceImpl {
	private final MensajeSriResponseRepository mensajeSriResponseRepository;

	@Transactional
	public MensajeSriResponse saveMensajeRecepcionSriResponse(ValidarComprobanteResponse response, SriComprobante sriComprobante){
		MensajeSriResponse mensajeSriResponse = new MensajeSriResponse();
		mensajeSriResponse.setSriComprobante(sriComprobante);
		mensajeSriResponse.setEstado(response.getRespuestaRecepcionComprobante().getEstado());
		for (Comprobante comp : response.getRespuestaRecepcionComprobante().getComprobantes().getComprobante()) {
			for (Mensaje m : comp.getMensajes().getMensaje()) {
				mensajeSriResponse.setIdentificador(m.getIdentificador());
				mensajeSriResponse.setTipo(m.getTipo());
				mensajeSriResponse.setMensaje(m.getMensaje());
				mensajeSriResponse.setMensajeAdcional(m.getInformacionAdicional());
			}
		}
		return mensajeSriResponseRepository.save(mensajeSriResponse);
	}

	@Transactional
	public MensajeSriResponse saveMensajeAutorizacionSriResponse(AutorizacionComprobanteResponse response, SriComprobante sriComprobante){
		MensajeSriResponse mensajeSriResponse = new MensajeSriResponse();
		mensajeSriResponse.setSriComprobante(sriComprobante);
		mensajeSriResponse.setClaveAccesoConsultada(response.getRespuestaAutorizacionComprobante().getClaveAccesoConsultada());
		mensajeSriResponse.setNumeroComprobantes(response.getRespuestaAutorizacionComprobante().getNumeroComprobantes());
		for (Autorizacion aut : response.getRespuestaAutorizacionComprobante().getAutorizaciones().getAutorizacion()) {
			mensajeSriResponse.setEstado(aut.getEstado());
			mensajeSriResponse.setNumeroAutorizacion(aut.getNumeroAutorizacion());
			//LocalDateTime fechaLocalDateTime = aut.getFechaAutorizacion().toGregorianCalendar().toZonedDateTime().toLocalDateTime();
			//mensajeSriResponse.setFechaAutorizacion(fechaLocalDateTime);
			mensajeSriResponse.setAmbiente(aut.getAmbiente());
			mensajeSriResponse.setComprobante(aut.getComprobante());
			if(aut.getMensajes().getMensaje()!=null){
				for (ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.Mensaje m : aut.getMensajes().getMensaje()) {
					mensajeSriResponse.setIdentificador(m.getIdentificador());
					mensajeSriResponse.setTipo(m.getTipo());
					mensajeSriResponse.setMensaje(m.getMensaje());
					mensajeSriResponse.setMensajeAdcional(m.getInformacionAdicional());
				}
			}
		}
		return mensajeSriResponseRepository.save(mensajeSriResponse);
	}




}
