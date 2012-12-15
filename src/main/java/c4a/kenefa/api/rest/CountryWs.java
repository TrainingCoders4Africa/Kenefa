package c4a.kenefa.api.rest;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.City;

@Path("/countries")
public class CountryWs {
	
	@Inject
	CountryDao<Country> cdao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Country> getCountries() {
		return cdao.getCountries();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Country getCountryById(@PathParam("id") String id) {
		return cdao.getCountryById(id);
	}

	@Path("/{id : .+}/cities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<City> getCitiesCountry(@PathParam("id") String id) {
		Country country= cdao.getCountryById(id);
				return country.getCities();
	}
	

	@Path("/{name : .+}/facilities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Facility> getAllFacilitiesByCountry(@PathParam("name") String country) {
		return cdao.getFacilitiesByCountry(country);
	}

}
