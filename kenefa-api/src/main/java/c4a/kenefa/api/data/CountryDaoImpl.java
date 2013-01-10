package c4a.kenefa.api.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import c4a.kenefa.api.model.CityFull;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.CountryFull;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.City;
import c4a.kenefa.api.model.embedded.Number;

public class CountryDaoImpl<E> implements CountryDao<E>{
	@Inject
	private DAO<Country> dao;
	
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountries(){
		String qstr = "SELECT c FROM " + Country.class.getName()+" c";
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
	
	public Country getCountryById(String id){
		return dao.getEm().find(Country.class, id);
		
	}
	
	@Override
	public CountryFull getCountryFullById(String id) {
		Country country= dao.getEm().find(Country.class, id);
		CountryFull cf= new CountryFull();
		cf.setCities(country.getCities());
		cf.setDescription(country.getDescription());
		cf.setId(country.getId());
		cf.setName(country.getName());
		cf.setStatistics(getCountryStatistics(country.getName()));
		cf.setTopFacilities(getCountryTopFacilities(country.getName(), 5));
		return cf;
	}
	@Override
	public CityFull getCityFullByCountryAndName(String countryId, String city) {
		Country country= dao.getEm().find(Country.class, countryId);
		Object[] cities =country.getCities().toArray();
		for(int i=0;i<cities.length;i++){
			if(((City)cities[i]).getName().equals(city)){
				System.out.println("found it");
				break;
			}
			if(i==cities.length && !((City)cities[i]).getName().equals(city)){
				System.out.println(city+" isn't within "+country.getName());
				return null;
			}
		}
		/*
		if(!country.getCities().contains(cityTest)){
			System.out.println(city+" isn't within "+country.getName());
			return null;
		}
		*/
		CityFull ct= new CityFull();
		ct.setName(city);
		ct.setStatistics(getCityStatistics(country.getName(), city));
		ct.setTopFacilities(getCityTopFacilities(country.getName(), city, 5));
		System.out.println("got it");
		return ct;
	}
	public Map<String, Long> getCountryStatistics(String countryName){
		Map<String, Long> hm = new HashMap<String, Long>();
		String qstr="SELECT f.type, COUNT(f.id) FROM "+Facility.class.getName()+
				" f WHERE f.country='"+countryName+"' GROUP BY f.type";
		TypedQuery<Object[]> query =dao.getEm().createQuery(qstr,Object[].class);
		List<Object[]> results = query.getResultList();
		for(Object[] result : results){
			hm.put((String)result[0], (Long)result[1]);
		}
		return hm;
		/*
		Number nb = new Number();//requete sql group by
		List<Facility> facilities=getFacilitiesByCountry(countryName);
		for(Facility f : facilities){
			if(f.getType().equals("CLINIC"))
				nb.clinics++;
			if(f.getType().equals("HOSPITAL"))
				nb.hospitals++;
			if(f.getType().equals("HEALTH CENTER"))
				nb.healthCenters++;
			if(f.getType().equals("DISPENSARY"))
				nb.dispensaries++;
		}
		nb.getFacilitiesNumber();
		return nb;
		*/
	}

	public Map<String, Long> getCityStatistics(String countryName, String city){
		Map<String, Long> hm = new HashMap<String, Long>();
		String qstr="SELECT f.type, COUNT(f.id) FROM "+Facility.class.getName()+
				" f WHERE upper(f.country)='"+countryName.toUpperCase()+"' and upper(f.city)='"+
				city.toUpperCase()+"' GROUP BY f.type";
		TypedQuery<Object[]> query =dao.getEm().createQuery(qstr,Object[].class);
		List<Object[]> results = query.getResultList();
		for(Object[] result : results){
			hm.put((String)result[0], (Long)result[1]);
		}
		return hm;
		/*
		List<Facility> facilities=getFacilitiesByCountryAndCity(countryName,city);
		for(Facility f : facilities){
			if(f.getType().equals("CLINIC"))
				nb.clinics++;
			if(f.getType().equals("HOSPITAL"))
				nb.hospitals++;
			if(f.getType().equals("HEALTH CENTER"))
				nb.healthCenters++;
			if(f.getType().equals("DISPENSARY"))
				nb.dispensaries++;
		}
		nb.getFacilitiesNumber();
		return nb;
		*/
	}
	public List<Facility> getFacilitiesByCountryAndCity(String country, String city){
		String qstr = "SELECT f FROM " + Facility.class.getName()
				+ " f where upper(f.country) = '" + country.toUpperCase() + "' and upper(f.city) = '"
				+ city.toUpperCase() + "'";
		Query query = dao.getEm().createQuery(qstr);
		return query.getResultList();
	}
	public Map<String, String> getCountryTopFacilities(String country, int number){
		HashMap<String, String> topFacilities= new HashMap<String, String>();
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+
		country+"' ORDER BY f.rating.overall ASC";
		Query query = dao.getEm().createQuery(qstr);
		List<Facility> fc=query.setMaxResults(number).getResultList();
		for(Facility f: fc){
			topFacilities.put(f.getId(), f.getName());
		}
		return topFacilities;
	}
	public Map<String, String> getCityTopFacilities(String country, String city, int number){
		HashMap<String, String> topFacilities= new HashMap<String, String>();
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+
		country+"' AND upper(f.city)= '"+city.toUpperCase()+"' ORDER BY f.rating.overall ASC";
		Query query = dao.getEm().createQuery(qstr);
		List<Facility> fc=query.setMaxResults(number).getResultList();
		for(Facility f: fc){
			topFacilities.put(f.getId(), f.getName());
		}
		return topFacilities;
	}
}
