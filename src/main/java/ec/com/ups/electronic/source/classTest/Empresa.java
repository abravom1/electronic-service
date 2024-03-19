package ec.com.ups.electronic.source.classTest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Empresa {
    private String nombre;
    private String numeroIdentificacion;
    private String razonSocial;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String numeroEstablecimiento;
    private String tipoAmbiente;
}
