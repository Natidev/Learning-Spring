package com.security.xo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class XoApplicationTests {

	TestRestTemplate template=new TestRestTemplate();
	@Test
	void contextLoads() {
	}
	@Test
	void globalAddressTest(){
		ResponseEntity<String> response=template.getForEntity("http://localhost:8080",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

}
