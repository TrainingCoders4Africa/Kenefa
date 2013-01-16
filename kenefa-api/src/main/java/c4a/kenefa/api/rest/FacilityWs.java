package c4a.kenefa.api.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.bson.types.ObjectId;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.data.FacilityDao;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.Capacity;
import c4a.kenefa.api.model.embedded.Rating;
import c4a.kenefa.api.model.embedded.Service;

@Path("/facilities")
public class FacilityWs {

	@Inject FacilityDao<Facility> fdao;
	@Inject CountryDao<Country> cdao;
	
	public FacilityWs() {
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON , "text/javascript"})
	public List<Facility> getFacilities(@QueryParam("fields") String fields,
			@DefaultValue("0") @QueryParam("offset") int offset,
			@DefaultValue("90") @QueryParam("limit") int limit) {
		//GenericEntity<List<Facility>> entity = new GenericEntity<List<Facility>>(fdao.getFacilities(offset, limit)){};
		//return CorsFilter.getInstance().customResponse(fdao.getFacilities(offset, limit), "GET , OPTIONS");
		return fdao.getFacilities(offset, limit); //CorsFilter.getInstance().customResponse(entity,"GET, OPTIONS");
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON})
	@Consumes({ MediaType.APPLICATION_JSON})
	public Facility createFacility(Facility facility) {
		fdao.getDao().persist(facility);
		return facility;
	}
	
	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Facility updateFacility(@PathParam("id") String id,Facility facility, @Context UriInfo uriInfo) {
		return fdao.updateFacility(id, facility);
	} 
	
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public void deleteFacility(@PathParam("id") String id) {
		fdao.removeFacility(id);
	} 
 
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Facility getFacilityById(@PathParam("id") String id) {
		return fdao.getDao().find(Facility.class, new ObjectId(id)); 
	}
	
	@Path("/{id : .+}/capacity")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Capacity getCapacityFacility(@PathParam("id") String id) {
		return fdao.getDao().find(Facility.class, new ObjectId(id)).getCapacity();
	}
	
	@Path("/{id : .+}/service")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Service getServiceFacility(@PathParam("id") String id) {
		return fdao.getDao().find(Facility.class, new ObjectId(id)).getService();		
	}
	
	@Path("/{id : .+}/rating")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Rating getRatingFacility(@PathParam("id") String id) {
		return fdao.getDao().find(Facility.class, new ObjectId(id)).getRating();
	}

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Facility> getFacilityByCountryAndCity(
			@QueryParam("cityName") String cityName,
			@QueryParam("country") String country,
			@QueryParam("city") Integer city) {
		if (cityName == null && country != null && city != null)
			return cdao.getFacilitiesByCountryAndCity(country, city);
		else if (cityName != null && country != null)
			return cdao.getFacilitiesByCountryAndCityName(country, cityName);
		else return null;
	}
}
