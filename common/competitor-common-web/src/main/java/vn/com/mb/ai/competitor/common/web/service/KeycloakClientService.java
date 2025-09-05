package vn.com.mb.ai.competitor.common.web.service;

import org.keycloak.admin.client.Keycloak;

/**
 * @author hungdp
 */
public interface KeycloakClientService {
    Keycloak getServiceAccount();

    String getServiceAccountAccessToken(boolean includedBearer);

    String getRealm();

    String getClientId();
}
