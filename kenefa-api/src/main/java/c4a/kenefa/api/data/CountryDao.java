package c4a.kenefa.api.data;

import java.util.List;

import c4a.kenefa.api.model.CityFull;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.CountryFull;
import c4a.kenefa.api.model.Facility;

public interface CountryDao<E> {
	public List<Country> getCountries();
	public List<Facility> getFacilitiesByCountry(String country);
	public CountryFull getCountryFullById(String id);
	public Country getCountryById(String id);
	public CityFull getCityFullByCountryAndName(String countryId, String city);
}
