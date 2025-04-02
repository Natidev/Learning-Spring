package com.security.xo;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

import static  org.assertj.core.api.Assertions.assertThat;
import javax.crypto.SecretKey;

@SpringBootTest
public class KeyGeneratorTest {
    @Autowired
    public RestTemplate template;
    @Test
    public void generatekey(){
        SecretKey jwtKey= Jwts
                .SIG
                .HS512
                .key().build();
        //System.out.println(DatatypeConverter.printHexBinary(jwtKey.getEncoded()));;
        assertThat(3).isLessThan(10);
    }

}
