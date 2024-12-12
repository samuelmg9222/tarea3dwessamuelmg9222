package tarea3dwes.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tarea3dwes.modelo.Ejemplar;
import tarea3dwes.modelo.Mensaje;
import tarea3dwes.modelo.Persona;
import tarea3dwes.modelo.Planta;
import tarea3dwes.servicios.ServiciosCredencial;
import tarea3dwes.servicios.ServiciosEjemplar;
import tarea3dwes.servicios.ServiciosMensaje;
import tarea3dwes.servicios.ServiciosPersona;
import tarea3dwes.servicios.ServiciosPlanta;
@Component
public class VistaEjemplares {
	 private Scanner in = new Scanner(System.in);
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
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
	@Autowired
	 @Lazy
	VistaPlantas vistaplantas;
	@Autowired
	 @Lazy
	VistaPersonas vistapersonas;

	
	
	  public void mostrarMenuAdmin(Persona p,String username ) {
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
	                	vistaplantas.GestionDePlantas(p,username);
	                case 2:
	                	mostrarMenuGestionEjemplares(p,username);
	                    break;
	                case 3:
	                	vistapersonas.mostrarMenuGestionPersonas(p);
	                    break;
	                default:
	                
	                    break;
	            }
	        } while (opcionIntAdmin != 99);
	    }
	 
	  
	  
	  
	    public void mostrarMenuGestionEjemplares(Persona per,String username) {

			
			System.out.println("Gestion de ejemplares "+"("+username+")");
			String opcion = "";
			int opcionInt = -1;
			do {
			    System.out.println("\n\tSeleccione una opción:\n");
			    System.out.println("\t1. Registrar ejemplar.\n");
			    System.out.println("\t2. Filtrar ejemplares por tipo de planta .\n");
			    System.out.println("\t3. Ver mensajes para ejemplar .\n");
			    System.out.println("\t4. Crear Mensaje.\n");
			    System.out.println("\t5. Filtrar mensajes.\n");
			    System.out.println("\t99. Volver\n");

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
						System.out.println("Dame el número (índice) de la planta que desea insertar ejemplar: (Introduce 9999 si desea salir)");
						String indice = in.nextLine().trim();  

						int ind = Integer.parseInt(indice);
						if (ind == 9999) {
							break;
						}
						  String codigoPlanta = plantas.get(ind - 1).getCodigo();
						    Planta planta = plantas.get(ind - 1);
						    String nombreEjemplar = servejemplar.generarNombreEjemplar(planta.getCodigo());
						    Ejemplar ejemplar = new Ejemplar(nombreEjemplar, planta);
						    LocalDateTime fechaH = LocalDateTime.now();
						    String fechaformated= fechaH.format(formatter);
						   Mensaje mensaje =new Mensaje(fechaH,servmensaje.generarMensaje(per.getId(), fechaformated),per,ejemplar);
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
			        List<Planta> pl=new ArrayList<Planta>();
              	if(!servplant.verPlantas().isEmpty() && servplant.verPlantas()!=null){
	                 pl = servplant.verPlantas();
	                 int i=1;
	                 System.out.println(String.format("%-5s %-15s %-25s %-30s", "ID", "Código", "Nombre común", "Nombre científico"));
  				for (Planta pl1 : pl) {
						
  					System.out.println(String.format("%-5d %-15s %-25s %-30s", i, pl1.getCodigo(), pl1.getNombrecomun(), pl1.getNombrecientifico()));
						i++;
					}
              	
              	
              	
              	}else {
              		System.out.println("La lista esta vacía");
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
			                        
			                        if (indice.equals("9999")) {
				                        break;
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
			           
			            if (!codigos.isEmpty()) {
			            	for (String c : codigos) {
			            	    
			            	    List<Ejemplar> ejemplares = servejemplar.verEjemplaresPorCodigoPlanta(c);
			            	    plantas = servplant.verPlantas();
			            	    
			            	    for (Planta pln : plantas) {
			            	     
			            	        if (pln.getCodigo().equals(c)) {
			            	            if (ejemplares.isEmpty()) {
			            	                System.out.println("\nLa planta con código " + c + " se encuentra sin ejemplares");
			            	            } else {
			            	                System.out.println("\nEjemplares con código de planta " + c + ":");
			            	                for (Ejemplar ejmp : ejemplares) {
			            	                    System.out.println("id: "+ejmp.getId() + "  " + ejmp.getNombre());
			            	                }
			            	            }
			            	        }
			            	    }
			            	}
			            }
			        } while(!indice.equals("9999"));
			        break;
			    
			    case 3:
			    	Long idEjem = 0L;
			    	Ejemplar Ejemp=new Ejemplar();
					String indc,idEjemStr = "";
					int j = 1;
					int opc = 0;
					List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();
					List<Mensaje> mensajesAsociados = new ArrayList<Mensaje>();
					if (servejemplar.verEjemplares() != null && !servejemplar.verEjemplares().isEmpty()) {

						ejemplares = servejemplar.verEjemplares();
						for (Ejemplar c : ejemplares) {
							System.out.println(j + ".   nombre: " + c.getNombre());
							j++;
						}

						System.out.println("\nDame un indice (NUMERO DE LISTA) para ver mensajes asociados a ese ejemplar: (9999 para salir)");
						indc = in.nextLine().trim();
						 if(indc.equals("9999")) {
							 break;
						 }

						try {
							opc = Integer.parseInt(indc);
							 Ejemp=ejemplares.get(opc-1);
							idEjem = ejemplares.get(opc - 1).getId();
							idEjemStr = ejemplares.get(opc - 1).getId().toString();
						} catch (Exception e) {
							if(opc!=9999) {
							System.out.println("Error: el indice debe de ser un numero y dentro del rango");
							continue;
						}
						}

						if (servejemplar.existeEjemplar(idEjem)) {
							try {
								if (servmensaje.filtrarMensajePorEjemplar(Ejemp) == null
										|| servmensaje.filtrarMensajePorEjemplar(Ejemp).isEmpty()) {

									System.out.println("Ese ejemplar no tiene mensajes asociados");
								} else {
									mensajesAsociados = servmensaje.filtrarMensajePorEjemplar(Ejemp);
									System.out.println("Mensajes asociados encontrados para el ejemplar: "+ ejemplares.get(opc - 1).getNombre() +": "+ mensajesAsociados.size());
									int h = 1;
									for (Mensaje m : mensajesAsociados) {
										System.out.println(h+": "+ejemplares.get(opc - 1).getNombre()+": "+m.getMensaje()+"  Nombre: "+(m.getPersona().getNombre().toUpperCase())+"  Email: "+m.getPersona().getEmail().toUpperCase());
										h++;
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else
							System.out.println("Codigo no valido");
					
					
					}else {System.out.println("No se han encontrado ejemplares");}
					
					
					 
					break;
					
			    case 4:
			    	Long idjmp = -5L;
			    	String codigoEjemplar;
			    	String mensajeIntroducido;
			    	Ejemplar ejmpl = new Ejemplar();
			    	List<Ejemplar> ejemplares2 = new ArrayList<>();
			    	int k = 1;
			    	int indiceCodEj = 0;

			    	if (servejemplar.verEjemplares() != null && !servejemplar.verEjemplares().isEmpty()) {

			    	    ejemplares2 = servejemplar.verEjemplares();
			    	    for (Ejemplar c : ejemplares2) {
			    	        System.out.println(k + ".  id: " + c.getId() + "  nombre: " + c.getNombre());
			    	        k++;
			    	    }

			    	    System.out.println("\nDame un índice (NUMERO DE LISTA) para ver crear un mensaje asociado a ese ejemplar: (9999 para salir)");
			    	    codigoEjemplar = in.nextLine().trim();

			    	    if (codigoEjemplar.equals("9999")) {
			    	        return; 
			    	    }

			    	    try {
			    	        indiceCodEj = Integer.parseInt(codigoEjemplar);

			    	        if (indiceCodEj > 0 && indiceCodEj <= ejemplares2.size()) {
			    	            ejmpl = ejemplares2.get(indiceCodEj - 1);
			    	            idjmp = ejmpl.getId();
			    	            System.out.println("Ejemplar seleccionado: " + ejmpl.getNombre());
			    	        } else {
			    	            System.out.println("Error: El índice ingresado no es válido. Debe estar entre 1 y " + ejemplares2.size());
			    	            continue;
			    	        }

			    	        if (servejemplar.existeEjemplar(idjmp)) {
			    	            LocalDateTime h = LocalDateTime.now();
			    	           
			    	            System.out.println("Introduce el mensaje que desea introducir");
			    	            mensajeIntroducido = in.nextLine().trim();

			    	            int verif = servmensaje.verificarMensajeIntroducido(mensajeIntroducido);

			    	            if (verif == -1) {
			    	                System.out.println("No puede dejar el mensaje vacío");
			    	            } else if (verif == -2) {
			    	                System.out.println("La longitud no es válida. Debe de ser de 5 a 100 caracteres");
			    	            } else if (verif == -3) {
			    	                System.out.println("Error: Solamente se pueden introducir espacios, letras sin tildes, números, @ y /");
			    	            } else if (verif == 1) {
			    	                Mensaje ms = new Mensaje(h, mensajeIntroducido, per, ejmpl);

			    	                if (servmensaje.insertarMensaje(ms)) {
			    	                    System.out.println("Mensaje generado y almacenado con éxito: " + ms.getMensaje());
			    	                } else {
			    	                    System.out.println("No se ha podido insertar el mensaje");
			    	                }
			    	            }
			    	        } else {
			    	            System.out.println("No se han encontrado ejemplares");
			    	        }
			    	    } catch (NumberFormatException e) {
			    	        System.out.println("Error: Debes ingresar un número válido.");
			    	    } catch (Exception e) {
			    	        e.printStackTrace();
			    	    }
			    	}
break;
			    case 5:
			        boolean subMenuActivo = true;
			        do {
			            System.out.println("\n\tSeleccione una opción:\n");
			            System.out.println("\t1.  Filtrar por usuario.\n");
			            System.out.println("\t2.  Filtrar por tipoplanta.\n");
			            System.out.println("\t3.  Filtrar por Fechas.\n");
			            System.out.println("\t9999.  Volver.\n");

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

			            if (opcionInt == 9999) {
			                System.out.println("Volviendo...");
			                subMenuActivo = false; 
			                break;
			            } else if (opcionInt < 1 || opcionInt > 3) {
			                System.out.println("Opción incorrecta. Por favor, elige una opción entre 1 y 3, o 9999 para volver.");
			                continue;
			            }

			            switch (opcionInt) {
			                case 1: 
			                    List<Persona> users = servpersona.verPersonas();
			                    if (users == null || users.isEmpty()) {
			                        System.out.println("No hay usuarios disponibles.");
			                        break;
			                    }

			                    System.out.println("Lista de usuarios:");
			                    int userIndex = 1;
			                    for (Persona user : users) {
			                        System.out.println(userIndex + ": " + user.getId() + "\t" + user.getNombre() + "\t" + user.getEmail());
			                        userIndex++;
			                    }

			                    System.out.println("Dame el número (índice) de usuario sobre el que quieres ver mensajes:");
			                    String indic = in.nextLine().trim();

			                    try {
			                        int indP = Integer.parseInt(indic);
			                        if (indP < 1 || indP > users.size()) {
			                            System.out.println("Índice fuera de rango.");
			                            break;
			                        }

			                        Persona selectedUser = users.get(indP - 1);
			                        List<Mensaje> mensajespersona = servmensaje.filtrarMensajePorPersona(selectedUser);

			                        if (mensajespersona == null || mensajespersona.isEmpty()) {
			                            System.out.println("No hay mensajes para este usuario.");
			                        } else {
			                        	int i=1;
			                            System.out.println("Mensajes del usuario con ID " + selectedUser.getId() + ":");
			                            for (Mensaje mensaje : mensajespersona) {
			                                System.out.println(i+":  "+mensaje.getMensaje()+"  Ejemplar:"+mensaje.getEjemplar().getNombre());
			                            i++;
			                            }
			                        }
			                    } catch (NumberFormatException e) {
			                        System.out.println("Entrada no válida. Por favor, introduce un número.");
			                    }
			                    break;

			                case 2: 
			                    List<Planta> plantass = servplant.verPlantas();
			                    if (plantass == null || plantass.isEmpty()) {
			                        System.out.println("No hay plantas disponibles.");
			                        break;
			                    }

			                    System.out.println(String.format("%-5s %-15s %-25s %-30s", "ID", "Código", "Nombre común", "Nombre científico"));
			                    int plantaIndex = 1;
			                    for (Planta planta : plantass) {
			                        System.out.println(String.format("%-5d %-15s %-25s %-30s", plantaIndex, planta.getCodigo(), planta.getNombrecomun(), planta.getNombrecientifico()));
			                        plantaIndex++;
			                    }

			                    System.out.println("Dame el número (índice) de la planta sobre la que quieres ver mensajes:");
			                    String plantaInput = in.nextLine().trim();

			                    try {
			                        int plantaInd = Integer.parseInt(plantaInput);
			                        if (plantaInd < 1 || plantaInd > plantass.size()) {
			                            System.out.println("Índice fuera de rango.");
			                            break;
			                        }

			                        Planta selectedPlanta = plantass.get(plantaInd - 1);
			                        List<Mensaje> mensajesplanta = servmensaje.filtrarMensajePorPlanta(selectedPlanta);

			                        if (mensajesplanta == null || mensajesplanta.isEmpty()) {
			                            System.out.println("No hay mensajes para esta planta.");
			                        } else {
			                        	int i=1;
			                            System.out.println("Mensajes de la planta con código " + selectedPlanta.getCodigo() + ":");
			                            for (Mensaje mensaje : mensajesplanta) {
			                                System.out.println(i+":  "+mensaje.getMensaje()+"  Ejemplar:"+mensaje.getEjemplar().getNombre());
			                            i++;
			                            }
			                        }
			                    } catch (NumberFormatException e) {
			                        System.out.println("Entrada no válida. Por favor, introduce un número.");
			                    }
			                    break;

			                case 3: 
			                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			                    LocalDate fechaInicio = null, fechaFin = null;

			                    try {
			                        System.out.println("Introduce la fecha de inicio (formato yyyy-MM-dd): 999 para salir:");
			                        String fechaInicioStr = in.nextLine().trim();
			                        if ("999".equals(fechaInicioStr)) break;

			                        fechaInicio = LocalDate.parse(fechaInicioStr, formatter);

			                        System.out.println("Introduce la fecha de fin (formato yyyy-MM-dd): 999 para salir:");
			                        String fechaFinStr = in.nextLine().trim();
			                        if ("999".equals(fechaFinStr)) break;

			                        fechaFin = LocalDate.parse(fechaFinStr, formatter);

			                        if (fechaInicio.isAfter(fechaFin)) {
			                            System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin.");
			                            break;
			                        }

			                        List<Mensaje> mensajesFecha = servmensaje.filtrarMensajeRangoFechas(fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59));

			                        if (mensajesFecha == null || mensajesFecha.isEmpty()) {
			                            System.out.println("No hay mensajes en el rango de fechas seleccionado.");
			                        } else {
			                            System.out.println("Mensajes entre " + fechaInicio + " y " + fechaFin + ":");
			                            int i=1;
			                            for (Mensaje mensaje : mensajesFecha) {
			                                System.out.println(i+":  "+mensaje.getMensaje()+"  Ejemplar:"+mensaje.getEjemplar().getNombre());
			                           i++;
			                            }
			                        }
			                    } catch (DateTimeParseException e) {
			                        System.out.println("Formato de fecha no válido. Debes usar el formato yyyy-MM-dd.");
			                    }
			                    break;

			                default:
			                    System.out.println("Opción no válida.");
			            }
			        } while (subMenuActivo);

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
