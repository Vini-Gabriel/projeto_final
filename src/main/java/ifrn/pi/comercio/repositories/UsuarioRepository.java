package ifrn.pi.comercio.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ifrn.pi.comercio.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
	
	Optional<Usuario> findByNome(String nome);
}
