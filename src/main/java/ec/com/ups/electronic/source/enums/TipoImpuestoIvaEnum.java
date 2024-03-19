package ec.com.ups.electronic.source.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoImpuestoIvaEnum {
	IVA_VENTA_0("0"),
	IVA_VENTA_12("2"),
	IVA_VENTA_14("3"),
	NO_OBJETO_DE_IMPUESTO("6"),
	IVA_VENTA_EXCENTO("7");
	private String code;

}
