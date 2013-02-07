package c4a.kenefa.api.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import c4a.kenefa.api.data.DAO;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryFull extends Country{
//	private String id;
//	private String name = null;
//	private String description;
	//@XmlElement
	//@XmlTransient
	Map<String, Long> statistics=new HashMap<String, Long>();	
	Map<String, Long> statScope=new HashMap<String, Long>();
	//@XmlElement
//	@XmlTransient
//	private Map<String, String> topFacilities= new HashMap<String, String>();
//	@XmlElement
//	private Collection<City> cities=new ArrayList<City>();
	List<Facility> topFacilities;
	List<Facility> bottomFacilities;
	Long numberFacilies=null;
	@Inject
	private DAO<Country> dao;
	
	public CountryFull(Country country){
		this.id=country.getId();
		this.name=country.getName();
		this.description=country.getDescription();
		this.latitude=country.getLatitude();
		this.longitude=country.getLongitude();
	}
	
	public CountryFull(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public CountryFull() {
		super();
	}

	// getters and setters
//	public String getId() {
//		return id;
//	}
//	
//	public Map<String, String> getTopFacilities() {
//		return topFacilities;
//	}
//
//	public void setTopFacilities(Map<String, String> topFacilities) {
//		this.topFacilities = topFacilities;
//	}
	
	public Map<String, Long> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<String, Long> statistics) {
		this.statistics = statistics;
	}

//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getDescription() {
//		return this.description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public Collection<City> getCities() {
//		return cities;
//	}
//
//	public void setCities(Collection<City> cities) {
//		this.cities = cities;
//	}

	@Override
	public String toString() {
		return "Country [" + (name != null ? "name=" + name + ", " : "")
				+ (cities != null ? "cities=" + cities : "") + "]";
	}

	public Long getNumberFacilies() {
		if(numberFacilies==null&& this.id!=null){
			String jpql="SELECT COUNT(f.id) from "+Facility.class.getName()+" f where f.country='"+id+"'";
			try{
				this.numberFacilies=new Long(""+dao.getResultList(jpql, null).size());
				//(Long) dao.getEm().createQuery(jpql).getSingleResult();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return numberFacilies;
	}

	public void setNumberFacilies(Long numberFacilies) {
		this.numberFacilies = numberFacilies;
	}

	public List<Facility> getTopFacilities() {
		if(topFacilities==null){
			this.topFacilities=this.getCountryTopBottomFacilities(id, "ASC", 5);
		}
		return topFacilities;
	}

	public void setTopFacilities(List<Facility> topFacilities) {
		this.topFacilities = topFacilities;
	}

	public List<Facility> getBottomFacilities() {
		if(bottomFacilities==null){
			this.bottomFacilities=this.getCountryTopBottomFacilities(id, "DESC", 5);
		}
		return bottomFacilities;
	}

	public void setBottomFacilities(List<Facility> bottomFacilities) {
		this.bottomFacilities = bottomFacilities;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	private List<Facility> getCountryTopBottomFacilities(String country, String sens,  int number){
		String qstr = "SELECT f FROM " + Facility.class.getName() + " f where f.country = '"+
				country+"' ORDER BY f.rating.overall " + sens;
				Query query = dao.getEm().createQuery(qstr);
		return query.setMaxResults(number).getResultList();
	}

	public Map<String, Long> getStatScope() {
		return statScope;
	}

	public void setStatScope(Map<String, Long> statScope) {
		this.statScope = statScope;
	}
}
