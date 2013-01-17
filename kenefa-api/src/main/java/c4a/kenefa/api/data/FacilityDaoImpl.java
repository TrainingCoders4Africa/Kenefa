package c4a.kenefa.api.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import c4a.kenefa.api.model.Facility;

public class FacilityDaoImpl<E>  implements FacilityDao<E> {
	
	@Inject
	private DAO<Facility> dao;
	private static final Logger LOGGER = Logger.getLogger(FacilityDaoImpl.class);
	
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
	
	public Facility updateFacility(Long id, Facility facility){
		EntityManager em = dao.getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Facility f = em.find(Facility.class, id);
		f=this.prepareFacility(f, facility);
		//System.out.println("id f= " + f.getId() + " id facility  = "+ facility.getId() +" facility ="+f.getName());
		//f.setId(id);
		//em.remove(em.find(Facility.class, id));//TODO to review this phenomen
		//facility=em.merge(f);
		em.persist(f);
		LOGGER.info("id facility= " + facility.getId() +" facility Merged = "+facility.getName());
		tx.commit();
		return facility;
	}
	
	/**
	 * TODO to perform update operation incomprehensive phenomen with Mongo 
	 * @param fManaged
	 * @param fNew
	 * @return
	 */
	private Facility prepareFacility(Facility fManaged, Facility fNew){
		fManaged.setUrl(fNew.getUrl());
		fManaged.setType(fNew.getType());
		fManaged.setService(fNew.getService());
		fManaged.setScope(fNew.getScope());
		fManaged.setRating(fNew.getRating());
		fManaged.setPhone(fNew.getPhone());
		fManaged.setName(fNew.getName());
		fManaged.setLongitude(fNew.getLongitude());
		fManaged.setLatitude(fNew.getLatitude());
		fManaged.setDescription(fNew.getDescription());
		fManaged.setCountry(fNew.getCountry());
		fManaged.setCityName(fNew.getCityName());
		fManaged.setCity(fNew.getCity());
		fManaged.setCapacity(fNew.getCapacity());
		fManaged.setBirth(fNew.getBirth());
		fManaged.setAddress(fNew.getAddress());
		fManaged.setOpeningHours(fNew.getOpeningHours());
		return fManaged;
	}

	@Override
	public void removeFacility(Long id) {
		EntityManager em = dao.getEm();
		EntityTransaction tx = em.getTransaction();
				tx.begin();
		Facility f=em.find(Facility.class, id);
		em.remove(f);
		tx.commit();
	}
}
