package c4a.kenefa.api.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.Facility;

public class CountryDaoImpl<E> implements CountryDao<E>{
	@Inject
	private DAO<Country> dao;
	
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountries(){
		String qstr = "SELECT c FROM " + Country.class.getName()+" c order by c.name";
		EntityManager em = dao.getEm();
		Query query = em.createQuery(qstr);
		List<Country> l= query.getResultList();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<Facility> getFacilitiesByCountry(String country){
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+country+"'";
		Query query = dao.getEm().createQuery(qstr);
		return query.getResultList();
	}

	@Override
	public Country getCountryById(Integer id) {
		Country country= dao.getEm().find(Country.class, id);
		return country;
	}

}
