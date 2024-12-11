package  tarea3dwes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tarea3dwes.modelo.*;
import tarea3dwes.servicios.*;
import tarea3dwes.vista.VistaInicial;






@Component
public class Principal implements CommandLineRunner {

	 @Autowired
	  VistaInicial vistainicial;
	
	public void run(String... args) throws Exception {
		
		
		vistainicial.mostrarMenuPrincipal();
		}

	
	
	
		 
		    

		 
		 
		
		    

		
		
	}

	

