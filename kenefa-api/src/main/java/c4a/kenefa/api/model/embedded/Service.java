package c4a.kenefa.api.model.embedded;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {
	boolean emergencyCare = false;
	/**
	 * Inpatient/Outpatient service
	 */
	boolean inpatient = false;
	/**
	 * specifics services: pediatric, radiology, pharmacy, and so on
	 */
	boolean pediatric = false;
	boolean radiology = false;
	boolean pharmacy = false;
	
	public Service(boolean emergencyCare, boolean inpatient, boolean pediatric,
			boolean radiology, boolean pharmacy) {
		super();
		this.emergencyCare = emergencyCare;
		this.inpatient = inpatient;
		this.pediatric = pediatric;
		this.radiology = radiology;
		this.pharmacy = pharmacy;
	}
	public Service() {
		super();
	}
	public boolean isEmergencyCare() {
		return emergencyCare;
	}
	public void setEmergencyCare(boolean emergencyCare) {
		this.emergencyCare = emergencyCare;
	}
	public boolean isInpatient() {
		return inpatient;
	}
	public void setInpatient(boolean inpatient) {
		this.inpatient = inpatient;
	}
	public boolean isPediatric() {
		return pediatric;
	}
	public void setPediatric(boolean pediatric) {
		this.pediatric = pediatric;
	}
	public boolean isRadiology() {
		return radiology;
	}
	public void setRadiology(boolean radiology) {
		this.radiology = radiology;
	}
	public boolean isPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(boolean pharmacy) {
		this.pharmacy = pharmacy;
	}
	
}
