package com.github.selfcrafted.strawberry.commands;

import net.minestom.server.command.builder.Command;

public class Commands {
    public static final Command SHUTDOWN = new ShutdownCommand();
    public static final Command RESTART = new RestartCommand();
}
