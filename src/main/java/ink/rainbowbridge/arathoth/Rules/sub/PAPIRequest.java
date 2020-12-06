package ink.rainbowbridge.arathoth.Rules.sub;

import ink.rainbowbridge.arathoth.Rules.SubRules;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * PAPIRequest - 通过PAPI判断自定义规则词条
 * 先画张大饼
 *
 * @author 寒雨
 * @create 2020/12/6 0:10
 */
public class PAPIRequest implements SubRules {

    private FileConfiguration file;
    private Pattern Primary;

    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        String[] def = {"%Arathoth_Primary_PhysicalDamage% >= 10","%player_name% equals ColdRain_Moro"};
        if (first) {
            config.set(getName()+".Pattern", "PAPIRequest: [VALUE]");
            config.set(getName()+".Register.Power", Arrays.asList(def));
        }

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\S)"));
    }

    @Override
    public boolean RulesPass(Player p, ItemStack item) {
        return false;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
