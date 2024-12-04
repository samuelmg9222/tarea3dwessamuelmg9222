package tarea3dwes.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import tarea3dwes.modelo.Planta;
import tarea3dwes.modelo.Ejemplar;
import tarea3dwes.modelo.Mensaje;
import tarea3dwes.modelo.Persona;
import tarea3dwes.repositories.EjemplarRepository;
import tarea3dwes.repositories.MensajeRepository;
import tarea3dwes.repositories.PersonaRepository;
@Service
public class ServiciosMensaje {
	@Autowired
	MensajeRepository mensajerepos;
	@Autowired
	PersonaRepository personarepos;
	@Autowired
	EjemplarRepository ejemplarrepos;
	
	public String generarMensaje(Long id,LocalDateTime hoy) {
		Optional<Persona> optionalPersona = personarepos.findById(id);
		  
		        Persona per = optionalPersona.get();  
		        Long personaId = per.getId();  
		        return "Fecha: " + hoy.toString()+"  Persona: "+personaId;
		    
		  
    }
	public boolean insertarMensaje(Mensaje m) {
	    try {
	        mensajerepos.saveAndFlush(m);
	        return true;
	    } catch (DataIntegrityViolationException e) {
	        
	        return false;
	    }
	}
	public List<Mensaje> filtrarMensajePorEjemplar(Ejemplar ej){
		return mensajerepos.findByEjemplar(ej);
	}
	public List<Mensaje> filtrarMensajePorPersona(Persona p){
		return mensajerepos.findByPersona(p);
	}
	
	public List<Mensaje> filtrarMensajePorPlanta(Planta planta) {
	    List<Ejemplar> ejemplares = ejemplarrepos.findByPlanta(planta); 
	    return mensajerepos.findByEjemplarIn(ejemplares); 
	}
	
	public int verificarfecha(LocalDate fechaInicio,LocalDate fechaFin,LocalDate fechaActual) {
		if (fechaInicio.isAfter(fechaFin)) {
	      
	        return -1;
	    }
		
	    
	    if (fechaInicio.isAfter(fechaActual)) {
	        
	        return -2; 
	    }
		return 1;
	}
	
	public List<Mensaje> filtrarMensajeRangoFechas(LocalDateTime inicio,LocalDateTime fin){
		return mensajerepos.findByFechahoraBetween(inicio,fin);
	}







	
}
