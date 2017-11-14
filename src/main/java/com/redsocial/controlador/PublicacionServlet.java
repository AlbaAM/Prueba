package com.redsocial.controlador;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.redsocial.modelo.Like;
import com.redsocial.modelo.Publicacion;
import com.redsocial.modelo.Respuesta;
import com.redsocial.persistencia.DAOLike;
import com.redsocial.persistencia.DAOPublicaciones;
import com.redsocial.persistencia.DAORespuestas;


/**
 * 
 * @author Usuario
 *
 */
@Controller
public class PublicacionServlet {


 @RequestMapping("publicar.do")

 public void publicar(HttpServletRequest request,HttpServletResponse response) throws IOException {
	 
	 String nombre = request.getParameter("nombre");
	 String email = request.getParameter("email");
	 String mensaje = request.getParameter("mensaje");
 
	 // Montamos la fecha actual para saber cuando se hizo la publicacion.
	 Calendar fecha = new GregorianCalendar();
	 String fechaPublicacion = "";
     int year = fecha.get(Calendar.YEAR);
     // Se le suma uno, porque calendar.month devuelve de 0-11
     int month = fecha.get(Calendar.MONTH)+1;
     int day = fecha.get(Calendar.DAY_OF_MONTH);
     int hour = fecha.get(Calendar.HOUR_OF_DAY);
     int minute = fecha.get(Calendar.MINUTE);
     String monthS = (month<10)?"0"+month:""+month;
     String dayS = (day<10)?"0"+day:""+day;
     fechaPublicacion = dayS+"/"+monthS+"/"+year+" "+hour+":"+minute;
	 
	 Publicacion publicacion = new Publicacion("", email, nombre, fechaPublicacion, "", mensaje);
	 ObjectId id = DAOPublicaciones.insert(publicacion);
	 
	 if (id!=null) {
		 response.getWriter().println("<script type=\"text/javascript\">");
		 response.getWriter().println("location='wall.jsp';");
		 response.getWriter().println("</script>");
	 }else {
		 response.getWriter().println("<script type=\"text/javascript\">");
		 response.getWriter().println("location='wall.jsp';");
		 response.getWriter().println("</script>"); 
	 } 
 }

 
 @RequestMapping("publicaciones.do")
 public void publicaciones(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 ArrayList<Publicacion> result = DAOPublicaciones.selectAll();
	 String json = new Gson().toJson(result);
	 response.getWriter().print(json);
 }
 
 @RequestMapping("deletepubli.do")
 public void deletepubli(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String id_publicacion = request.getParameter("id");
	 DAORespuestas.delete(id_publicacion);
	 DAOLike.deleteAll(id_publicacion);
	 ArrayList<Like> likes = DAOLike.select(id_publicacion);
	 ArrayList<Respuesta> respuestas = DAORespuestas.select(id_publicacion);
	 if (respuestas.size()==0 && likes.size()==0) {
		 DAOPublicaciones.delete(id_publicacion); 
	 }
	 

 }
 
 @RequestMapping("editarpubli.do")
 public void editarpubli(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 String nombre = request.getParameter("edit-nombre");
	 String email = request.getParameter("edit-email");
	 String idPublicacion = request.getParameter("edit-id");
	 String mensaje = request.getParameter("mensaje");
	 
	// Montamos la fecha actual para saber cuando se hizo la publicacion.
	 Calendar fecha = new GregorianCalendar();
	 String fechaPublicacion = "";
     int year = fecha.get(Calendar.YEAR);
     // Se le suma uno, porque calendar.month devuelve de 0-11
     int month = fecha.get(Calendar.MONTH)+1;
     int day = fecha.get(Calendar.DAY_OF_MONTH);
     int hour = fecha.get(Calendar.HOUR_OF_DAY);
     int minute = fecha.get(Calendar.MINUTE);
     String monthS = (month<10)?"0"+month:""+month;
     String dayS = (day<10)?"0"+day:""+day;
     fechaPublicacion = dayS+"/"+monthS+"/"+year+" "+hour+":"+minute;
	
     Publicacion publicacion = new Publicacion(idPublicacion, email, nombre, fechaPublicacion, "", mensaje);
     
     DAOPublicaciones.update(publicacion);
     
	 response.getWriter().println("<script type=\"text/javascript\">");
	 response.getWriter().println("location='wall.jsp';");
	 response.getWriter().println("</script>");
     
 }
}
