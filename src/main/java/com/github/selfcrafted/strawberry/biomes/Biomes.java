package com.github.selfcrafted.strawberry.biomes;

import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeEffects;
import net.minestom.server.world.biomes.BiomeParticle;

public class Biomes {
    public static final Biome DESERT = Biome.builder()
            .category(Biome.Category.DESERT)
            .name(NamespaceID.from("strawberry:desert"))
            .effects(BiomeEffects.builder()
                    .biomeParticle(new BiomeParticle(0.5f,
                            new BiomeParticle.DustOption(0.5f, 0.5f, 0.2f, 0.5f)))
                    .build())
            .build();

    public static final Biome WASTELANDS = Biome.builder()
            .name(NamespaceID.from("strawberry:wastelands"))
            .effects(BiomeEffects.builder()
                    .biomeParticle(new BiomeParticle(0.5f,
                            new BiomeParticle.DustOption(1.0f, 0.5f, 0.5f, 0.5f)))
                    .build())
            .build();
}
