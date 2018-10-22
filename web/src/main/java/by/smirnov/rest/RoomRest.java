package by.smirnov.rest;

import by.smirnov.service.RoomService;
import by.smirnov.service.impl.RoomServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/rooms")
public class RoomRest {
    private RoomService service = new RoomServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRooms(
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("0") @QueryParam("count") int count
    ) {
        return service.getRooms(page, count);
    }

    @GET
    @Path("/id")
    public String getRoomById(
            @QueryParam("client") String clientId,
            @QueryParam("agent") String agentId
    ) {
        return service.getRoomById(clientId, agentId);
    }
}
