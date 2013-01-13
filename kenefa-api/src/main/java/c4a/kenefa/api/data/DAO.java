package c4a.kenefa.api.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
public interface DAO<E> extends  Serializable{
	public E getSingleResult(Object objet, Map param) ;
	public List<E> getResultList(String jpql, Map<Integer, Object> param) ;
	public String persist(E objet);
	public String remove(E objet) ;
	public E find(Class<E> objet, Object id) ;
	public EntityManager getEm();
}
