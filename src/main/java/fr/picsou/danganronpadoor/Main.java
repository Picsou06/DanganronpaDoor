package fr.picsou.danganronpadoor;

import fr.picsou.danganronpadoor.components.commands.*;
import fr.picsou.danganronpadoor.components.listener.ListenerManager;
import fr.picsou.danganronpadoor.mysql.DatabaseManager;
import fr.picsou.danganronpadoor.utils.Commands.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {

    private static Main instance;

    public DatabaseManager database;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[DANGANRONPA DOOR] ON");
        database = new DatabaseManager("185.157.247.93", 3306, "Danganronpa_Door", "pluginLink", "P@ssword1");
        database.connection();

        //Creation Commands
        createCommand(new SimpleCommand("adddoor", "", new CommandAddDoor()));
        createCommand(new SimpleCommand("deletedoor", "", new CommandDeleteDoor()));
        createCommand(new SimpleCommand("listdoors", "", new CommandListDoors()));
        createCommand(new SimpleCommand("givekey", "", new CommandGiveKey()));
        createCommand(new SimpleCommand("givekeys", "", new CommandGiveKeys()));
        createCommand(new SimpleCommand("addHotWater", "", new CommandAddHotWater()));
        createCommand(new SimpleCommand("deleteHotWater", "", new CommandDeleteHotWater()));
        createCommand(new SimpleCommand("listHotWater", "", new CommandListHotWater()));
        new ListenerManager(this);
        registerGameTickEvent();
    }
    private GameTickEvent gametickevent = new GameTickEvent();

    private void registerGameTickEvent(){
        Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
            @Override
            public void run(){
                Bukkit.getPluginManager().callEvent(gametickevent);
            }
        }, 1, 1);
    }


    public static Main getInstance() {
        return instance;
    }
    private void createCommand(SimpleCommand command) {
        CraftServer server = (CraftServer) getServer();
        server.getCommandMap().register(getName(), command);
    }
}