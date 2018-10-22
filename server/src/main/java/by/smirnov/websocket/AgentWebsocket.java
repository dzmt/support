package by.smirnov.websocket;

import by.smirnov.base.UserBase;
import by.smirnov.coder.MessageDecoder;
import by.smirnov.coder.MessageEncoder;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.handler.DirectorChainHandler;
import by.smirnov.message.Message;
import by.smirnov.message.registry.MessageRegistry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.smirnov.enumeration.Status.UNREGISTERED;

@ServerEndpoint(value="/agent", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class AgentWebsocket extends User {

    private static final Logger logger = LogManager.getLogger(AgentWebsocket.class);
    private static UserBase base = new UserBase();

    public AgentWebsocket() {
    }

    private transient Session session;

    private Status status;
    private String name;
    private String id;
    private int maxCountActiveChat;
    private Set<String> interlocutors;

    @OnOpen
    public void onOpen(Session session) {

        this.session = session;
        status = UNREGISTERED;
        interlocutors = new HashSet<>();
        id = UUID.randomUUID().toString();


        try {
            send(MessageRegistry.getMessage("connection"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        session.addMessageHandler(DirectorChainHandler.buildAgentChain(this));

        logger.info("client has connected: " + "[" + getId() + "] status[" + getStatus() + "]");
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        logger.info("agent close connection with server");

        if (!getStatus().equals(UNREGISTERED)) {
            try {
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("agent[" + getId() + "] status[" + getStatus() + "] - safety exit.");
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.error(error);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setMaxCountActiveChat(int count) {
        this.maxCountActiveChat = count;
    }

    @Override
    public int getMaxCountActiveChat() {
        return maxCountActiveChat;
    }

    @Override
    public Set<String> getInterlocutors() {
        return interlocutors;
    }

    @Override
    public void setInterlocutors(Set<String> interlocutors) {
        this.interlocutors = interlocutors;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void addToBase() {
        base.addAgent(this);
    }

    @Override
    public void addToWaitingRoom() {

    }

    @Override
    public void send(Message message) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public void subscribe(User client) {
        interlocutors.add(client.getId());
    }

    @Override
    public void unsubcribe(User client) {
        interlocutors.remove(client.getId());
    }


    @Override
    public void notifySubscriber(Message message) throws IOException, EncodeException {
        if (interlocutors.contains(message.getTo())) {
            base.getClientById(message.getTo()).send(message);
        }
    }

    @Override
    public boolean subscribeReady() throws IOException, EncodeException {
        User readyUser = base.getWaitingClient();
        if (readyUser != null) {
            subscribeSequence(readyUser);
            LinkedList<Message> buffer = readyUser.readBuffer();
            while (!buffer.isEmpty())
                this.send(buffer.poll());

            return true;
        }
        return false;
    }

    @Override
    public List<User> getListInterlocutors() {
        return base.getClients().stream()
                .filter( client ->
                        interlocutors.contains(client.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public int getQuantityInterlocutors() {
        return interlocutors.size();
    }

    @Override
    public boolean ready() {
        return interlocutors.size() < maxCountActiveChat;
    }

    @Override
    public void removeFromBase() {
        base.removeAgent(this);
    }

    @Override
    public void writeBuffer(Message message) {
    }



    @Override
    public LinkedList<Message> readBuffer() {
        return null;
    }

    @Override
    public void removeFromWaitingRoom() {

    }
}