package ec.com.ups.electronic.service;

import ec.com.ups.electronic.source.model.request.ProductModelRequest;
import ec.com.ups.electronic.source.model.response.ProductDataResponse;

public interface ProductService {
    ProductDataResponse crearProducto(ProductModelRequest request);
}
