package c4a.kenefa.api.model.embedded;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Number {
	public int facilitiesNumber;
	public int clinics;
	public int hospitals;
	public int polyclinics;
	public int healthCenters;
	public int dispensaries;
	
	public int getFacilitiesNumber() {
		return facilitiesNumber=clinics + hospitals + polyclinics + healthCenters + dispensaries;
	}
	public int getClinics() {
		return clinics;
	}
	public int getHospitals() {
		return hospitals;
	}
	public int getPolyclinics() {
		return polyclinics;
	}
	public int getHealthCenters() {
		return healthCenters;
	}
	public int getDispensaries() {
		return dispensaries;
	}
	public void setClinics(int clinics) {
		this.clinics = clinics;
	}
	public void setHospitals(int hospitals) {
		this.hospitals = hospitals;
	}
	public void setPolyclinics(int polyclinics) {
		this.polyclinics = polyclinics;
	}
	public void setHealthCenters(int healthCenters) {
		this.healthCenters = healthCenters;
	}
	public void setDispensaries(int dispensairies) {
		this.dispensaries = dispensairies;
	}
		
	
}
