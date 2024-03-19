package ec.com.ups.electronic.source.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoComprobanteEnum {
	FACTURA("01", "FACTURA"),
	NOTA_DE_CREDITO("04", "NOTA DE CREDITO");
	private String code;
	private String descripcion;

}
