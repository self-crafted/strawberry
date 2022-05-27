package com.github.selfcrafted.strawberry.instances;

import com.github.selfcrafted.strawberry.biomes.Biomes;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Nether extends InstanceContainer {
    private static final Generator GENERATOR = new NetherGenerator();

    Nether(@NotNull UUID uniqueId, @Nullable IChunkLoader loader) {
        super(uniqueId, DimensionTypes.NETHER);
        this.setChunkLoader(loader);
        this.setGenerator(GENERATOR);
        // this.setExplosionSupplier();
        // TODO: 27.05.22 set time of the anvil world
        this.setTime(0);
    }

    static class NetherGenerator implements Generator {
        @Override
        public void generate(@NotNull GenerationUnit unit) {
            // TODO: 27.05.22 generate nether
            unit.modifier().fillHeight(-64, -63, Block.NETHERRACK);
            unit.modifier().fillBiome(Biomes.WASTELANDS);
        }
    }
}
