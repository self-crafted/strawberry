package com.github.selfcrafted.strawberry.receipe;

import net.minestom.server.recipe.Recipe;
import net.minestom.server.recipe.RecipeManager;

public class Recipes {
    public static final Recipe CRAFTING_TABLE = new CraftingTable();

    public static void register(RecipeManager manager) {
        manager.addRecipe(CRAFTING_TABLE);
    }
}
