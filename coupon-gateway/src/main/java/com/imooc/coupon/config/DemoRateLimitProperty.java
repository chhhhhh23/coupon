package com.imooc.coupon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author cyw
 */
@Data
@ConfigurationProperties(prefix = "rate-limit")
public class DemoRateLimitProperty {
    private Boolean enabled;
    private Map<String,String> limitConfig;

}
