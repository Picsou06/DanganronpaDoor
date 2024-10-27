package fr.dreamin.utils.Items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.world.food.FoodMetaData;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class ItemBuilder {
  private ItemStack is;

  public ItemBuilder(Material m) {
    this(m, 1);
  }

  public ItemBuilder(ItemStack is) {
    this.is = is;
  }

  public ItemBuilder(Material m, int amount) {
    is = new ItemStack(m, amount);
  }

  public ItemBuilder(Material m, int amount, short meta){
    is = new ItemStack(m, amount, meta);
  }

  public ItemBuilder clone() {
    return new ItemBuilder(is);
  }

  public ItemBuilder setDurability(short dur) {
    is.setDurability(dur);
    return this;
  }

  public ItemBuilder setPlayerHead(String player) {
    SkullMeta meta = (SkullMeta) is.getItemMeta();
    meta.setOwner(player);
    is.setItemMeta(meta);
    return this;
  }

  public ItemBuilder setPlayerHeadWithTexture(String texture) {
    SkullMeta meta = (SkullMeta) is.getItemMeta();
    GameProfile profile = new GameProfile(UUID.randomUUID(), null);
    profile.getProperties().put("textures", new Property("textures", texture));
    Field profileField = null;

    try {
      profileField = meta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(meta, profile);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

    is.setItemMeta(meta);

    return this;
  }

  public ItemBuilder setUnbreakable(boolean b) {
    ItemMeta im = is.getItemMeta();

    im.setUnbreakable(b);
    return this;
  }

  //setEnchantments
  public ItemBuilder setEnchant(Enchantment ench, int level) {
    ItemMeta im = is.getItemMeta();
    im.addEnchant(ench, level, true);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setName(String name) {
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(name);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setPotionColor(Color color) {

    PotionMeta meta = (PotionMeta) is.getItemMeta();
    meta.setColor(color);
    is.setItemMeta(meta);
    return this;
  }

  public ItemBuilder setCustomMeta(Integer idMeta) {
    ItemMeta im = is.getItemMeta();
    im.setCustomModelData(idMeta);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
    is.addUnsafeEnchantment(ench, level);
    return this;
  }

  public ItemBuilder removeEnchantment(Enchantment ench) {
    is.removeEnchantment(ench);
    return this;
  }

  public ItemBuilder setSkullOwner(String owner) {
    try {
      SkullMeta im = (SkullMeta) is.getItemMeta();
      im.setOwner(owner);
      is.setItemMeta(im);
    } catch (ClassCastException expected) {
    }
    return this;
  }

  public ItemBuilder addEnchant(Enchantment ench, int level) {
    ItemMeta im = is.getItemMeta();
    im.addEnchant(ench, level, true);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setInfinityDurability() {
    is.setDurability(Short.MAX_VALUE);
    return this;
  }

  public ItemBuilder setLore(String... lore) {
    ItemMeta im = is.getItemMeta();
    im.setLore(Arrays.asList(lore));
    is.setItemMeta(im);
    return this;
  }

  @SuppressWarnings("deprecation")
  public ItemBuilder setWoolColor(DyeColor color) {
    if (!is.getType().equals(Material.WHITE_WOOL))
      return this;
    this.is.setDurability(color.getWoolData());
    return this;
  }

  public ItemBuilder setLeatherArmorColor(Color color) {
    try {
      LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
      im.setColor(color);
      is.setItemMeta(im);
    } catch (ClassCastException expected) {
    }
    return this;
  }

  public ItemStack toItemStack() {
    return is;
  }
}
