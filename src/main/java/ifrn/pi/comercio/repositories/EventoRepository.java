package ifrn.pi.comercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.pi.comercio.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
