package net.scandicraft.classes;

import net.scandicraft.logs.LogManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ClasseManager {

    private static final ClasseManager INSTANCE = new ClasseManager();
    private HashMap<UUID, IClasse> playersClasse = new HashMap<>();

    private ClasseManager() {
    }

    public void registerPlayer(Player player, IClasse classe) {
        playersClasse.put(player.getUniqueId(), classe);
    }

    public void unregisterPlayer(Player player) {
        playersClasse.remove(player.getUniqueId());
    }

    public IClasse getPlayerClasse(Player player) {
        return playersClasse.get(player.getUniqueId());
    }

    public static ClasseManager getInstance() {
        return INSTANCE;
    }
}
