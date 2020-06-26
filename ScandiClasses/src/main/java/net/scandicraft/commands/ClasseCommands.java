package net.scandicraft.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.CommandExecute;
import net.scandicraft.classes.ClasseType;
import net.scandicraft.sql.manager.impl.SqlClassesManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Commande pour les classes
 */
public class ClasseCommands extends CommandExecute implements Listener, CommandExecutor {
    private final String baseCommand = CommandList.classeCommand;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                //Arguments
                this.handleCommand(args, player);
            } else {
                //Pas d'arguments
                this.onNoArg(player);
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Vous devez être un joueur pour exécuter cette commande.");
        }

        return true;
    }

    private void handleCommand(String[] args, Player player) {
        switch (args[0].toLowerCase()) {
            default:
                this.argNotFound(player);
                break;
            case "help":
                this.helpCommand(player);
                break;
            case "join":
                this.joinClasseCommand(player, args);
                break;
        }
    }

    private void joinClasseCommand(Player player, String[] args) {
        if (args.length > 1) {
            String argClasse = args[1];
            ClasseType classeType = ClasseType.getClasseTypeFromString(argClasse);
            if (classeType != null) {
                boolean joinSuccess = SqlClassesManager.getInstance().selectClasse(player, classeType);
                if (joinSuccess) {
                    player.sendMessage(ChatColor.GREEN + "Vous avez rejoint la classe " + classeType.getName() + " avec succès !");
                } else {
                    player.sendMessage(ChatColor.RED + " Vous ne pouvez pas rejoindre la classe " + classeType.getName());
                }
            } else {
                player.sendMessage(ChatColor.RED + "La classe " + argClasse + " est introuvable.");
            }
        } else {
            CommandHelper.sendHoverMessage(
                    player,
                    CommandHelper.formatChatMessage(ChatColor.AQUA, "Rejoindre quel classe ? TODO"),
                    "Clique pour afficher de l'aide",
                    String.format("/%s help", baseCommand)
            );
        }
    }

    private void argNotFound(Player player) {
        CommandHelper.sendHoverMessage(
                player,
                CommandHelper.formatChatMessage(ChatColor.RED, "Argument introuvable. Besoin d'aide ?"),
                "Clique pour afficher de l'aide",
                String.format("/%s help", baseCommand)
        );
    }

    private void helpCommand(Player player) {
        CommandHelper.sendHoverMessage(
                player,
                CommandHelper.formatChatMessage(ChatColor.AQUA, "Besoin d'aide ?"),
                "Clique pour afficher de l'aide",
                String.format("/%s help", baseCommand)
        );
    }

    /**
     * Commande affichée si le joueur n'as pas mis d'arguments
     *
     * @param player joueur
     */
    private void onNoArg(Player player) {
        TextComponent message = new TextComponent(ChatColor.AQUA + "Besoin d'aide?");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Clique pour afficher de l'aide").create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s help", baseCommand)));
        player.sendMessage(message);
    }

}
