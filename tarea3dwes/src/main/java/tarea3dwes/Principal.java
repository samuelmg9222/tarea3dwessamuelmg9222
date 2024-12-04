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
		                    System.out.println("Dicha opción no es válida");
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
		            System.out.println("\t3 - Modificar planta\n");
		            System.out.println("\t99 - Salir de la aplicación\n");
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
		                	GestionDePlantas();
		                case 2:
		                	mostrarMenuGestionEjemplares(persona.getId());
		                    break;
		                case 3:
		                	mostrarMenuGestionPersonas(persona.getId());
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
							    Planta planta = plantas.get(ind - 1);
							    String nombreEjemplar = servejemplar.generarNombreEjemplar(planta.getCodigo());
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
				                    List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();
				                    ejemplares=servejemplar.verEjemplares();
				                    plantas = servplant.verPlantas();
				                    for(Planta pln:plantas) {
				                    	if(pln.getCodigo().equals(c)){

				                    
				                    if (ejemplares.isEmpty()) {
				                        System.out.println("\nLa planta con código " + c + " se encuentra sin ejemplares");
				                    } else {
				                        System.out.println("\nEjemplares con código de planta " + c + ":");
				                        for (Ejemplar ejmp : ejemplares) {
				                            System.out.println(ejmp.getId()+" "+ejmp.getNombre());
				                        }
				                    }
				                }}
				            }
				            }  
				        } while(indicePlanta == 9999);
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
											||! servmensaje.filtrarMensajePorEjemplar(Ejemp).isEmpty()) {

										System.out.println("Ese ejemplar no tiene mensajes asociados");
									} else {
										mensajesAsociados = servmensaje.filtrarMensajePorEjemplar(Ejemp);
										System.out.println("Mensajes asociados encontrados: " + mensajesAsociados.size());
										for (Mensaje m : mensajesAsociados) {
											System.out.println(m.getMensaje());
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
				    	Long idEjempl=-3L;
				    	String idEjemStr2="";
						String codigoEjemplar;
						Ejemplar ejmpl=new Ejemplar();
						List<Ejemplar> ejemplares2 = new ArrayList<Ejemplar>();
						int k = 1;
						int indiceCodEj = 0;
						if (servejemplar.verEjemplares() != null
								&& !servejemplar.verEjemplares().isEmpty()) {

							ejemplares2 = servejemplar.verEjemplares();
							for (Ejemplar c : ejemplares2) {
								System.out.println(k + ".  id: " + c.getId() + "  nombre: " + c.getNombre());
								k++;
							}

							System.out.println("\nDame un indice (NUMERO DE LISTA) para ver crear un mensaje asociado a ese ejemplar: (9999 para salir)");
						;
						codigoEjemplar = in.nextLine().trim();
		if(codigoEjemplar.equals("9999")) {
			break;
		}
						try {
							indiceCodEj=Integer.parseInt(codigoEjemplar);
							idEjempl = ejemplares2.get(indiceCodEj - 1).getId();
							 ejmpl = ejemplares2.get(indiceCodEj - 1);
						}catch(Exception e) {
							if(indiceCodEj!=9999) {
								System.out.println("Error: el indice debe de ser un numero y dentro del rango");
								continue;
						}
						}
						try {
						if (servejemplar.existeEjemplar(idEjempl)) {
							
							Long idej = Long.parseLong(codigoEjemplar);
							LocalDateTime h=LocalDateTime.now();
							Mensaje ms = new Mensaje(h,servmensaje.generarMensaje(idej, h),persona,ejmpl);
									
							
							if (servmensaje.insertarMensaje(ms)) {
								System.out.println("Mensaje generado y almacenado con éxito: " + ms.getMensaje());
							} else {
								System.out.println("No se ha podido insertar el mensaje");
												
							}
						} else
							System.out.println("Codigo no valido");
						}catch(Exception e) {
							e.getMessage();
							continue;
						}


							}else {System.out.println("No se han encontrado ejemplares");}
						
				    	
				    	break;
				    case 5:
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
								
								
								break;
							} else if (opcionInt < 1 || opcionInt > 3) {
								System.out.println("Opción incorrecta.");
								continue;
							}

							switch (opcionInt) {
							
							
							case 1:
								List<Persona> users =new ArrayList<Persona>();
								List<Mensaje> mensajespersona=new ArrayList<Mensaje>();
								if (servpersona.verPersonas()!= null && !servpersona.verPersonas().isEmpty()) {
								users = servpersona.verPersonas();
								
								int l = 0;

								for (Persona u : users) {

									System.out.println(l + ": " + u.getId() + "\t" + u.getNombre() + "\t" + u.getEmail());
									l++;
								}
								} else {
										System.out.println("No se ha podido mostrar el listado o la lista está vacía.");
										break;
								 }
								System.out.println("Dame el numero (indice) de usuario sobre el que quiere ver mensajes:");
								String indic = in.nextLine().trim();
								try {
									int indP = Integer.parseInt(indic);

									if (!servpersona.existePersona(users.get(indP).getId())) {

										System.out.println("No existe persona con ese codigo");

									} else {
										
										if(servmensaje.filtrarMensajePorPersona(users.get(indP))==null && servmensaje.filtrarMensajePorPersona(users.get(indP)).isEmpty()) {
											System.out.println("No se ha podido mostrar el listado o la lista está vacía.");
										}else {
											mensajespersona=servmensaje.filtrarMensajePorPersona(users.get(indP));
											System.out.println("Mensajes del usuario con id " + users.get(indP).getId());
											for (Mensaje mns : mensajespersona) {
												System.out.println(mns);
										}
									}}
								} catch (NumberFormatException e) {
									System.out.println("No se ha podido obtener los mensjaes. Id introuducido no valido" + e.getMessage());
								} catch (Exception e) {
									System.out.println("Solo se pueden elegir dentro del indice");

								}
							break;
							
							case 2:
				
								int h = 1;
								List<Mensaje> mensajesplanta =new ArrayList <Mensaje>();
								List<Planta> plantas4 = new ArrayList <Planta>();
								if (servplant.verPlantas() != null && !servplant.verPlantas().isEmpty()) {

							

							
									 plantas4= servplant.verPlantas();
									for (Planta plnts : plantas4) {
										System.out.println(h+"   "+plnts);
										h++;
									}
									
								} else {
									System.out.println("No se ha podido mostrar el listado o la lista está vacía.");
									break;
								}
								
								System.out.println("Dame el numero (indice) de la planta sobre el que quieres ver mensajes");
								String index = in.nextLine().trim();
								try {
									int indce = Integer.parseInt(index);

									if (servplant.existeCodigoPlanta(plantas4.get(indce - 1).getCodigo())) {

								 if(servmensaje.filtrarMensajePorPlanta(plantas4.get(indce - 1))!=null && !servmensaje.filtrarMensajePorPlanta(plantas4.get(indce - 1)).isEmpty()) {
									 mensajesplanta= servmensaje.filtrarMensajePorPlanta(plantas4.get(indce - 1));
									 System.out.println("Mensades de la planta con codigo" + plantas4.get(indce - 1).getCodigo());
										for (Mensaje msjs : mensajesplanta) {
											System.out.println(msjs);
										}
									}
									}else {
										System.out.println("No se a podido mostras orque la lista esta vacia");
									}
									
								} catch (NumberFormatException e) {
									System.out.println(
											"No ha sido posible realizar la operacion, el indice debe de ser un numero");
									break;

								} 
							case 3:
								List<Mensaje> mensajesfecha=new ArrayList<Mensaje>();
								LocalDate fechaInicio = null;
								LocalDate fechaFin = null;
								LocalDate fechaActual = LocalDate.now();
								LocalDateTime fechai,fechaf,fehcaA; 
								String fechaInicioStr="",fechaFinStr="";
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

								try {
									System.out.println("Introduce la fecha de inicio (formato yyyy-MM-dd)  999 para salir:");
									 fechaInicioStr = in.nextLine().trim();
									if (fechaInicioStr.equals("999")){
										break;
									}
									fechaInicio = LocalDate.parse(fechaInicioStr, formatter);

									System.out.println("Introduce la fecha de fin (formato yyyy-MM-dd): 999 para salir");
									 fechaFinStr = in.nextLine().trim();
									if (fechaInicioStr.equals("999")){
										break;
									}
									fechaFin = LocalDate.parse(fechaFinStr, formatter);
									
									//Converir a LolcalDateTime para poder usarlo en filtrarMensajeRangoFechas
									
								    LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay(); 
								    LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);  
								    
									if (servmensaje.verificarfecha(fechaInicio, fechaFin,fechaActual)==-1) {
										
										 System.out.println("La fecha de inicio debe ser anterior o igual a la fecha de fin. Intenta de nuevo.");
									}if(servmensaje.verificarfecha(fechaInicio, fechaFin,fechaActual)==-2){
										
										 System.out.println("La fecha de inicio no puede ser posterior al día actual. Intenta de nuevo.");
									}if(servmensaje.verificarfecha(fechaInicio, fechaFin,fechaActual)==1) {
										
										 if(servmensaje.filtrarMensajeRangoFechas(fechaInicioDateTime, fechaFinDateTime)!=null
												 && !servmensaje.filtrarMensajeRangoFechas(fechaInicioDateTime, fechaFinDateTime).isEmpty()) {
										mensajesfecha=servmensaje.filtrarMensajeRangoFechas(fechaInicioDateTime, fechaFinDateTime);
										System.out.println("Rango de fechas elegido: " + fechaInicio + " a " + fechaFin);
										for (Mensaje ms : mensajesfecha) {
											System.out.println(ms);
										}
									
								}else {
									System.out.println("No hay mensajes para el rango de fechas elegido");
								}
									}	 
									} catch (NumberFormatException e) {
									System.out.println("Error al convertir el id de usuario a número: " + e.getMessage());
								} catch (DateTimeParseException e) {
									System.out.println("Formato de fecha no válido. Debes de usar el formato yyyy-MM-dd.");

								}


								break;
							
							}
				    	
				    	
				    	
				    	}while(opcion.equals("9999"));
					case 99:
						break;
					default:
						System.out.println("Opción incorrecta.");
						break;

					}
					 
				} while (opcionInt != 99);
			}
		    
		    public void mostrarMenuGestionPersonas(Long idpersona) {
		        Scanner in = new Scanner(System.in);
		        System.out.println("Gestion de ejemplares");
		        String opcion = "";
		        int opcionInt = -1;

		        do {
		            System.out.println("\n\tSeleccione una opción:\n");
		            System.out.println("\t1.  Registrar persona para dar de alta un nuevo usuario de perfil Personal\n");
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
		                break;
		            } else if (opcionInt < 1 || opcionInt > 1) {
		                System.out.println("Opción incorrecta.");
		                continue;
		            }

		            switch (opcionInt) {
		          
		                case 1:
		                    String contraseña = "", nombre = "", email = "", usuario = "";
		                 
		                    do {
		                        System.out.println("Dame el nombre de la persona que quieres registrar (9999 para cancelar el proceso)");
		                        nombre = in.nextLine().trim().toUpperCase();
		                        if (nombre.equals("9999")) break;

		                        System.out.println("Dame el email de la persona que quieres registrar (9999 para cancelar el proceso)");
		                        email = in.nextLine().trim().toUpperCase();
		                        if (email.equals("9999")) break;

		                        System.out.println("Dame el nombre de usuario de la persona que quieres registrar (9999 para cancelar el proceso)");
		                        usuario = in.nextLine().trim().toUpperCase();
		                         if (usuario.equals("9999")) break;

		                        System.out.println("Dame la contraseña de la persona que quieres registrar (9999 para cancelar el proceso)");
		                        contraseña = in.nextLine().trim();
		                        if (contraseña.equals("9999")) break;

		                        switch (servpersona.verificarPersona(nombre, email, usuario, contraseña)) {
		                       
		                        case -1:
		                            mostrarMensajeErrorNombrePersona();
		                        break;
		                        
		                        case -2:
		                            mostrarMensajeErrorEmail();
		                        break;
		                        
		                        case -3:
		                            mostrarMensajeErrorNombreUsuario();
		                            break;
		                        case -4:
		                            mostrarMensajeErrorContrasena();
		                            break;
		                        
		                        case -5:   
		                            System.out.println("Ese nombre de usuario ya existe, intentalo de nuevo con otro");
		                            break;
		                            
		                        case -6:   
		                            System.out.println("Ese email ya existe,intentalo de nuevo con otro");
		                            break;
		                        }
		                        
		                    } while (servpersona.verificarPersona(nombre, email, usuario, contraseña) != 1);
		                    
		                    if (contraseña.equals("9999") || usuario.equals("9999") || email.equals("9999") || nombre.equals("9999")) {
		                        break;
		                    }
		                    
		                    
		                    Persona p=new Persona(nombre,email);
		                    
		                        if (servpersona.insertarPersona(p)) {

		                            System.out.println("Nueva persona registrada con exito: Nombre: " + nombre + " Email: " + email);

		                          
		                                Credenciales c = new Credenciales(usuario, contraseña, p);
		                                if (servcredencial.insertarCredenciales(c)) {
		                                    System.out.println("Se ha completado el registro al completo con exito, el nombre de usuario elegido es " + usuario);
		                                    
		                                } else {
		                                    System.out.println("No se ha podido insertar las credenciales para el usuario por un error a la hora de su inserción");
		                                }
		                            } 
		                        else {
		                            System.out.println("No se ha podido completar el proceso debido a un error a la hora de insertar la persona");
		                        }
		                    
		                    break;

		                default:
		                    System.out.println("Opción no válida.");
		                    break;
		            }
		        } while (opcionInt != 99);
		    }
		    
		    
		    public static void mostrarMensajeErrorEmail() {
		        System.out.println("Error: Formato de correo electrónico no válido.\n");
		        System.out.println("Por favor, asegúrate de que el correo electrónico ingresado cumpla con el siguiente formato:");
		        System.out.println("1. Comienza con letras, números o los caracteres especiales permitidos: ., %, +, -");
		        System.out.println("2. Sigue con un símbolo '@'");
		        System.out.println("3. Incluye un dominio válido, como ejemplo.com, y una extensión entre 2 y 6 letras (por ejemplo, .com, .org, .email)\n");
		        
		        System.out.println("Ejemplos válidos:");
		        System.out.println(" - usuario@example.com");
		        System.out.println(" - nombre.apellido@empresa.org");
		        System.out.println(" - persona+info@dominio.co\n");

		        System.out.println("Ejemplo incorrecto:");
		        System.out.println(" - usuario@ejemplo (falta el dominio o la extensión)\n");
		        
		        System.out.println("Si tienes problemas, revisa el correo electrónico e inténtalo de nuevo.");
		    }
		    
		    public static void mostrarMensajeErrorNombreUsuario() {
		        System.out.println("Error: Formato de nombre de usuario no válido.\n");
		        System.out.println("Por favor, asegúrate de que el nombre de usuario ingresado cumpla con el siguiente formato:");
		        System.out.println("1. Contiene entre 3 y 20 caracteres.");
		        System.out.println("2. Solo usa letras, números y guiones bajos (_). No se permiten espacios ni caracteres especiales.\n");

		        System.out.println("Ejemplos válidos:");
		        System.out.println(" - usuario123");
		        System.out.println(" - nombre_apellido");
		        System.out.println(" - user2023\n");

		        System.out.println("Ejemplo incorrecto:");
		        System.out.println(" - usuario* (caracter especial no permitido)");
		        System.out.println(" - nombre apellido (no se permiten espacios)\n");

		        System.out.println("Si tienes problemas, revisa el nombre de usuario e inténtalo de nuevo.");
		    }
		    
		    public static void mostrarMensajeErrorContrasena() {
		        System.out.println("Error: Formato de contraseña no válido.\n");
		        System.out.println("Por favor, asegúrate de que la contraseña ingresada cumpla con el siguiente formato:");
		        System.out.println("1. Contiene entre 8 y 20 caracteres.");
		        System.out.println("2. Incluye al menos una letra mayúscula, una letra minúscula, un número y un caracter especial (como !, @, #, $).\n");

		        System.out.println("Ejemplos válidos:");
		        System.out.println(" - Password1!");
		        System.out.println(" - Segura$2023");
		        System.out.println(" - Mi@Contra7\n");
		    }
		    
		    public static void mostrarMensajeErrorNombrePersona() {
		        System.out.println("Error: Formato de nombre no válido.\n");
		        System.out.println("Por favor, asegúrate de que el nombre ingresado cumpla con el siguiente formato:");
		        System.out.println("1. Contiene solo letras sin tildes, sin números ni caracteres especiales.");
		        System.out.println("2. Puede incluir espacios o guiones si el nombre es compuesto.");
		        System.out.println("3. Tiene una longitud de entre 2 y 50 caracteres.\n");

		        System.out.println("Ejemplos válidos:");
		        System.out.println(" - Juan Pérez");
		        System.out.println(" - María-José");
		        System.out.println(" - Ana\n");
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
	
	

