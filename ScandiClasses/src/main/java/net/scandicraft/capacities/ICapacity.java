package net.scandicraft.capacities;

import net.scandicraft.capacities.target.ICapacityTarget;
import org.bukkit.entity.Player;

public interface ICapacity {

    /**
     * Nom de la capacité
     *
     * @return nom
     */
    String getName();

    /**
     * Temps entre chaque lancement de la capacité (SECONDES)
     *
     * @return temps
     */
    Integer getCooldownTime();

    /**
     * Utilise la capacité
     *
     * @param sender player qui utilise la capacité
     * @param target target de la capacité
     */
    void onUse(Player sender, ICapacityTarget target);

    /**
     * Message success après l'utilisation de la capacité
     *
     * @param sender player qui receoit le message
     */
    void sendSucessMessage(Player sender);

    /**
     * Message erreur après l'utilisation de la capacité (cooldown)
     *
     * @param sender player qui receoit le message
     */
    void sendWaitingMessage(Player sender, long cooldown);

}
