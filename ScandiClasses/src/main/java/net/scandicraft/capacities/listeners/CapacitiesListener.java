package net.scandicraft.capacities.listeners;

import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.capacities.impl.*;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.IClasse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class CapacitiesListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                //Utilise sa capacité sélectionnée
                CapacityManager.getInstance().launchCapacity(player);
            }
        }

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.SLIME_BALL) {
                //Lance la capacité du joueur
                GuerrierCapacity1 g1 = new GuerrierCapacity1();
                GuerrierCapacity3 g3 = new GuerrierCapacity3();
                MagicienCapacity2 m2 = new MagicienCapacity2(); //TODO target PLAYER
                GuerrierCapacity2 g2 = new GuerrierCapacity2();
                ArcherCapacity3 a3 = new ArcherCapacity3();
                MagicienCapacity1 m1 = new MagicienCapacity1();
                ArcherCapacity1 a1 = new ArcherCapacity1();
                ArcherCapacity2 a2 = new ArcherCapacity2();

//                String table = SqlClassesManager.getInstance().getTable();
//                LogManager.consoleInfo("table " + table);

//                int raduis = 10;
//                List<Entity> playersInRadius = player.getNearbyEntities(raduis, raduis, raduis).stream().filter(entity -> (entity instanceof Player)).collect(Collectors.toList());
//                ICapacityTarget target = new PlayerTarget((Player) getTarget(player, playersInRadius));
//                if (target.getTarget() == null) {
//                    player.sendMessage(ChatColor.RED + " no target");
//                } else {
//                CapacityManager.getInstance().useCapacity(player, a2, null);
//                }
            } else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                //Change la capacité sélectionnée
                IClasse playerclasse = ClasseManager.getInstance().getPlayerClasse(player);

                if (playerclasse == null) {
                    player.sendMessage(ChatColor.RED + "Vous devez avoir une classe pour sélectionner votre capacité.");
                    return;
                }

                List<ICapacity> playerCapacites = playerclasse.getCapacities();
                ICapacity nextCapacity = playerCapacites.get(new Random().nextInt(playerCapacites.size()));
                CapacityManager.getInstance().changeCurrentCapacity(player, nextCapacity);
                player.sendMessage(ChatColor.GREEN + "Vous avez sélectionner la capacité " + nextCapacity.getName());
            }
        }
    }

    public static <T extends org.bukkit.entity.Entity> T getTarget(final org.bukkit.entity.Entity entity, final Iterable<T> entities) {
        if (entity == null)
            return null;
        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Vector n = other.getLocation().toVector()
                    .subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n)
                    .lengthSquared() < threshold
                    && n.normalize().dot(
                    entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null
                        || target.getLocation().distanceSquared(
                        entity.getLocation()) > other.getLocation()
                        .distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }

//    public static Player getTargetPlayer(final Player fromPlayer, int raduis) {
//        List<Entity> playersInRadius = fromPlayer.getNearbyEntities(raduis, raduis, raduis).stream().filter(entity -> (entity instanceof Player)).collect(Collectors.toList());
//        for (Entity target : playersInRadius) {
//            if (target instanceof Player) {
//                if (fromPlayer.hasLineOfSight(target)) {
//                    //TODO calculer le joueur le plus proche ?
//                    return (Player) target;
//                }
//            }
//        }
//        return null;
//    }

}
