package ink.rainbowbridge.arathoth.Attributes;

import org.bukkit.plugin.Plugin;

/**
 * 0.1.0+时代大改属性对象
 * 一切属性类型的父类
 *
 * @author 寒雨
 * @create 2020/12/11 20:56
 */
public interface ArathothAttribute {
    /**
     * 获取名称
     *
     * @return name
     */
    public abstract String getName();

    /**
     * 注册方法
     *
     * @param plugin plugin
     */
    public abstract void register(Plugin plugin);

    /**
     * 是否启用
     *
     * @return isEnable
     */
    public abstract boolean isEnable();
}
