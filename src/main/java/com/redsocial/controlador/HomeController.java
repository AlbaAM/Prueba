package com.redsocial.controlador;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.redsocial.modelo.Usuario;
import com.redsocial.persistencia.DAOUsuario;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		Usuario result=DAOUsuario.select(email,pwd); 
		 
		 
		JSONObject json = new JSONObject();
		 if (result!=null) {
			 json.put("status", "ok");
			 json.put("name", result.getNombre());
			 json.put("email", result.getemail());
		 }else {
			 json.put("status", "ko");
			 json.put("message", "No se ha podido loguear");
		 }
		
		response.getWriter().print(json);
		return "wall";
	}
	
}
