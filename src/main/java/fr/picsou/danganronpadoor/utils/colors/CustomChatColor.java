package fr.picsou.danganronpadoor.utils.colors;

import net.md_5.bungee.api.ChatColor;

public enum CustomChatColor {

  WHITE(ChatColor.of("#FFFFFF"));

  private final ChatColor color;

  CustomChatColor(ChatColor color) {
    this.color = color;
  }

}
