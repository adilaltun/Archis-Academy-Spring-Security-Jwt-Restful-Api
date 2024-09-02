package com.works.archisproject.security;

import com.works.archisproject.service.impl.JwtService;
import com.works.archisproject.service.impl.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     *
     * @apiNote Bu method gelen request içerisindeki token'ı validate edecek.
     *
     * @code String authHeader = request.getHeader("Authorization") -> header'dan Authorization'ı aldık.
     * @code if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) -> email boş değil ve getAuthentication boş ise bizim bir token üretmemiz lazım
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        try {

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                email = jwtService.extractUser(token);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userService.loadUserByUsername(email);
                log.info("user loaded " + user);
                if (jwtService.validateToken(token, user)) {
                    log.info("token validated " + token);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException exception) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(exception.getMessage());
            log.error(exception.getMessage());
        }

    }
}
