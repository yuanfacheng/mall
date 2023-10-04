package com.macro.mall.demo;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.mall.model.PmsProduct;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MallDemoApplicationTests {
	private Logger logger = LoggerFactory.getLogger(MallDemoApplicationTests.class);
	@Test
	public void contextLoads() {
	}

	@Test
	public void testLogStash() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PmsProduct product = new PmsProduct();
		product.setId(1L);
		product.setName("小米手机");
		product.setBrandName("小米");
		logger.info(mapper.writeValueAsString(product));
		logger.error(mapper.writeValueAsString(product));
	}


	@Test
	public void yyy() throws Exception {
		ArrayList<String> list = new ArrayList<>();
		for(String str:list){
			System.out.println("11111");
			System.out.println(str);
		}
	}


	@Test
	public void kkk() {

		try{

			try{
				System.out.println("1111");
				int i = 1/0;
			}catch (Exception e){
				System.out.println("222222");
				throw  new OcrException();
			}
			System.out.println("3333333");
		}catch (OcrException e){
			System.out.println("4444444");
		}



	}

}
