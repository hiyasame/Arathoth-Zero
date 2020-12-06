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
 * OwnderRequest
 *
 * @author 寒雨
 * @create 2020/12/5 23:36
 */
public class OwnderRequest implements SubRules {

    private FileConfiguration file;
    private Pattern Primary;

    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        if (first) {
            config.set(getName()+".Pattern", "OwnderRequest: [VALUE]");
        }

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\S)"));
    }

    @Override
    public boolean RulesPass(Player p, ItemStack item) {
        if (RulesManager.Exist(this.getName())) {
            String name = null;
            if (ItemUtils.hasLore(item)) {
                for (String str : item.getItemMeta().getLore()) {
                    Matcher m = Primary.matcher(ChatColor.stripColor(str));
                    if (m.find()) {
                        name = m.group(1);
                        break;
                    }
                }
                if (name != null) {
                    if (name.equals(p.getName())) {
                        return true;
                    } else {
                        SendUtils.send(p, "物品 " + item.getItemMeta().getDisplayName() + "&7 属于 &f" + name + " &7而不属于你，将忽略其属性计算");
                        return false;
                    }
                } else {
                    return true;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
