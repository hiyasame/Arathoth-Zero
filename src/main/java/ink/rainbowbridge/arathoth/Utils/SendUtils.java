package ink.rainbowbridge.arathoth.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendUtils {
    public static String prefix = "§7§l[§f§lArathoth§7§l] §7";
    public static void info(String msg){
        Bukkit.getConsoleSender().sendMessage(prefix + msg.replaceAll("&","§"));
    }
    public static void warn(String msg){
        Bukkit.getConsoleSender().sendMessage(prefix.replace("7","c").replace("f","4") + msg.replaceAll("&","§"));
    }
    public static void send(Player p ,String msg){
        p.sendMessage(prefix + msg.replaceAll("&","§"));
    }
}
