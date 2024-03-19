package ec.com.ups.electronic.source.model.request;

import ec.com.ups.electronic.source.model.DetailDocumentModel;
import ec.com.ups.electronic.source.model.PaymentDocumentModel;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Validated
public record DocumentModelRequest(
        String documentId,
        String documentNumber,
        BigDecimal subtotal,
        BigDecimal taxPercentage,
        BigDecimal taxValue,
        BigDecimal discountPercentage,
        BigDecimal discountValue,
        BigDecimal totalValue,
        List<DetailDocumentModel> documentDetail,
        List<PaymentDocumentModel> documentPayment
) {
}
