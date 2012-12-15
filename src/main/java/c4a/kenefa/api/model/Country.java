package c4a.kenefa.api.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import c4a.kenefa.api.model.embedded.City;

@Entity
@Table(name = "countries")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id")
	private Integer id;
	private String name = null;
	
	@Embedded
	//@OneToMany(fetch=FetchType.EAGER)
	//@XmlList
	private List<City> cities;
	
	public Country() {
		super();
	}

	public Country(String name) {
		super();
		this.name = name;
	}

	// getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "Country [" + (name != null ? "name=" + name + ", " : "")
				+ (cities != null ? "cities=" + cities : "") + "]";
	}

}