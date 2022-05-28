package com.github.selfcrafted.strawberry;

import com.github.selfcrafted.strawberry.biomes.Biomes;
import com.github.selfcrafted.strawberry.commands.Commands;
import com.github.selfcrafted.strawberry.config.ServerConfig;
import com.github.selfcrafted.strawberry.config.ServerConfigImpl;
import com.github.selfcrafted.strawberry.instances.DimensionTypes;
import com.github.selfcrafted.strawberry.instances.Instances;
import com.github.selfcrafted.strawberry.serverlist.ListPing;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ClientPingServerEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.ping.ResponseData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class Server {
    public static final String VERSION = "&version";
    public static final String MINESTOM_VERSION = "&minestomVersion";
    private static final String START_SCRIPT_FILENAME = "start.sh";
    public static final ServerConfig CONFIG = ServerConfigImpl.read();

    public static void main(String[] args) throws IOException {
        MinecraftServer.LOGGER.info(" _____ _                      _                          ");
        MinecraftServer.LOGGER.info("/  ___| |                    | |                         ");
        MinecraftServer.LOGGER.info("\\ `--.| |_ _ __ __ ___      _| |__   ___ _ __ _ __ _   _ ");
        MinecraftServer.LOGGER.info(" `--. \\ __| '__/ _` \\ \\ /\\ / / '_ \\ / _ \\ '__| '__| | | |");
        MinecraftServer.LOGGER.info("/\\__/ / |_| | | (_| |\\ V  V /| |_) |  __/ |  | |  | |_| |");
        MinecraftServer.LOGGER.info("\\____/ \\__|_|  \\__,_| \\_/\\_/ |_.__/ \\___|_|  |_|   \\__, |");
        MinecraftServer.LOGGER.info("                                                    __/ |");
        MinecraftServer.LOGGER.info("                                                   |___/ ");
        MinecraftServer.LOGGER.info("&Name: " + VERSION);
        MinecraftServer.LOGGER.info("Protocol: %d (%s)".formatted(MinecraftServer.PROTOCOL_VERSION, MinecraftServer.VERSION_NAME));
        MinecraftServer.LOGGER.info("Minestom: " + MINESTOM_VERSION);
        MinecraftServer.LOGGER.info("Java: " + Runtime.version());

        if (Arrays.asList(args).contains("--version")) System.exit(0);

        File startScriptFile = new File(START_SCRIPT_FILENAME);
        if (!startScriptFile.exists()) {
            MinecraftServer.LOGGER.info("Create startup script.");
            Files.copy(
                    Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream(START_SCRIPT_FILENAME)),
                    startScriptFile.toPath());
            Runtime.getRuntime().exec("chmod u+x start.sh");
            MinecraftServer.LOGGER.info("Use './start.sh' to start the server.");
            System.exit(0);
        }

        if (CONFIG.getTps() != null)
            System.setProperty("minestom.tps", CONFIG.getTps());
        if (CONFIG.getChunkViewDistance() != null)
            System.setProperty("minestom.chunk-view-distance", CONFIG.getChunkViewDistance());
        if (CONFIG.getEntityViewDistance() != null)
            System.setProperty("minestom.entity-view-distance", CONFIG.getEntityViewDistance());
        if (CONFIG.isTerminalDisabled())
            System.setProperty("minestom.terminal.disabled", "");

        // Initialise server
        MinecraftServer server = MinecraftServer.init();

        MinecraftServer.getExtensionManager().setExtensionDataRoot(Path.of("config"));
        ListPing.setup();

        switch (CONFIG.getMode()) {
            case OFFLINE:
                break;
            case ONLINE:
                MojangAuth.init();
                break;
            case BUNGEECORD:
                BungeeCordProxy.enable();
                break;
            case VELOCITY:
                if (!CONFIG.hasVelocitySecret())
                    throw new IllegalArgumentException("The velocity secret is mandatory.");
                VelocityProxy.enable(CONFIG.getVelocitySecret());
        }

        // Register everything that needs to be registered
        Biomes.register();
        DimensionTypes.register();
        Instances.register();
        Commands.register();

        // Add server-wide events
        var globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            // TODO: 27.05.22 get players login point from world data
            event.setSpawningInstance(Instances.OVERWORLD);
        });

        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            var player = event.getPlayer();
            // TODO: 27.05.22 read players state from world data
            player.setGameMode(GameMode.CREATIVE);
            player.setRespawnPoint(new Pos(0, -63, 0));
        });

        // Actually start server
        MinecraftServer.LOGGER.info("Running in " + CONFIG.getMode() + " mode.");
        MinecraftServer.LOGGER.info("Listening on " + CONFIG.getServerIp() + ":" + CONFIG.getServerPort());
        server.start(CONFIG.getServerIp(), CONFIG.getServerPort());
    }
}