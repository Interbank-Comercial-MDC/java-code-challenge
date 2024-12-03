package com.prueba.crea.msgestionatransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.prueba.crea.msgestionatransaction")
public class MsGestionaTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestionaTransactionApplication.class, args);
	}

}
