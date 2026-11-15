package full_stack.spring_boot_project.dtos;

// Dados do usuário que o back devolve ao front. Não tem a senha.
public record UsuarioResponse(Long id, String nome, String email) {
}
