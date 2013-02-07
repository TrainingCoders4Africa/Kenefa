package c4a.kenefa.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import c4a.kenefa.api.data.UserDao;
import c4a.kenefa.api.model.User;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private static final String NAV_REDIRECT = "?faces-redirect=true";
	private static String HOME_PAGE="/admin/ui/home.xhtml";
	private static String LOGIN_PAGE="/admin/ui/login.xhtml";
	private User user;
	@Inject private UserDao<User> udao;
	private boolean logged=false;
	private String message;
	
	@PostConstruct
	public void init(){
		user=new User();
	}
	
	public String login(){
		User user=udao.getUserById(this.user.getLogin());
		if(user!=null){
			if(user.getPwd().equals(this.user.getPwd())){
				this.logged=true;
				this.message=null;
				return HOME_PAGE+NAV_REDIRECT;
			}
		}
		this.message="Connexion failed, verify your login and password are correct!!!";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Connexion failed, verify your login and password are correct!!!"));
		return null;
	}
	
	public String logout(){
		user=new User();
		this.logged=false;
		return LOGIN_PAGE+NAV_REDIRECT;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isLogged() {
		return logged;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
