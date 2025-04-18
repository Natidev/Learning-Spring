package com.security.xo.controller;

import com.security.xo.services.JwtService;
import com.security.xo.services.UserService;
import com.security.xo.type.PostUserDetail;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@CrossOrigin
public class WebController {


    JwtService jwt;
    JdbcTemplate jdbc;
    UserService userService;
    AuthenticationManager authenticationManager;
    UserDetailsService userDetailsService;
    private static final PolicyFactory SANITIZER = Sanitizers.FORMATTING
            .and(Sanitizers.LINKS)
            .and(Sanitizers.BLOCKS)
            .and(Sanitizers.IMAGES)
            .and(Sanitizers.STYLES);
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
        public void setJwt(JwtService jwt) {
            this.jwt = jwt;
        }

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
    public ResponseEntity<String> Hello(
            @RequestParam(defaultValue = "") String name
    ){
        return ResponseEntity.ok("Hello, "+SANITIZER.sanitize(name));
    }


    @PostMapping("/register")
    public ResponseEntity<Void> SignUp(
            @RequestBody PostUserDetail detail,
            HttpServletResponse response,
            HttpServletRequest request
    ){

//        System.out.println(detail.username());
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
    public ResponseEntity<String> authenticate(
            @RequestBody PostUserDetail detail
    ){
        Authentication auth=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(detail.username(),detail.password()));
        if(auth.isAuthenticated()){
            String cookie=ResponseCookie
                    .from("token", jwt.generateToken(userDetailsService.loadUserByUsername(detail.username())))
                    .httpOnly(true)
                    //.secure(true)
                    .build()
                    .toString();
            return ResponseEntity.noContent()
                    .header("Set-Cookie",cookie)
                    .build();
        }
        else throw new  UsernameNotFoundException("couldn't find user");
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
