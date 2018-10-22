package by.smirnov.command;

import by.smirnov.command.agent.*;
import by.smirnov.command.client.*;
import by.smirnov.enumeration.Status;
import by.smirnov.message.enumeration.Type;

import java.util.HashMap;

public class StatusCommandDirector {


    public static HashMap<Status, StatusCommand> getDefaultClientCommands() {
        HashMap<Status, StatusCommand> command = new HashMap<>();

        MessageCommand exit = new ClientExitMessageCommand();
        MessageCommand alredyRegistered = new ClientAlreadyRegisterMessageCommand();
        MessageCommand leave = new ClientLeaveMessageCommand();
        MessageCommand notRegistered = new ClientUnregisteredLeaveContentCommand();

        StatusCommand unregistered = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
                .setMessageCommand(Type.REGISTER, new RegisterClientMessageCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, notRegistered)
                .setMessageCommand(Type.CONTENT, notRegistered)
                .getCommand();

        StatusCommand sleeping = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.CONTENT, new ClientSleepingContentMessageCommand())
                .setMessageCommand(Type.REGISTER, alredyRegistered)
                .setMessageCommand(Type.LEAVE, new ClientSleepingLeaveMessageCommand())
                .getCommand();

        StatusCommand talking = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
                .setMessageCommand(Type.CONTENT, new ClientTalkingContentCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, leave)
                .setMessageCommand(Type.REGISTER, alredyRegistered)
                .getCommand();


        StatusCommand pending = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
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

    public static HashMap<Status, StatusCommand> getDefaultAgentCommands() {
        HashMap<Status, StatusCommand> command = new HashMap<>();
        MessageCommand unregisteredContentLeave = new AgentUnregistedLeaveContentCommand();
        MessageCommand exit = new AgentExitMessageCommand();
        MessageCommand alreadyRegister = new AgentAlreadyRegisterMessageCommand();
        MessageCommand leave = new AgentLeaveMessageCommand();

        StatusCommand unregistered = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
                .setMessageCommand(Type.REGISTER, new RegisterAgentMessageCommand())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.LEAVE, unregisteredContentLeave)
                .setMessageCommand(Type.CONTENT, unregisteredContentLeave)
                .getCommand();

        StatusCommand sleeping = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
                .setMessageCommand(Type.EXIT, exit)
                .setMessageCommand(Type.REGISTER, alreadyRegister)
                .setMessageCommand(Type.LEAVE, leave)
                .setMessageCommand(Type.CONTENT, new AgentSleepingContentMessageHandler())
                .getCommand();

        StatusCommand talking = new StatusCommandBuilder()
                .setStatusCommand(new StatusCommandSwitcher())
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
