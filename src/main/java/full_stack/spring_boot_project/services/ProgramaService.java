package full_stack.spring_boot_project.services;

import full_stack.spring_boot_project.dtos.ProgramaResumo;
import full_stack.spring_boot_project.dtos.ServicoGovResponse;
import full_stack.spring_boot_project.models.ProgramaSocial;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramaService {

    private final RestClient restClient;

    public ProgramaService(RestClient servicosGovRestClient) {
        this.restClient = servicosGovRestClient;
    }

    public ServicoGovResponse.Servico buscar(ProgramaSocial programa) {
        ServicoGovResponse resposta = restClient.get()
                .uri("/servicos/{id}", programa.getId())
                .retrieve()
                .body(ServicoGovResponse.class);

        if (resposta == null || resposta.resposta() == null || Boolean.TRUE.equals(resposta.hasErro())) {
            throw new IllegalStateException("O gov.br não devolveu dados para " + programa.getNome());
        }

        return resposta.resposta();
    }
    public List<ProgramaResumo> listar() {
        List<ProgramaResumo> lista = new ArrayList<>();
        for (ProgramaSocial programa : ProgramaSocial.values()) {
            lista.add(new ProgramaResumo(programa.getCodigo(), programa.getNome()));
        }
        return lista;
    }
}