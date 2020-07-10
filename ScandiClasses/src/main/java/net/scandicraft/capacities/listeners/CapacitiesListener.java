package net.scandicraft.capacities.listeners;

import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.IClasse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

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
            if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                //Change la capacité sélectionnée du joueur
                this.changeCurrentCapacity(player);
            }
        }
    }

    /**
     * Change la capacité sélectionnée du joueur
     *
     * @param player player
     */
    private void changeCurrentCapacity(Player player) {
        IClasse playerClasse = ClasseManager.getInstance().getPlayerClasse(player);

        //Contrôle la classe du joueur
        if (playerClasse == null) {
            player.sendMessage(ChatColor.RED + "Vous devez avoir une classe pour sélectionner votre capacité.");
            return;
        }

        //La prochaine capacité dans la sélection du joueur
        ICapacity nextCapacity = this.getNextCapacity(player, playerClasse);

        //Sélectionne la nouvelle capacité
        CapacityManager.getInstance().changeCurrentCapacity(player, nextCapacity);
        player.sendMessage(ChatColor.GREEN + "Vous avez sélectionné la capacité " + ChatColor.BOLD + nextCapacity.getName() + ChatColor.RESET + ChatColor.GREEN + " (" + (playerClasse.getCapacities().indexOf(nextCapacity) + 1) + ")");
    }

    /**
     * Trouve la prochaine capacité du joueur sans sa sélection
     *
     * @param playerClasse classe du joueur
     * @return prochaine capacité
     */
    private ICapacity getNextCapacity(Player player, IClasse playerClasse) {
        //Capacités de la classe du joueur
        List<ICapacity> playerCapacites = playerClasse.getCapacities();

        //Capacité sélectionne par le joueur
        ICapacity currentCapacity = CapacityManager.getInstance().getCurrentCapacity(player);

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

}
