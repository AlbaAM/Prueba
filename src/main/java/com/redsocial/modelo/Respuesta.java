package com.redsocial.modelo;

/**
 * 
 * @author Usuario
 *
 */
public class Respuesta {

	private String email;
	private String fecha;
	private String idPublicacion;
	private String mensaje;
	private String nombre;
	
	public Respuesta(String email, String fecha, String idPublicacion, String mensaje, String nombre) {
		super();
		this.email = email;
		this.fecha = fecha;
		this.idPublicacion = idPublicacion;
		this.mensaje = mensaje;
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdPublicacion() {
		return idPublicacion;
	}
	public void setIdPublicacion(String idPublicacion) {
		this.idPublicacion = idPublicacion;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
