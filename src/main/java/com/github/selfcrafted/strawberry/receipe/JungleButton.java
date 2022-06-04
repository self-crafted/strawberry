package com.github.selfcrafted.strawberry.receipe;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.DeclareRecipesPacket;
import net.minestom.server.recipe.ShapelessRecipe;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class JungleButton extends ShapelessRecipe {
    public static final NamespaceID RECIPE_ID = NamespaceID.from("strawberry:recipe_jungle_button");

    private static final DeclareRecipesPacket.Ingredient PLANKS =
            new DeclareRecipesPacket.Ingredient(Collections.singletonList(ItemStack.of(Material.JUNGLE_PLANKS)));

    JungleButton() {
        super(RECIPE_ID.asString(), "",
                Collections.singletonList(PLANKS),
                ItemStack.of(Material.JUNGLE_BUTTON));
    }

    @Override
    public boolean shouldShow(@NotNull Player player) { return true; }
}
