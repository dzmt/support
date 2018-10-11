package by.smirnov.command;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.command.agent.AgentStatusCommand;
import by.smirnov.command.agent.AgentStatusCommandBuilder;
import by.smirnov.command.agent.status.AgentStatusCommandSwitcher;
import by.smirnov.command.agent.type.*;
import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.command.client.ClientStatusCommand;
import by.smirnov.command.client.ClientStatusCommandBuilder;
import by.smirnov.command.client.status.ClientStatusCommandSwitcher;
import by.smirnov.command.client.type.*;
import by.smirnov.enumeration.Status;
import by.smirnov.message.enumeration.Type;

import java.util.HashMap;

public class StatusCommandDirector {


    public static HashMap<Status, ClientStatusCommand> getDefaultClientCommands() {
        HashMap<Status, ClientStatusCommand> command = new HashMap<>();

        ClientMessageCommand exit = new ClientExitMessageCommand();
        ClientMessageCommand alredyRegistered = new ClientAlreadyRegisterMessageCommand();
        ClientMessageCommand leave = new ClientLeaveMessageCommand();
        ClientMessageCommand notRegistered = new ClientUnregisteredLeaveContentCommand();

        ClientStatusCommand unregistered = new ClientStatusCommandBuilder()
                .setStatusCommand(new ClientStatusCommandSwitcher())
                .setMessageCommand(Type.REGISTER, new RegisterClientMessageCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, notRegistered)
                .setMessageCommand(Type.CONTENT, notRegistered)
                .getCommand();

        ClientStatusCommand sleeping = new ClientStatusCommandBuilder()
                .setStatusCommand(new ClientStatusCommandSwitcher())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.CONTENT, new ClientSleepingContentMessageCommand())
                .setMessageCommand(Type.REGISTER, alredyRegistered)
                .setMessageCommand(Type.LEAVE, new ClientSleepingLeaveMessageCommand())
                .getCommand();

        ClientStatusCommand talking = new ClientStatusCommandBuilder()
                .setStatusCommand(new ClientStatusCommandSwitcher())
                .setMessageCommand(Type.CONTENT, new ClientTalkingContentCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, leave)
                .setMessageCommand(Type.REGISTER, alredyRegistered)
                .getCommand();


        ClientStatusCommand pending = new ClientStatusCommandBuilder()
                .setStatusCommand(new ClientStatusCommandSwitcher())
                .setMessageCommand(Type.CONTENT, new ClientPendingContentMessageCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, leave)
                .setMessageCommand(Type.REGISTER, alredyRegistered)
                .getCommand();

        command.put(Status.UNREGISTERED, unregistered);
        command.put(Status.SLEEPING, sleeping);
        command.put(Status.TALKING, talking);
        command.put(Status.PENDING, pending);


        return command;
    }

    public static HashMap<Status, AgentStatusCommand> getDefaultAgentCommands() {
        HashMap<Status, AgentStatusCommand> command = new HashMap<>();
        AgentMessageCommand unregisteredContentLeave = new AgentUnregistedLeaveContentCommand();
        AgentMessageCommand exit = new AgentExitMessageCommand();
        AgentMessageCommand alreadyRegister = new AgentAlreadyRegisterMessageCommand();
        AgentMessageCommand leave = new AgentLeaveMessageCommand();

        AgentStatusCommand unregistered = new AgentStatusCommandBuilder()
                .setStatusCommand(new AgentStatusCommandSwitcher())
                .setMessageCommand(Type.REGISTER, new RegisterAgentMessageCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, unregisteredContentLeave)
                .setMessageCommand(Type.CONTENT, unregisteredContentLeave)
                .getCommand();

        AgentStatusCommand sleeping = new AgentStatusCommandBuilder()
                .setStatusCommand(new AgentStatusCommandSwitcher())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.REGISTER, alreadyRegister)
                .setMessageCommand(Type.LEAVE, leave)
                .setMessageCommand(Type.CONTENT, new AgentSleepingContentMessageHandler())
                .getCommand();

        AgentStatusCommand talking = new AgentStatusCommandBuilder()
                .setStatusCommand(new AgentStatusCommandSwitcher())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, leave)
                .setMessageCommand(Type.REGISTER, alreadyRegister)
                .setMessageCommand(Type.CONTENT, new AgentTalkingContentMessageCommand())
                .getCommand();



        command.put(Status.UNREGISTERED, unregistered);
        command.put(Status.SLEEPING, sleeping);
        command.put(Status.TALKING, talking);


        return command;
    }

}
