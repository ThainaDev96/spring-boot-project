package full_stack.spring_boot_project.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ServicoGovResponse(Servico resposta, String mensagem, Boolean hasErro) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Servico(
            String id,
            String nome,
            String sigla,
            String descricao,
            String gratuito,
            Boolean servicoDigital,
            String linkServicoDigital,
            String contato,
            Orgao orgao,
            Solicitantes solicitantes,
            List<Etapa> etapas,
            List<ServicoRelacionado> servicosRelacionados,
            String url) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Orgao(String nomeOrgao) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Solicitantes(List<Solicitante> solicitante) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Solicitante(String tipo, String requisitos) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Etapa(String titulo, String descricao) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ServicoRelacionado(String id, String nome) {
    }
}