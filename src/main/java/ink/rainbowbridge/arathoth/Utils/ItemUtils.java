package ink.rainbowbridge.arathoth.Utils;

import ink.rainbowbridge.arathoth.Arathoth;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static boolean isNull(ItemStack item) {
        if (item == null) {
            return true;
        }

        if (item.getType().equals(Material.AIR)) {
            return true;
        }

        return false;
    }

    public static boolean hasLore(ItemStack item) {
        if (isNull(item)) {
            return false;
        }

        if (!item.getItemMeta().hasLore()) {
            return false;
        }

        return true;
    }

    public static List<String> getUncoloredLore(ItemStack item){
        List<String> lore = new ArrayList<>();
        new BukkitRunnable() {
            @Override
            public void run() {

                for(String str : item.getItemMeta().getLore()){
                    lore.add(ChatColor.stripColor(str));
                }
            }
        }.runTaskAsynchronously(Arathoth.getInstance());
        return lore;
    }
}
