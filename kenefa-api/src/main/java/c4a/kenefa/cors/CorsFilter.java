package c4a.kenefa.cors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class CorsFilter {
	 /*private List<String> allowOrigins = Collections.emptyList();
	 
	 private boolean effectiveAllowAllOrigins(CrossOriginResourceSharing ann) {
        if (ann != null) {
            return ann.allowAllOrigins();
        } else {
            return allowOrigins.isEmpty();
        }
    }
	 
    public boolean effectiveAllowOrigins(CrossOriginResourceSharing ann, List<String> origins) {
        if (effectiveAllowAllOrigins(ann)) {
            return true;
        }
        List<String> actualOrigins = Collections.emptyList(); 
        if (ann != null) {
            actualOrigins = Arrays.asList(ann.allowOrigins());
        } 
        
        if (actualOrigins.isEmpty()) {
            actualOrigins = allowOrigins;
        }
        
        return actualOrigins.containsAll(origins);
    }*/
	
	private static CorsFilter instance= new CorsFilter();
	
	private CorsFilter(){
		
	}
	
	public static CorsFilter getInstance(){
		return instance;
	}
	
	public Response customResponse(Object entity, String method){
		ResponseBuilder builder = Response.ok().entity(entity);
		builder.header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, method)
				.header(CorsHeaderConstants.HEADER_ORIGIN, "*")
				.header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "false")
				.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
				.build();
		//Calendar.getInstance().set(2013, 1, 31, 17, 0); //31-Jan-2013 à 17h00
		//Date date = Calendar.getInstance().getTime();
		//builder.expires(date);
		/*CacheControl cc = new CacheControl();
		cc.setMaxAge(300);
		cc.setPrivate(true);
		cc.setNoStore(true);
		builder.cacheControl(cc);*/
		return builder.build();
	}
}
