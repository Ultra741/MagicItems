package me.ultradev.magicitems.items.wands;

import com.marcusslover.plus.lib.item.Item;
import com.marcusslover.plus.lib.text.Text;
import me.ultradev.magicitems.items.ItemManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VampireWand extends ItemManager {

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {

        if(e.getDamager() instanceof Player player) {
            if(new Item(player.getInventory().getItemInMainHand()).getTag("id", "").equals(getItem().toString())) {
                double healing = e.getFinalDamage() * 0.4;
                player.setHealth(Math.min(player.getHealth() + healing, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                player.sendActionBar(new Text("&c+" + healing + "\u2764").comp());
            }
        }

    }

}
