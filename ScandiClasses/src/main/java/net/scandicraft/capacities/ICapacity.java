package net.scandicraft.capacities;

import net.scandicraft.capacities.exception.CapacityException;
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
    int getCooldownTime();

    /**
     * Utilise la capacité
     *
     * @param sender player qui utilise la capacité
     */
    void onUse(Player sender) throws CapacityException;

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
