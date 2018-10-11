package by.smirnov;

import by.smirnov.enumeration.Role;
import by.smirnov.messenger.Messenger;
import by.smirnov.messenger.MessengerBuilder;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

public class ClientMessengerLauncher {
    public static void main(String[] args) {
        Messenger client = null;
        MessengerBuilder builder = new MessengerBuilder();

        try {
            client = builder.create(Role.CLIENT, "ws://localhost:8080/client");
        } catch (URISyntaxException e) {
            System.out.println("Not valid URI address, check address");
        }

        try {
            client.start();
        } catch (IOException e) {
            System.out.println("Server is not available now. Try later. First");
        } catch (DeploymentException e) {
            System.out.println("You can't to connect to server. Try later. Second");
        }
    }
}
