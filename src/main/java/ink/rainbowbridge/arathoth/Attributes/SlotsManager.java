package ink.rainbowbridge.arathoth.Attributes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class SlotsManager {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("Arathoth");
    public static FileConfiguration config = plugin.getConfig();
    public static HashMap<Integer,String> getSlots(){
        HashMap<Integer,String> Slots = new HashMap<>();
        for(String i : plugin.getConfig().getConfigurationSection("Slots.Register").getKeys(false)){
            Slots.put(Integer.parseInt(i),config.getString("Slots.Register."+i));
        }
        return Slots;
    }
}
