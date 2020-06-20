package net.scandicraft.capacities.listeners;

import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.impl.GuerrierCapacity1;
import net.scandicraft.capacities.impl.GuerrierCapacity3;
import net.scandicraft.capacities.impl.MagicienCapacity2;
import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.capacities.target.PlayerTarget;
import net.scandicraft.utils.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;
import java.util.stream.Collectors;

public class CapacitiesListener implements Listener {

    public CapacitiesListener() {
//        SpigotScanner clientInput = new SpigotScanner(System.in);

    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        System.out.print("capacitiesListener onPlayerLogin");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.SLIME_BALL) {
//                player.sendMessage("You have right click a slime ball!");

                GuerrierCapacity1 g1 = new GuerrierCapacity1();
                GuerrierCapacity3 g3 = new GuerrierCapacity3();
                MagicienCapacity2 m2 = new MagicienCapacity2(); //TODO target PLAYER
                ICapacityTarget target = new PlayerTarget(getTargetPlayer(player, 20));
                if (target.getTarget() == null) {
                    player.sendMessage(ChatColor.RED + " no target");
                } else {
                    CapacityManager.getInstance().useCapacity(player, m2, target);
                }
            }
        }
    }

    public static Player getTargetPlayer(final Player fromPlayer, int raduis) {
        List<Entity> playersInRadius = fromPlayer.getNearbyEntities(raduis, raduis, raduis).stream().filter(entity -> (entity instanceof Player)).collect(Collectors.toList());
        for (Entity target : playersInRadius) {
            if (target instanceof Player) {
                if (fromPlayer.hasLineOfSight(target)) {
                    //TODO calculer le joueur le plus proche ?
                    return (Player) target;
                }
            }
        }
        return null;
    }

}
