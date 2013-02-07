package c4a.kenefa.api.data;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import c4a.kenefa.api.model.User;

public class UserDaoImpl<E> implements UserDao<E>{
	@Inject
	private DAO<User> dao;
	private static final Logger LOGGER = Logger.getLogger(FacilityDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public DAO<E> getDao() {
		return (DAO<E>) dao;
	}

	@Override
	public List<E> getListUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getUserById(String login) {
		return (E) dao.find(User.class, login);
	}

}
