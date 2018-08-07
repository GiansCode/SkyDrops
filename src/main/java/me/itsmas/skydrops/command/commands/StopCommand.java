package me.itsmas.skydrops.command.commands;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.SubCommand;
import me.itsmas.skydrops.event.EnvoyEvent;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.permissions.Permission;
import me.itsmas.skydrops.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class StopCommand extends SubCommand
{
    public StopCommand(SkyDrops plugin)
    {
        super(plugin, false, Permission.COMMAND_STOP, "<name>", "stop");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length != 1)
        {
            help(sender);
            return;
        }

        EnvoyEvent event = plugin.getEventManager().getEvent(args[0]);

        if (event == null)
        {
            Util.message(sender, Message.INVALID_EVENT);
            return;
        }

        if (!event.isActive())
        {
            Util.message(sender, Message.NOT_ACTIVE);
            return;
        }

        event.stop();

        sender.sendMessage(Message.STOPPED_EVENT.value().replace("%event%", event.getName()));
        Bukkit.broadcastMessage(Message.STOPPED_EVENT_BROADCAST.value().replace("%event%", event.getName()));
    }
}
