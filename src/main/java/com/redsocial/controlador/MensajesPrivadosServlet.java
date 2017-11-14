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
import com.redsocial.modelo.MensajesPrivados;
import com.redsocial.persistencia.DAOMensajesPrivados;



/**
 * 
 * @author Usuario
 *
 */
@Controller
public class MensajesPrivadosServlet {

 @RequestMapping("enviar.do")

 public void enviar(HttpServletRequest request,HttpServletResponse response) throws IOException {
	 
	 String destinatario = request.getParameter("emaildestinatario");
	 String emisor = request.getParameter("emailemisor");
	 String mensaje = request.getParameter("mensaje");
 
	 // Montamos la fecha actual para saber cuando se hizo el mensaje.
	 Calendar fecha = new GregorianCalendar();
	 String fechamensaje = "";
     int year = fecha.get(Calendar.YEAR);
     // Se le suma uno, porque calendar.month devuelve de 0-11
     int month = fecha.get(Calendar.MONTH)+1;
     int day = fecha.get(Calendar.DAY_OF_MONTH);
     int hour = fecha.get(Calendar.HOUR_OF_DAY);
     int minute = fecha.get(Calendar.MINUTE);
     String monthS = (month<10)?"0"+month:""+month;
     String dayS = (day<10)?"0"+day:""+day;
     fechamensaje = dayS+"/"+monthS+"/"+year+" "+hour+":"+minute;
	 
	 MensajesPrivados msg = new MensajesPrivados("", fechamensaje, destinatario, emisor, mensaje);
	 ObjectId id = DAOMensajesPrivados.insert(msg);
	 
	 if (id!=null) {
		 response.getWriter().println("<script type=\"text/javascript\">");
		 response.getWriter().println("location='wallMsg.jsp';");
		 response.getWriter().println("</script>");
	 }else {
		 response.getWriter().println("<script type=\"text/javascript\">");
		 response.getWriter().println("location='wallMsg.jsp';");
		 response.getWriter().println("</script>"); 
	 } 
 }

 
 @RequestMapping("mensajes.do")
 public void mensajes(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String destinatario = request.getParameter("emaildestinatario");
	 ArrayList<MensajesPrivados> result = DAOMensajesPrivados.selectMsgUser(destinatario);
	 String json = new Gson().toJson(result);
	 response.getWriter().print(json);
 }
 
 @RequestMapping("deletemsg.do")
 public void deletemsg(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String id_msg = request.getParameter("id");
	 DAOMensajesPrivados.delete(id_msg);

 }
 
 @RequestMapping("editarmsg.do")
 public void editarmsg(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 String idmensaje = request.getParameter("edit-id");
	 String destinatario = request.getParameter("edit-destinatario");
	 String emisor = request.getParameter("edit-emisor");
	 String mensaje = request.getParameter("mensaje");
	 
	// Montamos la fecha actual para saber cuando se hizo la publicacion.
	 Calendar fecha = new GregorianCalendar();
	 String fechamensaje = "";
     int year = fecha.get(Calendar.YEAR);
     // Se le suma uno, porque calendar.month devuelve de 0-11
     int month = fecha.get(Calendar.MONTH)+1;
     int day = fecha.get(Calendar.DAY_OF_MONTH);
     int hour = fecha.get(Calendar.HOUR_OF_DAY);
     int minute = fecha.get(Calendar.MINUTE);
     String monthS = (month<10)?"0"+month:""+month;
     String dayS = (day<10)?"0"+day:""+day;
     fechamensaje = dayS+"/"+monthS+"/"+year+" "+hour+":"+minute;
	
     MensajesPrivados msg = new MensajesPrivados(idmensaje, fechamensaje, destinatario, emisor, mensaje);
     
     DAOMensajesPrivados.update(msg);
     
	 response.getWriter().println("<script type=\"text/javascript\">");
	 response.getWriter().println("location='wallMsg.jsp';");
	 response.getWriter().println("</script>");
     
 }
}
