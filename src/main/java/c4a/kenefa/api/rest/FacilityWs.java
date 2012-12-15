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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import c4a.kenefa.api.data.FacilityDao;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.Capacity;
import c4a.kenefa.api.model.embedded.Rating;
import c4a.kenefa.api.model.embedded.Service;
import c4a.kenefa.cors.CorsFilter;

@Path("/facilities")
public class FacilityWs {

	@Inject
	FacilityDao<Facility> fdao;
	
	public FacilityWs() {
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFacilities(@QueryParam("fields") String fields,
			@DefaultValue("0") @QueryParam("offset") int offset,
			@DefaultValue("90") @QueryParam("limit") int limit) {
		return CorsFilter.getInstance().customResponse(fdao.getFacilities(offset, limit), "GET");
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Facility createFacility(Facility facility) {
		fdao.getDao().persist(facility);
		return facility;
	}
	
	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Facility updateFacility(@PathParam("id") Integer id,Facility facility, @Context UriInfo uriInfo) {
		for(String uri : uriInfo.getMatchedURIs()) {
			System.out.println(uri);
			}
		return fdao.updateFacility(id, facility);
	} 
	
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public void deleteFacility(@PathParam("id") Integer id) {
		fdao.removeFacility(id);
	} 
 
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getFacilityById(@PathParam("id") Integer id) {
		return CorsFilter.getInstance().customResponse(fdao.getDao().find(Facility.class, id), "GET");
	}
	
	@Path("/{id : .+}/capacity")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Capacity getCapacityFacility(@PathParam("id") Integer id) {
		return fdao.getDao().find(Facility.class, id).getCapacity();
	}
	
	@Path("/{id : .+}/service")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Service getServiceFacility(@PathParam("id") Integer id) {
		return fdao.getDao().find(Facility.class, id).getService();
	}
	
	@Path("/{id : .+}/rating")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Rating getRatingFacility(@PathParam("id") Integer id) {
		return fdao.getDao().find(Facility.class, id).getRating();
	}

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Facility> getFacilityByCountryAndCity(
			@QueryParam("name") String name,
			@QueryParam("country") String country,
			@QueryParam("city") String city) {
		String jpql;
		if (name == null && country != null && city != null)
			jpql = "SELECT f FROM " + Facility.class.getName()
			+ " f where upper(f.country) = '" + country.toUpperCase() + "' and upper(f.city) = '"
			+ city.toUpperCase() + "'";
		else if (name != null && country != null && city != null)
			jpql = "SELECT f FROM " + Facility.class.getName()
			+ " f where upper(f.country) = '" + country.toUpperCase() + "' and upper(f.city) = '"
			+ city.toUpperCase() + "'" + " and upper(f.name) like '" + name.toUpperCase() +"%'";
		else return null;
		return fdao.getDao().getResultList(jpql,null);
	}
}
