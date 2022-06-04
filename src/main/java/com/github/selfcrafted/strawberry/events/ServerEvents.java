package com.github.selfcrafted.strawberry.events;

import com.github.selfcrafted.strawberry.instances.Instances;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class ServerEvents {
    public static void register(GlobalEventHandler eventHandler) {
        // Add server-wide events
        // TODO: 28.05.22 make join and leave messages configurable
        eventHandler.addListener(PlayerLoginEvent.class, event -> {
            var player = event.getPlayer();
            // TODO: 27.05.22 get players login point from world data
            event.setSpawningInstance(Instances.OVERWORLD);
            player.setRespawnPoint(new Pos(0, 100, 0));
        });

        eventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            var player = event.getPlayer();
            Audiences.players().sendMessage(Component.translatable("multiplayer.player.left",
                    Component.text(player.getUsername())).color(NamedTextColor.YELLOW));
            MinecraftServer.LOGGER.info(player.getUsername()+" left the game.");
        });

        eventHandler.addListener(PlayerSpawnEvent.class, event -> {
            var player = event.getPlayer();
            if (event.isFirstSpawn()) Audiences.players().sendMessage(
                    Component.translatable("multiplayer.player.joined",
                    Component.text(player.getUsername())).color(NamedTextColor.YELLOW));
            MinecraftServer.LOGGER.info(player.getUsername()+" joined the game.");
            // TODO: 27.05.22 read players state from world data
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setItemStack(0, ItemStack.builder(Material.JUNGLE_PLANKS).amount(64).build());
        });


    }
}
