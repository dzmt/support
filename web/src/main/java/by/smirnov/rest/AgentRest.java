package by.smirnov.rest;

import by.smirnov.service.AgentService;
import by.smirnov.service.impl.AgentServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/agents")
public class AgentRest {
    private AgentService service = new AgentServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgents(
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("0") @QueryParam("count") int count
    ) {
        return  service.getAgents(page, count);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgentById(@PathParam("id") String id) {
        return service.getAgentById(id);
    }

    @GET
    @Path("/ready")
    @Produces(MediaType.APPLICATION_JSON)
    public String getReadyAgents(
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("0") @QueryParam("count") int count
    ) {
        return service.getReadyAgents(page, count);
    }

    @GET
    @Path("/ready/count")
    public String getCountReadyAgents() {
        return service.getSizeReadyAgents();
    }

}
