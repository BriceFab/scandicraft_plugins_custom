package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.CapacitiesConfig;
import net.scandicraft.config.ClassesConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * se téléporte sur 10 blocs devant lui
 */
public class ArcherCapacity2 extends BaseCapacity {

    @Override
    public String getName() {
        return "téléportation archer";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_2;
    }

    @Override
    public String getUniqueIdentifier() {
        return "ArcherCapacity2";
    }

    @Override
    public void onUse(Player sender) {
        //même comportement que le compass
        Set<Material> transparentMaterials = new HashSet<>();
        transparentMaterials.add(Material.AIR);
        Block targetBlock = sender.getTargetBlock(transparentMaterials, CapacitiesConfig.MAX_TARGET_DISTANCE);

        if (targetBlock != null && targetBlock.getType() != Material.AIR) {
            Location targetLocation = targetBlock.getLocation();

            //TODO check si les 3 blocs au dessus sont du vide
            System.out.print("targetBlock: " + targetBlock);
            Block blockUp1 = sender.getWorld().getBlockAt(targetLocation.getBlockX(), targetLocation.getBlockY() + 1, targetLocation.getBlockZ());
            Block blockUp2 = sender.getWorld().getBlockAt(targetLocation.getBlockX(), targetLocation.getBlockY() + 2, targetLocation.getBlockZ());

            if (blockUp1.getType() != Material.AIR || blockUp2.getType() != Material.AIR) {
                sender.sendMessage("Les 2 blocks du haut doivent être de l'air");
            } else {
                targetLocation.setY(targetLocation.getBlockY() + 1); //pas arriver dans le sol
                targetLocation.setPitch(sender.getLocation().getPitch()); //on garde les directions du player
                targetLocation.setYaw(sender.getLocation().getYaw());   //on garde les directions du player
                sender.teleport(targetLocation);
            }
//        List<Block> targetBlock = sender.getLineOfSight(transparentMaterials, 20);
        } else {
            sender.sendMessage("Vous ne pouvez pas vous téléporter dans l'air");
        }
    }
}
