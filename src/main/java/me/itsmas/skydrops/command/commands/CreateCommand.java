package me.itsmas.skydrops.command.commands;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.SubCommand;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.permissions.Permission;
import me.itsmas.skydrops.util.Util;
import org.bukkit.command.CommandSender;

public class CreateCommand extends SubCommand
{
    public CreateCommand(SkyDrops plugin)
    {
        super(plugin, false, Permission.COMMAND_CREATE, "<name>", "create");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length != 1)
        {
            help(sender);
            return;
        }

        String name = args[0];

        if (plugin.getEventManager().getEvent(name) != null)
        {
            Util.message(sender, Message.ALREADY_EXISTS);
            return;
        }

        sender.sendMessage(Message.ADDED_EVENT.value().replace("%name%", args[0]));

        plugin.getEventManager().addEvent(name);
    }
}
