package ec.com.ups.electronic.source.classTest;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Entidad {
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String identificacion;
    private String correo;
    private String telefonoCelular;
    private String direccion;
}
