package com.github.selfcrafted.strawberry.extra.startscript;

import com.github.selfcrafted.strawberry.Server;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class StartScript {
    private static final String START_SCRIPT_FILENAME = "start.sh";

    public static void generate() {
        File startScriptFile = new File(START_SCRIPT_FILENAME);
        if (!startScriptFile.exists()) {
            try {
                MinecraftServer.LOGGER.info("Create startup script.");
                Files.copy(
                        Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream(START_SCRIPT_FILENAME)),
                        startScriptFile.toPath());
                Runtime.getRuntime().exec("chmod u+x start.sh");
                MinecraftServer.LOGGER.info("Use './start.sh' to start the server.");
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
