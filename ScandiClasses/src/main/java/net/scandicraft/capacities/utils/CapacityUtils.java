package net.scandicraft.capacities.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class CapacityUtils {

    /**
     * Trouve le joueur ciblé si y'en a un
     *
     * @param sender joueur qui lance la capacité
     * @param raduis nombre de bloc max
     * @return joueur ciblé
     */
    public static Player getTargetPlayer(final Player sender, final int raduis) {
        List<Entity> nearPlayers = getEntitiesInRaduis(sender, raduis, Player.class);
        return (Player) getTarget(sender, nearPlayers);
    }

    /**
     * Liste les entités autours d'un joueur
     *
     * @param sender      joueur
     * @param raduis      blocs max
     * @param entityClass classe des entités cherchées
     * @return liste des entités
     */
    public static List<Entity> getEntitiesInRaduis(final Player sender, final int raduis, final Class<? extends Entity> entityClass) {
        return sender.getNearbyEntities(raduis, raduis, raduis).stream().filter(entity -> classeContainsInterface(entity.getClass(), entityClass) && entity != sender).collect(Collectors.toList());
    }

    /**
     * Contrôle si une classe contient l'interface d'une autre classe
     *
     * @param entityClasse classe dont on va regardé les interfaces
     * @param classe       classe réflérence
     * @return si oui/non contient
     */
    public static boolean classeContainsInterface(Class<?> entityClasse, Class<?> classe) {
        for (Class<?> currentEntityClasse : entityClasse.getInterfaces()) {
            if (currentEntityClasse.equals(classe)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Trouve la cible visée (Entité)
     *
     * @param sender   player qui vise qqch
     * @param entities entités autour du player
     * @param <T>      entité cible
     * @return entitée visée (cible)
     */
    public static <T extends Entity> T getTarget(final Entity sender, final Iterable<T> entities) {
        if (sender == null) {
            return null;
        }

        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Vector n = other.getLocation().toVector().subtract(sender.getLocation().toVector());
            if (sender.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(sender.getLocation().getDirection().normalize()) >= 0) {
                if (target == null || target.getLocation().distanceSquared(sender.getLocation()) > other.getLocation().distanceSquared(sender.getLocation())) {
                    target = other;
                }
            }
        }

        return target;
    }

}
