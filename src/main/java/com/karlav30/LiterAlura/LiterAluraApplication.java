package com.karlav30.LiterAlura;

import com.karlav30.LiterAlura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




@SpringBootApplication
public class LiterAluraApplication {

	private static long inicio; // 👉 Visible para todo

	public static void main(String[] args) {
		inicio = System.currentTimeMillis();
		System.out.println("Iniciando app...");

		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(Principal principal) {
		System.out.println("⏱️ Tiempo de carga: " + (System.currentTimeMillis() - inicio) + " ms");

		return args ->  principal.muestraMenu();
	}
}





