package vn.com.mb.ai.competitor.common.jpa.entity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import vn.com.mb.ai.competitor.common.constants.Constants;
import vn.com.mb.ai.competitor.common.security.SecurityService;

import java.util.Optional;

/**
 * @author hungdp
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    private static final Logger log = LoggerFactory.getLogger(AuditorAwareImpl.class);

    SecurityService securityService;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            var userToken = securityService.getCurrentUser();
            return Optional.of(userToken.getUserId());
        } catch (Exception e) {
            log.warn("Gan gia tri createdBy va updatedBy bang SYSTEM", e);
        }
        return Optional.of(Constants.SYSTEM);
    }
}
