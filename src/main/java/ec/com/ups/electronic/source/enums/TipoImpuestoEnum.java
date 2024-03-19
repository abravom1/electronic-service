package ec.com.ups.electronic.source.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoImpuestoEnum {
    IVA("2", "I.V.A."), ICE("3", "I.C.E."), IRBPNR("5", "I.R.B.P.N.R.");
    private String code;
    private String descripcion;
}
