package c4a.kenefa.api.rest;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.model.CityFull;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.City;
import c4a.kenefa.cors.CorsFilter;

@Path("/countries")
public class CountryWs {
	
	@Inject
	CountryDao<Country> cdao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCountries() {
		GenericEntity<List<Country>> entity = new GenericEntity<List<Country>>(cdao.getCountries()){};
		//return CorsFilter.getInstance().customResponse(cdao.getCountries(), "GET");
		return CorsFilter.getInstance().customResponse(entity,"GET");
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCountryById(@PathParam("id") String id) {
		return CorsFilter.getInstance().customResponse(cdao.getCountryFullById(id), "GET");
	}

	@Path("/{id : .+}/cities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCitiesCountry(@PathParam("id") String id) {
		Country country= cdao.getCountryById(id);
		GenericEntity<Collection<City>> entity = new GenericEntity<Collection<City>>(country.getCities()){};
		return CorsFilter.getInstance().customResponse(entity, "GET");
	}
	

	@Path("/{id : .+}/facilities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllFacilitiesByCountry(@PathParam("id") String id) {
		String name=cdao.getCountryById(id).getName();
		GenericEntity<List<Facility>> entity= new GenericEntity<List<Facility>>(cdao.getFacilitiesByCountry(name)){};
		return CorsFilter.getInstance().customResponse(entity, "GET");		
	}
	
	@Path("/{id : .+}/{city : .+}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCityFullByName(@PathParam("id") String id, @PathParam("city") String city){
		CityFull ct=cdao.getCityFullByCountryAndName(id, city);
		return CorsFilter.getInstance().customResponse(ct, "GET");
	}
	//@Path("/{id : .+}/{city}/facilities")
	
}
