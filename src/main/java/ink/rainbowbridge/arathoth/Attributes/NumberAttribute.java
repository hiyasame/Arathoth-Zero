package ink.rainbowbridge.arathoth.Attributes;

import ink.rainbowbridge.arathoth.Attributes.data.AttributeData;
import org.bukkit.event.Event;

import java.util.List;

/**
 * 数值类型属性
 *
 * @author 寒雨
 * @create 2020/12/11 20:58
 */
public interface NumberAttribute extends ArathothAttribute{
    /**
     * 从物品中获取属性值
     * 0.1.3 返回值类型由拉胯Double[]转为AttributeData
     *
     * @param uncoloredlores lore
     * @return data
     */
    public abstract AttributeData parseNumber(List<String> uncoloredlores);

    /**
     * 事件处理
     *
     * @param e 事件
     */
    public abstract void function(Event e);
}
