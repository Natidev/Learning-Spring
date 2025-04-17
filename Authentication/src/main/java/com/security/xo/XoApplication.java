package com.security.xo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class XoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XoApplication.class, args);

	}


}
