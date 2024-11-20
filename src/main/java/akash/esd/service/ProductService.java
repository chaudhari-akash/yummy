package akash.esd.service;

import akash.esd.dto.ProductRequest;
import akash.esd.entity.Product;
import akash.esd.mapper.ProductMapper;
import akash.esd.repo.ProductRepo;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo repo;
    private final ProductMapper mapper;

    public Product createProduct(ProductRequest.ProductCreateRequest request) {
        Product product = mapper.toEntity(request);
        return repo.save(product);
    }

    public List<Product> getAllProduct(){
        return repo.findAll();
    }

    public List<Product> getAllByPriceRance(BigDecimal lower, BigDecimal upper){
        return repo.findProductsInPriceRange(lower, upper);
    }

    public Product getById(Long id) {
        Optional<Product> optionalProduct = repo.findById(id);

        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return product;
        }
        return null;
    }

    public Product updateProduct(Long id, ProductRequest.ProductUpdateRequest request) throws IOException {
        Optional<Product> optionalProduct = repo.findById(id);

        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if(request.name()!=null) {
                product.setName(request.name());
            }
            if(request.price()!=null) {
                product.setPrice(request.price());
            }
            return repo.save(product);
        }
        return null;
    }

    public int deleteProduct(Long id) throws IOException{
        Optional<Product> optionalProduct = repo.findById(id);

        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            repo.delete(product);
            return 1;
        }
        return 0;
    }
}
