package org.abhishek.customerapi;

import java.io.IOException;
import java.net.URI;

import org.abhishek.customerapi.dao.CustomerDAO;
import org.abhishek.customerapi.service.CustomerService;
import org.abhishek.customerapi.service.CustomerServiceImpl;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.abhishek.customerapi package
        //final ResourceConfig rc = new ResourceConfig().packages("org.abhishek.customerapi");
        final ResourceConfig config = new ResourceConfig()
        		.packages("org.abhishek.customerapi", "org.abhishek.customerapi.resources");
        
        config.register(new AbstractBinder(){
            @Override
            protected void configure() {
                bind(CustomerServiceImpl.class).to(CustomerService.class);
                bind(CustomerDAO.class).to(CustomerDAO.class);
                //bind(HibernateUtils.class).to(HibernateUtils.class);
            }
        });

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

