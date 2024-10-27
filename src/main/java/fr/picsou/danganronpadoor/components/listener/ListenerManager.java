package fr.picsou.danganronpadoor.components.listener;

import fr.picsou.danganronpadoor.components.listener.Player.MovePlayerListener;
import fr.picsou.danganronpadoor.components.listener.blocks.BlockListener;
import fr.picsou.danganronpadoor.components.listener.interact.EnderChestListener;
import fr.picsou.danganronpadoor.components.listener.interact.InteractListener;
import fr.picsou.danganronpadoor.components.listener.Player.JoinPlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager {

    public ListenerManager(JavaPlugin plugin) {

        plugin.getServer().getPluginManager().registerEvents(new BlockListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new JoinPlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InteractListener(), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new MovePlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EnderChestListener(), plugin);
    }
}
