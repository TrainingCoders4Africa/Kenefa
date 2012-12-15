package c4a.kenefa.api.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import c4a.kenefa.api.model.Facility;

public class FacilityDaoImpl<E>  implements FacilityDao<E> {
	
	@Inject
	private DAO<Facility> dao;
	
	
	@SuppressWarnings("unchecked")
	public List<Facility> getFacilities(int offset, int limit){
		String qstr = "SELECT f FROM " + Facility.class.getName()+" f";
		Query query =  dao.getEm().createQuery(qstr);
		query.setFirstResult(offset).setMaxResults(limit);
		return query.getResultList();
	}

	public DAO<Facility> getDao() {
		return dao;
	}
	
	public Facility updateFacility(String id, Facility facility){
		EntityManager em = dao.getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Facility f;//=em.find(Facility.class, id);
		//System.out.println("id f= " + f.getId() + " id facility  = "+ facility.getId() +" facility ="+f.getName());
		//f.setId(id);
		f=em.merge(facility);
		em.remove(em.find(Facility.class, id));//TODO to review this phenomen
		f.setId(id);
		System.out.println("id facility= " + f.getId() +" facility Merged ="+f.getName());
		tx.commit();
		return f;
	}

	@Override
	public void removeFacility(String id) {
		EntityManager em = dao.getEm();
		EntityTransaction tx = em.getTransaction();
				tx.begin();
		Facility f=em.find(Facility.class, id);
		em.remove(f);
		tx.commit();
	}
}
