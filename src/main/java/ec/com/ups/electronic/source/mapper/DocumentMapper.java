package ec.com.ups.electronic.source.mapper;


import ec.com.ups.electronic.source.entity.Document;
import ec.com.ups.electronic.source.entity.SriComprobante;
import ec.com.ups.electronic.source.model.request.DocumentModelRequest;
import ec.com.ups.electronic.source.model.response.DocumentDataResponse;
import ec.com.ups.electronic.source.model.response.SriReceiptDataResponse;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {

    @Mapping(source = "documentId", target = "id")
    @Mapping(source = "documentNumber", target = "numeroDocumento")
    @Mapping(source = "subtotal", target = "subtotal")
    @Mapping(source = "taxPercentage", target = "porcentajeImpuesto")
    @Mapping(source = "taxValue", target = "valorImpuesto")
    @Mapping(source = "discountPercentage", target = "porcentajeDescuento")
    @Mapping(source = "discountValue", target = "valorDescuento")
    @Mapping(source = "totalValue", target = "valorTotal")
    Document mapearDocumentoModelRequestADocumento(DocumentModelRequest documentModelRequest);


    @Mappings({
            @Mapping(source = "documento.id", target = "documentId"),
            @Mapping(target = "autMessage", expression = "java(mensaje != null ? mensaje : \"Comprobante no Autorizado\")")
    })
    DocumentDataResponse documentToDocumentDataResponse(Document documento, String mensaje);

    @Mappings({
            @Mapping(source = "documento.numeroDocumento", target = "documentNumber"),
            @Mapping(source = "claveAcceso", target = "accesskey"),
            @Mapping(source = "estado", target = "documentStatus"),
            @Mapping(source = "observaciones", target = "observations"),
            @Mapping(source = "numeroAutorizacion", target = "authorizationNumber"),
            @Mapping(source = "fechaAutorizacion", target = "authorizationDate"),
            @Mapping(source = "xml", target = "xml")
    })
    SriReceiptDataResponse sriComprobanteToSriReceiptDataResponse(SriComprobante sriComprobante);

}
