package me.ultradev.magicitems;

import me.ultradev.magicitems.eventlisteners.PlayerCraftItem;
import me.ultradev.magicitems.eventlisteners.PlayerJoin;
import me.ultradev.magicitems.items.GameItem;
import me.ultradev.magicitems.items.recipes.Ingredient;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        log("Registering items...");
        for(GameItem item : GameItem.VALUES) {

            ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(item.toString().toLowerCase()), item.build().getItemStack());
            String shape = item.getRecipe().shape();
            recipe.shape(shape.substring(0, 3), shape.substring(3, 6), shape.substring(6, 9));
            for(Ingredient ingredient : item.getRecipe().ingredients()) {
                recipe.setIngredient(ingredient.key(), new RecipeChoice.ExactChoice(new ItemStack(ingredient.material())));
            }

            Bukkit.getServer().addRecipe(recipe);

        }

        log("Registering listeners...");
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerJoin(), this);
        manager.registerEvents(new PlayerCraftItem(), this);

        log("Successfully enabled plugin.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static Main getInstance() {
        return instance;
    }

    public static void log(String s) {
        instance.getLogger().log(Level.INFO, "[MagicItems] " + s);
    }

}
