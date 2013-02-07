package c4a.kenefa.api.model.embedded;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Capacity {

	private Integer bed=0;
	private Integer doctor=0;
	private Integer nurse=0;
	
	public Capacity(Integer bed, Integer doctor, Integer nurse) {
		super();
		this.bed = bed;
		this.doctor = doctor;
		this.nurse = nurse;
	}
	public Capacity() {
		super();
	}
	public Integer getBed() {
		return bed;
	}
	public void setBed(Integer bed) {
		this.bed = bed;
	}
	public Integer getDoctor() {
		return doctor;
	}
	public void setDoctor(Integer doctor) {
		this.doctor = doctor;
	}
	public Integer getNurse() {
		return nurse;
	}
	public void setNurse(Integer nurse) {
		this.nurse = nurse;
	}
}
