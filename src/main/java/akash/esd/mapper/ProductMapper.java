package akash.esd.mapper;

import akash.esd.dto.ProductRequest;
import akash.esd.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toEntity(ProductRequest.ProductCreateRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
    }

    public Product toEntity(ProductRequest.ProductUpdateRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
    }
}
