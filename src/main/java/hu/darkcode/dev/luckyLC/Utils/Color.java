package hu.darkcode.dev.luckyLC.Utils;

import org.bukkit.ChatColor;

import java.util.List;

public class Color{

    public static List<String> tl(List<String> list) {
        return list.stream()
                .map(Color::t)
                .toList();
    }

    public static String t(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
