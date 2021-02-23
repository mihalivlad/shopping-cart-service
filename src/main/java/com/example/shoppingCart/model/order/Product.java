package com.example.shoppingCart.model.order;

import java.io.Serializable;

public class Product implements Serializable {
    private Long id;
    private Double price;

    public Product(){

    }

    public Product(Long id, Double price) {
        this.id = id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
