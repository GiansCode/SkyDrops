package me.itsmas.skydrops.event;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.util.RandomMap;
import me.itsmas.skydrops.util.Util;
import me.itsmas.skydrops.util.UtilCommand;
import me.itsmas.skydrops.util.UtilLoc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EventManager
{
    private final SkyDrops plugin;

    public EventManager(SkyDrops plugin)
    {
        this.plugin = plugin;

        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null)
        {
            holographicDisplays = HolographicDisplays.getInstance();
        }

        parseEvents();

        Util.registerListener(new ChestListener(plugin));
    }

    private HolographicDisplays holographicDisplays;

    HolographicDisplays getHolographicDisplays()
    {
        return holographicDisplays;
    }

    private final Set<EnvoyEvent> events = new HashSet<>();

    public EnvoyEvent getEvent(String name)
    {
        for (EnvoyEvent event : events)
        {
            if (event.getName().equalsIgnoreCase(name))
            {
                return event;
            }
        }

        return null;
    }

    Set<EnvoyEvent> getActiveEvents()
    {
        return events.stream().filter(EnvoyEvent::isActive).collect(Collectors.toSet());
    }

    public void addEvent(String name)
    {
        plugin.getConfig().set("drops." + name + ".locations", new ArrayList<String>());
        plugin.getConfig().set("drops." + name + ".commands", new ArrayList<String>());

        events.add(new EnvoyEvent(plugin, name));
    }

    /**
     * Load events from config
     */
    public void parseEvents()
    {
        events.clear();

        FileConfiguration config = plugin.getConfig();

        ConfigurationSection section = config.getConfigurationSection("drops");

        if (section != null)
        {
            for (String name : section.getKeys(false))
            {
                Set<Location> locations = UtilLoc.deserializeLocations(plugin.getConfig("drops." + name + ".locations"));

                List<String> commandsList = plugin.getConfig("drops." + name + ".commands");

                RandomMap<String> commands = UtilCommand.parseCommands(commandsList);
                Map<Double, String> rawCommands = UtilCommand.parseRawCommands(commandsList);

                events.add(new EnvoyEvent(plugin, name, locations, commands, rawCommands));
            }
        }
    }

    public void cleanup()
    {
        events.forEach(EnvoyEvent::cleanup);

        plugin.saveConfig();
    }
}
