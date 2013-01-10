package c4a.kenefa.api.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
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
	private String id;
	private String name = null;
	private String description=null;

	/*
	 * @XmlElementWrapper(name="cities")
	 * 
	 * @XmlElement(name="city")
	 */

	private Collection<City> cities = new ArrayList<City>();

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

}