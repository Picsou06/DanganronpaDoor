package fr.picsou.danganronpadoor.components.listener.blocks;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        var config = Main.getPlugin(Main.class).getConfig();
        Player player = event.getPlayer();
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var config = Main.getPlugin(Main.class).getConfig();
        Player player = event.getPlayer();
    }
}
