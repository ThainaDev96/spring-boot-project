package full_stack.spring_boot_project.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient servicosGovRestClient(@Value("${servicos.base-url}") String baseUrl) {
        return RestClient.create(baseUrl);
    }
}