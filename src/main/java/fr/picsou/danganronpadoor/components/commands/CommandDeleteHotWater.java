package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeleteHotWater implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {
                if (strings.length < 1) {
                    player.sendMessage("Merci d'ajouter le nom de la source à supprimer !");
                    return false;
                }

                String WaterName = String.join(" ", strings);

                if (Main.getInstance().database.hashotwater(WaterName)) {
                    Main.getInstance().database.deletehotwater(WaterName);
                    player.sendMessage("La source '" + WaterName + "' a été supprimée de la base de données.");
                } else {
                    player.sendMessage("La source '" + WaterName + "' n'existe pas !");
                }

            } else {
                player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
                return false;
            }
        }
        return false;
    }
}
