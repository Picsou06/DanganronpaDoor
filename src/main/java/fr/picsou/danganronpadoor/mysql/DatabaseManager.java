package fr.picsou.danganronpadoor.mysql;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private String urlBase;
    private String host;
    private String database;
    private String userName;
    private String password;

    private int port;
    private static Connection connection;

    public DatabaseManager(String host, Integer port, String database, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.database = database;
    }
    public static Connection getConnection() {
        return connection;
    }

    public void connection() {
        if (!isOnline()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" +this.database, this.userName, this.password);
                Bukkit.getConsoleSender().sendMessage("[§eDanganronpaManager§6] §aON");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void addDoor(String name, int x, int y, int z, int open){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Portes (name, x, y, z) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, String.valueOf(x));
            preparedStatement.setString(3, String.valueOf(y));
            preparedStatement.setString(4, String.valueOf(z));
            preparedStatement.execute();
            preparedStatement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public boolean hasdoor(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM Portes WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            boolean doorExists = rs.next();
            rs.close();
            preparedStatement.close();

            return doorExists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Location getdoorlocation(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT x, y, z FROM Portes WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                return new Location(Bukkit.getWorld("world"), x, y, z);
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean getdoorstate(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT open FROM Portes WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                boolean open = rs.getBoolean("open");
                return open;
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Map<String, List<Object>> listdoor() {
        Map<String, List<Object>> doorsMap = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM Portes");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String doorName = rs.getString("name");
                doorsMap.put(doorName, new ArrayList<>());
            }

            rs.close();
            preparedStatement.close();

            return doorsMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void updateDoorState(String name, int open) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Portes SET open = ? WHERE name = ?");
            preparedStatement.setString(1, String.valueOf(open));
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDoor(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Portes WHERE name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHotWater(String name, int x, int y, int z, int distance){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO HotWater (name, x, y, z, distance) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, String.valueOf(x));
            preparedStatement.setString(3, String.valueOf(y));
            preparedStatement.setString(4, String.valueOf(z));
            preparedStatement.setString(5, String.valueOf(distance));
            preparedStatement.execute();
            preparedStatement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean hashotwater(String name) {
        Map<String, List<Integer>> hotWaterMap = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM HotWater WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            boolean doorExists = rs.next();
            rs.close();
            preparedStatement.close();

            return doorExists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deletehotwater(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM HotWater WHERE name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<Object>> listhotwater() {
        Map<String, List<Object>> doorsMap = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM HotWater");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String doorName = rs.getString("name");
                doorsMap.put(doorName, new ArrayList<>());
            }

            rs.close();
            preparedStatement.close();

            return doorsMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public Location gethotwaterlocation(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT x, y, z FROM HotWater WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                return new Location(Bukkit.getWorld("world"), x, y, z);
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int gethotwaterdistance(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT distance FROM HotWater WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int distance = rs.getInt("distance");
                return distance;
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean hasEnderChest(Player player) {
        String playerId = player.getUniqueId().toString();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT player_id FROM ender_chest_data WHERE player_id = ?")) {
            statement.setString(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Inventory getEnderChest(Player player) {
        try {
            String playerId = player.getUniqueId().toString();
            PreparedStatement selectStatement = connection.prepareStatement("SELECT slot, item_type, item_amount, item_meta FROM ender_chest_data WHERE player_id = ?");
            selectStatement.setString(1, playerId);
            ResultSet resultSet = selectStatement.executeQuery();

            Inventory customEnderChest = Bukkit.createInventory(null, 9, "Votre coffre de l'Ender");

            while (resultSet.next()) {
                int slot = resultSet.getInt("slot");
                String itemType = resultSet.getString("item_type");
                int itemAmount = resultSet.getInt("item_amount");
                String itemMeta = resultSet.getString("item_meta");

                ItemStack itemStack = new ItemStack(Material.valueOf(itemType), itemAmount);
                ItemMeta meta = itemStack.getItemMeta();
                itemStack.setItemMeta(meta);

                customEnderChest.setItem(slot, itemStack);

            }
            return customEnderChest;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createDefaultEnderChest(Player player) {
        String playerId = player.getUniqueId().toString();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO ender_chest_data (player_id, slot, item_type, item_amount, item_meta) VALUES (?, ?, ?, ?, ?)")) {

            Inventory defaultEnderChest = Bukkit.createInventory(null, 9, "Votre coffre de l'Ender");

            ItemStack apple = new ItemStack(Material.APPLE);
            defaultEnderChest.setItem(0, apple);


            for (int i = 0; i < defaultEnderChest.getSize(); i++) {
                ItemStack itemStack = defaultEnderChest.getItem(i);
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    statement.setString(1, playerId);
                    statement.setInt(2, i);
                    statement.setString(3, itemStack.getType().toString());
                    statement.setInt(4, itemStack.getAmount());
                    statement.setString(5, itemStack.getItemMeta().toString());
                    statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deconnexion() {
        if (isOnline())
            try {
                connection.close();
                Bukkit.getConsoleSender().sendMessage("[§eDanganronpaManager§6] §cOFF");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public boolean isOnline() {
        try {
            if (connection == null || connection.isClosed())
                return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}