package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    ProductRepository productRepository;

    String productName;

    String priceGroup;
    String pricePart1;
    double productPrice;

    String priceBeforePromotionGroup;
    String promotionPart1;
    double priceBeforePromotion;

    String productUrl;

    @Test
    void addProduct() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Bestia\\IdeaProjects\\Resources\\chromedriver.exe");

        WebDriver findDriver = new ChromeDriver();

        findDriver.get("https://www.emag.ro/laptopuri/c?ref=bc");
        List<WebElement> products = findDriver.findElements(By.cssSelector("#card_grid > div.card-item"));

        for (WebElement prod : products) {
            try {
                productName = prod.findElement(By.tagName("h2")).getText();

                priceGroup = prod.findElement(By.cssSelector("p.product-new-price")).getText();
                pricePart1 = priceGroup.replace("Lei", "").replace(".", "").replace("de la", "").trim();
                productPrice = Double.parseDouble(pricePart1) / 100;

                if (prod.findElements(By.cssSelector("p.product-old-price > s")).size() > 0) {
                    priceBeforePromotionGroup = prod.findElement(By.cssSelector("p.product-old-price > s")).getText();
                    promotionPart1 = priceBeforePromotionGroup.replace("Lei", "").replace(".", "").trim();
                    priceBeforePromotion = Double.parseDouble(promotionPart1) / 100;
                }

                productUrl = prod.findElement(By.cssSelector("div.card-section-mid > h2 > a")).getAttribute("href");

                // Test variables

                System.out.println("Number of products = " + products.size());
                System.out.println("Product name = " + productName);
                System.out.println("Price before promotion group = " + priceBeforePromotionGroup);
                System.out.println("Price before promotion = " + priceBeforePromotion);
                System.out.println("Price group = " + priceGroup);
                System.out.println("Product price = " + productPrice);
                System.out.println("Product URL = " + productUrl);
                System.out.println("Product added");
                System.out.println("");

                Product product = new Product();
                product.setProductName(productName);
                product.setProductPrice(productPrice);
                product.setPriceBeforePromotion(priceBeforePromotion);
                product.setProductUrl(productUrl);
                product.setLastUpdated(Instant.now());
                productRepository.save(product);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}




