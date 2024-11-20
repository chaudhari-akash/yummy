package akash.esd.controller;

import akash.esd.dto.ProductRequest;
import akash.esd.entity.Product;
import akash.esd.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest.ProductCreateRequest productCreateRequest) {
        return ResponseEntity.ok(productService.createProduct(productCreateRequest));
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getAllProducts(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> getProductsByRange(@RequestParam BigDecimal lower,
                                                            @RequestParam BigDecimal upper){
        System.out.println("this is low" + lower + " " + upper);
        return ResponseEntity.ok(productService.getAllByPriceRance(lower, upper));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductRequest.ProductUpdateRequest productUpdateRequest) {

        Product product1 = null;
        product1 = productService.updateProduct(id, productUpdateRequest);
        if (product1 != null) {
            return new ResponseEntity<>("Product Updated Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        int action = 0;
        action = productService.deleteProduct(id);
        if (action != 0) {
            return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete", HttpStatus.BAD_REQUEST);
        }
    }
}
