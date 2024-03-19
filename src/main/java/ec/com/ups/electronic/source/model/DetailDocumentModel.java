package ec.com.ups.electronic.source.model;

import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record DetailDocumentModel(
        String documentDetailId,
        String productId,
        BigDecimal quantity,
        BigDecimal subtotal,
        BigDecimal taxPercentage,
        BigDecimal taxValue,
        BigDecimal discountPercentage,
        BigDecimal discountValue,
        BigDecimal totalValue,
        BigDecimal returnedQuantity
) {
}
