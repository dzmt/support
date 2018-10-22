package by.smirnov.rest;

import by.smirnov.facade.User;
import by.smirnov.service.RegistrationService;
import by.smirnov.service.impl.RegistrationServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Path("/registration")
public class RegistrationRest {
    private RegistrationService service = new RegistrationServiceImpl();
    private Gson gson = new GsonBuilder().create();

    @Path("/client")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public String registerClient(
            @FormParam("name") String name,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        if (name != null) {
            User client = service.registerClient(name);
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(120);
            session.setAttribute("user", client);
            return gson.toJson(client.getId());
        } else {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Path("/agent")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public String registerAgent(
            @FormParam("name") String name,
            @FormParam("count") int activeChats,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        if (name != null && activeChats > 0) {
            User agent = service.registerAgent(name, activeChats);
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(60);
            session.setAttribute("user", agent);
            return gson.toJson(agent.getId());
        } else {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
