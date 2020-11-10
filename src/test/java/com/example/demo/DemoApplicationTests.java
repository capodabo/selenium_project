package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void addProduct() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Bestia\\IdeaProjects\\Resources\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.emag.ro/cafea-boabe-lavazza-super-crema-1-kg-8000070042025/pd/DPNCZZBBM/");

		String name = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[1]/h1")).getText();
		String priceGroup = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[2]/div[2]/div/div/div[2]/form/div[1]/div[1]/div/div/p")).getText();

		String sup = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[2]/div[2]/div/div/div[2]/form/div[1]/div[1]/div/div/p/sup")).getText();
		String currency = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[2]/div[2]/div/div/div[2]/form/div[1]/div[1]/div/div/p/span")).getText();

		String part1 = priceGroup.replace(sup, "").replace(currency, "").trim();
		double price = Double.parseDouble(part1 + "." + sup);


		// another way to do it

/*		String name = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[1]/h1")).getText();
		String currency = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[2]/div[2]/div/div/div[2]/form/div[1]/div[1]/div/div/p/span")).getText();
		String priceGroup[] = driver.findElement(new By.ByXPath("//*[@id=\"main-container\"]/section[1]/div/div[2]/div[2]/div/div/div[2]/form/div[1]/div[1]/div/div/p")).getText().split(" ");
		double price = Double.valueOf(priceGroup[0])/100;*/


		// Test if variables are picked up and formatted correctly

/*		System.out.println("Product = " + name);
		System.out.println("Price = " + price);
		System.out.println("Currency = " + currency);*/

		Product product = new Product();
		product.setProductName(name);
		product.setPrice(price);
		product.setCurrency(currency);
		productRepository.save(product);


	}

}
