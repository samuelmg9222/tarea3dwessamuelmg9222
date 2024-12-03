package tarea3dwes.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tarea3dwes.modelo.Ejemplar;
import tarea3dwes.modelo.Planta;
import tarea3dwes.repositories.EjemplarRepository;
import tarea3dwes.repositories.PlantaRepository;

@Service
public class ServiciosEjemplar {

	
	@Autowired
	EjemplarRepository ejemplarrepos;
	@Autowired
	PlantaRepository plantarepos;

	
	
	public void insertarEjempalr(Ejemplar e) {
		ejemplarrepos.saveAndFlush(e);
	}
	
	
	public String generarNombreEjemplar(String codigo) {
        Planta planta = plantarepos.findByCodigo(codigo);
        return  planta.getNombrecomun()+"_"+ejemplarrepos.countByNombreStartingWith(planta.getNombrecomun());
    }
	  public int verificarInsercion(String codigo, List<Planta> pl, int i) {

			if (i < 1 || (i - 1) >= pl.size()) {
				return -1;
			}

			if (generarNombreEjemplar(codigo)==null) {

				return -2;
			}


			return 1;
		}
	  
	  

	

}
