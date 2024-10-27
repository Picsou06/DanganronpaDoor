package fr.picsou.danganronpadoor.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class GameUtils {

  private static final String PROFILE_API_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";

  public static List<Material> voidMaterial = Arrays.asList(Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD, Material.IRON_BARS, Material.DARK_OAK_DOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.CRIMSON_DOOR, Material.IRON_DOOR, Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.WARPED_DOOR, Material.DARK_OAK_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.CRIMSON_TRAPDOOR, Material.IRON_TRAPDOOR, Material.JUNGLE_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.WARPED_TRAPDOOR, Material.DARK_OAK_SIGN, Material.ACACIA_SIGN, Material.BIRCH_SIGN, Material.JUNGLE_SIGN, Material.OAK_SIGN, Material.SPRUCE_SIGN, Material.WARPED_SIGN, Material.DARK_OAK_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.CRIMSON_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.OAK_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.WARPED_WALL_SIGN, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.DARK_OAK_FENCE, Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.CRIMSON_FENCE, Material.JUNGLE_FENCE, Material.OAK_FENCE, Material.SPRUCE_FENCE, Material.WARPED_FENCE
  );
  public static List<Material> fullMaterial = Arrays.asList(Material.SCAFFOLDING);

  public static void sendActionBarMessage(Player player, String message) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }

  public static boolean compareItem(ItemStack itemA, ItemStack itemB) {

    return itemA != null
            && itemB != null &&
            ((itemA.getItemMeta() == null && itemB.getItemMeta() == null) || (itemA.getItemMeta() != null && itemB.getItemMeta() != null && itemA.getItemMeta().getDisplayName().equals(itemB.getItemMeta().getDisplayName())))
            && itemA.getType().equals(itemB.getType());

  }

  public static String brouilleMessage(String message) {
    StringBuilder resultat = new StringBuilder();

    for (int i = 0; i < message.length(); i++) {
      char lettre = message.charAt(i);

      if (i % 4 == 0)
        resultat.append("§k" + lettre + "§r");
      else
        resultat.append(lettre);
    }

    return resultat.toString();
  }

  public static Location getLocWithYawAndPitch(Location location, float yaw, float pitch) {
    Location loc = location.clone();
    loc.setYaw(yaw);
    loc.setPitch(pitch);
    return loc;
  }

  public static String getPlayerName(String uuid) throws Exception {
    URL url = new URL(PROFILE_API_URL + uuid);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }
    reader.close();

    String response = builder.toString();
    if (response == null || response.isEmpty()) {
      throw new Exception("Response from server is empty.");
    }

    JsonObject json = new JsonParser().parse(response).getAsJsonObject();
    if (!json.has("name")) {
      throw new Exception("Invalid UUID.");
    }

    String name = json.get("name").getAsString();
    return name;
  }

  public static String getSkinName(NPC npc) {
    SkinTrait skinTrait = npc.getTrait(SkinTrait.class);
    return skinTrait.getSkinName();
  }

  public static boolean isBlockNearPlayer(Player player, Material material, int radius) {
    Location playerLocation = player.getLocation();
    World world = playerLocation.getWorld();
    int playerX = playerLocation.getBlockX();
    int playerY = playerLocation.getBlockY();
    int playerZ = playerLocation.getBlockZ();

    for (int x = playerX - radius; x <= playerX + radius; x++) {
      for (int y = playerY - radius; y <= playerY + radius; y++) {
        for (int z = playerZ - radius; z <= playerZ + radius; z++) {
          Block block = world.getBlockAt(x, y, z);
          if (block.getType() == material) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static Location getLocationToGround(Location loc) {
    Location location = loc.getBlock().getLocation().clone();

    boolean isOnGround = false;
    while (!isOnGround) {
      location.add(0, -1, 0);
      Block block = location.getBlock();
      Material blockType = block.getType();

      if (blockType.isSolid()) {
        isOnGround = true;
        location.setX((double) Math.round(loc.getX() * 10000.0) / 10000.0);
        location.setZ((double) Math.round(loc.getZ() * 10000.0) / 10000.0);

        adjustLocationForSpecialBlocks(location, loc);

        location.setPitch(loc.getPitch());
        location.setYaw(loc.getYaw());

        return location;
      }
    }
    return null;
  }

  public static void teleportPlayerToGround(Player player) {
    Location location = player.getLocation().getBlock().getLocation().clone();

    boolean isOnGround = false;

    while (!isOnGround) {
      location.add(0, -1, 0);
      Block block = location.getBlock();
      Material blockType = block.getType();

      if (blockType.isSolid()) {
        isOnGround = true;
        location.setX((double) Math.round(player.getLocation().getX() * 10000.0) / 10000.0);
        location.setZ((double) Math.round(player.getLocation().getZ() * 10000.0) / 10000.0);

        adjustLocationForSpecialBlocks(location, player.getLocation());

        location.setPitch(player.getLocation().getPitch());
        location.setYaw(player.getLocation().getYaw());
        player.teleport(location);
      }
    }
  }

  private static void adjustLocationForSpecialBlocks(Location location, Location playerLocation)  {
    Block block = location.getBlock();
    Material blockType = block.getType();

    if (block.getBlockData() instanceof Slab) {
      Slab slab = (Slab) block.getBlockData();

      if (slab.getType() == Slab.Type.TOP)
        location.add(0, 1, 0);
      else if (slab.getType() == Slab.Type.DOUBLE)
        location.add(0, 1, 0);
      else
        location.add(0, 0.5, 0);
    }
    else if (block.getBlockData() instanceof TrapDoor) {
      TrapDoor trapDoor = (TrapDoor) block.getBlockData();
      if (trapDoor.getHalf() == Bisected.Half.TOP)
        location.add(0, 1, 0);
      else if (trapDoor.isOpen())
        location.add(0, 1, 0);
      else
        location.add(0, 0.19, 0);
    }
    else if (block.getBlockData() instanceof Stairs) {
      Stairs stairs = (Stairs) block.getBlockData();

      if (stairs.getHalf() == Bisected.Half.TOP)
        location.add(0, 1, 0);
      else {
        double yDifference = playerLocation.getY() - location.getY();
        boolean isPlayerInAir = !playerLocation.getBlock().getRelative(0, -1, 0).getType().isSolid();
        boolean isBlockAboveSolid = block.getRelative(0, 1, 0).getType().isSolid();

        if (yDifference >= 1.0 && !isPlayerInAir) {
          location.add(0, 1, 0);
        } else if (yDifference >= 0.5 && !isPlayerInAir && !isBlockAboveSolid) {
          location.add(0, 0.5, 0);
        } else {
          location.add(0, 1, 0);
        }
      }
    }
    else if (blockType == Material.SNOW) {
      Snow snow = (Snow) block.getBlockData();
      int layers = snow.getLayers(); // obtient le nombre de couches
      // convertit le nombre de couches en hauteur (chaque couche mesure 1/8 bloc)
      double height = layers / 8.0;
      location.add(0, height, 0);
    }
    else if (blockType.name().contains("FENCE") || blockType.name().contains("WALL")) {
      location.add(0, 1.5, 0);
    } else {
      location.add(0, 1, 0);
    }
  }

  public static boolean isInBlocOrUnderSolidBloc(Location location) {
    Block blockUnder = location.getBlock();
    Block blockFeet = location.clone().add(0, -1, 0).getBlock();

    if (blockUnder.getType().isSolid() || blockFeet.getType().isSolid())
      return true;
    else
      return false;
  }

  public static double calculerRatio(int parametre, int nbrDivisé) {
    double ratio = (double)parametre / (double)nbrDivisé;
    return Math.round(ratio * 1000.0) / 1000.0;
  }

  public static Player raycastPlayerWithParticules(Player player, double maxDistance, Particle particle, int nbrParticule, double vitesse, double v1, double v2, double v3, double particleGap, boolean particulePassedThrough) {
    Location playerLocation = player.getEyeLocation();
    Vector playerDirection = playerLocation.getDirection();

    World world = player.getWorld();
    RayTraceResult result = playerLocation.getWorld().rayTrace(playerLocation, playerDirection, maxDistance, FluidCollisionMode.NEVER, true, 0.5, entity -> entity instanceof Player && !entity.getUniqueId().equals(player.getUniqueId()));

    double distance = maxDistance;
    for (double i = 0; i < distance; i += particleGap) {
      Location particleLoc = playerLocation.clone().add(playerDirection.clone().multiply(i));
      if (!particulePassedThrough && particleLoc.getBlock().getType().isSolid())
        break;

      world.spawnParticle(particle, particleLoc, nbrParticule, vitesse ,v1,v2, v3);
    }

    if (result != null) {
      Entity hitEntity = result.getHitEntity();
      if (hitEntity instanceof Player) {
        return (Player) hitEntity;
      }
    }
    return null;
  }

  public static Player raycastPlayer(Player player, double maxDistance) {
    Location playerLocation = player.getEyeLocation();
    Vector playerDirection = playerLocation.getDirection().normalize();

    RayTraceResult result = playerLocation.getWorld().rayTrace(playerLocation, playerDirection, maxDistance, FluidCollisionMode.NEVER, true, 0.5, entity -> entity instanceof Player && !entity.getUniqueId().equals(player.getUniqueId()));

    if (result != null) {
      if (result.getHitEntity() instanceof Player) {
        return (Player) result.getHitEntity();
      }
    }
    return null;
  }

  public static void clearAllPlayersEffects() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      for (PotionEffect effect : player.getActivePotionEffects()) {
        player.removePotionEffect(effect.getType());
      }
    }
  }

  public static Block getBlockUnderPlayer(Entity entity) {
    Location loc = entity.getLocation(); // récupère l'emplacement du joueur
    loc.subtract(0, 1, 0); // décale l'emplacement d'une unité vers le bas
    return loc.getBlock(); // récupère le bloc à l'emplacement décalé
  }

  //fonction qui permet de modifier les dégats selon les effects des joueurs
  public static Double checkEffectForDamageValue(Double initialDamage, Player victim, Player damager) {

    double multiplierDamager = 1.0;
    double multiplierVictim = 1.0;

    //Verifie les effect du damager
    for (PotionEffect damagerPotionEffect : damager.getActivePotionEffects()) {
      if (damagerPotionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE) && damagerPotionEffect.getAmplifier() == 0)
        multiplierDamager = multiplierDamager + 0.5;
      else if (damagerPotionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE) && damagerPotionEffect.getAmplifier() == 1)
        multiplierDamager = multiplierDamager + 1;
      else if (damagerPotionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE) && damagerPotionEffect.getAmplifier() >= 2)
        multiplierDamager = multiplierDamager + 1.5;
      else if (damagerPotionEffect.getType().equals(PotionEffectType.WEAKNESS) && damagerPotionEffect.getAmplifier() == 0)
        multiplierDamager = multiplierDamager - 0.25;
      else if (damagerPotionEffect.getType().equals(PotionEffectType.WEAKNESS) && damagerPotionEffect.getAmplifier() == 1)
        multiplierDamager = multiplierDamager - 0.5;
      else if (damagerPotionEffect.getType().equals(PotionEffectType.WEAKNESS) && damagerPotionEffect.getAmplifier() >= 2)
        multiplierDamager = multiplierDamager - 0.75;
      else if (damagerPotionEffect.getType().equals(PotionEffectType.LUCK) && damagerPotionEffect.getAmplifier() >= 0) {
        if (new Random().nextDouble() < 0.1)
          multiplierDamager = multiplierDamager + 1;
      }
    }

    //Verifie les effect de la victime
    for (PotionEffect victimePotionEffect : victim.getActivePotionEffects()) {
      if (victimePotionEffect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) && victimePotionEffect.getAmplifier() == 0)
        multiplierVictim = multiplierVictim - 0.2;
      else if (victimePotionEffect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) && victimePotionEffect.getAmplifier() == 1)
        multiplierVictim = multiplierVictim - 0.4;
      else if (victimePotionEffect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) && victimePotionEffect.getAmplifier() >= 2)
        multiplierVictim = multiplierVictim - 0.6;
      else if (victimePotionEffect.getType().equals(PotionEffectType.LUCK) && victimePotionEffect.getAmplifier() == 0) {
        if (new Random().nextDouble() < 0.1)
          multiplierVictim = multiplierVictim - 0.5;
      }
      else if (victimePotionEffect.getType().equals(PotionEffectType.LUCK) && victimePotionEffect.getAmplifier() >= 1) {
        if (new Random().nextDouble() < 0.25)
          multiplierVictim = multiplierVictim - 0.5;
      }
    }

    //Addictionne et modifie les damages
    double modifier = multiplierDamager * multiplierVictim;
    initialDamage = initialDamage * modifier;

    return initialDamage;
  }

  public static String getTimeSecMin(long start, int cooldown) {
    long timeBetween = getTime(start, new Date().getTime());
    int minutes = (int) (timeBetween / 1000 / 60);
    int seconds = (int) (timeBetween / 1000) % 60;

    // On vérifie si le temps restant est supérieur à 60 secondes
    if (cooldown - seconds > 60) {
      // Si oui, on déduit le temps en minutes et en secondes du temps restant
      minutes = ((cooldown - seconds) / 60) - minutes;
      seconds = (cooldown - seconds) % 60;
      if (seconds < 10)
        return "Temps restant §l" + minutes + ":0" + seconds + " minutes";
      else
        return "Temps restant §l" + minutes + ":" + seconds + " minutes";
    } else {
      // Si non, on déduit le temps restant en secondes seulement
      seconds = (cooldown - seconds);
      return "Temps restant §l" + seconds + " secondes";
    }
  }

  public static String getTimeSecMin(int seconds) {

    if (seconds > 60) {
      int minutes = seconds / 60;
      seconds = seconds % 60;
      if (seconds < 10)
        return minutes + ":0" + seconds + "min";
      else
        return minutes + ":" + seconds + "min";
    } else {
      return seconds + "sec";
    }
  }


  public static int getTimeSeconds(long start) {
    long timeBetween = GameUtils.getTime(start, new Date().getTime());
    int seconds = (int) (timeBetween / 1000);
    return seconds;
  }

  public static String getDirectionalArrow(Player player, Location loc) {

    double playerX = player.getLocation().getX();
    double playerY = player.getLocation().getY();
    double playerZ = player.getLocation().getZ();

    double targetX = loc.getX();
    double targetY = loc.getY();
    double targetZ = loc.getZ();

    // Calculez l'angle entre la position actuelle du joueur et la cible
    double dx = targetX - playerX;
    double dz = targetZ - playerZ;
    double angle = Math.atan2(dz, dx);

    // Convertir l'angle en degrés
    angle = Math.toDegrees(angle) - 90;

    // Calculez l'angle de yaw du joueur
    double yaw = player.getLocation().getYaw();

    // Soustrayez l'angle de yaw du joueur de l'angle entre la position actuelle et la cible
    double rotation = yaw - angle;

    // Utilisez l'angle de rotation pour déterminer la direction à suivre
    rotation = (rotation + 360) % 360;

    if (rotation >= 337.5 || rotation < 22.5) {
      return "⬆"; // Flèche vers le haut
    } else if (rotation < 67.5) {
      return "⬉"; // Flèche diagonale haut droite
    } else if (rotation < 112.5) {
      return "⬅"; // Flèche vers la gauche
    } else if (rotation < 157.5) {
      return "⬋"; // Flèche diagonale bas gauche
    } else if (rotation < 202.5) {
      return "⬇"; // Flèche vers le bas
    } else if (rotation < 247.5) {
      return "⬊"; // Flèche diagonale bas droite
    } else if (rotation < 292.5) {
      return "➡"; // Flèche vers la droite
    } else {
      return "⬈"; // Flèche diagonale haut gauche
    }
  }

  public static Location getNearestLocation(Player player, List<Location> locations) {
    Location playerLocation = player.getLocation();
    Location closestLocation = null;
    double closestDistance = Double.MAX_VALUE;

    for (Location location : locations) {
      double distance = location.distance(playerLocation);
      if (distance < closestDistance) {
        closestLocation = location;
        closestDistance = distance;
      }
    }

    return closestLocation;
  }

  public static int getTimeSeconds(long start, int cooldown) {
    long timeBetween = GameUtils.getTime(start, new Date().getTime());
    int seconds = (int) (timeBetween / 1000);
    return cooldown - seconds;
  }


  public static long getTime(long time1, long time2) {
    return Math.abs(time1 - time2);
  }

  public static int getRandomNumber(int min, int max) {
    return new Random().nextInt((max - min) + 1) + min;
  }

  public static String chooseElement(String[] names, Double[] values) {
    Random rand = new Random();
    double total = 0;

    for (double value : values) {
      total += value;
    }

    double randomValue = rand.nextDouble() * total;

    for (int i = 0; i < values.length; i++) {
      randomValue -= values[i];
      if (randomValue <= 0) {
        return names[i];
      }
    }

    return names[names.length - 1];
  }

  public static List<String> reverseArray(List<String> array, int id) {
    List<String> result = new ArrayList<>(array.size());
    for (int i = 0; i < array.size(); i++) {
      result.add(array.get((id + i - 1) % array.size()));
    }
    return result;
  }

  public static float calculateProgress(int min, int max) {
    // Récupération de la santé de l'ennemi
    float minFloat = min;
    float maxFloat = max;
    // calculer la progression en pourcentage
    return minFloat / maxFloat;
  }

  public static double calculatePercentage(double min, double max) {
    return min * 100 / max;
  }

  public static Location findNearestEmptySpace(int y, Location location) {
    int radius = 10; // Rayon de 10 blocs
    World world = location.getWorld();
    Location nearestLocation = null;
    double minDistance = Double.MAX_VALUE;

    for (int x = -radius; x <= radius; x++) {
      for (int z = -radius; z <= radius; z++) {
        Location currentLocation = new Location(world, location.getBlockX() + x, y, location.getBlockZ() + z);
        Block lowerBlock = currentLocation.getBlock();
        Block upperBlock = currentLocation.clone().add(0, 1, 0).getBlock();
        Block blockBelow = currentLocation.clone().add(0, -1, 0).getBlock(); // Bloc sous l'espace vide

        if (lowerBlock.getType() == Material.AIR && upperBlock.getType() == Material.AIR && blockBelow.getType() != Material.AIR) {
          double distance = location.distanceSquared(currentLocation);
          if (distance < minDistance) {
            minDistance = distance;
            nearestLocation = currentLocation;
          }
        }
      }
    }

    return nearestLocation;
  }

  public static ItemStack craftPotion(String name, Color color, PotionEffectType effect, Integer duration, Integer amplifier) {

    ItemStack potion = new ItemStack(Material.POTION);
    PotionMeta meta = (PotionMeta) potion.getItemMeta();
    meta.addCustomEffect(new PotionEffect(effect, duration, amplifier, false, false), false);
    meta.setColor(color);
    meta.setDisplayName(name);
    potion.setItemMeta(meta);

    return potion;
  }


}