package tarea3dwes.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="personas")
public class Persona implements Serializable {
	private static final long serialVersionUID =1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nombre;
	@Column
	private String email;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="idpersona")
	private List<Mensaje> mensjaes=new LinkedList<Mensaje>();
	 @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "idcredencial", referencedColumnName = "id")
	    private Credenciales credenciales;
	 @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
	    private List<Seguimiento> seguimientos;
}
