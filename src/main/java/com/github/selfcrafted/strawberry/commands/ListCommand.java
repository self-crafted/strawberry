package com.github.selfcrafted.strawberry.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

public class ListCommand extends Command {
    ListCommand() {
        super("list");
        addSyntax((sender, context) -> {
            var players = MinecraftServer.getConnectionManager().getOnlinePlayers();
            var playersNames = players.stream().collect(StringBuilder::new, StringBuilder::append,
                    (a, b) -> a.append(",").append(b)).toString();
            var playerCount = players.size();
            // TODO: 28.05.22 make maxplayers configurable
            var maxPlayers = players.size()+1;
            var message = Component.translatable("commands.list.players",
                    Component.text(playerCount), Component.text(maxPlayers), Component.text(playersNames));
            sender.sendMessage(message);
        });
    }
}
