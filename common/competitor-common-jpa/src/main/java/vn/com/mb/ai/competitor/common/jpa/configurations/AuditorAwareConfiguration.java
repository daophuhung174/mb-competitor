package vn.com.mb.ai.competitor.common.jpa.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import vn.com.mb.ai.competitor.common.jpa.entity.AuditorAwareImpl;

/**
 * @author hungdp
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareConfiguration {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
