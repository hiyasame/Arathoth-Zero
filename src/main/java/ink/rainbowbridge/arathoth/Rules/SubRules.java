package ink.rainbowbridge.arathoth.Rules;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * @author 寒雨
 * @create 2020/11/29 7:46
 */
public interface SubRules {
    /**
     * 注册规则词条抽象方法
     *
     * @param plugin 插件
     * @param config 初始配置
     * @param first 是否第一次注册
     */
    public abstract void register(Plugin plugin, FileConfiguration config, boolean first);

    /**
     * 条件判断
     *
     * @param item 目标物品
     * @return PassOrNot
     */
    public abstract boolean RulesPass(Player p, ItemStack item);

    /**
     * 获取属性名
     *
     * @return getClass().getSimpleName()
     */
    public abstract String getName();
}
