package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.picsou.danganronpadoor.mysql.DatabaseManager;
import java.util.Map;
import java.util.List;


public class CommandListDoors implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {

                Map<String, List<Object>> doorsMap = Main.getInstance().database.listdoor();

                if (doorsMap.isEmpty()) {
                    player.sendMessage("Il n'y a aucune porte enregistrée.");
                    return false;
                }

                player.sendMessage("Liste des portes :");
                for (String doorName : doorsMap.keySet()) {
                    player.sendMessage("- " + doorName);
                }

            } else {
                player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
                return false;
            }
        }
        return false;
    }


}
