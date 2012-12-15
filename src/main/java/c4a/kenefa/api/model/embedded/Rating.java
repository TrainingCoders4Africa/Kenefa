package c4a.kenefa.api.model.embedded;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rating {
	/**
	 * overall quality
	 */
	private int overall = -1;
	private int training = -1;
	private int knowledge = -1;
	/**
	 * attitude of staff
	 */
	private int staff = -1;
	private int cleanliness = -1;
	/**
	 * state of repair/disrepair of building
	 */
	private int buildingState = -1;
	/**
	 * state of repair/disrepair of equipment
	 */
	private int equipmentState = -1;
	
	public Rating(int overall, int training, int knowledge, int staff,
			int cleanliness, int buildingState, int equipmentState) {
		super();
		this.overall = overall;
		this.training = training;
		this.knowledge = knowledge;
		this.staff = staff;
		this.cleanliness = cleanliness;
		this.buildingState = buildingState; 
		this.equipmentState = equipmentState;
	}
	public Rating() {
		super();
	}
	public int getOverall() {
		return overall;
	}
	public void setOverall(int overall) {
		this.overall = overall;
	}
	public int getTraining() {
		return training;
	}
	public void setTraining(int training) {
		this.training = training;
	}
	public int getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}
	public int getStaff() {
		return staff;
	}
	public void setStaff(int staff) {
		this.staff = staff;
	}
	public int getCleanliness() {
		return cleanliness;
	}
	public void setCleanliness(int cleanliness) {
		this.cleanliness = cleanliness;
	}
	public int getBuildingState() {
		return buildingState;
	}
	public void setBuildingState(int buildingState) {
		this.buildingState = buildingState;
	}
	public int getEquipmentState() {
		return equipmentState;
	}
	public void setEquipmentState(int equipmentState) {
		this.equipmentState = equipmentState;
	}
}
