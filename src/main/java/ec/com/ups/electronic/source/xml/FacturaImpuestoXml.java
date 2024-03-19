package ec.com.ups.electronic.source.xml;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "impuesto", propOrder = { "codigo", "codigoPorcentaje", "tarifa", "baseImponible", "valor" })
@Data
public class FacturaImpuestoXml {
	@XmlElement(required = true)
	protected String codigo;
	@XmlElement(required = true)
	protected String codigoPorcentaje;
	@XmlElement(required = true)
	protected BigDecimal tarifa;
	@XmlElement(required = true)
	protected BigDecimal baseImponible;
	@XmlElement(required = true)
	protected BigDecimal valor;
}
