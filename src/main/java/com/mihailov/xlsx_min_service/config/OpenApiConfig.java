package com.mihailov.xlsx_min_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("XLSX Analyzer API")
                        .description("REST API to find Nth minimum number in Excel file")
                        .version("1.0.0")
                );
    }
}
