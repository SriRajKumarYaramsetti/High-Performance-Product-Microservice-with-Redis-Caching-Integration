package dev.SriRaj.ProductCatalog.services;

import dev.SriRaj.ProductCatalog.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SelfProductService {
    CreateProductResponseDto createProduct(CreateProductRequestDto product);

    GetProductReponseDto getProductById(Long Id);

    GetAllProductsResponseDto getAllProducts();

    GetProductReponseDto updateProductById(CreateProductRequestDto product, Long id);

    void deleteProductById(Long id);

    GetAllProductsResponseDto getCategoryById(String categoryType);
    List<CategoryTypeDto> getAllCategories();
}
