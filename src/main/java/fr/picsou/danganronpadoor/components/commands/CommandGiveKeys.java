package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.picsou.danganronpadoor.mysql.DatabaseManager;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandGiveKeys implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (!player.isOp()) {
                player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
                return false;
            }

            Map<String, List<Object>> keysMap = Main.getInstance().database.listdoor();

            if (keysMap.isEmpty()) {
                player.sendMessage("Aucune porte n'existe dans la base de données !");
                return false;
            }

            for (String porte : keysMap.keySet()) {
                ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta customKey = key.getItemMeta();
                customKey.setDisplayName("Clé de " + porte);
                customKey.setLore(Arrays.asList("Peut être utilisée pour ouvrir la porte de " + porte));
                key.setItemMeta(customKey);
                player.getInventory().addItem(key);
            }

            player.sendMessage("Vous avez reçu les clés de toutes les portes !");
        }

        return true;
    }
}