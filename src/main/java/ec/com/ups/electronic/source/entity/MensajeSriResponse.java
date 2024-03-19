package ec.com.ups.electronic.source.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "MENSAJESRIRESPONSE")
@NoArgsConstructor
@DynamicUpdate
public class MensajeSriResponse {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.ups.electronic.util.CustomSequenceGenerator")
    private String id;
    @ManyToOne
    @JoinColumn(name = "SRICOMPROBANTEID", nullable = false, foreignKey = @ForeignKey(name = "FK_SRICOMPROBANTE"))
    private SriComprobante sriComprobante;
    @Column(name = "IDENTIFICADOR", length = 10)
    private String identificador;
    @Column(name = "MENSAJE", columnDefinition = "text")
    private String mensaje;
    @Column(name = "MENSAJEADICIONAL", columnDefinition = "text")
    private String mensajeAdcional;
    @Column(name = "TIPO", length = 100)
    private String tipo;
    @Column(name = "ESTADO", length = 100)
    private String estado;
    @Column(name = "CLAVEACCESOCONSULTADA", length = 100)
    private String claveAccesoConsultada;
    @Column(name = "NUMEROCOMPROBANTES", length = 3)
    private String numeroComprobantes;
    @Column(name = "NUMEROAUTORIZACION", length = 100)
    private String numeroAutorizacion;
    @Column(name = "FECHAAUTORIZACION")
    private LocalDateTime fechaAutorizacion;
    @Column(name = "AMBIENTE", length = 100)
    private String ambiente;
    @Column(name = "COMPROBANTE", columnDefinition = "text")
    private String comprobante;
}
