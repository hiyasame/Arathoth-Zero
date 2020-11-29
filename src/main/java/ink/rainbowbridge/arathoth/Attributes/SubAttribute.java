package ink.rainbowbridge.arathoth.Attributes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract interface SubAttribute {
    /**
     * 注册属性抽象方法
     *
     * @param plugin 插件
     * @param config 默认文件
     * @param first 是否需要生成默认文件
     */
    public abstract void register(Plugin plugin , FileConfiguration config , boolean first);


    /**
     * parseAttr
     *
     * @param uncoloredlores 去颜色lore
     * @return Double[1,2,3] -> Primary Regular Percent
     */
    public abstract Double[] parse(List<String> uncoloredlores);

    /**
     *
     * @return getClass().getSimpleName();
     */
    public abstract String getName();

    /**
     *
     * @return 优先级，超过500视为禁用
     */
    public abstract Integer getPriority();

    /**
     * 处理属性事件方法
     *
     * @param e 当前事件
     */
    public abstract void Action(Event e);
}
