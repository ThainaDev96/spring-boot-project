package full_stack.spring_boot_project.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import full_stack.spring_boot_project.repositories.UsuarioRepository;

// Busca o usuário no banco para o Spring Security conferir o login
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Recebe o e-mail e devolve o usuário com a senha em hash
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}
