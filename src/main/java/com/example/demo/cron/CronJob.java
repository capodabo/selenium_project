package com.example.demo.cron;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Component
public class CronJob {

    @Autowired
    ProductRepository productRepository;

    Long laptopId;

    String productName;

    String priceGroup;
    String pricePart1;
    double productPrice;

    String priceBeforePromotionGroup;
    String promotionPart1;
    double priceBeforePromotion;

    String productUrl;

    // second, minute, hour, day, month, week day
    // @Scheduled(cron = "0 1 1 * * ?")

    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    public void sortProducts() {
        List<Product> result = productRepository.sortProducts();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Bestia\\IdeaProjects\\Resources\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        for (Product laptop : result) {

            try {
                driver.get(laptop.getProductUrl());

                laptopId = laptop.getId();

                productName = driver.findElement(By.cssSelector("#main-container > section:nth-child(1) > div > div.page-header.has-subtitle-info > h1")).getText();

                priceGroup = driver.findElement(By.cssSelector("#main-container > section:nth-child(1) > div > div:nth-child(2) > div.col-sm-5.col-md-7.col-lg-7 > div > div > div.col-sm-12.col-md-6.col-lg-5 > form > div.product-highlight.product-page-pricing > div:nth-child(1) > div > div.w-50.mrg-rgt-xs > p.product-new-price")).getText();
                pricePart1 = priceGroup.replace("Lei", "").replace(".", "").replace("de la", "").trim();
                productPrice = Double.parseDouble(pricePart1) / 100;

                productUrl = laptop.getProductUrl();

                if (driver.findElements(By.cssSelector("#main-container > section:nth-child(1) > div > div:nth-child(2) > div.col-sm-5.col-md-7.col-lg-7 > div > div > div.col-sm-12.col-md-6.col-lg-5 > form > div.product-highlight.product-page-pricing > div:nth-child(1) > div > div.w-50.mrg-rgt-xs > p.product-old-price > s")).size() > 0) {
                    priceBeforePromotionGroup = driver.findElement(By.cssSelector("#main-container > section:nth-child(1) > div > div:nth-child(2) > div.col-sm-5.col-md-7.col-lg-7 > div > div > div.col-sm-12.col-md-6.col-lg-5 > form > div.product-highlight.product-page-pricing > div:nth-child(1) > div > div.w-50.mrg-rgt-xs > p.product-old-price > s")).getText();
                    promotionPart1 = priceBeforePromotionGroup.replace("Lei", "").replace(".", "").trim();
                    priceBeforePromotion = Double.parseDouble(promotionPart1) / 100;
                }
                productUrl = laptop.getProductUrl();

                // Test variables
                System.out.println("");
                System.out.println("Product ID = " + laptopId);
                System.out.println("Product name = " + productName);
                System.out.println("Price before promotion group = " + priceBeforePromotionGroup);
                System.out.println("Price before promotion = " + priceBeforePromotion);
                System.out.println("Price group = " + priceGroup);
                System.out.println("Product price = " + productPrice);
                System.out.println("Product URL = " + productUrl);
                System.out.println("");

                laptop.setProductName(productName);
                laptop.setProductPrice(productPrice);
                laptop.setPriceBeforePromotion(priceBeforePromotion);
                laptop.setLastUpdated(Instant.now());
//                productRepository.save(laptop);
                productRepository.updateProduct(productName, productPrice, priceBeforePromotion, laptopId);


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Product ID = " + laptop.getId());
                System.out.println("Product URL = " + laptop.getProductUrl());
                System.out.println("Product is no longer available or there was a problem retrieving this product");
                System.out.println();

            }
        }
    }

}
