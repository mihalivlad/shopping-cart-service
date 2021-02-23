package com.example.shoppingCart.model.order;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String username;
    private List<Integer> productsId;

    public Order() {

    }

    public Order(String username, List<Integer> productsId) {
        this.username = username;
        this.productsId = productsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<Integer> productsId) {
        this.productsId = productsId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "username='" + username + '\'' +
                ", productsId=" + productsId +
                '}';
    }
}
