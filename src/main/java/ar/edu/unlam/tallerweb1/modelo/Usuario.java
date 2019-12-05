package ar.edu.unlam.tallerweb1.modelo;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String email;

	private String password;

	@ManyToMany
	List<Viaje> viajes = new LinkedList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  List<Mail> mails = new ArrayList<>();

	@Enumerated(EnumType.ORDINAL)
	private Rol rol;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Usuario)) {
			return false;
		}

		Usuario usuario = (Usuario) obj;
		return this.id == usuario.id;
	}

	public Usuario(String email, String password){
		this.email= email;
		this.password = password;
	}
	public Usuario(){

	}
}
