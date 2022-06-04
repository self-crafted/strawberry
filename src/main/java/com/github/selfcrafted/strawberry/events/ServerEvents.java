package com.github.selfcrafted.strawberry.events;

import com.github.selfcrafted.strawberry.instances.Instances;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
import net.minestom.server.event.inventory.PlayerInventoryItemChangeEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.DeclareRecipesPacket;
import net.minestom.server.recipe.ShapelessRecipe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ServerEvents {
    public static void register(GlobalEventHandler eventHandler) {
        // Add server-wide events
        // TODO: 28.05.22 make join and leave messages configurable
        eventHandler.addListener(PlayerLoginEvent.class, event -> {
            var player = event.getPlayer();
            Audiences.players().sendMessage(Component.translatable("multiplayer.player.joined",
                    Component.text(player.getUsername())).color(NamedTextColor.YELLOW));
            MinecraftServer.LOGGER.info(player.getUsername()+" joined the game.");

            // TODO: 27.05.22 get players login point from world data
            event.setSpawningInstance(Instances.OVERWORLD);
            player.setRespawnPoint(new Pos(0, 100, 0));
        });

        eventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            var player = event.getPlayer();
            Audiences.players().sendMessage(Component.translatable("multiplayer.player.left",
                    Component.text(player.getUsername())).color(NamedTextColor.YELLOW));
            MinecraftServer.LOGGER.info(player.getUsername()+" left the game.");

        });

        eventHandler.addListener(PlayerSpawnEvent.class, event -> {
            var player = event.getPlayer();
            // TODO: 27.05.22 read players state from world data
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setItemStack(0, ItemStack.builder(Material.JUNGLE_PLANKS).amount(64).build());
        });

        eventHandler.addListener(InventoryItemChangeEvent.class, event -> {
            var inventory = event.getInventory();
            if (inventory == null) return;
            if (inventory.getInventoryType() == InventoryType.CRAFTING) {
                MinecraftServer.LOGGER.info("Crafting table slot {} {}", event.getSlot(), event.getNewItem());
                // TODO: 04.06.22 implement 3x3 crafting
                var itemStacks = Arrays.stream(inventory.getItemStacks())
                        //.map(itemStack -> itemStack.withAmount(1))
                        .collect(Collectors.toSet());
                MinecraftServer.getRecipeManager().getRecipes().forEach(recipe -> {
                    boolean isUsable = true;
                    switch (recipe.getRecipeType()) {
                        case SHAPED -> {}
                        case SHAPELESS -> {
                            var shapelessRecipe = ((ShapelessRecipe) recipe);
                            var ingredients = shapelessRecipe.getIngredients();
                            for (DeclareRecipesPacket.Ingredient ingredient : ingredients) {
                                if (!new HashSet<>(ingredient.items()).containsAll(itemStacks)) {
                                    isUsable = false;
                                    break;
                                }
                            }
                        }
                    }
                    MinecraftServer.LOGGER.info("Recipe candidate {}: {}", recipe.getRecipeId(), isUsable);
                });
            }
        });

        eventHandler.addListener(PlayerInventoryItemChangeEvent.class, event -> {
            // TODO: 04.06.22 implement 2x2 crafting
        });
    }
}
