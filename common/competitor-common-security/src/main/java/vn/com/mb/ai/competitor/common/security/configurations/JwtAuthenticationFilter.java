package vn.com.mb.ai.competitor.common.security.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @author hungdp
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${keycloak.introspection-url}")
    private String introspectionUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");

            if (isTokenRevoked(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\", error_description=\"Token has been revoked or is inactive.\"");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenRevoked(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> entity = new HttpEntity<>("token=" + accessToken, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(introspectionUrl, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                // Check if the token is active
                return responseBody == null || !(Boolean) responseBody.get("active");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }
}
