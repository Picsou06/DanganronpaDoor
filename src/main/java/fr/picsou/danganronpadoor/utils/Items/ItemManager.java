/*
package fr.picsou.danganronpadoor.utils.Items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
  private static final ItemManager itemManager = new ItemManager();

  public static ItemManager getGuiManagers() {
    return itemManager;
  }

  public void itemSetName(String name, Material material, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore);
    inv.setItem(slot, it.toItemStack());
  }

  public void itemSetName(String name, Material material, Color potionColor, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore).setPotionColor(potionColor);
    inv.setItem(slot, it.toItemStack());
  }

  public void itemSetItemStack(ItemStack itemStack, Inventory inv, int slot) {
    inv.setItem(slot, itemStack);
  }

  public void itemSetName(String name, Material material, Integer idMeta, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setCustomMeta(idMeta).setLore(lore);
    inv.setItem(slot, it.toItemStack());
  }

  public void itemSetName(String name, Material material, Integer idMeta, Enchantment ench, int level, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setCustomMeta(idMeta).setLore(lore).setEnchant(ench, level);
    inv.setItem(slot, it.toItemStack());
  }

  public void itemSetName(String name, Material material, Enchantment ench, int level, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore).setEnchant(ench, level);
    inv.setItem(slot, it.toItemStack());
  }

  public void itemSetPlayer(String name, Material material, String player, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore).setPlayerHead(player);
    inv.setItem(slot, it.toItemStack());
  }

  public void itemSetPlayerWithTexture(String name, Material material, String texture, Inventory inv, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore).setPlayerHeadWithTexture(texture);
    inv.setItem(slot, it.toItemStack());
  }

  public void setFond(String name, Material material, Inventory inv, String...lore) {
    for (int i = 0; i < inv.getSize(); i++) {
      ItemBuilder fond = new ItemBuilder(material).setName(name).setLore(lore);
      inv.setItem(i, fond.toItemStack());
    }
  }

  public void setFond(String name, Material material, Integer idMeta, Inventory inv, String...lore) {
    for (int i = 0; i < inv.getSize(); i++) {
      ItemBuilder fond = new ItemBuilder(material).setName(name).setCustomMeta(idMeta).setLore(lore);
      inv.setItem(i, fond.toItemStack());
    }
  }

  public void setItemPos(String name, Material material,int start, int end, Inventory inv, String... lore) {
    for (int i = start; i < end; i++) {
      ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore);
      inv.setItem(i, it.toItemStack());
    }
  }

}
*/
