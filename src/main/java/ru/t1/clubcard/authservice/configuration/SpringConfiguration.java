package ru.t1.clubcard.authservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class SpringConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(MappingJackson2HttpMessageConverter.class::isInstance)
                .findFirst()
                .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(UTF_8));
    }
}
