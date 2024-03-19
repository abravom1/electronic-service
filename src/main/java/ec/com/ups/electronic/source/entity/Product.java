package ec.com.ups.electronic.source.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "PRODUCTO")
@NoArgsConstructor
@DynamicUpdate
public class Product {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.ups.electronic.util.CustomSequenceGenerator")
    private String id;
    @Column(name = "CODIGO", nullable = false, unique = true)
    private String codigo;
    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;
    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;
}
