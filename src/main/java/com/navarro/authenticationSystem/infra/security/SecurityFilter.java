package com.navarro.authenticationSystem.infra.security;

import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.repository.UserRepository;
import com.navarro.authenticationSystem.serviceImpl.TokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenServiceImpl tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenServiceImpl tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = this.tokenService.validateToken(token);

        if(Objects.nonNull(login)) {
            User user = this.userRepository.findByUserName(login).orElseThrow(() -> new NotFoundException("User not found!"));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(Objects.isNull(authHeader)) return null;
        return authHeader.replace("Bearer ", "");
    }
}
