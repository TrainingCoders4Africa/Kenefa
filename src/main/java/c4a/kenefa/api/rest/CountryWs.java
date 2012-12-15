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
import c4a.kenefa.cors.CorsFilter;
import javax.ws.rs.core.Response;

@Path("/countries")
public class CountryWs {
	
	@Inject
	CountryDao<Country> cdao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCountries() {
		return CorsFilter.getInstance().customResponse(cdao.getCountries(), "GET");
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCountryById(@PathParam("id") Integer id) {
		return CorsFilter.getInstance().customResponse(cdao.getCountryById(id), "GET");
	}

	@Path("/{id : .+}/cities")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCitiesCountry(@PathParam("id") Integer id) {
		Country country= cdao.getCountryById(id);
				return CorsFilter.getInstance().customResponse(country.getCities(), "GET");
	}
	

	@Path("/{name : .+}/facilities")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Facility> getAllFacilitiesByCountry(@PathParam("name") String country) {
		return cdao.getFacilitiesByCountry(country);
	}

}
