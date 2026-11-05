package full_stack.spring_boot_project.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProgramaService {

    private final RestClient restClient;

    public ProgramaService(RestClient servicosGovRestClient) {
        this.restClient = servicosGovRestClient;
    }

    public String buscarServico(int id) {
        return restClient.get()
                .uri("/servicos/{id}", id)
                .retrieve()
                .body(String.class);
    }
}