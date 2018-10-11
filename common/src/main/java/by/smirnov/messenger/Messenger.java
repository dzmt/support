package by.smirnov.messenger;

import by.smirnov.connection.OutboundConnection;
import by.smirnov.enumeration.Role;
import by.smirnov.observer.MessageListener;
import by.smirnov.user.User;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class Messenger {
    private MessageListener userListener;
    private MessageListener serverListener;
    private OutboundConnection connection;

    private boolean isOpen;
    private Status status;
    private Role role;
    private User current;
    private User interlocutor;

    public Messenger() {
        this.isOpen = true;
        this.current = new User();
        this.interlocutor = new User();
        this.status = Status.UNREGISTERED;
    }

    public void start() throws IOException, DeploymentException {
        connection.connectToServer();
        try {

            while (isOpen && connection.isOpen()) {

                userListener.listen();
                serverListener.listen();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("Server is not available now.");
        }
    }

    public void stop() {
        isOpen = false;
    }

    public void setUserListener(MessageListener userListener) {
        this.userListener = userListener;
    }

    public void setServerListener(MessageListener serverListener) {
        this.serverListener = serverListener;
    }

    public User getCurrent() {
        return current;
    }

    public User getInterlocutor() {
        return interlocutor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setConnection(OutboundConnection connection) {
        this.connection = connection;
    }
}
