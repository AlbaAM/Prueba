package com.redsocial.controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.redsocial.auxiliares.SendMail;
import com.redsocial.auxiliares.Utilidades;
import com.redsocial.modelo.Usuario;
import com.redsocial.persistencia.DAOUsuario;



/**
 * 
 * @author Usuario
 *
 */
@Controller
public class UsuarioServlet {
 
@Autowired
private DAOUsuario servicioDAOUsuario;


private final static String emaill = "email";
private final static String status = "status";
private final static String ko = "ko";
private final static String ok = "ok";

@RequestMapping("deleteuser.do")
public void deleteuser(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String id_usuario = request.getParameter("id");
	 DAOUsuario.delete(id_usuario);

}

@RequestMapping("usuarios.do")
public void usuarios(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 ArrayList<Usuario> result = DAOUsuario.selectAll();
	 String json = new Gson().toJson(result);
	 response.getWriter().print(json);
}

 @RequestMapping("registro.do")
 public void registro(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String name = request.getParameter("username");
	 String email = request.getParameter(emaill);
	 String password = request.getParameter("password");
	 
	 Usuario user = new Usuario(name, email, password);
	 Usuario usuario = DAOUsuario.insertUserConPWD(user, password);
	 
	 JSONObject json = new JSONObject();
	 if (usuario!=null) {
		 json.put(status, ok);
		 json.put("name", usuario.getNombre());
		 json.put(emaill, usuario.getemail());
	 }else {
		 json.put(status, ko);
		 json.put("message", "No se ha podido loguear");
	 }
	
	response.getWriter().print(json);
	 
 }
 /*
 @RequestMapping("registro.do")
 public void registro(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String name = request.getParameter("nombre");
	 String email = request.getParameter("email");
	 String password = request.getParameter("pwd");
	 System.out.println("nombre:"+name+"email:"+email+"pass:"+password);
	 Usuario user = new Usuario(name, email, password);
	 Usuario usuario = DAOUsuario.insertUserConPWD(user, password);
	 
	 JSONObject json = new JSONObject();
	 if (usuario!=null) {
		 json.put("status", "ok");
		 json.put("name", usuario.getNombre());
		 json.put("email", usuario.getemail());
	 }else {
		 json.put("status", "ko");
		 json.put("message", "No se ha podido loguear");
	 }
	 // si eres administrador ejecuta el de abajo//
	 
	 
	 response.getWriter().print(json);
	 
 }
 */
 @RequestMapping("registroadmin.do")
 public void registroadmin(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String name = request.getParameter("nombre");
	 String email = request.getParameter(emaill);
	 String password = request.getParameter("pwd");
	 System.out.println("nombre:"+name+"email:"+email+"pass:"+password);
	 Usuario user = new Usuario(name, email, password);
	 Usuario usuario = DAOUsuario.insertUserConPWD(user, password);
	
	 JSONObject json = new JSONObject();
	 if (usuario!=null) {
		 json.put(status, ok);
		 json.put("name", usuario.getNombre());
		 json.put(emaill, usuario.getemail());
	 }else {
		 json.put(status, ko);
		 json.put("message", "No se ha podido loguear");
	 }
	 
	 
	 response.getWriter().println("<script type=\"text/javascript\">");
	 response.getWriter().println("location='walladmin.jsp';");
	 response.getWriter().println("</script>");
	//lo quito para admin
	 //response.getWriter().print(json);
	 
 }
 
 @RequestMapping("consultar.do")
 public void consultar(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String email = request.getParameter(emaill);
	 String result ="";
	 result=DAOUsuario.selectEmail(email);
	 response.getWriter().write(result); 

 }
 
 /*@RequestMapping("login.do")
 public void login(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String email = request.getParameter(emaill);
	 String pwd = request.getParameter("password");
	 Usuario result=DAOUsuario.select(email,pwd); 
	 
	 
	JSONObject json = new JSONObject();
	 if (result!=null) {
		 json.put(status, ok);
		 json.put("name", result.getNombre());
		 json.put(emaill, result.getemail());
	 }else {
		 json.put(status, ko);
		 json.put("message", "No se ha podido loguear");
	 }
	
	response.getWriter().print(json);
 }*/
 
 @RequestMapping("forgotpassword.do")
 public void forgotpassword(HttpServletRequest request,HttpServletResponse response) throws JSONException, Exception {
	
	 String email = request.getParameter(emaill);
	 String result ="";
	 result=DAOUsuario.selectEmail(email);
	 
	 if (result==ko) {
		
			Usuario user = DAOUsuario.selectSinPWD(email);
			SendMail send = new SendMail();
			send.sendMail(user.getemail(), Utilidades.Desencriptar(user.getPwd()));
			response.sendRedirect("index.html");
	 }

 }
 
 @RequestMapping("editarusu.do")
 public void editarusu(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 
	 
	 String nombre = request.getParameter("nombre");
	 String email = request.getParameter(emaill);
	 String id_usuario = request.getParameter("id");
	 String pwd= Utilidades.Encriptar(request.getParameter("pwd"));
	 
	// Montamos la fecha actual para saber cuando se hizo la publicaci�n.
	 
	/* idusuario primer argumento*/
     Usuario usuario = new Usuario( id_usuario,nombre, email,pwd);
     
     DAOUsuario.update(usuario);
     
	 response.getWriter().println("<script type=\"text/javascript\">");
	 response.getWriter().println("location='walladmin.jsp';");
	 response.getWriter().println("</script>");
     
 }
 
}
