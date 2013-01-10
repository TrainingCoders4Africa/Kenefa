package c4a.kenefa.api.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "typeFacilities")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TypeFacility {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id")
	private String id;
	private String name = null;

	public TypeFacility() {
		super();
	}

	public TypeFacility(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	// getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TypeFacility [name=" + name + "]";
	}

}