package c4a.kenefa.api.data;

import java.io.Serializable;
import java.util.List;

import c4a.kenefa.api.model.Facility;


public interface FacilityDao<E> extends  Serializable {
	public List<Facility> getFacilities(int offset, int limit);
	public DAO<Facility> getDao();
	public Facility updateFacility(String id, Facility facility);
	public void removeFacility(String id);
}
