package full_stack.spring_boot_project.services;

import full_stack.spring_boot_project.dtos.CadastroRequest;
import full_stack.spring_boot_project.exceptions.EmailJaCadastradoException;
import full_stack.spring_boot_project.models.Usuario;
import full_stack.spring_boot_project.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Regras do cadastro de usuário.
@Service
public class UsuarioService {

    // Salva e busca usuários no banco.
    private final UsuarioRepository usuarioRepository;
    // Transforma a senha em hash (código embaralhado).
    private final PasswordEncoder passwordEncoder;

    // O Spring entrega as duas peças aqui.
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(CadastroRequest request) {
        // Se o e-mail já existe, recusa.
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailJaCadastradoException("Já existe uma conta com este e-mail.");
        }

        // Monta o usuário. A senha é salva como hash, nunca como foi digitada.
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));

        // Salva no banco e devolve o usuário com o id gerado.
        return usuarioRepository.save(usuario);
    }
    // Busca o usuário pelo e-mail. Usado depois que o login dá certo
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));
    }
}
