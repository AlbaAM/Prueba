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
import com.redsocial.modelo.Respuesta;
import com.redsocial.persistencia.DAORespuestas;



/**
 * 
 * @author Usuario
 *
 */
@Controller
public class RespuestasServlet {

	 @RequestMapping("responder.do")
	 public void responder(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		 String email = request.getParameter("email");
		 String idPublicacion = request.getParameter("idPublicacion");
		 String mensaje = request.getParameter("mensaje");
		 String nombre = request.getParameter("nombre");
	 
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
	     
		 Respuesta respuesta = new Respuesta(email, fechaPublicacion, idPublicacion, mensaje, nombre);
		 ObjectId id = DAORespuestas.insert(respuesta);
		 
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
	
	 @RequestMapping("respuestas.do")
	 public void respuestas(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		 String idPublicacion = request.getParameter("idPublicacion");
		 ArrayList<Respuesta> result = DAORespuestas.select(idPublicacion);
		 String json = new Gson().toJson(result);
		 response.getWriter().print(json);
	 }
}
