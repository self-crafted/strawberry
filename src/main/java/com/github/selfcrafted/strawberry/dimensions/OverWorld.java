package com.github.selfcrafted.strawberry.dimensions;

import com.github.selfcrafted.strawberry.biomes.Biomes;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class OverWorld extends InstanceContainer {
    private static final Generator GENERATOR = new OverWorldGenerator();

    OverWorld(@NotNull UUID uniqueId, @Nullable IChunkLoader loader) {
        super(uniqueId, DimensionTypes.OVERWORLD);
        this.setChunkLoader(loader);
        this.setGenerator(GENERATOR);
        // this.setExplosionSupplier();
        // TODO: 27.05.22 set time of the anvil world
        this.setTime(0);
    }

    static class OverWorldGenerator implements Generator {
        @Override
        public void generate(@NotNull GenerationUnit unit) {
            // TODO: 27.05.22 generate overworld
            unit.modifier().fillHeight(-64, -63, Block.SAND);
            unit.modifier().fillBiome(Biomes.DESERT);
        }
    }
}
