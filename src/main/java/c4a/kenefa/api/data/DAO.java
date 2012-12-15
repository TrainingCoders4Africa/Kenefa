package c4a.kenefa.api.data;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import c4a.kenefa.api.model.Facility;
public interface DAO<E> {
	public  E getSingleResult(Object objet, Map param) ;
	public List<E> getResultList(String jpql, Map<Integer, Object> param) ;
	public String persist(E objet);
	public String remove(E objet) ;
	public E find(Class<E> objet, Object id) ;
	public EntityManager getEm();
}
