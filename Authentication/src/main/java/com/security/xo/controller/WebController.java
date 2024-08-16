package com.security.xo.controller;

import com.security.xo.services.UserService;
import com.security.xo.type.PostUserDetail;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            @RequestBody PostUserDetail detail,
            HttpServletResponse response,
            HttpServletRequest request
    ){
        System.out.println(detail.username());
        if(userService.userExists(detail.username()))
            return ResponseEntity
                .noContent()
                .build();

        userService.register(detail);

        // Add JSESSIONID cookie to the response
        Cookie jsessionidCookie = new Cookie("JSESSIONID", request.getSession(true).getId());
        jsessionidCookie.setHttpOnly(true);
        jsessionidCookie.setPath("/");
        response.addCookie(jsessionidCookie);

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


        return ResponseEntity
                .noContent()
                .header(HttpHeaders.SET_COOKIE,cookie.toString()).build();

    }


}
