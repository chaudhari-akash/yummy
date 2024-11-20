package akash.esd.repo;

import akash.esd.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :lower AND :upper")
    List<Product> findProductsInPriceRange(@Param("lower") BigDecimal lower, @Param("upper") BigDecimal upper);
}
