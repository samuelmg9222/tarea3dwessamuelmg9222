package tarea3dwes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import tarea3dwes.modelo.Persona;
import tarea3dwes.modelo.Planta;


public interface PersonaRepository extends JpaRepository <Persona,Long>{
	
}
