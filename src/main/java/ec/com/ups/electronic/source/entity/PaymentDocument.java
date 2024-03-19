package ec.com.ups.electronic.source.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "DOCUMENTOPAGO")
@NoArgsConstructor
@DynamicUpdate
public class PaymentDocument {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.ups.electronic.util.CustomSequenceGenerator")
    private String id;
    @Column(name = "DOCUMENTOID", nullable = false)
    private String documentoId;
    @Column(name = "FORMAPAGO", nullable = false)
    private String formaPago;
    @Column(name = "valor", precision = 16, scale = 4, nullable = false)
    private BigDecimal valor;
}
