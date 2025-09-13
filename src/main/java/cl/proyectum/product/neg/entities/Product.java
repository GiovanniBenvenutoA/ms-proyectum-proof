package cl.proyectum.product.neg.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "binnacle_hito_pg")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max=120)
    private String nameProduct;

    @NotNull @DecimalMin("0.0")
    private BigDecimal priceProduct;

    private Integer stockProduct;

    private String descriptionProduct;


}
