package com.github.selfcrafted.strawberry.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;

public class ServerConfigImpl implements ServerConfig {
    private static final File settingsFile = new File("settings.json");
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static ServerConfig read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
            return gson.fromJson(reader, ServerConfigImpl.class);
        } catch (FileNotFoundException e) {
            var defaultConfig = new ServerConfigImpl();
            try {
                // save
                String json = gson.toJson(defaultConfig);
                Writer writer = new FileWriter(settingsFile);
                writer.write(json);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return defaultConfig;
        }
    }

    private final String SERVER_IP;
    private final int SERVER_PORT;

    private final ConnectionMode MODE;
    private final String VELOCITY_SECRET;

    private final boolean PLAYER_RESTART;
    private final boolean PLAYER_SHUTDOWN;

    // JVM arguments
    private final String TPS;
    private final String CHUNK_VIEW_DISTANCE;
    private final String ENTITY_VIEW_DISTANCE;
    private final boolean TERMINAL_DISABLED;

    private final int MAX_PLAYERS;

    private ServerConfigImpl() {
        this.SERVER_IP = "localhost";
        this.SERVER_PORT = 25565;

        this.MODE = ConnectionMode.OFFLINE;
        this.VELOCITY_SECRET = "";

        this.PLAYER_RESTART = false;
        this.PLAYER_SHUTDOWN = false;

        this.TPS = null;
        this.CHUNK_VIEW_DISTANCE = null;
        this.ENTITY_VIEW_DISTANCE = null;
        this.TERMINAL_DISABLED = false;

        this.MAX_PLAYERS = 20;
    }

    @Override
    public ConnectionMode getMode() { return MODE; }

    @Override
    public String getServerIp() {
        return System.getProperty("server.ip", SERVER_IP);
    }
    @Override
    public int getServerPort() {
        int port = Integer.getInteger("server.port", SERVER_PORT);
        if (port < 1) return 25565;
        return port;
    }

    @Override
    public boolean hasVelocitySecret() {
        return !VELOCITY_SECRET.isBlank();
    }

    @Override
    public String getVelocitySecret() {
        return VELOCITY_SECRET;
    }

    @Override
    public boolean isAllowPlayerRestart() { return PLAYER_RESTART; }

    @Override
    public boolean isAllowPlayerShutdown() { return PLAYER_SHUTDOWN; }

    @Override
    public String getTps() { return TPS; }
    @Override
    public String getChunkViewDistance() { return CHUNK_VIEW_DISTANCE; }
    @Override
    public String getEntityViewDistance() { return ENTITY_VIEW_DISTANCE; }
    @Override
    public boolean isTerminalDisabled() { return TERMINAL_DISABLED; }

    @Override
    public int getMaxPlayers() { return MAX_PLAYERS; }
}
