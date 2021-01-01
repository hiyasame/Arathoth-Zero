package ink.rainbowbridge.arathoth.Rules.sub;

import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PermRequest
 *
 * @author 寒雨
 * @create 2020/12/5 23:53
 */
public class PermRequest implements SubRules {
    private FileConfiguration file;
    private Pattern Primary;

    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        if (first) {
            config.set(getName()+".Pattern", "PermRequest: [VALUE]");
        }

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\S+)"));
    }

    @Override
    public boolean RulesPass(Player p, ItemStack item) {
        if (RulesManager.Exist(this.getName())) {
            String perm = null;
            if (ItemUtils.hasLore(item)){
                for (String str : item.getItemMeta().getLore()){
                    Matcher m = Primary.matcher(ChatColor.stripColor(str));
                    if (m.find()){
                        perm = m.group(1);
                        break;
                    }
                }
                if (perm != null){
                    if (p.hasPermission("Arathoth.PermRequest."+perm)){
                        return true;
                    }
                    else{
                        SendUtils.send(p,"你没有 "+item.getItemMeta().getDisplayName()+"&7的使用权");
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
