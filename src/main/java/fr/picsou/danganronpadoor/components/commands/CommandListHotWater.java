package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandListHotWater implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {

                Map<String, List<Object>> doorsMap = Main.getInstance().database.listhotwater();

                if (doorsMap.isEmpty()) {
                    player.sendMessage("Il n'y a aucune source enregistrée.");
                    return false;
                }

                player.sendMessage("Liste des sources :");
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
