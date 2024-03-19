package ec.com.ups.electronic.source.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SRICOMPROBANTE")
@NoArgsConstructor
@DynamicUpdate
public class SriComprobante {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.ups.electronic.util.CustomSequenceGenerator")
    private String id;
    @ManyToOne
    @JoinColumn(name = "DOCUMENTOID", nullable = false, foreignKey = @ForeignKey(name = "FK_DOCUMENTO"))
    private Document documento;
    @Column(name = "TIPOAMBIENTE", nullable = false)
    private String tipoAmbiente;
    @Column(name = "TIPOEMISION", nullable = false)
    private String tipoEmision;
    @Column(name = "CLAVEACCESO", nullable = false)
    private String claveAcceso;
    @Column(name = "METODOAUTORIZACION", nullable = false)
    private String metodoAutorizacion;
    @Column(name = "FECHAAUTORIZACION")
    private LocalDateTime fechaAutorizacion;
    @Column(name = "NUMEROAUTORIZACION")
    private String numeroAutorizacion;
    @Column(name = "XML", columnDefinition = "text")
    private String xml;
    @Column(name = "OBSERVACIONES", columnDefinition = "text")
    private String observaciones;
    @Column(name = "ESTADO")
    private String estado;

}
