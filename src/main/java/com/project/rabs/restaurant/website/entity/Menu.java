package com.project.rabs.restaurant.website.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "menu")
public class Menu {
    @Id
    private String itemId;
    private String itemName;
    private double price;
    private int stockCount;
}
