package com.security.xo;

import com.security.xo.type.PostUserDetail;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.*;
@ActiveProfiles("test")
class ApplicationTests {

RestTemplate template=new RestTemplate();
	Logger logger= LoggerFactory.getLogger(ApplicationTests.class);
	String url="http://localhost:8080/";
	String token= "eyJhbGciOiJIUzUxMiJ9.eyJtc2ciOiJwbHMgd29yayIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImlhdCI6MTc0MzYxNDI3MiwiZXhwIjoxNzQzNjE3ODcyLCJzdWIiOiJicm9jb2xsaSJ9.faA5e6rzteLkBParXsWD8m07JqwEBHzKciDH-c17M01mcWWvSr9gnmnN8y94NxBVfz0xKDYNJ1iSvkqijFr4ew";
	@Test
	void contextLoads() {

	}

	@Test
	void createUser(){
		var resp=template.postForEntity(
				"http://localhost:8080/register",
				new PostUserDetail("testuser","testpassword"),
				String.class
		);
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		logger.info(resp.getHeaders().toString());
	}
	@Test
	void authenticateUser(){
		var resp=template.postForEntity(
				"http://localhost:8080/login",
				new PostUserDetail("testuser","testpassword"),
				String.class
		);
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.FOUND);
	}
	@Test
	void useProtectedRoute(){
try{
	var resp=template.getForEntity(
			url,
			String.class
	);
}catch(
		Exception e
){
	assert true;
}

	}
	@Test
	void useProtectedRouteWithToken(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
		assertThat(response.getBody()).isEqualTo("Hello world")	;
	}
	@Test
	void crossSiteScriptingProtectionTest(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
		var c=response.getHeaders().get("X-XSS-Protection");
        assert c != null;
        assert c.getFirst() != null;
		assertThat(c.getFirst()).isEqualTo("1; mode=block");
	}
}
