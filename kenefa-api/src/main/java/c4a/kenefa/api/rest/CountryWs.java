package c4a.kenefa.api.rest;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.model.CityFull;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.CountryFull;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.City;

@Path("/countries")
public class CountryWs {
	
	@Inject
	CountryDao<Country> cdao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Country> getCountries() {
		return cdao.getCountries();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public CountryFull getCountryById(@PathParam("id") String id) {
		return cdao.getCountryFullById(id);
	}
	
	@GET
	@Path("/{id}/cities/{city}/facilities")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Facility> getFacilitiesByCountryAndCity(@PathParam("id") String id, @PathParam("city") Integer city){
		return cdao.getFacilitiesByCountryAndCity(id, city);
	}
	
	@GET
	@Path("/stat/facilities")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<CountryFull> getNumberFacilitiesByCountry(@DefaultValue("5") @QueryParam("number") Integer number){
		return cdao.getNumberFacilitiesByCountry(number);
	}

	@Path("/{id : .+}/cities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<City> getCitiesCountry(@PathParam("id") String id) {
		Country country= cdao.getCountryById(id);
		return country.getCities();
	}
	

	@Path("/{id : .+}/facilities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Facility> getAllFacilitiesByCountry(@PathParam("id") String id) {
		String name=cdao.getCountryById(id).getName();
		return cdao.getFacilitiesByCountry(name);		
	}
	
	@Path("/{id : .+}/{city : .+}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public CityFull getCityFullByName(@PathParam("id") String id, @PathParam("city") String city){
		return cdao.getCityFullByCountryAndName(id, city);
	}
	
}
