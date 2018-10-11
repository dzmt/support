package by.smirnov.command.client;

import by.smirnov.message.enumeration.Type;

public class ClientStatusCommandBuilder {

    private ClientStatusCommand command;

    public ClientStatusCommandBuilder setStatusCommand(ClientStatusCommand command) {
        this.command = command;
        return this;
    }

    public ClientStatusCommandBuilder setMessageCommand(Type type, ClientMessageCommand command) {
        this.command.setCommand(type, command);
        return this;
    }

    public ClientStatusCommand getCommand() {
        return command;
    }
}
