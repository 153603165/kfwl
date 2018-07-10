package com.kfwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 項目启动类
 */
@EnableSwagger2
@SpringBootApplication
public class KfwlApplication {
	public static void main(String[] args) {
		SpringApplication.run(KfwlApplication.class, args);
	}
}