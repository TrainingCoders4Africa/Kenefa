package c4a.kenefa.api.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryFull extends Country{
//	private String id;
//	private String name = null;
//	private String description;
	@XmlElement
	private Map<String, Long> statistics;	
	@XmlElement
	private Map<String, String> topFacilities= new HashMap<String, String>();
//	@XmlElement
//	private Collection<City> cities=new ArrayList<City>();
	
	public CountryFull() {
		super();
	}

	// getters and setters
//	public String getId() {
//		return id;
//	}
	
	public Map<String, String> getTopFacilities() {
		return topFacilities;
	}

	public void setTopFacilities(Map<String, String> topFacilities) {
		this.topFacilities = topFacilities;
	}
	
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
}
