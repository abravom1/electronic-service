package ec.com.ups.electronic.source.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DOCUMENTO")
@NoArgsConstructor
@DynamicUpdate
public class Document {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.ups.electronic.util.CustomSequenceGenerator")
    private String id;
    @Column(name = "NUMERODOCUMENTO", nullable = false)
    private String numeroDocumento;
    @Column(name = "SUBTOTAL", precision = 16, scale = 4, nullable = false)
    private BigDecimal subtotal;
    @Column(name = "PORCENTAJEIMPUESTO", precision = 16, scale = 4)
    private BigDecimal porcentajeImpuesto;
    @Column(name = "VALORIMPUESTO", precision = 16, scale = 4)
    private BigDecimal valorImpuesto;
    @Column(name = "PORCENTAJEDESCUENTO", precision = 16, scale = 4)
    private BigDecimal porcentajeDescuento;
    @Column(name = "VALORDESCUENTO", precision = 16, scale = 4)
    private BigDecimal valorDescuento;
    @Column(name = "VALORTOTAL", precision = 16, scale = 4)
    private BigDecimal valorTotal;
    @Column(name = "FECHAREGISTRO", nullable = false)
    private LocalDateTime fechaRegistro;
    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
    }
}
