package c4a.kenefa.api.model.embedded;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.org.apache.xpath.internal.operations.Equals;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City {
	private String name = null;

	public City() {
		super();
	}

	public City(String name) {
		super();
		this.name = name;
	}

	// getters and setters
	@Column(name="city")
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
}