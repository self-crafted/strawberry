package com.github.selfcrafted.strawberry.commands;

import com.github.selfcrafted.strawberry.Server;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class ListCommand extends Command {
    ListCommand() {
        super("list");
        addSyntax((sender, context) -> {
            var players = MinecraftServer.getConnectionManager().getOnlinePlayers();
            var playersNames = players.stream().map(Player::getUsername).collect(StringBuilder::new, StringBuilder::append,
                    (a, b) -> a.append(",").append(b)).toString();
            var playerCount = players.size();
            var maxPlayers = Server.CONFIG.getMaxPlayers()<=0 ? players.size()+1 : Server.CONFIG.getMaxPlayers();
            var message = Component.translatable("commands.list.players",
                    Component.text(playerCount), Component.text(maxPlayers), Component.text(playersNames));
            sender.sendMessage(message);
        });
    }
}
