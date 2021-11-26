package com.dietrich.psiu.handler;

import com.dietrich.psiu.model.organization.Organization;
import com.dietrich.psiu.model.user.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final HttpSession httpSession;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    @Autowired
    public JwtAuthFilter(HttpSession httpSession,
                         UserDetailsServiceImpl userDetailsService,
                         ModelMapper modelMapper) {
        this.httpSession = httpSession;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    @Value("${jwt.key}")
    private static String SECRET = "test";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(checkJwtToken(request)) {
                Claims claims = validateToken(request);
                if(claims.get("authorities") != null && claims.get("user") != null) {
//                if(claims.get("authorities") != null) {
                    setUpAuth(claims);
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

    private void setUpAuth(Claims claims) throws JsonProcessingException {
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) claims.get("user");
        MyUserDetails userDetails = createUserDetails(map);
        List<String> authorities = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private MyUserDetails createUserDetails(LinkedHashMap<String, Object> map) {
        if(map.containsKey("master")) {
            Admin admin = new Admin();
            create(admin, map);
            admin.setMaster((Boolean) map.get("master"));
            admin.setOrganization(createOrganization((LinkedHashMap<String, Object>) map.get("organization")));
            return new MyUserDetails(admin);
        } else if(map.containsKey("organization")) {
            Volunteer volunteer = new Volunteer();
            create(volunteer, map);
            return new MyUserDetails(volunteer);
        } else {
            User user = new User();
            create(user, map);
            return new MyUserDetails(user);
        }
    }

    private Organization createOrganization(LinkedHashMap<String, Object> map) {
        Organization organization = new Organization();
        organization.setId(Long.valueOf((Integer) map.get("id")));
        organization.setName((String) map.get("name"));
        organization.setEmail((String) map.get("email"));
        organization.setActive((Boolean) map.get("active"));
        return organization;
    }

    private void create(Person person, LinkedHashMap<String, Object> map) {
       person.setId(Long.valueOf((Integer) map.get("id")));
       person.setName((String) map.get("name"));
       person.setEmail((String) map.get("email"));
       person.setRoles(new HashSet<>(((List<Role>) map.get("roles"))));
       person.setActive((Boolean) map.get("active"));
    }

    private boolean checkJwtToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        return header != null && header.startsWith(PREFIX);
    }
}
