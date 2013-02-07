package c4a.kenefa.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import c4a.kenefa.api.data.UserDao;
import c4a.kenefa.api.model.User;

@ManagedBean
@ViewScoped
public class AdminBean implements  Serializable{
	private User user;
	@Inject private UserDao<User> udao;
	
	@PostConstruct
	public void init(){
		user=new User();
	}
	
	public void saveUser(){
		udao.getDao().persist(user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation done in success !!!!"));
		this.cancelUser();
	}
	
	public void cancelUser(){
		user=new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
