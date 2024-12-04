package tarea3dwes.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tarea3dwes.modelo.Credenciales;
import tarea3dwes.modelo.Persona;
import tarea3dwes.repositories.CredencialRepository;
import tarea3dwes.repositories.PlantaRepository;
@Service
public class ServiciosCredencial {

	
	@Autowired
	CredencialRepository credencialesRepository;
	
	
	
	  public Credenciales encontrarCredenciales(String p) {
	        return credencialesRepository.findByUsuario(p);
		
		
		
	}
	
	   public int registro(String usuario, String contraseña){
	        Credenciales cred =credencialesRepository.findByUsuario(usuario);
	        
	        if (cred == null) {
	            return -1;
	        }
	        if (usuario.equals("ADMIN") && contraseña.equals("admin")){
	            return 0;
	        }

	        if (usuario.equals(cred.getUsuario()) && contraseña.equals(cred.getPassword())){
	            return 1;
	        }
	        return -1;
	    }
	   
	   public boolean insertarCredenciales(Credenciales cr) {
		    return credencialesRepository.saveAndFlush(cr) != null;
		}
}
