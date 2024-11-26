package tarea3dwes.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tarea3dwes.repositories.EjemplarRepository;
import tarea3dwes.repositories.PlantaRepository;

@Service
public class ServiciosEjemplar {

	
	@Autowired
	EjemplarRepository plantarepos;

}
