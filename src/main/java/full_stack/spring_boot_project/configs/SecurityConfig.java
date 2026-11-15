package full_stack.spring_boot_project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

// Classe de configuração: o Spring lê ao ligar e executa os métodos @Bean.
@Configuration
public class SecurityConfig {

    // @Bean: o Spring guarda o objeto devolvido e o entrega a quem pedir um PasswordEncoder.
    // BCrypt: faz o hash da senha, um código embaralhado que não volta ao original.
    // encode(senha) gera o hash para salvar. matches(digitada, hash) confere no login.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Regras de acesso: quem pode entrar em cada rota.
    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        http
                // Desativa a proteção CSRF por enquanto. Ela exige um token extra em todo POST,
                // o que bloquearia o cadastro.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(regras -> regras

                        // Cadastro e login: qualquer pessoa pode acessar.
                        .requestMatchers("/api/auth/**").permitAll()

                        // Todo o resto: só quem estiver logado.
                        .anyRequest().authenticated()
                );
        return http.build();
    }
    // Entrega o conferidor de login para usarmos no controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}