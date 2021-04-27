package com.oauth.auth.config;

import com.oauth.auth.helper.JwtUtil;
import com.oauth.auth.service.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //get jwt header
        //bearer start
        // validate

        String reqTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (reqTokenHeader != null && reqTokenHeader.startsWith("Bearer ")) {

            jwtToken = reqTokenHeader.substring(7);

            try {

                username = this.jwtUtil.extractUsername(jwtToken);

            } catch (Exception e) {

            }

            UserDetails userDetails = this.customerUserDetailService.loadUserByUsername(username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());


                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            } else {
                System.out.println("Token is no validate");
            }

        }
        filterChain.doFilter(request, response);

    }
}
