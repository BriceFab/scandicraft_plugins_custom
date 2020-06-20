package net.scandicraft.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class MathUtils {

    public static int TICK_SECONDS = 20;    //1 tick = 20 seconds

    public static int convertSecondsToTicks(int seconds) {
        return TICK_SECONDS * seconds;
    }

    public static int convertMinutesToTicks(int minutes) {
        return TICK_SECONDS * minutes * 60;
    }

    public static int convertMinutesToSecondes(int minutes) {
        return minutes * 60;
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

//    public static <T extends org.bukkit.entity.Entity> T getTarget(final Entity entity, final Iterable<T> entities) {
//        if (entity == null)
//            return null;
//        T target = null;
//        final double threshold = 3.5;
//        for (final T other : entities) {
//            final Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
//            if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
//                if (target == null || target.getLocation().distanceSquared(entity.getLocation()) > other.getLocation().distanceSquared(entity.getLocation()))
//                    target = other;
//            }
//        }
//        return target;
//    }

}
