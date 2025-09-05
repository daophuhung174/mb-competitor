package vn.com.mb.ai.competitor.common.web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.com.mb.ai.competitor.common.web.service.KeycloakClientService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hungdp
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakClientServiceImpl implements KeycloakClientService {
    @Value("${keycloak.realm}")
    private String REALM;

    @Value("${keycloak.auth-server-url}")
    private String AUTH_SERVER_URL;

    @Value("${keycloak.resource}")
    private String RESOURCE;

    @Value("${keycloak.credentials.secret}")
    private String CREDENTIALS_SECRET;

    private static final Map<String, Keycloak> keycloakMap = new HashMap<>();

    @Override
    public Keycloak getServiceAccount() {
        Keycloak keycloak = keycloakMap.get(REALM);
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .realm(REALM)
                    .serverUrl(AUTH_SERVER_URL)
                    .clientId(RESOURCE)
                    .clientSecret(CREDENTIALS_SECRET)
                    .build();
            keycloakMap.put(REALM, keycloak);
        }
        return keycloak;
    }

    @Override
    public String getServiceAccountAccessToken(boolean includedBearer) {
        return StringUtils.join(
                includedBearer ? "Bearer " : "",
                getServiceAccount().tokenManager().getAccessTokenString()
        );
    }

    @Override
    public String getRealm() {
        return REALM;
    }

    @Override
    public String getClientId() {
        return RESOURCE;
    }
}
