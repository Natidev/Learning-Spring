package com.security.xo;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class KeyGeneratorTest {
    @Test
    public void generatekey(){
        SecretKey jwtKey= Jwts
                .SIG
                .HS512
                .key().build();
        System.out.println(DatatypeConverter.printHexBinary(jwtKey.getEncoded()));
    }
}
