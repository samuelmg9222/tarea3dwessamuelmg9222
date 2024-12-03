package tarea3dwes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tarea3dwes.modelo.Ejemplar;


@Repository
public interface EjemplarRepository extends JpaRepository <Ejemplar,Long> {

	int countByNombreStartingWith(String nombreComun);
	
}