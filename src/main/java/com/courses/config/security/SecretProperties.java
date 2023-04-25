package com.courses.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("secret")
@AllArgsConstructor
@Getter
public class SecretProperties {
    private String key;
}
