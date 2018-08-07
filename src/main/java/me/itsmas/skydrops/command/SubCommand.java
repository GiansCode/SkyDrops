package me.itsmas.skydrops.command;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.message.Message;
import org.bukkit.command.CommandSender;

public abstract class SubCommand
{
    protected final SkyDrops plugin;

    private final boolean player;

    private final String permission;
    private final String usage;
    private final String[] aliases;

    public SubCommand(SkyDrops plugin, boolean player, String permission, String usage, String... aliases)
    {
        this.plugin = plugin;

        this.player = player;

        this.permission = permission;
        this.usage = usage;
        this.aliases = aliases;
    }

    boolean isPlayerCommand()
    {
        return player;
    }

    String getPermission()
    {
        return permission;
    }

    String[] getAliases()
    {
        return aliases;
    }

    protected void help(CommandSender sender)
    {
        sender.sendMessage(Message.COMMAND_USAGE.value().replace("%usage%", "/skydrops " + getAliases()[0] + " " + usage));
    }

    public abstract void execute(CommandSender sender, String[] args);
}
