package com.github.selfcrafted.strawberry.instances;

import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.UUID;

public class Instances {
    public static final InstanceContainer OVERWORLD = new OverWorld(
            UUID.randomUUID(),
            new AnvilLoader(Path.of("instances", "overworld")));
    public static final InstanceContainer NETHER = new Nether(
            UUID.randomUUID(),
            new AnvilLoader(Path.of("instances", "nether")));


    public static void register(@NotNull InstanceManager instanceManager) {
        // Register instances
        instanceManager.registerInstance(OVERWORLD);
        instanceManager.registerInstance(NETHER);
    }
}
