package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import fr.picsou.danganronpadoor.mysql.DatabaseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeleteDoor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {
                if (strings.length < 1) {
                    player.sendMessage("Merci d'ajouter un nom de porte à supprimer !");
                    return false;
                }

                String doorName = String.join(" ", strings);

                if (Main.getInstance().database.hasdoor(doorName)) {
                    Main.getInstance().database.deleteDoor(doorName);
                    player.sendMessage("La porte '" + doorName + "' a été supprimée de la base de données.");
                } else {
                    player.sendMessage("La porte '" + doorName + "' n'existe pas !");
                }

            } else {
                player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
                return false;
            }
        }
        return false;
    }
}
