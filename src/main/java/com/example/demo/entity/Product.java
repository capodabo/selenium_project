package com.example.demo.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "product_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)

    private Long id;

    private String productName;

    private double productPrice;

    private double priceBeforePromotion;

    private String productUrl;

    private Instant lastUpdated;

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getPriceBeforePromotion() {
        return priceBeforePromotion;
    }

    public void setPriceBeforePromotion(double priceBeforePromotion) {
        this.priceBeforePromotion = priceBeforePromotion;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", priceBeforePromotion ='" + priceBeforePromotion + '\'' +
                ", productUrl ='" + productUrl + '\'' +
                '}';
    }
}
