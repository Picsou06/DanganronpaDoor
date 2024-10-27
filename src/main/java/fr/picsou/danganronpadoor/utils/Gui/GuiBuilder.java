package fr.picsou.danganronpadoor.utils.Gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
  public abstract String name(Player player);
  public abstract int getSize();
  public abstract void contents(Player player, Inventory inv) throws Exception;
  public abstract void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action);
}
