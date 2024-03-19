package ec.com.ups.electronic.source.xml;


import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "infoTributaria", "infoFactura", "detalles", "infoAdicional" })
@XmlRootElement(name = "factura")
@Data
public class FacturaXml {
	@XmlElement(required = true)
	protected InfoTributariaXml infoTributaria;
	@XmlElement(required = true)
	protected InfoFactura infoFactura;
	@XmlElement(required = true)
	protected Detalles detalles;
	protected InfoAdicional infoAdicional;
	@XmlAttribute
	protected String id;
	@XmlAttribute
	@XmlSchemaType(name = "anySimpleType")
	protected String version;


	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "detalle" })
	@Data
	public static class Detalles {
		@XmlElement(required = true)
		protected List<Detalle> detalle;

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "codigoPrincipal", "codigoAuxiliar", "descripcion", "cantidad",
				"precioUnitario", "descuento", "precioTotalSinImpuesto", "detallesAdicionales", "impuestos" })
		@Data
		public static class Detalle {
			@XmlElement(required = true)
			protected String codigoPrincipal;
			protected String codigoAuxiliar;
			@XmlElement(required = true)
			protected String descripcion;
			@XmlElement(required = true)
			protected BigDecimal cantidad;
			@XmlElement(required = true)
			protected BigDecimal precioUnitario;
			@XmlElement(required = true)
			protected BigDecimal descuento;
			@XmlElement(required = true)
			protected BigDecimal precioTotalSinImpuesto;
			protected DetallesAdicionales detallesAdicionales;
			@XmlElement(required = true)
			protected Impuestos impuestos;

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "detAdicional" })
			public static class DetallesAdicionales {
				@XmlElement(required = true)
				protected List<DetAdicional> detAdicional;


				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "")
				public static class DetAdicional {
					@XmlAttribute
					protected String nombre;
					@XmlAttribute
					protected String valor;
				}
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "impuesto" })
			@Data
			public static class Impuestos {
				@XmlElement(required = true)
				protected List<FacturaImpuestoXml> impuesto;
			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "campoAdicional" })
	@Data
	public static class InfoAdicional {
		@XmlElement(required = true)
		protected List<CampoAdicional> campoAdicional;

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "value" })
		@Data
		public static class CampoAdicional {
			@XmlValue
			protected String value;
			@XmlAttribute
			protected String nombre;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "fechaEmision", "dirEstablecimiento", "contribuyenteEspecial",
			"obligadoContabilidad", "tipoIdentificacionComprador", "guiaRemision", "razonSocialComprador",
			"identificacionComprador", "totalSinImpuestos", "totalDescuento", "totalConImpuestos", "compensaciones",
			"propina", "importeTotal", "moneda", "pagos" })
	@Data
	public static class InfoFactura {
		@XmlElement(required = true)
		protected String fechaEmision;
		@XmlElement(required = true)
		protected String dirEstablecimiento;
		protected String contribuyenteEspecial;
		protected String obligadoContabilidad;
		@XmlElement(required = true)
		protected String tipoIdentificacionComprador;
		protected String guiaRemision;
		@XmlElement(required = true)
		protected String razonSocialComprador;
		@XmlElement(required = true)
		protected String identificacionComprador;
		@XmlElement(required = true)
		protected BigDecimal totalSinImpuestos;
		@XmlElement(required = true)
		protected BigDecimal totalDescuento;
		@XmlElement(required = true)
		protected TotalConImpuestos totalConImpuestos;
		@XmlElement(required = true)
		protected Compensaciones compensaciones;
		@XmlElement(required = true)
		protected BigDecimal propina;
		@XmlElement(required = true)
		protected BigDecimal importeTotal;
		protected String moneda;
		@XmlElement(required = true)
		protected Pagos pagos;

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "totalImpuesto" })
		@Data
		public static class TotalConImpuestos {
			@XmlElement(required = true)
			protected List<TotalImpuesto> totalImpuesto;
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "codigo", "codigoPorcentaje", "baseImponible", "tarifa", "valor" })
			@Data
			public static class TotalImpuesto {
				@XmlElement(required = true)
				protected String codigo;
				@XmlElement(required = true)
				protected String codigoPorcentaje;
				@XmlElement(required = true)
				protected BigDecimal baseImponible;
				protected BigDecimal tarifa;
				@XmlElement(required = true)
				protected BigDecimal valor;
			}
		}
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "compensacion" })
		public static class Compensaciones {
			@XmlElement(required = true)
			protected List<Compensacion> compensacion;

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "codigo", "tarifa", "valor" })
			public static class Compensacion {
				@XmlElement(required = true)
				protected String codigo;
				@XmlElement(required = true)
				protected String tarifa;
				@XmlElement(required = true)
				protected BigDecimal valor;

			}
		}
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "pago" })
		@Data
		public static class Pagos {
			@XmlElement(required = true)
			protected List<Pago> pago;

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "formaPago", "total", "plazo", "unidadTiempo" })
			@Data
			public static class Pago {
				@XmlElement(required = true)
				protected String formaPago;
				@XmlElement(required = true)
				protected BigDecimal total;
				@XmlElement(required = true)
				protected String plazo;
				@XmlElement(required = true)
				protected String unidadTiempo;

			}
		}
	}
}
