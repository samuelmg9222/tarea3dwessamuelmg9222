package tarea3dwes.vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tarea3dwes.modelo.Persona;
import tarea3dwes.modelo.Planta;
import tarea3dwes.servicios.ServiciosCredencial;
import tarea3dwes.servicios.ServiciosEjemplar;
import tarea3dwes.servicios.ServiciosMensaje;
import tarea3dwes.servicios.ServiciosPersona;
import tarea3dwes.servicios.ServiciosPlanta;
@Component
public class VistaPlantas {
	@Autowired
	ServiciosPlanta servplant;
	@Autowired
	 @Lazy
	VistaInicial vistainicial;
	
	@Autowired
	 @Lazy
	VistaEjemplares vistaejemplares;
	

	
	 private Scanner in = new Scanner(System.in);
	 public void GestionDePlantas(Persona p,String username) {
           int opcionint;
		 do {
	            System.out.println("\n\tSistema Gestor del Vivero ("+username+")\n");
	            System.out.println("\t1 - Visualizar plantas\n");
	            System.out.println("\t2 - Insertar planta\n");
	            System.out.println("\t3 - Modificar planta\n");
	            System.out.println("\t99 - Volver\n");
	            System.out.print("\tSelecciona una opción: ");
	             opcionint = obtenerOpcionValida();

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
	                	System.out.println("Dame codigo de nueva planta");
	                    String codigo=in.nextLine().toUpperCase();
	                    System.out.println("Dame nombre comun de nueva planta");
	                    String nombreComun=in.nextLine().toUpperCase();
	                    System.out.println("Dame nombre cientifico de nueva planta");
	                    String nombreCientifico=in.nextLine().toUpperCase();
	                    Planta planta=new Planta();
	                    planta.setCodigo(codigo);
	                    planta.setNombrecomun(nombreComun);
	                    planta.setNombrecientifico(nombreCientifico);
	                    
	                    int validacion = servplant.validarInsercion(planta);
	                    switch (validacion) {
	                        case -1:
	                            System.out.println("Error: El código de la planta ya existe.");
	                            break;
	                        case -2:
	                            System.out.println("Error: El nombre común debe tener entre 3 y 30 caracteres.");
	                            break;
	                        case -3:
	                            System.out.println("Error: El nombre común solo puede contener letras y espacios.");
	                            break;
	                        case -4:
	                            System.out.println("Error: El nombre científico debe tener entre 3 y 30 caracteres.");
	                            break;
	                        case -5:
	                            System.out.println("Error: El nombre científico solo puede contener letras y espacios.");
	                            break;
	                        case -6:
	                            System.out.println("Error: El código solo puede contener letras.");
	                            break;
	                        default:
	                            
	                            servplant.insertarPlanta(planta);
	                            System.out.println("Planta insertada correctamente.");
	                            break;
	                    }
	                    break;
	                case 3:
	                
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
	                	
	                	

						try {
							System.out.println("Dame el número (índice) de la planta que desea modificar: (Introduce 9999 si desea salir)");
							String indice = in.nextLine().trim();  

							int ind = Integer.parseInt(indice);
							if (ind == 9999) {
								break;
							}
							System.out.println("Dame nombre común que desea poner a la planta. Si no desea modificarlo, introduce su nombre como está:");
							nombreComun = in.nextLine().trim().toUpperCase();

							System.out.println("Dame nombre científico de la planta. Si no desea modificarlo, introduce su nombre como está:");
							nombreCientifico = in.nextLine().trim().toUpperCase(); 
							Planta pl =new Planta();
							pl.setCodigo(plantas.get(ind-1).getCodigo());
							pl.setNombrecomun(nombreComun);
							pl.setNombrecientifico(nombreCientifico);
							

							int resultado = servplant.verificarModificacion(pl, plantas, ind);

							switch (resultado) {
							case 1:
								servplant.modificarplanta(pl);
								System.out.println("Has modificado la planta de código [" + pl.getCodigo() + "] con éxito");
								break;
							case -1:
								System.out.println("Número fuera de rango del índice");
								break;
							case -2:
								System.out.println("Nombre común no válido. Debe ser de 3 a 99 letras incluyendo espacios (opcional) y sin tildes ni ñ");
								break;
							case -3:
								System.out.println("Nombre científico no válido. Debe ser de 3 a 99 letras incluyendo espacios (opcional) y sin tildes ni ñ");
								break;
							case -4:
								System.out.println("No se ha podido modificar nada ya que los datos son los mismos que los ya existentes");
								break;
							default:
								System.out.println("Error desconocido al intentar modificar la planta");
							}
						} catch (IndexOutOfBoundsException e) {
							System.out.println("El índice ingresado está fuera del rango de la lista de plantas.");
						} catch (NumberFormatException e) {
							System.out.println("El valor ingresado no es un número válido.");
						} catch (Exception e) {
							System.out.println("No se ha podido realizar la modificación.");
						}
					
	                    break;
	                case 99:
	                	if(username.equals("ADMIN")) {
	                		vistaejemplares.mostrarMenuAdmin(p,username);
	                	}else
	                    System.out.println("Volviendo...");
	                    vistainicial.mostrarMenuPrincipal();
	                    break;
	                    default:
	                    System.out.println("Opción no válida.");
	                    break;
	            }
	        } while (true);
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
	}
}
