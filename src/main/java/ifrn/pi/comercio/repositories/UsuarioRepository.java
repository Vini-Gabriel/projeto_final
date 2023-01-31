package ifrn.pi.comercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.pi.comercio.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByEmail(String email);
}
