package me.itsmas.skydrops.event;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.collect.Lists;
import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.util.RandomMap;
import me.itsmas.skydrops.util.Util;
import me.itsmas.skydrops.util.UtilCommand;
import me.itsmas.skydrops.util.UtilLoc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class EnvoyEvent
{
    private final SkyDrops plugin;

    private final String name;

    private final Set<Location> locations;

    private final RandomMap<String> commands;
    private final Map<Double, String> rawCommands;

    EnvoyEvent(SkyDrops plugin, String name, Set<Location> locations, RandomMap<String> commands, Map<Double, String> rawCommands)
    {
        this.plugin = plugin;

        this.name = name;

        this.locations = locations;

        this.commands = commands;
        this.rawCommands = rawCommands;
    }

    EnvoyEvent(SkyDrops plugin, String name)
    {
        this.plugin = plugin;

        this.name = name;

        this.locations = new HashSet<>();

        this.commands = new RandomMap<>();
        this.rawCommands = new HashMap<>();
    }

    public String getName()
    {
        return name;
    }

    private boolean active = false;

    public boolean isActive()
    {
        return active;
    }

    public void activate(int chests, int seconds)
    {
        active = true;

        spawnChests(chests, seconds);
    }

    public void addLocation(Location location)
    {
        locations.add(new Location(location.getWorld(), (double) location.getBlockX(), (double) location.getBlockY(), (double) location.getBlockZ()));
    }

    boolean isChest(Chest chest)
    {
        boolean contains = chests.keySet().contains(chest);

        if (contains)
        {
            Hologram hologram = chests.remove(chest);

            if (hologram != null)
            {
                hologram.delete();
            }

            updateActive();
        }

        return contains;
    }

    public int getLocationCount()
    {
        return locations.size();
    }

    public int getCommandsCount()
    {
        return commands.size();
    }

    private Map<Chest, Hologram> chests = new HashMap<>();

    private HolographicDisplays holographs;

    private void spawnChests(int amount, int seconds)
    {
        if (holographs == null)
        {
            holographs = plugin.getEventManager().getHolographicDisplays();
        }

        String hologramLine = Util.colour(plugin.getConfig("hologram_message")).replace("%name%", getName());

        List<Location> locations = Lists.newArrayList(this.locations);

        Collections.shuffle(locations, ThreadLocalRandom.current());

        for (int i = 0; i < amount; i++)
        {
            Location location = locations.get(i);
            location.getBlock().setType(Material.CHEST);

            Hologram hologram = null;

            if (holographs != null)
            {
                hologram = HologramsAPI.createHologram(plugin, location.clone().add(0.5, 1.5, 0.5));

                try
                {
                    hologram.appendTextLine(hologramLine);
                }
                catch (Exception ignored) {}
            }

            chests.put((Chest) location.getBlock().getState(), hologram);
        }

        if (seconds != 0)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if (isActive())
                    {
                        cleanup();

                        broadcastEventEnded();
                    }
                }
            }.runTaskLater(plugin, 20L * seconds);
        }
    }

    public void stop()
    {
        cleanup();
    }

    private void updateActive()
    {
        if (chests.size() == 0)
        {
            active = false;

            broadcastEventEnded();
        }
    }

    private void broadcastEventEnded()
    {
        Bukkit.broadcastMessage(Message.EVENT_ENDED.value().replace("%name%", getName()));
    }

    String getRandomCommand()
    {
        return commands.nextRandom();
    }

    public void addCommand(double chance, String command)
    {
        commands.put(command, chance);
        rawCommands.put(chance, command);
    }

    /**
     * Set all possible chest blocks to air and save locations
     */
    void cleanup()
    {
        active = false;

        locations.stream().filter(location -> location.getBlock().getType() == Material.CHEST).forEach(loc -> loc.getBlock().setType(Material.AIR));

        if (holographs != null)
        {
            chests.keySet().stream().map(chests::get).filter(Objects::nonNull).forEach(Hologram::delete);
        }

        plugin.getConfig().set("drops." + getName() + ".locations", UtilLoc.serializeLocations(locations));
        plugin.getConfig().set("drops." + getName() + ".commands", UtilCommand.serializeCommands(rawCommands));
    }
}
