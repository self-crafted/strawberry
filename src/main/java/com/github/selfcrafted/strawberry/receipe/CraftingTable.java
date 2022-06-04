package com.github.selfcrafted.strawberry.receipe;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.DeclareRecipesPacket;
import net.minestom.server.recipe.ShapedRecipe;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CraftingTable extends ShapedRecipe {
    public static final NamespaceID RECIPE_ID = NamespaceID.from("strawberry:recipe_crafting_table");
    private static final DeclareRecipesPacket.Ingredient PLANKS =
            new DeclareRecipesPacket.Ingredient(List.of(
                    ItemStack.of(Material.OAK_PLANKS),
                    ItemStack.of(Material.ACACIA_PLANKS),
                    ItemStack.of(Material.BIRCH_PLANKS),
                    ItemStack.of(Material.JUNGLE_PLANKS),
                    ItemStack.of(Material.SPRUCE_PLANKS),
                    ItemStack.of(Material.DARK_OAK_PLANKS),
                    ItemStack.of(Material.WARPED_PLANKS),
                    ItemStack.of(Material.CRIMSON_PLANKS)
            ));

    CraftingTable() {
        super(RECIPE_ID.asString(), 2, 2, "",
                List.of(
                        PLANKS, PLANKS,
                        PLANKS, PLANKS
                ),
                ItemStack.of(Material.CRAFTING_TABLE));
    }

    @Override
    public boolean shouldShow(@NotNull Player player) {
        return true;
    }
}
