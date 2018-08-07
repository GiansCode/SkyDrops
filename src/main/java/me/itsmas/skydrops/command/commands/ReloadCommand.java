package me.itsmas.skydrops.command.commands;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.SubCommand;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.permissions.Permission;
import me.itsmas.skydrops.util.Util;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand
{
    public ReloadCommand(SkyDrops plugin)
    {
        super(plugin, false, Permission.COMMAND_RELOAD, null, "reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        Message.init(plugin);

        plugin.getEventManager().cleanup();
        plugin.getEventManager().parseEvents();

        Util.message(sender, Message.PLUGIN_RELOADED);
    }
}
