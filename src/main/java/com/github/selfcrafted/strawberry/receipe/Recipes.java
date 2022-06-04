package com.github.selfcrafted.strawberry.receipe;

import net.minestom.server.recipe.Recipe;
import net.minestom.server.recipe.RecipeManager;

public class Recipes {
    public static final Recipe CRAFTING_TABLE = new CraftingTable();
    public static final Recipe JUNGLE_BUTTON = new JungleButton();

    public static void register(RecipeManager manager) {
        manager.addRecipes(
                CRAFTING_TABLE,
                JUNGLE_BUTTON
        );
    }
}
