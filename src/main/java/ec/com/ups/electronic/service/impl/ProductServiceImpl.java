package ec.com.ups.electronic.service.impl;


import ec.com.ups.electronic.repository.ProductRepository;
import ec.com.ups.electronic.service.ProductService;
import ec.com.ups.electronic.source.entity.Product;
import ec.com.ups.electronic.source.mapper.ProductMapper;
import ec.com.ups.electronic.source.model.request.ProductModelRequest;
import ec.com.ups.electronic.source.model.response.ProductDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public ProductDataResponse crearProducto(ProductModelRequest request) {
        Product product = productMapper.mapProductModelToProdcut(request);
        product = productRepository.save(product);
        return productMapper.toProductDataResponse(product);
    }
}
