package c4a.kenefa.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import c4a.kenefa.api.model.embedded.Capacity;
import c4a.kenefa.api.model.embedded.Rating;
import c4a.kenefa.api.model.embedded.Service;

@Entity
@Table(name = "facilities")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Facility implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "_id")
	@XmlTransient
	private String id=null;
	private String name = null;
	private String address = null;
	private String phone = null;
	private String scope = null;
	private String cityName = null;
	private Integer city = null;
	private String country = null;
	private String url;
	private String openingHours = null;
	private Double longitude = null;
	private Double latitude = null;
	private String type = null; // (physician, clinic, hospital, nursing home)
	private Date birth = null;
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Country country;

	@Embedded
	@XmlElement
	private Capacity capacity = null;
	@Embedded
	@XmlElement
	private Service service = null;
	@Embedded
	@XmlElement
	private Rating rating = null;
	
	/**
	 * Interestingly, one of the key determinants on how patients rank their experience with a health facility is the availability of drugs
	 * this part surely will be implemented next.....
	 */
	/*
	@Embedded
	private Pharmacy pharmacy = null;
	*/
	
	

	public Facility(String name, String address, String phone, String scope,
			Integer city, String country, String url, String openingHours,
			Double longitude, Double latitude, String type, Date birth,
			Capacity capacity, Service service, Rating rating) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.scope = scope;
		this.city = city;
		this.country = country;
		this.url = url;
		this.openingHours = openingHours;
		this.longitude = longitude;
		this.latitude = latitude;
		this.type = type;
		this.birth = birth;
		this.capacity = capacity;
		this.service = service;
		this.rating = rating;
	}

	public Facility(String name, String address, Double longitude,
			Double latitude) {
		super();
		this.name = name;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Facility(String name, String address, String phone,
			Double longitude, Double latitude, String type, Date birth,
			Capacity capacity) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.longitude = longitude;
		this.latitude = latitude;
		this.type = type;
		this.birth = birth;
		this.capacity = capacity;
	}
	
	public Facility(Capacity capacity, Service service, Rating rating) {
		super();
		this.capacity = capacity;
		this.service = service;
		this.rating = rating;
	}

	public Facility(String name, String type, String scope, Date birth,
			String address, String phone, String url, Integer city,
			String country, Double longitude, Double latitude,
			Capacity capacity, Service service) {
		super();
		this.name = name;
		this.type = type;
		this.scope = scope;
		this.birth = birth;
		this.address = address;
		this.phone = phone;
		this.url = url;
		this.city = city;
		this.country = country;
		this.longitude = longitude;
		this.latitude = latitude;
		this.capacity = capacity;
		this.service = service;
	}

	public Facility() {
		super();
	}

	// getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Capacity getCapacity() {
		return capacity;
	}

	public void setCapacity(Capacity capacity) {
		this.capacity = capacity;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Facility [" + (name != null ? "name=" + name + ", " : "")
				+ (scope != null ? "scope=" + scope + ", " : "")
				+ (longitude != null ? "longitude=" + longitude + ", " : "")
				+ (latitude != null ? "latitude=" + latitude + ", " : "");
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
		Facility other = (Facility) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String  cityName) {
		this.cityName = cityName;
	}

}