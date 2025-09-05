package vn.com.mb.ai.competitor.common.web.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.com.mb.ai.competitor.common.web.utils.InputControlModule;

/**
 * @author hungdp
 */
@Configuration
public class SpringBeanConfiguration {
    @Bean
    public InputControlModule inputControlModule() {
        return new InputControlModule();
    }
}
