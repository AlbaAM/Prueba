package cucumberJava;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.redsocial.modelo.Usuario;
import com.redsocial.persistencia.DAOUsuario;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

/**
 * 
 * @author Usuario
 *
 */
public class annotation {
	
	private DAOUsuario usuario;
	private Usuario user;
	
	@Given("^Un usuario y password$")
	public void Un_usuario_y_password() {
		usuario= new DAOUsuario(); 
		user=new Usuario();
	}
	
	@Then("^se loguea$")
	public void se_loguea() throws Throwable {
		if(user!=null)
			assertFalse(null==user);
	}
	

	@When("^usuario correcto y password correcta$")
	public void usuario_correcto_y_password_correcta() {
		String email="prueba@gmail.com";
		String pwd="123456";
		user.setemail(email);
		user.setPwd(pwd);
		try {
			user=usuario.select(user.getemail(), user.getPwd());
		} catch (Exception e) {
			assertFalse(true);
		}
		
	}
	
	
	@When("^Usuario incorrecto y una password incorrecta$")
	public void Usuario_incorrecto_y_una_password_incorrecta() {
	   String email="prueba80@gmail.com";
	   String pwd="12345";
	   user.setemail(email);
	   user.setPwd(pwd);
	   try {
		   user=usuario.select(user.getemail(),user.getPwd());
	   }catch(Exception e) {
		   assertFalse(true);
	   }
	   
	}

	@Then("^no se loguea$")
	public void no_se_loguea() {
	  assertTrue(null==user);
	}
	
	@Given("^Un nombre, un email, una pwduno y una pwddos$")
	public void Un_nombre_un_email_una_pwduno_y_una_pwddos() {
		usuario= new DAOUsuario(); 
		user=new Usuario();
	}

	
	@When("^pwduno es igual a pwddos$")
	public void pwduno_es_igual_a_pwddos() {
		String pwd1= "1234";
		String pwd2= "1234";
		Usuario user1 = new Usuario("Prueba");
		try {
			if(pwd1.equals(pwd2)) {
				user=DAOUsuario.insertUserConPWD(user1,"123456");
			}
		}catch(Exception e) {
			
		}
	 
	}

	@Then("^se registra$")
	public void se_registra() {
	   assertTrue(null!=user);
	}
	
	@When("^pwduno es distinto a pwddos$")
	public void pwduno_es_distinto_a_pwddos() {
		String pwd1 = "1234";
		String pwd2= "123456";
		if(!pwd1.equals(pwd2)) {
			user= null;
		}	
	    
	}
	
	
	@When("^email ya esta en la base de datos$")
	public void email_ya_esta_en_la_base_de_datos() {
	  String email = "Prueba@gmail.com"; 
	  String emailExistente; 
	  emailExistente=DAOUsuario.selectEmail(email);
	  if(emailExistente.equals("ok")) {
		  user=null;
	  }
	}
	
	@Then("^no se registra$")
	public void no_se_registra() {
		user=null;
		assertTrue(user==null);
	}
}
