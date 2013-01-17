package c4a.kenefa.api.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import c4a.kenefa.api.model.CityFull;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.CountryFull;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.City;

public class CountryDaoImpl<E> implements CountryDao<E>{
	@Inject
	private DAO<Country> dao;
	private static final Logger LOGGER = Logger.getLogger(CountryDao.class);
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountries(){
		String qstr = "SELECT c FROM " + Country.class.getName()+" c order by c.name";
		EntityManager em = dao.getEm();
		Query query = em.createQuery(qstr);
		List<Country> l= query.getResultList();
		em=null;
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<Facility> getFacilitiesByCountry(String country){
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+country+"'";
		Query query = dao.getEm().createQuery(qstr);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Facility> getFacilitiesByCountryAndCityName(String country, String cityName){
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where upper(f.country) = '"+country.toUpperCase()+"' " +
				"and upper(f.cityName) = '" + cityName.toUpperCase() +"'";
		Query query = dao.getEm().createQuery(qstr);
		return query.getResultList();
	}
	
	@Override
	public Country getCountryById(String id){
		return dao.getEm().find(Country.class, id);
	}
	
	@Override
	public CountryFull getCountryFullById(String id) {
		Country country= dao.getEm().find(Country.class, id);
		CountryFull cf= new CountryFull(country);
		cf.setCities(country.getCities());
//		cf.setDescription(country.getDescription());
//		cf.setId(country.getId());
//		cf.setName(country.getName());
		cf.setStatistics(getCountryStatistics(id));
		Map<String, Long> map = getCountryStatistics(id);
		LOGGER.info("COUNTRY sTATIsTICs :" +map.size());
		cf.setTopFacilities(getCountryTopBottomFacilities(id, "ASC", 5));
		cf.setBottomFacilities(getCountryTopBottomFacilities(id, "DESC", 5));
		return cf;
	}
	
	@Override
	public CityFull getCityFullByCountryAndName(String countryId, String city) {
		Country country= dao.getEm().find(Country.class, countryId);
		Object[] cities =country.getCities().toArray();
		for(int i=0;i<cities.length;i++){
			if(((City)cities[i]).getName().equals(city)){
				break;
			}
			if(i==cities.length && !((City)cities[i]).getName().equals(city)){
				return null;
			}
		}
		CityFull ct= new CityFull();
		ct.setName(city);
		ct.setStatistics(getCityStatistics(country.getName(), city));
		ct.setTopFacilities(getCityTopFacilities(country.getName(), city, 5));
		return ct;
	}
	
	private Map<String, Long> getCountryStatistics(String countryName){
		Map<String, Long> hm = new HashMap<String, Long>();
		String qstr="SELECT f.type, COUNT(f.id) FROM "+Facility.class.getName()+
				" f WHERE f.country='"+countryName+"' GROUP BY f.type";
		TypedQuery<Object[]> query =dao.getEm().createQuery(qstr,Object[].class);
		List<Object[]> results = query.getResultList();
		for(Object[] result : results){
			hm.put((String)result[0], (Long)result[1]);
		}
		return hm;
	}

	private Map<String, Long> getCityStatistics(String countryName, String city){
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
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Facility> getFacilitiesByCountryAndCity(String country, Integer idCity){
		String qstr = "SELECT f FROM " + Facility.class.getName()
				+ " f where upper(f.country) = '" + country.toUpperCase() + "' and f.city = "
				+ idCity;
		Query query = dao.getEm().createQuery(qstr);
		return query.getResultList();
	}
	
//	@SuppressWarnings("unchecked")
//	private List<Facility> getCountryTopFacilities(String country, int number){
//		//HashMap<String, String> topFacilities= new HashMap<String, String>();
//		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+
//		country+"' ORDER BY f.rating.overall ASC";
//		Query query = dao.getEm().createQuery(qstr);
//		List<Facility> fc=query.setMaxResults(number).getResultList();
////		for(Facility f: fc){
////			topFacilities.put(f.getName(), f.getAddress());
////		}
//		return fc;// topFacilities;
//	}
	
	@SuppressWarnings({ "unchecked" })
	private List<Facility> getCountryTopBottomFacilities(String country, String sens,  int number){
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+
				country+"' ORDER BY f.rating.overall " + sens;
				Query query = dao.getEm().createQuery(qstr);
		return query.setMaxResults(number).getResultList();
	}
	
	private Map<String, String> getCityTopFacilities(String country, String cityName, int number){
		HashMap<String, String> topFacilities= new HashMap<String, String>();
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+
		country+"' AND upper(f.city)= '"+cityName.toUpperCase()+"' ORDER BY f.rating.overall ASC";
		Query query = dao.getEm().createQuery(qstr);
		List<Facility> fc=query.setMaxResults(number).getResultList();
		for(Facility f: fc){
			topFacilities.put(f.getId().toString(), f.getName());
		}
		return topFacilities;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryFull> getNumberFacilitiesByCountry(int number){
		List<CountryFull> l=new LinkedList<CountryFull>();
		String jpql="select f.country, count(f.id) from " + Facility.class.getName() + " f group by f.country";
		Query query = dao.getEm().createQuery(jpql);
		List<Object[]> r= query.setMaxResults(number).getResultList();
		for(Object[] obj:r){
			CountryFull cf= new CountryFull((Country)dao.getEm().find(Country.class, obj[0]));
			Map<String, Long> statistics=new HashMap<String, Long>();
			statistics.put(cf.getName(), (Long)obj[1]);
			cf.setNumberFacilies((Long)obj[1]);
			cf.setStatistics(statistics);
			l.add(cf);
		}
		return l;
	}
}
