package vn.com.mb.ai.competitor.common.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hungdp
 */
@AutoConfiguration
@ComponentScan("vn.com.mbbank.aicenter.beegen.common.web")
@EnableFeignClients(basePackages = "vn.com.mbbank.aicenter.beegen.common.web.feign")
public class BeeGenWebEnableAutoConfiguration {

}
