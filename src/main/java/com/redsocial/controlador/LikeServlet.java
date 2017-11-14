package com.redsocial.controlador;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.Gson;
import com.redsocial.modelo.Like;
import com.redsocial.persistencia.DAOLike;


/**
 * 
 * @author Usuario
 *
 */
@Controller
public class LikeServlet {

  @RequestMapping("darlike.do")

  public void darlike(HttpServletRequest request,HttpServletResponse response) throws Exception {

    String emailUsuario = request.getParameter("email");
    String idPublicacion = request.getParameter("id");

    Like like= new Like(emailUsuario, idPublicacion);
    
    
    DAOLike.insert(like);

  }


 @RequestMapping("likes.do")
 public void respuestas(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String idPublicacion = request.getParameter("idPublicacion");
	 ArrayList<Like> result = DAOLike.select(idPublicacion);
	 String json = new Gson().toJson(result);
	 response.getWriter().print(json);
 }
 
 @RequestMapping("checklike.do")
 public void checklike(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String idPublicacion = request.getParameter("idPublicacion");
	 String emailUsuario = request.getParameter("emailUsuario");
	 Like like = DAOLike.checkLike(idPublicacion, emailUsuario);
	 
	 JSONObject json = new JSONObject();
	 if (like!=null) {
		 json.put("status", "ok");
	 }else {
		 json.put("status", "ko");
	 }
	 
	response.getWriter().print(json);
 }

 @RequestMapping("deletelike.do")
 public void deletelike(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String idPublicacion = request.getParameter("idPublicacion");
	 String emailUsuario = request.getParameter("emailUsuario");
	 Like like = new Like (emailUsuario,idPublicacion);
	 DAOLike.delete(like);

 }

}