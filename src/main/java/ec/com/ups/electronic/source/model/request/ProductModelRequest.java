package ec.com.ups.electronic.source.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record ProductModelRequest(
        String productId,
        @NotNull(message = "Attribute 'code' cannot be null")
        @NotBlank(message = "Attribute 'code' cannot be empty")
        String code,
        @NotNull(message = "Attribute 'name' cannot be null")
        @NotBlank(message = "Attribute 'name' cannot be empty")
        String name,
        @NotNull(message = "Attribute 'price' cannot be null")
        BigDecimal price
) {
}
