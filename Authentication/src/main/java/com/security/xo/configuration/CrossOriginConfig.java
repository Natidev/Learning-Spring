package com.security.xo.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Enumeration;
import java.util.List;
@Component
public class CrossOriginConfig implements CorsConfigurationSource {

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration cors=new CorsConfiguration();
        cors
                .setAllowedOrigins(List.of("http://localhost:5173"));
        cors
                .setAllowedMethods(List.of("POST","GET"));

       Enumeration<String> a=request.getHeaderNames();
       var b=a.asIterator();
       while(b.hasNext())
           System.out.println(b.next());

        return cors;
    }


}
