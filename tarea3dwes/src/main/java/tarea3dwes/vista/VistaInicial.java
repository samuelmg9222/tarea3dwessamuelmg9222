package tarea3dwes.vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tarea3dwes.modelo.Credenciales;
import tarea3dwes.modelo.Persona;
import tarea3dwes.modelo.Planta;
import tarea3dwes.servicios.ServiciosCredencial;
import tarea3dwes.servicios.ServiciosEjemplar;
import tarea3dwes.servicios.ServiciosMensaje;
import tarea3dwes.servicios.ServiciosPersona;
import tarea3dwes.servicios.ServiciosPlanta;
@Component
public class VistaInicial {
	private Scanner in = new Scanner(System.in);
	
	
	@Autowired
	ServiciosPlanta servplant;
	
	@Autowired
	ServiciosCredencial servcredencial;
	
	@Autowired
	ServiciosPersona servpersona;
	
	@Autowired
	 @Lazy
	VistaPlantas vistaplantas;
	
	@Autowired
	 @Lazy
	VistaPersonas vistapersonas;
	Persona persona;
	String username;
	@Autowired
	 @Lazy
	VistaEjemplares vistaejemplares;
	public void mostrarMenuPrincipal() {
        int opcionint;
        do {
            System.out.println("\n\tSistema Gestor del Vivero (Modo invitado)\n");
            System.out.println("\t1 - Visualizar plantas\n");
            System.out.println("\t2 - Iniciar sesión\n");
            System.out.println("\t99 - Salir de la aplicación\n");
            System.out.print("\tSelecciona una opción: ");
            opcionint = obtenerOpcionValida();

            String password;
			switch (opcionint) {
                
			
			
			case 1:
				List<Planta> plantas=new ArrayList<Planta>();
            	if(!servplant.verPlantas().isEmpty() && servplant.verPlantas()!=null){
                 plantas = servplant.verPlantas();
                 int i=1;
                 System.out.println(String.format("%-5s %-15s %-25s %-30s", "ID", "Código", "Nombre común", "Nombre científico"));
				for (Planta pl : plantas) {
					
					System.out.println(String.format("%-5d %-15s %-25s %-30s", i, pl.getCodigo(), pl.getNombrecomun(), pl.getNombrecientifico()));
					i++;
				}
            	
            	
            	
            	}else {
            		System.out.println("La lista esta vacía");
            	break;
            	}
            	
            	break;
                
                
                case 2:
                	   
                	        System.out.print("Nombre de usuario: ");
                	        username = in.nextLine().toUpperCase().trim(); 
                	        System.out.print("Contraseña: ");
                	        password = in.nextLine().trim(); 

                	        int tipouser = servcredencial.registro(username, password);
                	        

                	        if (tipouser == -1) {
                	            System.out.println("Contraseña o usuario incorrectos");
                	        } else if (tipouser == 1) {
                	        	Credenciales cr= servcredencial.encontrarCredenciales(username);
		                	       persona = cr.getPersona();
		                	       vistaejemplares.mostrarMenuGestionEjemplares(persona,username);
                	        } else if (tipouser == 0) {
                	        	Credenciales cr= servcredencial.encontrarCredenciales(username);
		                	       persona = cr.getPersona();
		                	       vistaejemplares.mostrarMenuAdmin(persona,username);
                	        }
                	    
                
                    break;
                case 99:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Dicha opción no es válida");
                    break;
            }
        } while (opcionint != 99);
    }
    private int obtenerOpcionValida() {
        while (true) {
            if (in.hasNextInt()) {
                int opcion = in.nextInt();
                in.nextLine();
                return opcion;
            } else {
                System.out.println("Entrada no válida. Por favor, introduzca solo un número sin espacios.");
                in.nextLine();
            }
        }
    }}
