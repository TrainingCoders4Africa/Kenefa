package c4a.kenefa.api.data;

import java.io.Serializable;
import java.util.List;

public interface UserDao<E> extends Serializable{
	public DAO<E> getDao();
	public List<E> getListUser();
	public E getUserById(String login);
}
