package c4a.kenefa.api.model;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import c4a.kenefa.api.model.embedded.City;

@Entity
@Table(name = "countries")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "_id")
	private String id;
	private String name = null;
	private String description;
	@Embedded
	//@OneToMany(fetch=FetchType.EAGER)
	//@XmlList
	/*@XmlElementWrapper(name="cities")
	@XmlElement(name="city")*/
	@XmlTransient
	private Collection<City> cities=new ArrayList<City>();
	
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