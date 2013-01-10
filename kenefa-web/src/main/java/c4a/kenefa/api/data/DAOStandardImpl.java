package c4a.kenefa.api.data;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public class DAOStandardImpl<E> implements DAO<E> {
	
	private EntityManager em;

	public DAOStandardImpl() {
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
			query = this.getEm().createQuery((String) req);
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
			EntityTransaction tx = this.getEm().getTransaction();
			tx.begin();
			this.getEm().persist(objet);
			tx.commit();
		return "succeed";
	}

	public  String remove(E objet) {
		EntityTransaction tx;
			tx = this.getEm().getTransaction();
			tx.begin();
			this.getEm().remove(objet);
			tx.commit();
		return "succeed";
	}

	public  E find(Class<E> objet, Object id) {
		return (E) this.getEm().find(objet, id);
	}
	
	public EntityManager getEm() {
		if(em==null){
			EntityManagerFactory emf = Emf.getEmfactory().getEmf();
			em = emf.createEntityManager();
		}
		 return em;
	}
}
