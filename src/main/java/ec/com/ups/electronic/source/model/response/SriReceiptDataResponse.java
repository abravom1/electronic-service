package ec.com.ups.electronic.source.model.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SriReceiptDataResponse(
        String documentNumber,
        String accesskey,
        String documentStatus,
        String observations,
        String authorizationNumber,
        LocalDateTime authorizationDate,
        String xml
) {
}
