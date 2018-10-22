package by.smirnov.rest;

import by.smirnov.service.ClientService;
import by.smirnov.service.impl.ClientServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/clients")
public class ClientRest {
    private ClientService service = new ClientServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWaitingClients() {
        return service.getWaitingClients();
    }

    @GET
    @Path("/{id}")
    public String getClientById(@PathParam("id") String id) {
        return service.getClientById(id);
    }
}
