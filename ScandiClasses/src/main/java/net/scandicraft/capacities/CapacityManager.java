package net.scandicraft.capacities;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.IClasse;
import net.scandicraft.commands.CommandList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Gère les capacités des joueurs
 */
public class CapacityManager {

    private static final CapacityManager INSTANCE = new CapacityManager();
    private final HashMap<UUID, ICapacity> playersCurrentCapacities = new HashMap<>();
    private final Multimap<UUID, CapacityCooldown> cooldowns = ArrayListMultimap.create();

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
            this.useCapacity(sender, currentCapacity);
        } else {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas de capacité selectionnée");
        }
    }

    /**
     * Utilise la capacité
     *
     * @param sender   joueur
     * @param capacity capacité
     */
    private void useCapacity(Player sender, ICapacity capacity) {
        CapacityCooldown capacityCooldown = getCapacityCooldownFromKey(sender.getUniqueId(), capacity);

        //Test si le joueur peut utiliser sa capacité
        if (canUseCapacity(capacityCooldown, sender)) {
            try {
                //Utilise la capacité
                capacity.onUse(sender);

                //Enregistre le cooldown pour la prochaine utilisation
                cooldowns.remove(sender.getUniqueId(), capacityCooldown);
                cooldowns.put(sender.getUniqueId(), new CapacityCooldown(System.currentTimeMillis(), capacity));

                //A utilisé la capacité avec succès
                capacity.sendSucessMessage(sender);
            } catch (CapacityException e) {
                //Une erreur est survenue, annule la capacité
                sender.sendMessage(e.getPlayerErrorMessage());
            }
        } else {
            //Affiche le message du cooldown
            capacity.sendWaitingMessage(sender, calculateRemainingTime(capacityCooldown));
        }
    }

    /**
     * Retourne oui/non si le player peut utiliser sa capacité (check cooldown)
     *
     * @param sender joueur
     * @return oui/non
     */
    private boolean canUseCapacity(CapacityCooldown capacityCooldown, Player sender) {
        if (capacityCooldown != null && this.cooldowns.containsEntry(sender.getUniqueId(), capacityCooldown)) {
            return calculateRemainingTime(capacityCooldown) <= 0;
        } else {
            return true;
        }
    }

    /**
     * Trouve le cooldown depuis l'UUID du player & en fonciton de la capacité
     *
     * @param key      player uuid
     * @param capacity capacité
     * @return le cooldown de la capacité
     */
    private CapacityCooldown getCapacityCooldownFromKey(UUID key, ICapacity capacity) {
        if (!this.cooldowns.containsKey(key)) {
            return null;
        }

        Collection<CapacityCooldown> values = this.cooldowns.get(key);
        for (CapacityCooldown capacityCooldown : values) {
            if (capacityCooldown.getCapacity().equals(capacity)) {
                return capacityCooldown;
            }
        }

        return null;
    }

    /**
     * Enlève tous les cooldowns qui ont expiré
     *
     * @param player le joueur
     */
    public void playerRemoveAllExpiredCooldowns(Player player) {
        if (this.cooldowns.containsKey(player.getUniqueId())) {
            this.cooldowns.get(player.getUniqueId()).removeIf((cooldown) -> this.calculateRemainingTime(cooldown) <= 0);
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

    /**
     * Change la capacité sélectionnée du joueur par la suivante de la liste
     *
     * @param player player
     */
    public void selectNextCapacity(Player player) {
        IClasse playerClasse = ClasseManager.getInstance().getPlayerClasse(player);

        //Contrôle la classe du joueur
        if (playerClasse == null) {
            player.sendMessage(ChatColor.RED + "Vous devez avoir une classe pour sélectionner votre capacité.");
            return;
        }

        //La prochaine capacité dans la sélection du joueur
        ICapacity nextCapacity = this.getNextCapacity(player, playerClasse);

        //Sélectionne la nouvelle capacité
        this.changeCurrentCapacity(player, nextCapacity);
        player.sendMessage(ChatColor.GREEN + "Vous avez sélectionné la capacité " + ChatColor.BOLD + nextCapacity.getName() + ChatColor.RESET + ChatColor.GREEN + " (" + (playerClasse.getCapacities().indexOf(nextCapacity) + 1) + ")");
    }

    /**
     * Trouve la prochaine capacité du joueur dans sa sélection
     *
     * @param playerClasse classe du joueur
     * @return prochaine capacité
     */
    private ICapacity getNextCapacity(Player player, IClasse playerClasse) {
        //Capacités de la classe du joueur
        List<ICapacity> playerCapacites = playerClasse.getCapacities();

        //Capacité sélectionne par le joueur
        ICapacity currentCapacity = this.getCurrentCapacity(player);

        //Si pas de capacité sélectionne, retourne la 1ère
        if (currentCapacity == null) {
            return playerCapacites.get(0);
        }

        //Sinon prend la prochaine
        int currentCapacityIndex = playerCapacites.indexOf(currentCapacity);
        if (currentCapacityIndex + 1 < playerCapacites.size()) {
            return playerCapacites.get(currentCapacityIndex + 1);
        } else {
            return playerCapacites.get(0);
        }
    }

    public static CapacityManager getInstance() {
        return INSTANCE;
    }
}
