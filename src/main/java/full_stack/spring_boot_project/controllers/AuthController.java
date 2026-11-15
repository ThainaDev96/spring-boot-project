package full_stack.spring_boot_project.controllers;

import full_stack.spring_boot_project.dtos.CadastroRequest;
import full_stack.spring_boot_project.dtos.UsuarioResponse;
import full_stack.spring_boot_project.models.Usuario;
import full_stack.spring_boot_project.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import full_stack.spring_boot_project.dtos.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

// Recebe os pedidos de cadastro e login que o site envia.
// @RestController: esta classe responde pedidos HTTP e devolve JSON.
// @RequestMapping: todas as rotas daqui começam com /api/auth.
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Quem sabe cadastrar o usuário. O controller só repassa o pedido para ele.
    private final UsuarioService usuarioService;

    // O conferidor que checa e-mail e senha
    private final AuthenticationManager authenticationManager;

    // Guarda na sessão que a pessoa entrou
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    // Rota de cadastro: POST /api/auth/cadastro
    // @PostMapping: responde a POST, usado quando o site envia dados.
    // @ResponseStatus(CREATED): se der certo, responde 201 ("criado").
    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@RequestBody CadastroRequest request) {
        // @RequestBody: pega o JSON que o site enviou e transforma em CadastroRequest.
        // Cadastra o usuário (confere o e-mail, embaralha a senha e salva no banco).
        Usuario usuario = usuarioService.cadastrar(request);

        // Devolve só id, nome e e-mail. A senha nunca volta para o site.
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    // Rota de login: POST /api/auth/login
    @PostMapping("/login")
    public UsuarioResponse login(@RequestBody LoginRequest request,
                                 HttpServletRequest httpRequest,
                                 HttpServletResponse httpResponse) {
        // 1. Confere e-mail e senha. Se estiverem errados, dá erro aqui
        Authentication autenticacao = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

        // 2. Guarda na sessão que a pessoa entrou
        SecurityContext contexto = SecurityContextHolder.createEmptyContext();
        contexto.setAuthentication(autenticacao);
        SecurityContextHolder.setContext(contexto);
        securityContextRepository.saveContext(contexto, httpRequest, httpResponse);

        // 3. Devolve id, nome e e-mail. A senha não volta
        Usuario usuario = usuarioService.buscarPorEmail(request.email());
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    // Rota "quem sou eu": GET /api/auth/me
    // O Principal é quem está logado. Se ninguém entrou, ele vem vazio
    @GetMapping("/me")
    public UsuarioResponse eu(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Você não está logado.");
        }

        // O nome do Principal é o e-mail usado no login
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
