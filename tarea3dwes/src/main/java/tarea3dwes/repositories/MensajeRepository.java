package tarea3dwes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tarea3dwes.modelo.Ejemplar;
import tarea3dwes.modelo.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository <Mensaje,Long>{
	List<Mensaje> findByEjemplar(Ejemplar ejemplar);

	
}
