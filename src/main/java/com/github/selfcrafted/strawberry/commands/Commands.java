package com.github.selfcrafted.strawberry.commands;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.NotNull;

public class Commands {
    public static final Command SHUTDOWN = new ShutdownCommand();
    public static final Command RESTART = new RestartCommand();
    public static final Command LIST = new ListCommand();
    public static final Command UPTIME = new UptimeCommand();


    public static void register(@NotNull CommandManager commandManager) {
        // Register commands
        commandManager.register(SHUTDOWN);
        commandManager.register(RESTART);
        commandManager.register(LIST);
        commandManager.register(UPTIME);
    }
}
