package com.github.selfcrafted.strawberry.instances;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;

import java.nio.file.Path;
import java.util.UUID;

public class Instances {
    public static final InstanceContainer OVERWORLD = new OverWorld(
            UUID.randomUUID(),
            new AnvilLoader(Path.of("instances", "overworld")));
    public static final InstanceContainer NETHER = new Nether(
            UUID.randomUUID(),
            new AnvilLoader(Path.of("instances", "nether")));


    public static void register() {
        // Register instances
        var instanceManager = MinecraftServer.getInstanceManager();
        instanceManager.registerInstance(OVERWORLD);
        instanceManager.registerInstance(NETHER);
    }
}
