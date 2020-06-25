package net.scandicraft.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.scandicraft.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandHelper {

    public static void sendHoverMessage(Player player, String textMessage, String hoverMessage) {
        sendHoverMessage(player, textMessage, hoverMessage, null);
    }

    public static void sendHoverMessage(Player player, String textMessage, String hoverMessage, String onClickCommand) {
        TextComponent message = new TextComponent(textMessage);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
        if (onClickCommand != null) {
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, onClickCommand));
        }
        player.sendMessage(message);
    }

    public static String formatChatMessage(ChatColor color, String message) {
        return String.format("%s %s%s", Config.PLUGIN_PREFIX, color, message);
    }
}
