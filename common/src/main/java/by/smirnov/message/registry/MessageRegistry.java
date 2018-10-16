package by.smirnov.message.registry;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class MessageRegistry {
    private static Properties properties;
    private static HashMap<String, Message> registry;

    static {
        try {
            load("server.messages.properties");
            createMessagesRegistry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load(String path) throws IOException{
        properties = new Properties();
        ClassLoader classLoader = MessageRegistry.class.getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        properties.load(classLoader.getResourceAsStream(path));
    }

    private static void createMessagesRegistry() {
        registry = new HashMap<>();
        properties
                .stringPropertyNames()
                .stream()
                .forEach(key -> {
                    String text = properties.getProperty(key);
                    Message message = createContentMessage(text);
                    registry.put(key, message);
                });
    }
    public static Message getMessage(String name) {
        return registry.get(name);
    }

    private static Message createContentMessage(String text) {
        return new Message(Type.CONTENT, text);
    }


}
