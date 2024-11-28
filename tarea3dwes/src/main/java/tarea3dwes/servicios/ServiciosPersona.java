package tarea3dwes.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tarea3dwes.repositories.PersonaRepository;

@Service
public class ServiciosPersona {
@Autowired
PersonaRepository personarepos;
}
