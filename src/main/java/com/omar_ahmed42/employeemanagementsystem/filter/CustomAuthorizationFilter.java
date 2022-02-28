package com.omar_ahmed42.employeemanagementsystem.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omar_ahmed42.employeemanagementsystem.utils.jwt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/auth/login")){
            filterChain.doFilter(request, response);
        } else{
          String authorizationHeader = request.getHeader(AUTHORIZATION);
          if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){
              try{
                  String token = authorizationHeader.substring("Bearer ".length());

                  Map<?, ?> decodedTokenMap = jwt.decodeHMAC256(token);
                  String username = (String) decodedTokenMap.get("username");
                  Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) decodedTokenMap.get("authorities");

                  UsernamePasswordAuthenticationToken authenticationToken =
                          new UsernamePasswordAuthenticationToken(username, null, authorities);
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  filterChain.doFilter(request, response);

              }catch (Exception e){
                  response.setStatus(FORBIDDEN.value());

                  Map<String, String> error = new HashMap<>();
                  error.put("error_message", e.getMessage());
                  response.setContentType(APPLICATION_JSON_VALUE);
                  new ObjectMapper().writeValue(response.getOutputStream(), error);

              }

          } else {
              filterChain.doFilter(request, response);
          }
        }
    }
}
