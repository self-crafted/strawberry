package com.github.selfcrafted.strawberry.extra.serverlist;

import com.github.selfcrafted.strawberry.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class ListPing {
    private static final ResponseData RESPONSE_DATA = new ResponseData();

    static {
        RESPONSE_DATA.setDescription(Component.text("Strawberry server", NamedTextColor.DARK_RED));
        try {
            var bgTileSprite = ImageIO.read(Objects.requireNonNull(
                    ListPing.class.getResourceAsStream("/favicon.png")));
            var os = new ByteArrayOutputStream();
            ImageIO.write(bgTileSprite, "png", os);
            var base64 = Base64.getEncoder().encodeToString(os.toByteArray());
            RESPONSE_DATA.setFavicon("data:image/png;base64," + base64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setup() {
        var eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(ServerListPingEvent.class, event -> {
            event.setResponseData(RESPONSE_DATA);
            event.setCancelled(false);
        });
        eventHandler.addListener(PlayerLoginEvent.class, event -> {
            var playerCount = MinecraftServer.getConnectionManager().getOnlinePlayers().size();
            var maxPlayers = Server.CONFIG.getMaxPlayers()<=0 ? playerCount+1 : Server.CONFIG.getMaxPlayers();
            RESPONSE_DATA.setOnline(playerCount);
            RESPONSE_DATA.setMaxPlayer(maxPlayers);
        });
        eventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            var playerCount = MinecraftServer.getConnectionManager().getOnlinePlayers().size();
            var maxPlayers = Server.CONFIG.getMaxPlayers()<=0 ? playerCount+1 : Server.CONFIG.getMaxPlayers();
            RESPONSE_DATA.setOnline(playerCount);
            RESPONSE_DATA.setMaxPlayer(maxPlayers);
        });
    }
}
