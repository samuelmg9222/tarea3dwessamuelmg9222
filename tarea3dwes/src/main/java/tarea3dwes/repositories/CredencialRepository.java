package tarea3dwes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tarea3dwes.modelo.Credenciales;
import tarea3dwes.modelo.Persona;


@Repository
public interface CredencialRepository extends JpaRepository <Credenciales,Long>{

	Credenciales findByUsuario(String p);

}
