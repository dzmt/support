package by.smirnov.message.util.impl;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.util.StringMessageParser;

import java.util.HashMap;

public class StringMessageParserImpl implements StringMessageParser {
    private static HashMap<String, Message> commands = new HashMap<>();

    static {
        commands.put("exit", new Message(Type.EXIT, "exit"));
        commands.put("leave", new Message(Type.LEAVE, "leave"));
    }

    public Message parse (String text) {
        String[] tokens = text.trim().split(" ");
        if (text.trim().startsWith("/")) {
            String command = text.trim().toLowerCase().split(" ")[0].substring(1);
            if (commands.containsKey(command)) {
                return commands.get(command);
            } else if (command.equals("register")){
                if (tokens.length >= 2) {
                    Type type = Type.REGISTER;
                    String userName = tokens[1];
                    StringBuilder msg = new StringBuilder();
                    msg.append(userName)
                            .append(" ")
                            .append("1");
                    return new Message(type, msg.toString());
                }

            }
        }
        return new Message(Type.CONTENT, text);

    }

}
