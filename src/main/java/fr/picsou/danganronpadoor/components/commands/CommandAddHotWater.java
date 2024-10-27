package fr.picsou.danganronpadoor.components.commands;

import fr.picsou.danganronpadoor.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddHotWater implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {
                if (strings.length < 1){
                    player.sendMessage("Merci d'ajouter un nom à la source!");
                    return false;
                }
                if (strings.length < 2){
                    player.sendMessage("Merci d'ajouter une distance à la source!");
                    return false;
                }
                Main.getInstance().database.addHotWater(strings[0], player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), Integer.valueOf(strings[1]));

                player.sendMessage("La source "+String.join(" ", strings[0])+" à bien été crée!");
            }
            else {
                player.sendMessage("Vous n'avez pas la permission d'executer cette commandes!");
                return false;
            }
        }
        return false;
    }
}
