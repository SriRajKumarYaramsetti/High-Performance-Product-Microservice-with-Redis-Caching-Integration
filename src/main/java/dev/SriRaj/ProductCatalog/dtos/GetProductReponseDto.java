package dev.SriRaj.ProductCatalog.dtos;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetProductReponseDto implements Serializable {
    private Long id;
    private String title;
    private String category;
    private String description;
    private String image;
    private double price;
}
