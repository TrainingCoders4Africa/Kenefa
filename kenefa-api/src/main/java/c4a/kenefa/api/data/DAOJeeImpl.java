package c4a.kenefa.api.data;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
@Alternative
public class DAOJeeImpl<E> implements DAO<E> {
	
	@PersistenceContext(name="kenefa")
	private EntityManager em;

	public DAOJeeImpl() {
	}
	
	public  E getSingleResult(Object objet, Map param) {
		try {
			return (E) (this.getQuery(objet, param)).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private  Query getQuery(Object req, Map param) {
		Query query = null;
		if (req instanceof String) {
			query = this.em.createQuery((String) req);
			if (param == null || param.isEmpty())
				return query;
		} else if (req instanceof Query) {
			query = (Query) req;
			if (param == null || param.isEmpty())
				return (Query) req;
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i + 1, param.get(i));
			}

			return query;
		}
		return query;
	}

	public  List<E> getResultList(String jpql, Map<Integer, Object> param) {
		return this.getQuery(jpql, param).getResultList();
	}

	public  String persist(E objet) {
			em.persist(objet);
		return "succeed";
	}

	public  String remove(E objet) {
			em.remove(objet);
		return "succeed";
	}

	public  E find(Class<E> objet, Object id) {
		return (E) this.em.find(objet, id);
	}
	
	public EntityManager getEm() {
		 return em;
	}
}
