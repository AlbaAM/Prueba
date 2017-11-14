package com.redsocial.modelo;

/**
 * 
 * @author Usuario
 *
 */
public class MensajesPrivados {
	private String idmensaje;
	private String fecha;
	private String emaildestinatario;
	private String emailemisor;
	private String mensaje;
	
	public MensajesPrivados() {}
	
	public MensajesPrivados(String idmensaje, String fecha, String destinatario, String emisor, String mensaje) {
		super();
		this.idmensaje = idmensaje;
		this.fecha = fecha;
		this.emaildestinatario = destinatario;
		this.emailemisor = emisor;
		this.mensaje = mensaje;
	}

	public String getIdmensaje() {
		return idmensaje;
	}

	public void setIdmensaje(String idmensaje) {
		this.idmensaje = idmensaje;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDestinatario() {
		return emaildestinatario;
	}

	public void setDestinatario(String destinatario) {
		this.emaildestinatario = destinatario;
	}

	public String getEmisor() {
		return emailemisor;
	}

	public void setEmisor(String emisor) {
		this.emailemisor = emisor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "MensajesPrivados [idmensaje=" + idmensaje + ", fecha=" + fecha + ", destinatario=" + emaildestinatario
				+ ", emisor=" + emailemisor + ", mensaje=" + mensaje + "]";
	}
}
