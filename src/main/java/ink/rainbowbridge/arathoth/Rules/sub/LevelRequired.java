package ink.rainbowbridge.arathoth.Rules.sub;

import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒雨
 * @create 2020/11/29 7:54
 */
public class LevelRequired implements SubRules {

    private FileConfiguration file;
    private Pattern Primary;

    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        if (first) {
            config.set(getName()+".Pattern", "LevelRequired: [VALUE]");
        }

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\d+)"));

    }

    @Override
    public boolean RulesPass(Player p, ItemStack item) {
        Integer lvl = null;
        if (RulesManager.Exist(this.getName())) {
            for (String str : item.getItemMeta().getLore()) {
                Matcher m = Primary.matcher(ChatColor.stripColor(str));
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
                    SendUtils.send(p,"等级不足以使用: "+item.getItemMeta().getDisplayName()+ " &7Required &f"+lvl+" &7but only &f"+p.getLevel());
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


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
