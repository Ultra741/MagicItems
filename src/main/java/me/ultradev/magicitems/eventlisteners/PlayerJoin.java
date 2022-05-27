package me.ultradev.magicitems.eventlisteners;

import com.marcusslover.plus.lib.item.Item;
import me.ultradev.magicitems.items.GameItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        for(int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack itemStack = player.getInventory().getContents()[i];
            if(itemStack == null) continue;
            Item item = new Item(itemStack);
            String id = item.getTag("id", "");
            if(!id.equals("")) {
                ItemStack updatedItem = GameItem.valueOf(id).build().getItemStack();
                if(!itemStack.isSimilar(updatedItem)) {
                    player.getInventory().setItem(i, updatedItem);
                }
            }
        }

    }

}
