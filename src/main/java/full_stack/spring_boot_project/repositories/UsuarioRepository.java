package full_stack.spring_boot_project.repositories;

import full_stack.spring_boot_project.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}

// CRUD do banco:
// C (Create, criar)     -> save(usuario) com usuário novo
// R (Read, ler)         -> findById, findAll, e os seus findByEmail e existsByEmail
// U (Update, atualizar) -> save(usuario) com um usuário que já existe
// D (Delete, apagar)    -> delete e deleteById
