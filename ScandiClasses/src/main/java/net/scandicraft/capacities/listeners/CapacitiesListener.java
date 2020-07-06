package net.scandicraft.capacities.listeners;

import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.capacities.impl.*;
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
            if (player.getItemInHand().getType() == Material.SLIME_BALL) {
                //Lance la capacité du joueur
                GuerrierCapacity1 g1 = new GuerrierCapacity1();
                GuerrierCapacity3 g3 = new GuerrierCapacity3();
                MagicienCapacity2 m2 = new MagicienCapacity2(); //TODO target PLAYER
                GuerrierCapacity2 g2 = new GuerrierCapacity2();
                ArcherCapacity3 a3 = new ArcherCapacity3();
                MagicienCapacity1 m1 = new MagicienCapacity1();
                ArcherCapacity1 a1 = new ArcherCapacity1();
                ArcherCapacity2 a2 = new ArcherCapacity2();

//                String table = SqlClassesManager.getInstance().getTable();
//                LogManager.consoleInfo("table " + table);

//                int raduis = 10;
//                List<Entity> playersInRadius = player.getNearbyEntities(raduis, raduis, raduis).stream().filter(entity -> (entity instanceof Player)).collect(Collectors.toList());
//                ICapacityTarget target = new PlayerTarget((Player) getTarget(player, playersInRadius));
//                if (target.getTarget() == null) {
//                    player.sendMessage(ChatColor.RED + " no target");
//                } else {
//                CapacityManager.getInstance().useCapacity(player, a2, null);
//                }
            } else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
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
