package net.scandicraft.capacities;

import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.capacities.target.PlayerTarget;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.IClasse;
import net.scandicraft.commands.CommandList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Gère les capacités des joueurs
 */
public class CapacityManager {

    private static final CapacityManager INSTANCE = new CapacityManager();
    private final HashMap<UUID, ICapacity> playersCurrentCapacities = new HashMap<>();
    private final HashMap<UUID, CapacityCooldown> cooldowns = new HashMap<>();

    private CapacityManager() {
    }

    /**
     * Le joueur a lancé l'action de lancer sa capacité
     *
     * @param sender joueur
     */
    public void launchCapacity(Player sender) {
        ICapacity currentCapacity = getCurrentCapacity(sender);
        if (currentCapacity != null) {
            final int raduis = 10;
            List<Entity> playersInRadius = sender.getNearbyEntities(raduis, raduis, raduis).stream().filter(entity -> (entity instanceof Player)).collect(Collectors.toList());
            ICapacityTarget target = new PlayerTarget((Player) getTarget(sender, playersInRadius));
            this.useCapacity(sender, currentCapacity, target);
        } else {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas de capacité selectionnée");
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

    /**
     * Utilise la capacité
     *
     * @param sender   joueur
     * @param capacity capacité
     * @param target   la cible
     */
    private void useCapacity(Player sender, ICapacity capacity, ICapacityTarget target) {
        if (canUseCapacity(sender, capacity)) {
            capacity.sendSucessMessage(sender);
            capacity.onUse(sender, target);
        } else {
            CapacityCooldown capacityCooldown = this.cooldowns.get(sender.getUniqueId());
            capacity.sendWaitingMessage(sender, calculateRemainingTime(capacityCooldown));
        }
    }

    /**
     * Retourne oui/non si le player peut utiliser sa capacité
     *
     * @param sender   joueur
     * @param capacity capacité
     * @return oui/non
     */
    private boolean canUseCapacity(Player sender, ICapacity capacity) {
        if (this.cooldowns.containsKey(sender.getUniqueId())) {
            CapacityCooldown capacityCooldown = this.cooldowns.get(sender.getUniqueId());

            if (calculateRemainingTime(capacityCooldown) > 0) {
                return false;
            } else {
                this.cooldowns.remove(sender.getUniqueId());
                return true;
            }
        } else {
            cooldowns.put(sender.getUniqueId(), new CapacityCooldown(System.currentTimeMillis(), capacity));
            return true;
        }
    }

    /**
     * Calcule le temps restant avant la prochaine utilisation d'une capacité
     *
     * @param capacityCooldown le cooldown de la capacité
     * @return le temps restant
     */
    private long calculateRemainingTime(CapacityCooldown capacityCooldown) {
        return ((capacityCooldown.getTime() / 1000) + capacityCooldown.getCapacity().getCooldownTime()) - System.currentTimeMillis() / 1000;
    }

    /**
     * Change la capacité selectionnée par le joueur
     *
     * @param player   joueur
     * @param capacity capacité
     */
    public void changeCurrentCapacity(Player player, ICapacity capacity) {
        IClasse playerclasse = ClasseManager.getInstance().getPlayerClasse(player);

        //Vérifie la classe du joueur
        if (playerclasse == null) {
            player.sendMessage(ChatColor.RED + "Vous devez avoir une classe pour sélectionner votre capacité.");
            player.performCommand(CommandList.classeCommand + " help");
            return;
        }

        //Vérifie que la classe du joueur continent la capacité
        if (!playerclasse.getCapacities().contains(capacity)) {
            player.sendMessage(ChatColor.RED + "Cette capacité n'est pas disponible pour votre classe.");
            player.performCommand(CommandList.classeCommand + " help");
            return;
        }

        if (this.playersCurrentCapacities.containsKey(player.getUniqueId())) {
            this.playersCurrentCapacities.replace(player.getUniqueId(), capacity);
        } else {
            this.playersCurrentCapacities.put(player.getUniqueId(), capacity);
        }
    }

    /**
     * Récupérer la capacités actuelle sélectionnée par le joueur
     *
     * @param player joueur
     * @return la capacité du joueur
     */
    public ICapacity getCurrentCapacity(Player player) {
        return this.playersCurrentCapacities.get(player.getUniqueId());
    }

    /**
     * Supprime la capacité sélectionnée du joueur
     *
     * @param player joueur
     */
    public void removeCurrentCapacity(Player player) {
        this.playersCurrentCapacities.remove(player.getUniqueId());
    }

    public static CapacityManager getInstance() {
        return INSTANCE;
    }
}
