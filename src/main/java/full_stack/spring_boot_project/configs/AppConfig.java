package full_stack.spring_boot_project.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Serve para chamar outras APIs
import org.springframework.web.client.RestClient;

// @configuration: diz que a classe contém configurações e deve ser lida quando a aplicação iniciar
@Configuration
public class AppConfig {

    // @Bean: Ensina o Spring a como criar o objeto.
    // @Value: Busca um valor no application.properties e coloca no parâmetro
    @Bean
    public RestClient servicosGovRestClient(@Value("${servicos.base-url}") String baseUrl) {
        return RestClient.create(baseUrl);
    }
}