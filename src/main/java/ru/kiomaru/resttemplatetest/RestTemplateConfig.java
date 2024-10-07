package ru.kiomaru.resttemplatetest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


import java.net.CookieStore;

@Configuration
public class RestTemplateConfig {



    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
