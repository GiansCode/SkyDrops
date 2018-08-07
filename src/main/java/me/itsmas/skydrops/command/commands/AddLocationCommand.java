package me.itsmas.skydrops.command.commands;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.SubCommand;
import me.itsmas.skydrops.event.EnvoyEvent;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.permissions.Permission;
import me.itsmas.skydrops.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddLocationCommand extends SubCommand
{
    public AddLocationCommand(SkyDrops plugin)
    {
        super(plugin, true, Permission.COMMAND_ADD_LOCATION, "<name>", "addlocation", "addloc");
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

        EnvoyEvent event = plugin.getEventManager().getEvent(name);

        if (event == null)
        {
            Util.message(sender, Message.INVALID_EVENT);
            return;
        }

        sender.sendMessage(Message.ADDED_LOCATION.value().replace("%name%", event.getName()));

        event.addLocation(((Player) sender).getLocation());
    }
}
