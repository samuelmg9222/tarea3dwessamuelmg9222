package  tarea3dwes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


import tarea3dwes.modelo.*;
import tarea3dwes.servicios.*;




//NUESTRA CAPA DE VISTA
public class Principal implements CommandLineRunner {
	 private Scanner in = new Scanner(System.in);
	@Autowired
	ServiciosPlanta servplant;
	@Autowired
	ServiciosEjemplar servejemplar;
	@Autowired
	ServiciosCredencial servcredencial;
	@Autowired
	ServiciosMensaje servmensaje;
	@Autowired
	ServiciosPersona servpersona;
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("INI");
		Planta p =new Planta();
	    
		mostrarMenuPrincipal();
		}
	
	Persona persona;
	String username;
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
		                	  if(!servplant.verPlantas().isEmpty()) {
		                	  List<Planta> plantas=servplant.verPlantas();
		                	  
			                	  plantas.forEach(planta -> System.out.println("Código: " + planta.getCodigo() +
"Nombre Común: " + planta.getNombrecomun() +
                                      "  Nombre Científico: " + planta.getNombrecientifico()));
		                	  }else System.out.println("La lista esta vacía");
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
		                	        	GestionDePlantas();
		                	        } else if (tipouser == 0) {
		                	        	Credenciales cr= servcredencial.encontrarCredenciales(username);
				                	       persona = cr.getPersona();
				                	       mostrarMenuAdmin();
		                	        }
		                	    
		                
		                    break;
		                case 99:
		                    System.out.println("Saliendo...");
		                    System.exit(0);
		                    break;
		                default:
		                   // opcNoValida();
		                    break;
		            }
		        } while (opcionint != 99);
		    }

		 public void GestionDePlantas() {
	            int opcionint;
			 do {
		            System.out.println("\n\tSistema Gestor del Vivero ("+username+")\n");
		            System.out.println("\t1 - Visualizar plantas\n");
		            System.out.println("\t2 - Insertar planta\n");
		            System.out.println("\t2 - Modificar planta\n");
		            System.out.println("\t99 - Salir de la aplicación\n");
		            System.out.print("\tSelecciona una opción: ");
		             opcionint = obtenerOpcionValida();

		            switch (opcionint) {
		                case 1:
		                	if(!servplant.verPlantas().isEmpty()){
		                	  List<Planta> plantas = servplant.verPlantas();
		                	 
		                	    
		                	  plantas.forEach(planta -> System.out.println("Código: " + planta.getCodigo() +
                                   "  Nombre Común: " + planta.getNombrecomun() +
                                   "  Nombre Científico: " + planta.getNombrecientifico()));
		                	  }else System.out.println("La lista esta vacía");
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
		                	List<Planta> plantas=new ArrayList<Planta>();
		                	if(!servplant.verPlantas().isEmpty()){
			                 plantas = servplant.verPlantas();
		              
		    				
		                	
		                	
			                 int i=1;
		    				for (Planta pl : plantas) {
								
								System.out.println(i + ": " +pl.getCodigo()+"\t"+ pl.getNombrecomun() + "\t" + pl.getNombrecientifico());
								i++;
							}
		                	
		                	
		                	
		                	}else {
		                		System.out.println("La lista esta vacía");
		                	break;}
		                	

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
									System.out.println("Nombre común no válido. Debe ser de 3 a 99 letras incluyendo espacios y sin tildes ni ñ");
									break;
								case -3:
									System.out.println("Nombre científico no válido. Debe ser de 3 a 99 letras incluyendo espacios y sin tildes ni ñ");
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
		                    System.out.println("Volviendo...");
		                    mostrarMenuPrincipal();
		                    break;
		                default:
		                   // opcNoValida();
		                    break;
		            }
		        } while (opcionint != 99);
		 }
		 
		 
		 
		  private void mostrarMenuAdmin() {
		        int opcionIntAdmin;
		        do {
		            System.out.println("\n\tMenú del Administrador\n");
		            System.out.println("\t1 - Gestión de plantas\n");
		            System.out.println("\t2 - Gestión de ejemplares\n");
		            System.out.println("\t3 - Gestión de empleados\n");
		            System.out.println("\t99 - Cerrar sesión\n");
		            System.out.print("\tSelecciona una opción: ");
		            opcionIntAdmin = obtenerOpcionValida();

		            if (opcionIntAdmin == 99) {
		                System.out.println("Sesión cerrada. Volviendo al menú invitado...");
		                break;
		            }

		            switch (opcionIntAdmin) {
		                case 1:
		                	mostrarMenuGestionEjemplares(persona.getId());
		                case 2:
		                    
		                    break;
		                case 3:
		                    
		                    break;
		                default:
		                    
		                    break;
		            }
		        } while (opcionIntAdmin != 99);
		    }
		 
		  
		  
		  
		    public void mostrarMenuGestionEjemplares(Long idpersona) {

				
				System.out.println("Gestion de ejemplares");
				String opcion = "";
				int opcionInt = -1;
				do {
				    System.out.println("\n\tSeleccione una opción:\n");
				    System.out.println("\t1. Registrar ejemplar.\n");
				    System.out.println("\t2. Filtrar ejemplares por tipo de planta .\n");
				    System.out.println("\t3. Ver mensajes para ejemplar .\n");
				    System.out.println("\t4. Crear Mensaje.\n");
				    System.out.println("\t5. Filtrar mensajes.\n");
				    System.out.println("\t99. Volver al menú Principal\n");

				    opcion = in.nextLine().trim();
				    if (!opcion.matches("\\d+")) { 
				        System.out.println("Entrada no válida. Por favor, introduzca solo un número sin espacios.");
				        continue;  
				    }
				    try {
				        opcionInt = Integer.parseInt(opcion.trim());
				    } catch (NumberFormatException e) {
				        opcionInt = -1;
				    }

				    if (opcionInt == 99) {
				        System.out.println("Volviendo...");
				    } else if (opcionInt < 1 || opcionInt > 5) {
				        System.out.println("Opción incorrecta.");
				        continue;
				    }

				    switch (opcionInt) {
				    case 1:
				    	List<Planta> plantas=new ArrayList<Planta>();
	                	if(!servplant.verPlantas().isEmpty()){
		                 plantas = servplant.verPlantas();
	                	
		                 int i=1;
	    				for (Planta pl : plantas) {
							
							System.out.println(i + ": " +pl.getCodigo()+"\t"+ pl.getNombrecomun() + "\t" + pl.getNombrecientifico());
							i++;
						}
	                	
	                	
	                	
	                	}else {
	                		System.out.println("La lista esta vacía");
	                	break;}
	                	

						try {
							System.out.println("Dame el número (índice) de la planta que desea insertar ejemplar: (Introduce 9999 si desea salir)");
							String indice = in.nextLine().trim();  

							int ind = Integer.parseInt(indice);
							if (ind == 9999) {
								break;
							}
							  String codigoPlanta = plantas.get(ind - 1).getCodigo();
							    Planta planta = servplant.obtenerPlantaPorCodigo(codigoPlanta);
							    String nombreEjemplar = servejemplar.generarNombreEjemplar(planta.getNombrecomun());
							    Ejemplar ejemplar = new Ejemplar(nombreEjemplar, planta);
							    LocalDateTime fechaH = LocalDateTime.now();
							   Mensaje mensaje =new Mensaje(fechaH,servmensaje.generarMensaje(persona.getId(), fechaH),persona,ejemplar);
							int resultado =servejemplar.verificarInsercion(codigoPlanta, plantas, ind);
							    switch (resultado) {
								case 1:
									
									 servejemplar.insertarEjempalr(ejemplar);
									    System.out.println("Ejemplar de la planta " + planta.getNombrecomun() + " insertado correctamente.");
									servmensaje.insertarMensaje(mensaje);
									    break;
								case -1:
									System.out.println("Número fuera de rango del índice");
									break;
								case -2:
									System.out.println("Error al generar el nombre del ejemplar");
									break;
								
								
								default:
									System.out.println("Error desconocido al intentar insertar el ejemplar");
									break;
								}
							} catch (IndexOutOfBoundsException e) {
								System.out.println("El índice ingresado está fuera del rango de la lista de plantas.");
							} catch (NumberFormatException e) {
								System.out.println("El valor ingresado no es un número válido.");
							} catch (Exception e) {
								System.out.println("No se ha podido realizar la insercion.");
							}
						
				        break;
				    case 2:
				        ArrayList<String> codigos = new ArrayList<>();
				        List<Planta> pl = new ArrayList<Planta>();

				       
	                	if(!servplant.verPlantas().isEmpty()){
		                 pl = servplant.verPlantas();
	                	
		                 int i=1;
	    				for (Planta pl1 : pl) {
							
							System.out.println(i + ": " +pl1.getCodigo()+"\t"+ pl1.getNombrecomun() + "\t" + pl1.getNombrecientifico());
							i++;
						}
	                	
				        } else {
				            System.out.println("No se ha podido mostrar el listado o la lista está vacía.");
				            break;
				        }
				        String indice;
				        int indicePlanta;
				        do {
				            do {
				                try {
				                    System.out.println("Dame el numero (índice) de la planta sobre el que quieres filtrar ejemplares (9999 para salir)");
				                    indicePlanta = -1;
				                    indice = in.nextLine().trim(); 
				                    indicePlanta = Integer.parseInt(indice);
				                    if (!(indicePlanta > 0 && indicePlanta <= pl.size() || indicePlanta == 9999)) {
				                        System.out.println("Por favor, elige un número válido dentro del rango.");
				                        continue;
				                    }

				                    if (indice.equals("9999")) {
				                        break;
				                    }

				                    if (servplant.procesarCodigo(pl.get(indicePlanta - 1).getCodigo(), codigos)==3) {

				                        if (servplant.existeCodigoPlanta(pl.get(indicePlanta - 1).getCodigo())) {

				                            System.out.println("Código válido, ¿desea ingresar otro código? (S/N)");

				                            String opc;
				                            do {
				                                opc = in.nextLine().toUpperCase().trim(); 
				                                if (!opc.equals("S") && !opc.equals("N")) {
				                                    System.out.println("Introduce una opción válida; (S/N)");
				                                }
				                            } while (!opc.equals("S") && !opc.equals("N"));

				                            if (opc.equals("N")) {
				                                break;
				                            }
				                        } else {
				                            System.out.println("Código no válido");
				                        }
				                    } else if(servplant.procesarCodigo(pl.get(indicePlanta - 1).getCodigo(), codigos)==1){
				                        System.out.println("Ese codigo ya lo has introducido");
				                    }else if(servplant.procesarCodigo(pl.get(indicePlanta - 1).getCodigo(), codigos)==2){
				                    	System.out.println ("Ese código de planta no existe.");
				                    }
				                } catch (NumberFormatException e) {
				                    System.out.println("Introduce un número válido.");
				                    continue;
				                } catch (Exception e) {
				                    System.out.println(e.getMessage());
				                }
				            } while (true);
				            if (indice.equals("9999")) {
		                        break;
		                    }
				            if (!codigos.isEmpty()) {
				                for (String c : codigos) {
				                    List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();
				                    if (ejemplares.isEmpty()) {
				                        System.out.println("\nLa planta con código " + c + " se encuentra sin ejemplares");
				                    } else {
				                        System.out.println("\nEjemplares con código de planta " + c + ":");
				                        for (Ejemplar ejmp : ejemplares) {
				                            System.out.println(ejmp);
				                        }
				                    }
				                }
				            }
				            
				        } while(indicePlanta == 9999);
				        break;
				    

					case 99:
						break;
					default:
						System.out.println("Opción incorrecta.");
						break;

					}
					 
				} while (opcionInt != 99);
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
	
	

