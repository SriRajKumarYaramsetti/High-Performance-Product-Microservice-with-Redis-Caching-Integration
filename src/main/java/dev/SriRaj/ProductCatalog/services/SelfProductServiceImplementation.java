package dev.SriRaj.ProductCatalog.services;


import dev.SriRaj.ProductCatalog.dtos.*;
import dev.SriRaj.ProductCatalog.models.Product;
import dev.SriRaj.ProductCatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class SelfProductServiceImplementation implements SelfProductService {


    private ProductRepository productRepository;
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public SelfProductServiceImplementation(ProductRepository productRepository,
                                            RedisTemplate<String,Object> redisTemplate){
        this.productRepository=productRepository;
        this.redisTemplate=redisTemplate;

    }

    CreateProductResponseDto convertCreateProductRequestDtoToCreateProductResponseDto(CreateProductRequestDto request){
        Product newProduct=new Product();
        newProduct.setTitle(request.getTitle());
        newProduct.setCategory(request.getCategory());
        newProduct.setImage(request.getImage());
        newProduct.setDescription(request.getDescription());
        newProduct.setPrice(request.getPrice());

        Product savedProduct=productRepository.save(newProduct);

        CreateProductResponseDto response=new CreateProductResponseDto();
        response.setId(savedProduct.getId());
        response.setTitle(savedProduct.getTitle());
        response.setPrice(savedProduct.getPrice());
        response.setImage(savedProduct.getImage());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());

        return response;
    }
    @Override
    public CreateProductResponseDto createProduct(CreateProductRequestDto request) {
        return convertCreateProductRequestDtoToCreateProductResponseDto(request);
    }

    @Override
    public GetProductReponseDto getProductById(Long Id) {

        //check if this product Id exists in the cache
        GetProductReponseDto getProductReponseDto=(GetProductReponseDto)redisTemplate.opsForHash().get("PRODUCTS",Id);

        //If yes directly written from the cache

        if (getProductReponseDto!=null){
            return getProductReponseDto;
        }

        //Else make an db request and save details in the cache then return



        Optional<Product> optionalProduct = productRepository.findById(Id);


        //If (product.getStatus().equals(PRIVATE){
        //if (userIdTryingToAccess.equals(product.getCreatorId()))
        //      return product
        //     }
                    //return null;
        //}
        //  if it is public return product


    //}
        if (optionalProduct.isPresent()){
            Product product= optionalProduct.get();
        }
        else {
            return null;
        }

        GetProductReponseDto response=new GetProductReponseDto();
        response.setId(optionalProduct.get().getId());
        response.setTitle(optionalProduct.get().getTitle());
        response.setPrice(optionalProduct.get().getPrice());
        response.setImage(optionalProduct.get().getImage());
        response.setCategory(optionalProduct.get().getCategory());
        response.setDescription(optionalProduct.get().getDescription());

        redisTemplate.opsForHash().put("PRODUCTS",Id,response);

        return response;
    }

    @Override
    public GetAllProductsResponseDto getAllProducts() {
        GetAllProductsResponseDto ResponseDto=new GetAllProductsResponseDto();
        List<GetProductReponseDto> ProductResponseDto=new ArrayList<>();
        List<Product> products=productRepository.findAll();



        return  createAllProductsResponseDto(ResponseDto,ProductResponseDto,products);
    }

    @Override
    public GetProductReponseDto updateProductById(CreateProductRequestDto updatedDetails, Long id) {

        Optional<Product> productToBeUpdated=productRepository.findById(id);
        if (productToBeUpdated.isEmpty()){
            return null;
        }

        Product product=productToBeUpdated.get();
        product.setTitle(updatedDetails.getTitle());
        product.setDescription(updatedDetails.getDescription());
        product.setCategory(updatedDetails.getCategory());
        product.setImage(updatedDetails.getImage());
        product.setPrice(updatedDetails.getPrice());

        Product updatedProduct=productRepository.save(product);

        GetProductReponseDto productReponseDto=new GetProductReponseDto();
        productReponseDto.setTitle(updatedProduct.getTitle());
        productReponseDto.setDescription(updatedProduct.getDescription());
        productReponseDto.setPrice(updatedProduct.getPrice());
        productReponseDto.setCategory(updatedProduct.getCategory());
        productReponseDto.setImage(updatedProduct.getImage());
        productReponseDto.setPrice(updatedProduct.getPrice());

        return productReponseDto;
    }

    @Override
    public void deleteProductById(Long id) {

        productRepository.deleteById(id);

    }

    @Override
    public GetAllProductsResponseDto getCategoryById(String category) {

        List<Product> productDetailsByCategory= productRepository.getProductsByCategory(category);

        if (productDetailsByCategory.isEmpty()) {
            return null;
        }

        GetAllProductsResponseDto responseDto=new GetAllProductsResponseDto();
        List<GetProductReponseDto> products=new ArrayList<>();

        GetAllProductsResponseDto allProducts=createAllProductsResponseDto(responseDto,products,productDetailsByCategory);



        return allProducts;

    }

    @Override
    public List<CategoryTypeDto> getAllCategories() {
        List<CategoryTypeDto> allCategoryResponseDtos=new ArrayList<>();
        List<String> category=productRepository.getAllByCategory();
        for(String category1:category){
            CategoryTypeDto categoryTypeDto=new CategoryTypeDto();
            categoryTypeDto.setCategory(category1);
            allCategoryResponseDtos.add(categoryTypeDto);
        }
        return allCategoryResponseDtos ;
    }

    public GetAllProductsResponseDto createAllProductsResponseDto(GetAllProductsResponseDto ResponseDto,
                                                                  List<GetProductReponseDto> ProductResponseDto,
                                                                  List<Product> products){

        for (Product product:products){
            GetProductReponseDto newProduct=new GetProductReponseDto();
            newProduct.setId(product.getId());
            newProduct.setTitle(product.getTitle());
            newProduct.setPrice(product.getPrice());
            newProduct.setImage(product.getImage());
            newProduct.setCategory(product.getCategory());
            newProduct.setDescription(product.getDescription());
            ProductResponseDto.add(newProduct);
        }

        ResponseDto.setAllProducts(ProductResponseDto);
        return ResponseDto;
    }


}
