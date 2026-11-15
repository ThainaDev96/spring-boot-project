package full_stack.spring_boot_project.dtos;

// Molde do que o login recebe: só e-mail e senha
public record LoginRequest(String email, String senha) {}
