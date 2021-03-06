package com.github.selfcrafted.strawberry.dimensions;

import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.DimensionTypeManager;

public class DimensionTypes {
    public static final DimensionType OVERWORLD = DimensionType.builder(NamespaceID.from("strawberry:overworld"))
            .ultrawarm(false)
            .natural(true)
            .piglinSafe(false)
            .respawnAnchorSafe(false)
            .bedSafe(true)
            .raidCapable(true)
            .skylightEnabled(true)
            .ceilingEnabled(false)
            .fixedTime(null)
            .ambientLight(0.0f)
            .height(384)
            .minY(-64)
            .logicalHeight(384)
            .infiniburn(NamespaceID.from("minecraft:infiniburn_overworld"))
            .build();

    public static final DimensionType NETHER = DimensionType.builder(NamespaceID.from("strawberry:nether"))
            .ultrawarm(true)
            .natural(false)
            .piglinSafe(true)
            .respawnAnchorSafe(true)
            .bedSafe(false)
            .raidCapable(false)
            .skylightEnabled(false)
            .ceilingEnabled(true)
            .fixedTime(null)
            .ambientLight(0.0f)
            .height(384)
            .minY(-64)
            .logicalHeight(384)
            .infiniburn(NamespaceID.from("minecraft:infiniburn_nether"))
            .coordinateScale(8)
            .build();


    public static void register(DimensionTypeManager dimensionTypeManager) {
        // Register dimension types
        dimensionTypeManager.addDimension(OVERWORLD);
        dimensionTypeManager.addDimension(NETHER);
    }
}
