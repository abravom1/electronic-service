package ec.com.ups.electronic.source.mapper;

import ec.com.ups.electronic.source.entity.Product;
import ec.com.ups.electronic.source.model.request.ProductModelRequest;
import ec.com.ups.electronic.source.model.response.ProductDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "productId", target = "id")
    @Mapping(source = "code", target = "codigo")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "price", target = "precio")
    Product mapProductModelToProdcut(ProductModelRequest productModelRequest);


    @Mappings({
            @Mapping(source = "id", target = "productId"),
    })
    ProductDataResponse toProductDataResponse(Product producto);

}
