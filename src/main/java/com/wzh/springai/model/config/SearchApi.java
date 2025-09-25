package com.wzh.springai.model.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "search-api")
@Data
public class SearchApi {

    private String apiKey;

    private String apiUrl;
}
