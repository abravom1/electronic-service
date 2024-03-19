package ec.com.ups.electronic.source.xml;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoTributaria", propOrder = { "ambiente", "tipoEmision", "razonSocial", "nombreComercial", "ruc",
		"claveAcceso", "codDoc", "estab", "ptoEmi", "secuencial", "dirMatriz", "contribuyenteRimpe" })
@Data
public class InfoTributariaXml {
	@XmlElement(required = true)
	protected String ambiente;
	@XmlElement(required = true)
	protected String tipoEmision;
	@XmlElement(required = true)
	protected String razonSocial;
	protected String nombreComercial;
	@XmlElement(required = true)
	protected String ruc;
	@XmlElement(required = true)
	protected String claveAcceso;
	@XmlElement(required = true)
	protected String codDoc;
	@XmlElement(required = true)
	protected String estab;
	@XmlElement(required = true)
	protected String ptoEmi;
	@XmlElement(required = true)
	protected String secuencial;
	@XmlElement(required = true)
	protected String dirMatriz;
	@XmlElement(required = true)
	protected String contribuyenteRimpe;
}
