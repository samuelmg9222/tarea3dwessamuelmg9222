package  tarea3dwes;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import tarea3dwes.vista.VistaInicial;






@Component
public class Principal implements CommandLineRunner {

	 @Autowired
	  VistaInicial vistainicial;
	
	public void run(String... args) throws Exception {
		
		
		vistainicial.mostrarMenuPrincipal();
		}

	
	
	
		 
		    

		 
		 
		
		    

		
		
	}

	

