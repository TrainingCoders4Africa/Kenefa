package c4a.kenefa.api.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CityFull {
	@XmlElement
	private String name;
	@XmlElement
	private Map<String, Long> statistics = new HashMap<String, Long>();
	@XmlElement
	private Map<String, String> topFacilities= new HashMap<String, String>();
	public String getName() {
		return name;
	}	
	
	public Map<String, Long> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<String, Long> statistics) {
		this.statistics = statistics;
	}

	public Map<String, String> getTopFacilities() {
		return topFacilities;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public void setTopFacilities(Map<String, String> topFacilities) {
		this.topFacilities = topFacilities;
	}
	
}
