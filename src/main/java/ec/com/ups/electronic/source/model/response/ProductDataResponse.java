package ec.com.ups.electronic.source.model.response;

import lombok.Builder;

@Builder
public record ProductDataResponse(
        String productId,
        String name
) {
}
