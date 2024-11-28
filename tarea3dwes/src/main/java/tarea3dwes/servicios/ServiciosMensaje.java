package tarea3dwes.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tarea3dwes.repositories.MensajeRepository;
@Service
public class ServiciosMensaje {
	@Autowired
	MensajeRepository mensajerepos;
}
