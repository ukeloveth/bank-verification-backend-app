package com.task.Bank.Verification.Number.Application.security;

import com.task.Bank.Verification.Number.Application.services.BlackListService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BlackListService blackListService;



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get jwtToken from http Request
        String token = getJwtFromRequest(request);
        if (blackListService.tokenExist(token)){
            throw new BadCredentialsException("Token blacklised!");
        }
        // validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
        // get username from token
            String username = jwtTokenProvider.getUsernameFromJwt(token);
            log.info("This is the token {}",token);
            log.info("This is the UserName {}",username);
        // load user associated with the token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // set spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }
        filterChain.doFilter(request, response);

    }
    private  String getJwtFromRequest(HttpServletRequest request){
        var bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            System.out.println(bearerToken.substring(7, bearerToken.length()));
            return bearerToken.substring(7, bearerToken.length());

        }
        return null;
    }

}



