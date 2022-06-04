package com.github.selfcrafted.strawberry.instances;

import com.github.selfcrafted.strawberry.biomes.Biomes;
import net.kyori.adventure.text.Component;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.utils.NamespaceID;
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
            unit.modifier().fillHeight(-63, -62, Block.CRAFTING_TABLE.withHandler(new BlockHandler() {

                @Override
                public boolean onInteract(@NotNull Interaction interaction) {
                    interaction.getPlayer().openInventory(new Inventory(InventoryType.CRAFTING, Component.text("NYI")));
                    return BlockHandler.super.onInteract(interaction);
                }

                @Override
                public @NotNull NamespaceID getNamespaceId() {
                    return NamespaceID.from("strawberry:handler_crafting_table");
                }
            }));
            unit.modifier().fillBiome(Biomes.DESERT);
        }
    }
}
