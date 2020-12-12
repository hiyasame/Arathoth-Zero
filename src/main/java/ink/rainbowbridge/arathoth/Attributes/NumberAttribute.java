package ink.rainbowbridge.arathoth.Attributes;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * 数值类型属性
 *
 * @author 寒雨
 * @create 2020/12/11 20:58
 */
public interface NumberAttribute extends ArathothAttribute{
    /**
     * 从物品中获取属性值
     *
     * @param item 物品
     * @return Numbers
     */
    public abstract Double[] parseNumber(ItemStack item);

    /**
     * 事件处理
     *
     * @param e 事件
     */
    public abstract void function(Event e);
}
