package ink.rainbowbridge.arathoth.Rules;

import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelRequire {
    public static boolean LevelRequirePass(Player p, ItemStack item) {
        Integer lvl = null;
        if (RulesManager.Exist("LevelRequire")) {
            Pattern p1 = Pattern.compile(RulesManager.Patterns.get("LevelRequire").replace("[VALUE]","(/d+)"));
            for (String str : item.getItemMeta().getLore()) {
                Matcher m = p1.matcher(ChatColor.stripColor(str));
                if(m.find()){
                    lvl = Integer.parseInt(m.group(1));
                    break;
                }
            }
            if(lvl != null){
                if (p.getLevel() >= lvl){
                    return true;
                }
                else{
                    SendUtils.send(p,"ItemLevelRequireNotPass: "+item.getItemMeta().getDisplayName()+ " &7Required &f"+lvl+" &7but only &f"+p.getLevel());
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }
}
