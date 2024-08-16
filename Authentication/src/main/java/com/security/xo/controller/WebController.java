package com.security.xo.controller;

import com.security.xo.services.UserService;
import com.security.xo.type.PostUserDetail;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@CrossOrigin
public class WebController {

    JdbcTemplate jdbc;
    UserService userService;
    AuthenticationManager authenticationManager;
    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<String> Hello(){
        return ResponseEntity.ok("Hello world");
    }


    @PostMapping("/register")
    public ResponseEntity<Void> SignUp(
            @RequestBody PostUserDetail detail
    ){
        if(userService.userExists(detail.username()))
            return ResponseEntity
                .noContent()
                .build();

        userService.register(detail);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(){
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/prefs/{vard}")
    public ResponseEntity<String> cookies(@PathVariable("vard") String vard, HttpServletRequest request){
        var cookie=ResponseCookie.from("other")
                .value(vard)
                .maxAge(Duration.ofSeconds(600))
                .build();
        Cookie cookie1 = request.getCookies()[0];
        System.out.println(cookie1.getName()+cookie1.getValue());

        return ResponseEntity
                .noContent()
                .header(HttpHeaders.SET_COOKIE,cookie.toString()).build();

    }


}
