package com.redsocial.modelo;

/**
 * 
 * @author Usuario
 *
 */
public class Like {
  private String emailUsuario;
  private String idPublicacion;
  
  public Like(String email, String idPublicacion2) {
    super();
    this.emailUsuario = email;
    this.idPublicacion = idPublicacion2;
  }

  public String getEmailUsuario() {
    return emailUsuario;
  }

  public void setEmailUsuario(String emailUsuario) {
    this.emailUsuario = emailUsuario;
  }

  public String getIdPublicacion() {
    return idPublicacion;
  }

  public void setIdPublicacion(String idPublicacion) {
    this.idPublicacion = idPublicacion;
  }

  @Override
  public String toString() {
    return "Like [emailUsuario=" + emailUsuario + ", idPublicacion=" + idPublicacion + "]";
  }
}