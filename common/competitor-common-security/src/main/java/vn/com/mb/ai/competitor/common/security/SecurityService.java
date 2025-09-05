package vn.com.mb.ai.competitor.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.com.mb.ai.competitor.common.dto.UserToken;

/**
 * @author hungdp
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SecurityService {
    public Jwt getJwtToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AccessDeniedException("Authentication is null");
        }

        if (!(authentication instanceof JwtAuthenticationToken)) {
            throw new AccessDeniedException("Wrong authentication");
        }
        return ((JwtAuthenticationToken) authentication).getToken();
    }

    public UserToken getCurrentUser() {
        var jwt = getJwtToken();

        return UserToken.builder()
                .username(jwt.getClaim("preferred_username"))
                .token(jwt.getTokenValue())
                .userId(jwt.getSubject())
                .name(jwt.getClaim("name"))
                .clientId(jwt.getClaim("clientId"))
                .sessionState(jwt.getClaim("session_state"))
                .build();
    }
}
