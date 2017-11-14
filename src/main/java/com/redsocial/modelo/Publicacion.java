package com.redsocial.modelo;

/**
 * 
 * @author Usuario
 *
 */
public class Publicacion {
	
	private String idPublicacion;
	private String email;
	private String nombre;
	private String fecha;
	private String imagen;
	private String mensaje;
	
	public Publicacion() {
		
	}
	
	public Publicacion(String idPublicacion, String email, String nombre, String fecha, String imagen, String mensaje) {
		
		this.idPublicacion= idPublicacion;
		this.email=email;
		this.nombre=nombre;
		this.fecha=fecha;
		this.imagen=imagen;
		this.mensaje=mensaje;	
	}

	public String getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(String idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	@Override
	public String toString() {
		return "Publicaciones [idPublicacion=" + idPublicacion + ", email=" + email + ", nombre=" + nombre + ", fecha="
				+ fecha + ", imagen=" + imagen + ", mensaje=" + mensaje + "]";
	}
	

}
