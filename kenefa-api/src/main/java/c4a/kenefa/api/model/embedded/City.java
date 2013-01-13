package c4a.kenefa.api.model.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@Table(name = "countries.cities")
public class City {
	protected Integer id;
	protected String name = null;
	protected Double longitude = null;
	protected Double latitude = null;
	
	public City() {
		super();
	}

	public City(String name) {
		super();
		this.name = name;
	}

	// getters and setters
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "City [ name=" + name + "]";
	}
	@Override
	public boolean equals(Object object){
		if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        return this.name.equals(other.getName());                
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}