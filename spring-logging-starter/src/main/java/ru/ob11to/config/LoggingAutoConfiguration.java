package ru.ob11to.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ob11to.filter.LoggingFilter;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoggingFilter loggingFilter(LoggingProperties loggingProperties) {
        return new LoggingFilter(loggingProperties);
    }
}