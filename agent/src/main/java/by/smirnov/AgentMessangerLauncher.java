package by.smirnov;

import by.smirnov.enumeration.Role;
import by.smirnov.messenger.Messenger;
import by.smirnov.messenger.MessengerBuilder;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class AgentMessangerLauncher {
    public static void main(String[] args) {
        Messenger client = null;
        MessengerBuilder builder = new MessengerBuilder();
        Properties prop = new Properties();

        try {
            prop.load(ClassLoader.getSystemResourceAsStream("websocket.properties"));
            client = builder.create(Role.AGENT, prop.getProperty("websocket.agent.url"));
        } catch (URISyntaxException e) {
            System.out.println("Not valid URI address, check address");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.start();
        } catch (IOException e) {
            System.out.println("Server is not available now. Try later.");
        } catch (DeploymentException e) {
            System.out.println("You can't to connect to server. Try later.");
        }
    }
}
