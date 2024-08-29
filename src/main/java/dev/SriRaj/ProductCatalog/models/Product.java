package dev.SriRaj.ProductCatalog.models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseModel{
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;

}
