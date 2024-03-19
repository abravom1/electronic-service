package ec.com.ups.electronic.controller;

import ec.com.ups.electronic.service.ProductService;
import ec.com.ups.electronic.source.model.request.ProductModelRequest;
import ec.com.ups.electronic.source.model.response.ProductDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Description("Product controller")
@Tag(name = "product")
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @Operation(summary = "Create a new product Product", description = "Create a new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDataResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping()
    public ResponseEntity<?> crearProducto(
            @Valid @RequestBody ProductModelRequest productModelRequest
    ) {
        if (productModelRequest == null) {
            throw new IllegalArgumentException("Body must not be null");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", productService.crearProducto(productModelRequest));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
