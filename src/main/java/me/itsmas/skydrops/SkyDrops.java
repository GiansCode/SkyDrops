package me.itsmas.skydrops;

import me.itsmas.skydrops.command.SkyDropsCommand;
import me.itsmas.skydrops.event.EventManager;
import me.itsmas.skydrops.message.Message;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyDrops extends JavaPlugin
{
    private EventManager eventManager;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        Message.init(this);

        eventManager = new EventManager(this);

        getCommand("skydrops").setExecutor(new SkyDropsCommand(this));
    }

    @Override
    public void onDisable()
    {
        getEventManager().cleanup();
    }

    public EventManager getEventManager()
    {
        return eventManager;
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfig(String path)
    {
        return (T) getConfig().get(path);
    }
}
