package me.ultradev.magicitems.items;

import com.marcusslover.plus.lib.color.Color;
import com.marcusslover.plus.lib.item.Item;
import me.ultradev.magicitems.Main;
import me.ultradev.magicitems.items.annotations.Manager;
import me.ultradev.magicitems.items.recipes.Ingredient;
import me.ultradev.magicitems.items.recipes.Recipe;
import me.ultradev.magicitems.items.wands.VampireWand;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public enum GameItem {

    @Manager(manager = VampireWand.class)
    VAMPIRE_WAND("&cVampire Wand", "Hitting an enemy heals 40% of the damage dealt.",
            new Item(Material.BLAZE_ROD)
                    .addAttribute(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "", 6.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
                    .addAttribute(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "", 1.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)),
            new Recipe("  P" +
                            " B " +
                            "N  ",
                    new Ingredient('N', Material.NETHERITE_SWORD), new Ingredient('B', Material.BLAZE_ROD), new Ingredient('P', Material.ENDER_PEARL))),

    SLIME_BOOTS("&aSlime Boots", null,
            new Item(Material.LEATHER_BOOTS).setColor(new Color("#32a852"))
                    .addAttribute(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET))
                    .addAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "", 0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)),
            new Recipe("   " +
                            "S S" +
                            "S S",
                    new Ingredient('S', Material.SLIME_BLOCK)))

    ;

    public static final List<GameItem> VALUES = Arrays.asList(values());
    public static final Map<String, Item> CACHED_ITEMS = new HashMap<>();

    private final String name;
    private final String description;
    private final Item item;

    private final Recipe recipe;

    GameItem(String name, String description, Item item, Recipe recipe) {

        this.name = name;
        this.description = description;
        this.item = item;
        this.recipe = recipe;

        Class<? extends ItemManager> manager = getManagerClass();
        if(manager != null) {
            try {
                ItemManager managerInstance = manager.newInstance();
                managerInstance.setItem(this);
                Bukkit.getPluginManager().registerEvents(managerInstance, Main.getInstance());
            } catch(InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    GameItem(String name, String description, Item item) {
        this(name, description, item, null);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Item getItem() {
        return item;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Nullable
    public Class<? extends ItemManager> getManagerClass() {

        try {
            for(Annotation annotation : getClass().getField(toString()).getAnnotations()) {
                if(annotation instanceof Manager manager) {
                    return manager.manager();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Item build() {

        if(CACHED_ITEMS.containsKey(toString())) return CACHED_ITEMS.get(toString());

        Item item = this.item.clone();
        item.setName(name).setTag("id", toString());

        if(description != null) {
            item.setLore(Arrays.stream(WordUtils.wrap(description, 30)
                .replaceAll("\r", "").split("\n"))
                .map(line -> "&7" + line).collect(Collectors.toList()));
        }

        CACHED_ITEMS.put(toString(), item);
        return item;

    }

}
