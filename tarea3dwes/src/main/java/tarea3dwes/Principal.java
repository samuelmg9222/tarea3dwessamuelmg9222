package  tarea3dwes;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import tarea3dwes.modelo.*;
import tarea3dwes.servicios.*;


//NUESTRA CAPA DE VISTA
public class Principal implements CommandLineRunner {

	@Autowired
	ServiciosPlanta servplant;
	@Autowired
	ServiciosEjemplar servejemplar;
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("INI");
		Planta p =new Planta();
		
		
		if(!servplant.validarPl(p)) {
			System.out.println("El codigo ya existe");
		}
		System.out.println("--------------");
		
		
		System.out.println("FIN");
		
	}

	
}
