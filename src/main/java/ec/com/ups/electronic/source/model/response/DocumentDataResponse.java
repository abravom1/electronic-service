package ec.com.ups.electronic.source.model.response;

import lombok.Builder;

@Builder
public record DocumentDataResponse(
        String documentId,
        String autMessage
) {
}
