package tarea3dwes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tarea3dwes.modelo.Planta;

@Repository
public interface PlantaRepository extends JpaRepository <Planta,Long> {

}
