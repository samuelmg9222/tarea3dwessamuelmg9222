package tarea3dwes.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tarea3dwes.modelo.Ejemplar;
import tarea3dwes.modelo.Mensaje;
import tarea3dwes.modelo.Persona;
import tarea3dwes.modelo.Planta;

@Repository
public interface MensajeRepository extends JpaRepository <Mensaje,Long>{
	List<Mensaje> findByEjemplar(Ejemplar ejemplar);
	List<Mensaje> findByPersona(Persona persona);
	 List<Mensaje> findByEjemplarIn(List<Ejemplar> ejemplares);
	  List<Mensaje> findByFechahoraBetween(LocalDateTime start, LocalDateTime end);
}
