package ar.edu.unlam.tallerweb1.modelo;

public enum Modalidad {
	CON_AUTO("con_auto"),
	SIN_AUTO("sin_auto");
	
    private final String descripcion;
	
	Modalidad(String descripcion){
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

}
