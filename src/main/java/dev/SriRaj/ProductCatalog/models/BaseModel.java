package dev.SriRaj.ProductCatalog.models;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.function.LongFunction;

@MappedSuperclass
@Getter

public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",columnDefinition = "bigint",nullable = false)
    private Long id;

}
