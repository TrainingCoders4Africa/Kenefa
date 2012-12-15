package c4a.kenefa.api.data;

import java.util.List;

import c4a.kenefa.api.model.Facility;


public interface FacilityDao<E> {
	public List<Facility> getFacilities(int offset, int limit);
	public DAO<Facility> getDao();
	public Facility updateFacility(Integer id, Facility facility);
	public void removeFacility(Integer id);
}
