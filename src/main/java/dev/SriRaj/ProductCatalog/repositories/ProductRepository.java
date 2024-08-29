package dev.SriRaj.ProductCatalog.repositories;

import dev.SriRaj.ProductCatalog.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {


    Product save(Product product);

    Optional<Product> findById(Long id);

    @Query(value = "select * from Product", nativeQuery = true)
    List<Product> findAll();

    void deleteById(Long id);

    List<Product> getProductsByCategory(String category);


    @Query(value = "select distinct category FROM Product",nativeQuery = true)
    List<String> getAllByCategory();
}
