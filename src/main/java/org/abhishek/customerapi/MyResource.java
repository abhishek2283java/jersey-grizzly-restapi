package org.abhishek.customerapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getIt() {
//        return "Got it!";
//    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt2(@Context UriInfo uriInfo) {
    	System.out.println("Absolute path: " + uriInfo.getAbsolutePath().toString());
    	System.out.println("Get path: " + uriInfo.getPath().toString());
    	
    	
    	MultivaluedMap<String,String> queryParameters = uriInfo.getQueryParameters();
    	System.out.println(queryParameters);
    	
        return "Got it!";
    }
}
