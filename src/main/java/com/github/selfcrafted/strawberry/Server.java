package com.github.selfcrafted.strawberry;

import com.github.selfcrafted.strawberry.biomes.Biomes;
import com.github.selfcrafted.strawberry.commands.Commands;
import com.github.selfcrafted.strawberry.config.ServerConfig;
import com.github.selfcrafted.strawberry.config.ServerConfigImpl;
import com.github.selfcrafted.strawberry.events.listeners.Listeners;
import com.github.selfcrafted.strawberry.extra.serverlist.ListPing;
import com.github.selfcrafted.strawberry.extra.startscript.StartScript;
import com.github.selfcrafted.strawberry.dimensions.DimensionTypes;
import com.github.selfcrafted.strawberry.dimensions.Dimensions;
import com.github.selfcrafted.strawberry.receipe.Recipes;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.velocity.VelocityProxy;

import java.nio.file.Path;
import java.util.Arrays;

public class Server {
    public static final String VERSION = "&version";
    public static final String MINESTOM_VERSION = "&minestomVersion";
    public static final ServerConfig CONFIG = ServerConfigImpl.read();

    public static void main(String[] args) {
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
        StartScript.generate();

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
            case OFFLINE -> MinecraftServer.LOGGER.warn("Running in offline mode allows anyone and everyone to join!");
            case ONLINE -> MojangAuth.init();
            case BUNGEECORD -> BungeeCordProxy.enable();
            case VELOCITY -> {
                if (!CONFIG.hasVelocitySecret())
                    throw new IllegalArgumentException("The velocity secret is mandatory.");
                VelocityProxy.enable(CONFIG.getVelocitySecret());
            }
        }

        // Register everything that needs to be registered
        Biomes.register(MinecraftServer.getBiomeManager());
        DimensionTypes.register(MinecraftServer.getDimensionTypeManager());
        Dimensions.register(MinecraftServer.getInstanceManager());
        Commands.register(MinecraftServer.getCommandManager());
        Recipes.register(MinecraftServer.getRecipeManager());
        Listeners.register(MinecraftServer.getGlobalEventHandler());

        // Actually start server
        MinecraftServer.LOGGER.info("Running in " + CONFIG.getMode() + " mode.");
        MinecraftServer.LOGGER.info("Listening on " + CONFIG.getServerIp() + ":" + CONFIG.getServerPort());
        server.start(CONFIG.getServerIp(), CONFIG.getServerPort());
    }
}