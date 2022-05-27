package me.ultradev.magicitems.items;

import org.bukkit.event.Listener;

public class ItemManager implements Listener {

    private GameItem item = null;

    public GameItem getItem() {
        return item;
    }

    public void setItem(GameItem item) {
        this.item = item;
    }

}
