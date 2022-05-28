package com.github.selfcrafted.strawberry.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

public class Commands {
    public static final Command SHUTDOWN = new ShutdownCommand();
    public static final Command RESTART = new RestartCommand();
    public static final Command LIST = new ListCommand();


    public static void register() {
        // Register commands
        var commandManager = MinecraftServer.getCommandManager();
        commandManager.register(SHUTDOWN);
        commandManager.register(RESTART);
        commandManager.register(LIST);
    }
}
