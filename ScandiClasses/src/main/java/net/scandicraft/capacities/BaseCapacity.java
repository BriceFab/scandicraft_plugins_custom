package net.scandicraft.capacities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public abstract class BaseCapacity implements ICapacity {

    @Override
    public void sendSucessMessage(Player sender) {
        sender.sendMessage(ChatColor.GREEN + "Capacité " + ChatColor.BOLD + this.getName() + ChatColor.RESET + ChatColor.GREEN + " utilisée avec succès.");
    }

    @Override
    public void sendWaitingMessage(Player sender, long cooldown) {
        sender.sendMessage(String.format("%sVous devez attendre %s pour utiliser la capacité %s.", ChatColor.RED, this.getWaitingTime(cooldown), this.getName()));
    }

    private String getWaitingTime(long cooldown) {
        StringBuilder timeWaiting = new StringBuilder();

        long minutes = TimeUnit.SECONDS.toMinutes(cooldown);
        long secondes = TimeUnit.SECONDS.toSeconds(cooldown);

        if (minutes > 0) {
            timeWaiting.append(String.format("%d %s", (int) minutes, minutes <= 1 ? "minute " : "minutes "));
            secondes = cooldown % 60;
        }

        timeWaiting.append(String.format("%d %s", (int) secondes, secondes <= 1 ? "seconde" : "secondes"));

        return timeWaiting.toString();
    }

}
