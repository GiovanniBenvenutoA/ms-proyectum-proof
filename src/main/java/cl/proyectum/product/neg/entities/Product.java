package cl.proyectum.product.neg.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max=120)
    @Column(name = "nameProduct", nullable = false)
    private String nameProduct;

    @NotNull @DecimalMin("0.0")
    @Column(name = "priceProduct", nullable = false)
    private BigDecimal priceProduct;

    @Column(name = "stockProduct", nullable = false)
    private Integer stockProduct;

    @Column(name = "descriptionProduct", nullable = false)
    private String descriptionProduct;
}
