package ro.theredpoint.shopagent.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.theredpoint.shopagent.domain.Product;
import ro.theredpoint.shopagent.service.ProductService;
import ro.theredpoint.shopagent.service.SecurityService;

/**
 * @author Radu DELIU
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	@Autowired
	private SecurityService securityService;  
	
	private Product randomProduct() {
		Product product = new Product();
		
		product.setName("Product " + RANDOM.nextInt(100));
		product.setPicture(getRandomImage());
		product.setHasStock(RANDOM.nextBoolean());
		product.setPrice(new BigDecimal(RANDOM.nextDouble() * 100).setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		if ((product.hasStock) && (securityService.isAgent())) {
			product.setStock(RANDOM.nextInt(100));
		}
		
		return product;
	}

	public List<Product> getProducts() {
		
		List<Product> products = new ArrayList<Product>();
		
		for (int i = 0; i < 10; i++) {
			products.add(randomProduct());
		}
		
		return products;
	}

	private String getRandomImage() {
		String picture = "";
		int image = RANDOM.nextInt(20);
		switch(image) {
			case 0: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/I/M/IMG_8717.JPG"; break;
			case 1: picture = "http://www.ikea.com/PIAimages/0151521_PE309574_S3.JPG"; break;
			case 2: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1018300.jpg"; break;
			case 3: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/10347_large.jpg"; break;
			case 4: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1002051.jpg"; break;
			case 5: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1003367_1.jpg"; break;
			case 6: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1027681.jpg"; break;
			case 7: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1024332_1.jpg"; break;
			case 8: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1018502.jpg"; break;
			case 9: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/3/4/34112.jpg"; break;
			case 10: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1019117_2.jpg"; break;
			case 12: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1032638.jpg"; break;
			case 13: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/6/0/6000284_1.jpg"; break;
			case 14: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/6/0/6000292.jpg"; break;
			case 15: picture = "http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/5/0/5008423.jpg"; break;
			case 16: picture = "http://www.ikea.com/PIAimages/0243920_PE383208_S3.JPG"; break;
			case 17: picture = "http://www.ikea.com/PIAimages/0164021_PE319126_S3.JPG"; break;
			case 18: picture = "http://www.ikea.com/PIAimages/0150893_PE308939_S3.JPG"; break;
			case 19: picture = "http://www.ikea.com/PIAimages/0186630_PE338811_S3.JPG"; break;
			default: picture = "http://www.ikea.com/PIAimages/57633_PE163233_S3.JPG";
		}
		return picture;
	}
}