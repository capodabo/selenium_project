package com.example.demo.repository;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Product ORDER BY last_updated DESC LIMIT 3")
    List<Product> sortProducts();

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Product u SET product_name = :productName, product_price = :productPrice, price_before_promotion = :priceBeforePromotion, last_updated = now() where u.id = :productId")
    void updateProduct(@Param("productName") String productName, @Param("productPrice") double productPrice, @Param("priceBeforePromotion") double priceBeforePromotion, @Param("productId") Long laptopId);

}


