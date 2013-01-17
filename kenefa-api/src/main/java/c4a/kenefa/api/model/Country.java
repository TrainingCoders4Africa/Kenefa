package c4a.kenefa.api.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import c4a.kenefa.api.model.embedded.City;

@Entity
@Table(name = "countries")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {
	@Id	
	@Column(name = "_id")
	protected String id;
	protected String name = null;
	protected String description=null;
	//@XmlTransient
	//@OneToMany(fetch = FetchType.EAGER)
	//private Collection<Facility> facilities;
	@Embedded
	protected Collection<City> cities = new ArrayList<City>();
	protected Double longitude = null;
	protected Double latitude = null;
	
	public Country() {
		super();
	}

	public Country(String name) {
		super();
		this.name = name;
	}

	
	// getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
//	public Collection<Facility> getFacilities() {
//		return facilities;
//	}
//
//	public void setFacilities(Collection<Facility> facilities) {
//		this.facilities = facilities;
//	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<City> getCities() {
		return cities;
	}

	public void setCities(Collection<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "Country [" + (name != null ? "name=" + name + ", " : "")
				+ (cities != null ? "cities=" + cities : "") + "]";
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}