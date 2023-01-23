package ifrn.pi.comercio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.pi.comercio.models.Convidado;
import ifrn.pi.comercio.models.Evento;

public interface ConvidadoRepository extends JpaRepository<Convidado, Long>{

	List<Convidado> findByEvento(Evento evento);
	
}
