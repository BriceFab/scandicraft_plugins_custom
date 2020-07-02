package net.scandicraft.classes;

import net.scandicraft.capacities.CapacityManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Stocke les classes des joueurs actuellement connectés
 */
public class ClasseManager {

    private static final ClasseManager INSTANCE = new ClasseManager();
    private final HashMap<UUID, IClasse> playersClasse = new HashMap<>();

    private ClasseManager() {
    }

    public void registerPlayer(Player player, IClasse classe) {
        //Ajoute le joueur
        playersClasse.put(player.getUniqueId(), classe);

        //Sélectionne la 1ère capacité du joueur
        CapacityManager.getInstance().changeCurrentCapacity(player, classe.getCapacities().get(0));
    }

    public void unregisterPlayer(Player player) {
        //Enlève le joueur
        playersClasse.remove(player.getUniqueId());

        //Déselectionne la capacité du joueur
        CapacityManager.getInstance().removeCurrentCapacity(player);
    }

    public IClasse getPlayerClasse(Player player) {
        return playersClasse.get(player.getUniqueId());
    }

    public static ClasseManager getInstance() {
        return INSTANCE;
    }
}
