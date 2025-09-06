package com.mbbank.competitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableAsync
@EnableCaching
@EnableWebSecurity
@SpringBootApplication(
        exclude = {
                ThymeleafAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, // 🔥 Thêm dòng này
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class
        },
        scanBasePackages = {"com.mbbank.competitor"}
)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class CompetitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompetitorApplication.class, args);
	}

}
