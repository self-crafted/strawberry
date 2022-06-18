package com.github.selfcrafted.strawberry.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

public class GameModeCommand extends Command {
    GameModeCommand() {
        super("gamemode", "gm");
        var gameModeArg = ArgumentType.Enum("gamemode", GameMode.class);
        addConditionalSyntax(
                (sender, commandString) -> sender instanceof Player,
                (sender, context) -> {
                    var player = ((Player) sender);
                    player.setGameMode(context.get(gameModeArg));
                }, gameModeArg);
    }
}
