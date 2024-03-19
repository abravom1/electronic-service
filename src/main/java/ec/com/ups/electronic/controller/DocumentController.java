package ec.com.ups.electronic.controller;

import ec.com.ups.electronic.service.DocumentService;
import ec.com.ups.electronic.source.model.request.DocumentModelRequest;
import ec.com.ups.electronic.source.model.response.DocumentDataResponse;
import ec.com.ups.electronic.source.model.response.SriReceiptDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Description("Document controller")
@Tag(name = "document")
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "Create a new document", description = "Create a new document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = DocumentDataResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping()
    public ResponseEntity<?> crearDocumento(
            @Valid @RequestBody DocumentModelRequest documentRequest
    ) {
        if (documentRequest == null) {
            throw new IllegalArgumentException("Body must not be null");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", documentService.createDocument(documentRequest));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Getting information of document", description = "Getting information of document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SriReceiptDataResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, value = "getDocument")
    public ResponseEntity<?> consultaDocumento(
            @Parameter(description = "Document ID") @RequestParam(name = "documentId") String documentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", documentService.consultDocument(documentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
