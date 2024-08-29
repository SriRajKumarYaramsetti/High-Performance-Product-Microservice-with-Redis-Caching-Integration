package dev.SriRaj.ProductCatalog.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponseDto {

    private Long id;
    private String title;
    private String category;

    private String description;

    private String image;

    private double price;
}
