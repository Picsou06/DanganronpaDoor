package fr.picsou.danganronpadoor.components.listener.Player;

import fr.picsou.danganronpadoor.GameTickEvent;
import fr.picsou.danganronpadoor.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class MovePlayerListener implements Listener {

    @EventHandler
    public void AtTick(GameTickEvent event) {
        Map<String, List<Object>> hotWaterMap = Main.getInstance().database.listhotwater();
        boolean isPlayerOnHotWater = false;

        for (String hotWaterName : hotWaterMap.keySet()) {
                Location hotWaterLocation = getHotWaterLocation(hotWaterName);
                if (hotWaterLocation != null) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getLocation().distance(hotWaterLocation) <= Main.getInstance().database.gethotwaterdistance(hotWaterName)) {
                                isPlayerOnHotWater = true;
                        }
                    }
            }

            if (!isPlayerOnHotWater) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location blockLocation = player.getLocation().clone().add(0, 0, 0);
                    Block block = player.getWorld().getBlockAt(blockLocation);

                    if (block.getType().equals(Material.WATER)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 3));
                        player.setFreezeTicks(100);

                        if (player.getHealth() > 2.0) {
                            player.damage(2);
                        }
                    }
                }
            }
        }
    }

    private Location getHotWaterLocation(String hotWaterName) {
        return Main.getInstance().database.gethotwaterlocation(hotWaterName);
    }
}
