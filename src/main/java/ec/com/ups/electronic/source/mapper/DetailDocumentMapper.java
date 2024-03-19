package ec.com.ups.electronic.source.mapper;

import ec.com.ups.electronic.source.entity.DetailDocument;
import ec.com.ups.electronic.source.model.DetailDocumentModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetailDocumentMapper {

    @Mapping(source = "documentDetailId", target = "id")
    @Mapping(source = "productId", target = "producto.id")
    @Mapping(source = "quantity", target = "cantidad")
    @Mapping(source = "subtotal", target = "subtotal")
    @Mapping(source = "taxPercentage", target = "porcentajeImpuesto")
    @Mapping(source = "taxValue", target = "valorImpuesto")
    @Mapping(source = "discountPercentage", target = "porcentajeDescuento")
    @Mapping(source = "discountValue", target = "valorDescuento")
    @Mapping(source = "totalValue", target = "valorTotal")
    DetailDocument mapDetailDocumentModelToDetailDocument(DetailDocumentModel detailDocumentModel);



}
