package fr.picsou.danganronpadoor.components.listener.interact;

import fr.picsou.danganronpadoor.Main;
import fr.picsou.danganronpadoor.mysql.DatabaseManager;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Location;
import org.bukkit.Material;

public class InteractListener implements Listener {

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (isDoorKey(item)) {
                String keyName = item.getItemMeta().getDisplayName();
                String doorName = getDoorNameFromKey(keyName);

                if (doorName != null) {
                    Location doorLocation = Main.getInstance().database.getdoorlocation(doorName);
                    if (doorLocation != null) {
                        if (player.getLocation().distance(doorLocation) <= 3) {
                            boolean doorOpen = Main.getInstance().database.getdoorstate(doorName);
                            if (doorOpen) {
                                closeDoor(player, doorName);
                            } else {
                                openDoor(player, doorName);
                            }
                        } else {
                            player.sendMessage("Vous êtes trop éloigné de la porte pour l'ouvrir.");
                        }
                        event.setCancelled(true);
                    } else {
                        player.sendMessage("La porte " + doorName + " n'existe pas.");
                        player.getInventory().remove(item);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


    private boolean isDoorKey(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            return item.getType() == Material.TRIPWIRE_HOOK && itemMeta.hasDisplayName() && itemMeta.hasLore();
        }
        return false;
    }

    private String getDoorNameFromKey(String keyName) {
        if (keyName.startsWith("Clé de ")) {
            return keyName.substring(7);
        }
        return null;
    }

    private Location getDoorLocation(Player player, String doorName) {
        Location doorLocation = Main.getInstance().database.getdoorlocation(doorName);
        return doorLocation;
    }

    private void closeDoor(Player player, String doorName) {
        Main.getInstance().database.updateDoorState(doorName, 0);

        Location doorLocation = getDoorLocation(player, doorName);

        if (doorLocation != null) {
            World world = doorLocation.getWorld();

            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Location blockLocation = doorLocation.clone().add(0, y, z);
                    Block block = world.getBlockAt(blockLocation);
                    block.setType(Material.STRIPPED_MANGROVE_WOOD);
                }
            }
        }
    }

    private void openDoor(Player player, String doorName) {
        Main.getInstance().database.updateDoorState(doorName, 1);

        Location doorLocation = getDoorLocation(player, doorName);

        if (doorLocation != null) {
            World world = doorLocation.getWorld();

            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Location blockLocation = doorLocation.clone().add(0, y, z);
                    Block block = world.getBlockAt(blockLocation);
                    block.setType(Material.AIR);
                }
            }
        }
    }

}
