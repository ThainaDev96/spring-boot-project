package full_stack.spring_boot_project.dtos;

// Dados que chegam do front quando a pessoa se cadastra (corpo do pedido, em JSON).
// Record: guarda só os dados e já gera os métodos de leitura: nome(), email() e senha().
public record CadastroRequest(String nome, String email, String senha) {
}
