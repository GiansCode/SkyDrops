package me.itsmas.skydrops.util;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class Util
{
    private static final SkyDrops plugin = JavaPlugin.getPlugin(SkyDrops.class);

    public static void registerListener(Listener listener)
    {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public static String[] trimArgs(String[] args)
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(args));
        list.remove(0);

        return list.toArray(new String[0]);
    }

    public static String colour(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void message(CommandSender sender, Message message)
    {
        sender.sendMessage(message.value());
    }

    public static void log(String msg)
    {
        plugin.getLogger().info(msg);
    }

    public static void logErr(String msg)
    {
        plugin.getLogger().log(Level.WARNING, msg);
    }


}
