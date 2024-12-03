package tarea3dwes.servicios;

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
import tarea3dwes.repositories.MensajeRepository;
import tarea3dwes.repositories.PersonaRepository;
@Service
public class ServiciosMensaje {
	@Autowired
	MensajeRepository mensajerepos;
	@Autowired
	PersonaRepository personarepos;
	
	
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
	
}
