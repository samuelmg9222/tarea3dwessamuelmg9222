package tarea3dwes.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tarea3dwes.modelo.Planta;
import tarea3dwes.repositories.PlantaRepository;
@Service
public class ServiciosPlanta {

	@Autowired
	PlantaRepository plantarepos;
	
	
	
	
	public boolean validarplanta(Planta p) {
		return true;
	}
}
