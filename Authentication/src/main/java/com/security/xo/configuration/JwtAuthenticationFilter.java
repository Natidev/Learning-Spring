package com.security.xo.configuration;

import com.security.xo.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtService jwtService;
    UserDetailsService userDetailsService;
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equals("POST")){
            filterChain.doFilter(request,response);
            return;
        }
String authHeader;
        if(request.getCookies()!=null){
            filterChain.doFilter(request,response);
            return;
        }
        var cookies=Arrays.stream(request.getCookies()).filter(c->c.getName().equals("token")).toList();
        if (cookies.size()==1)
            authHeader=cookies.getFirst().getValue();
        else{
            filterChain.doFilter(request,response);
            return;
        }
        String username=jwtService.getUserName(authHeader);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user=userDetailsService.loadUserByUsername(username);
            if(user!=null && jwtService.checkTokenValidity(authHeader)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

    }
    private boolean containsToken(List<Cookie> cookies){
        if(cookies==null)
            return false;
        var result=cookies.stream().filter(c->c.getName().equals("token")).toList();
        return result.size()==1;
    }
}
