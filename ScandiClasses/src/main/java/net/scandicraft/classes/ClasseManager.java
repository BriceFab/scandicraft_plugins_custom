package net.scandicraft.classes;

import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.commands.CommandList;
import net.scandicraft.sql.manager.impl.SqlClassesManager;
import org.bukkit.ChatColor;
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

    public void registerPlayerNewClasse(Player player, IClasse classe) {
        if (playersClasse.containsKey(player.getUniqueId())) {
            playersClasse.replace(player.getUniqueId(), classe);
        }
    }

    public IClasse getPlayerClasse(Player player) {
        return playersClasse.get(player.getUniqueId());
    }

    /**
     * Le joueur rejoint la classe x
     *
     * @param player    joueur
     * @param argClasse classe choisie
     */
    public void playerJoinClasse(Player player, String argClasse) {
        IClasse playerClasse = ClasseManager.getInstance().getPlayerClasse(player);
        ClasseType classeType = ClasseType.getClasseTypeFromString(argClasse);

        if (playerClasse == null) {
            if (classeType != null) {
                boolean joinSuccess = SqlClassesManager.getInstance().selectClasse(player, classeType);
                if (joinSuccess) {
                    registerPlayer(player, classeType.getIClasse());
                    player.sendMessage(ChatColor.GREEN + "Vous avez rejoint la classe " + classeType.getName() + " avec succès !");
                } else {
                    player.sendMessage(ChatColor.RED + "Vous ne pouvez pas rejoindre la classe " + classeType.getName());
                }
            } else {
                player.sendMessage(ChatColor.RED + "La classe " + argClasse + " est introuvable.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Vous faites déjà parti de la classe " + playerClasse.getDisplayClasseName());
        }
    }

    /**
     * Change la classe du joueur
     *
     * @param player     joueur
     * @param argClasse nouvelle classe
     */
    public void playerChangeClasse(Player player, String argClasse) {
        ClasseType nextClasse = ClasseType.getClasseTypeFromString(argClasse);

        if (getPlayerClasse(player) != null) {
            //TODO Check last change time
            if (nextClasse != null) {
                boolean changeSuccess = SqlClassesManager.getInstance().changePlayerClasse(player, nextClasse);
                if (changeSuccess) {
                    registerPlayerNewClasse(player, nextClasse.getIClasse());
                    player.sendMessage(ChatColor.GREEN + "Classe changée avec succès. Bienvenu dans la classe " + nextClasse.getName());
                } else {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas pu changer de classe.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "La classe " + argClasse + " est introuvable.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Vous n'avez pas de classe, veuillez en choisir une.");
            player.performCommand(CommandList.classeCommand + " help");
        }
    }

    public static ClasseManager getInstance() {
        return INSTANCE;
    }
}
