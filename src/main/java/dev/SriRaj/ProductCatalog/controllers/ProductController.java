package dev.SriRaj.ProductCatalog.controllers;


import dev.SriRaj.ProductCatalog.dtos.*;

import dev.SriRaj.ProductCatalog.services.SelfProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private SelfProductService selfProductService;



    @Autowired
    public ProductController(SelfProductService selfProductService
                             ){
        this.selfProductService=selfProductService;

    }


    @PostMapping("/create")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto product){
        return selfProductService.createProduct(product);
    }


    @GetMapping("{id}")
    public ResponseEntity<GetProductReponseDto> getProductById( @PathVariable("id") Long id){









        GetProductReponseDto response=selfProductService.getProductById(id);

        if (response==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


    @GetMapping("/allProducts")
    public ResponseEntity<GetAllProductsResponseDto> getAllProducts(){

        GetAllProductsResponseDto response= selfProductService.getAllProducts();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    @PutMapping("{id}")
   public ResponseEntity<GetProductReponseDto> updateProductById(@RequestBody CreateProductRequestDto product,@PathVariable("id") Long id){
        GetProductReponseDto responseDto=selfProductService.updateProductById(product,id);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id){
        selfProductService.deleteProductById(id);
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{categoryType}")
    public  ResponseEntity<GetAllProductsResponseDto> getCategoryById(@PathVariable("categoryType") String categoryType){
        GetAllProductsResponseDto responseDto= selfProductService.getCategoryById(categoryType);
        if(responseDto==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                responseDto,HttpStatus.OK
        );


    }

    @GetMapping("/categories")
     public ResponseEntity<List<CategoryTypeDto>> getAllCategories(){
        List<CategoryTypeDto> response=selfProductService.getAllCategories();

       if (response.isEmpty()){
           return new ResponseEntity<>(
                   response,HttpStatus.NOT_FOUND
           );

       }
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );


    }



}
