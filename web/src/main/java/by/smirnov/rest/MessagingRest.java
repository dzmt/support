package by.smirnov.rest;


import by.smirnov.command.StatusCommand;
import by.smirnov.command.StatusCommandDirector;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.storage.MessageStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


@Path("/msg")
public class MessagingRest {
    private static HashMap<Status, StatusCommand> clientCommands = StatusCommandDirector.getDefaultClientCommands();
    private static HashMap<Status, StatusCommand> agentsCommands = StatusCommandDirector.getDefaultAgentCommands();

    private Gson gson = new GsonBuilder().create();

    @Path("/client/send")
    @POST
    public void handleClientMessage(
            @FormParam("msg") String msg,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
       handleMessage(msg, request, response, clientCommands);
    }



    @Path("/agent/send")
    @POST
    public void handleAgentMessage(
            @FormParam("msg") String msg,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        handleMessage(msg, request, response, agentsCommands);
    }

    @Path("/receive")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserMessageById(
            @QueryParam("id") String id,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LinkedList<Message> messages = MessageStorage.getUserMessagesById(id);
            return gson.toJson(messages);
        } else {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private void handleMessage(
            String msg,
            HttpServletRequest request,
            HttpServletResponse response,
            HashMap<Status, StatusCommand> handler) {

        HttpSession session = request.getSession(false);
        User user = null;
        Message message = null;
        if (session != null) {
            user = (User) request.getSession().getAttribute("user");
            message = gson.fromJson(msg, Message.class);
            try {
                clientCommands.get(user.getStatus()).handle(message, user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
