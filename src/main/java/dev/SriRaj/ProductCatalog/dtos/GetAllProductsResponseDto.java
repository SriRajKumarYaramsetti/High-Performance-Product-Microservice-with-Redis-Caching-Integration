package dev.SriRaj.ProductCatalog.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllProductsResponseDto {
    List<GetProductReponseDto> allProducts;

}
