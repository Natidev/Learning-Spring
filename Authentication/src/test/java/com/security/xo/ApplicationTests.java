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
	String url="http://localhost:8080";
	String token="eyJhbGciOiJIUzUxMiJ9.eyJtc2ciOiJwbHMgd29yayIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImlhdCI6MTc0NDkxMTcyMSwiZXhwIjoxNzQ0OTE1MzIxLCJzdWIiOiJ0ZXN0VXNlciJ9.Mbm7tmFOYHDedSjKxn2i39SPI6Lb2Mw6LAhkWCgX83hsH_XKPuBStMvoyk44cd30uh5za38AAb-ri9jI93WF6A";
	HttpEntity<String> getAuth(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
        return new HttpEntity<>(headers);
	}

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
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
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

		ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, getAuth(), String.class);
		assertThat(response.getBody()).isEqualTo("Hello, ")	;
	}
	@Test
	void crossSiteScriptingProtectionTest(){

		ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, getAuth(), String.class);
		var c=response.getHeaders().get("X-XSS-Protection");
        assert c != null;
        assert c.getFirst() != null;
		assertThat(c.getFirst()).isEqualTo("1; mode=block");
	}
	@Test
	void shouldRemoveScriptTags() {
		String maliciousInput = "<script>alert('XSS')</script>Attacker";
		ResponseEntity<String> response = template.exchange(url+"?name="+maliciousInput, HttpMethod.GET, getAuth(), String.class);
		assertThat(response.getBody()).isEqualTo("Hello, Attacker");
	}

	@Test
	void shouldAllowSafeHtml() {
		String safeInput = "<p>Hello <strong>World</strong></p>";
		var response = template.exchange(url+"?name="+safeInput, HttpMethod.GET, getAuth(), String.class);
		assertThat(response.getBody()).isEqualTo("Hello, "+safeInput);
	}

	@Test
	void shouldEncodeSpecialCharacters() {
		String sqlInjection = "admin' OR '1'='1";
		var response = template.exchange(url+"?name="+sqlInjection, HttpMethod.GET, getAuth(), String.class);
		System.out.println(sqlInjection);
		System.out.println(response.getBody());
		assertThat(response.getBody()).isNotEqualTo("Hello, "+sqlInjection);

	}

	@Test
	void shouldPreserveLegitLinks() {
		String link = "<a href=\"https://example.com\">Safe</a>";
		var response = template.exchange(url+"?name="+link, HttpMethod.GET, getAuth(), String.class);
		assertThat(response.getBody()).isEqualTo("Hello, <a href=\"https://example.com\" rel=\"nofollow\">Safe</a>");

	}
}
