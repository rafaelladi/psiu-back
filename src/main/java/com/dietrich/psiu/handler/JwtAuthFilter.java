package com.dietrich.psiu.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final HttpSession httpSession;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtAuthFilter(HttpSession httpSession,
                         UserDetailsServiceImpl userDetailsService) {
        this.httpSession = httpSession;
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.key}")
    private static String SECRET = "test";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(checkJwtToken(request)) {
                Claims claims = validateToken(request);
                String username = claims.getSubject();
                if(claims.get("authorities") != null) {
                    setUpAuth(claims, username);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private Claims validateToken(HttpServletRequest request) throws Exception {
        String header = request.getHeader(HEADER);
        if(!StringUtils.hasText(header))
            throw new Exception("No authorization header!");
        String token = header.replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
    }

    private void setUpAuth(Claims claims, String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        List<String> authorities = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private boolean checkJwtToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        return header != null && header.startsWith(PREFIX);
    }
}
