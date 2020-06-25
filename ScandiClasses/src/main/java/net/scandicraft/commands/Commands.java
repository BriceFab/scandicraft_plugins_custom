package net.scandicraft.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Commands extends CommandExecute implements Listener, CommandExecutor {

    public String command1 = "test";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            if (command.getName().equalsIgnoreCase(command1)) {
                Player p = (Player) sender;

                TextComponent msg = new TextComponent(ChatColor.AQUA + "Besoin d'aide?");
                msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click pour afficher de l'aide").create()));
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help"));
                p.sendMessage(msg);

//                sender.sendMessage("Commande reçue avec success " + (args[0] != null ? args[0] : " no args"));

                return true;
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Vous devez être un joueur pour exécuter cette commande.");
        }

        return false;
    }

}
