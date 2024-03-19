package ec.com.ups.electronic.source.model;

import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record PaymentDocumentModel(
        String documentPaymentId,
        String paymentMethod,
        BigDecimal value
) {
}
