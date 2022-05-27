package com.github.selfcrafted.strawberry.config;

public interface ServerConfig {
    ConnectionMode getMode();

    String getServerIp();
    int getServerPort();

    boolean hasVelocitySecret();
    String getVelocitySecret();

    boolean isAllowPlayerRestart();
    boolean isAllowPlayerShutdown();

    String getTps();
    String getChunkViewDistance();
    String getEntityViewDistance();
    boolean isTerminalDisabled();
}
