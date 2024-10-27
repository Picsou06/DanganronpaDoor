package fr.picsou.danganronpadoor.components.listener.interact;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderChestListener implements Listener {

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType() == Material.ENDER_CHEST) {
                player.sendMessage("Ender Chest");
                OpenEnderChest(player);
            }
        }
    }

    private void OpenEnderChest(Player player) {
        if (!Main.getInstance().database.hasEnderChest(player)) {
            Main.getInstance().database.createDefaultEnderChest(player);
        }

        Inventory customEnderChest = Main.getInstance().database.getEnderChest(player);
        player.openInventory(customEnderChest);
    }
}
