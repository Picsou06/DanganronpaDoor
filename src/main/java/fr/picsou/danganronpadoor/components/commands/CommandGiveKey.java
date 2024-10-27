package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import fr.picsou.danganronpadoor.mysql.DatabaseManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandGiveKey implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length < 1) {
                player.sendMessage("Merci de donner le nom de la porte !");
                return false;
            }

            String doorName = String.join(" ", strings);

            Boolean doorInfo = Main.getInstance().database.hasdoor(doorName);

            if (!doorInfo) {
                player.sendMessage("La porte " + doorName + " n'existe pas dans la base de données !");
                return false;
            }

            if (player.isOp()) {
                ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta customKey = key.getItemMeta();
                customKey.setDisplayName("Clé de " + doorName);
                customKey.setLore(Arrays.asList("Peut être utilisée pour ouvrir la porte de " + doorName));
                key.setItemMeta(customKey);
                player.getInventory().addItem(key);
                player.sendMessage("Vous avez reçu une clé pour la porte " + doorName + " !");
            } else {
                player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
                return false;
            }
        }

        return false;
    }

}
