package com.security.xo;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class XoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XoApplication.class, args);

	}

}
