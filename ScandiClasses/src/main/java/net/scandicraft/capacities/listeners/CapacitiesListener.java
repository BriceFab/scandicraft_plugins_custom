package net.scandicraft.capacities.listeners;

import net.scandicraft.capacities.CapacityManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CapacitiesListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.SCEPTER_CAPACITY) {
                //Utilise sa capacité sélectionnée
                CapacityManager.getInstance().launchCapacity(player);
            }
        }

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.SCEPTER_CAPACITY) {
                //Change la capacité sélectionnée du joueur
                CapacityManager.getInstance().selectNextCapacity(player);
            }
        }
    }

}
