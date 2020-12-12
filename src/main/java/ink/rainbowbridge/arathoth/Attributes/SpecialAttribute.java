package ink.rainbowbridge.arathoth.Attributes;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * 特殊类型属性
 *
 * @author 寒雨
 * @create 2020/12/11 21:01
 */
public interface SpecialAttribute extends ArathothAttribute{
    /**
     * 获取任意类型的属性值
     *
     * @param e 对象生物
     * @return Object类型属性值
     */
    public abstract Object parseValue(LivingEntity e);

    /**
     * 事件处理，两个参数有一个不为null即可
     * 在使用事件监听执行属性时填Event，在利用runnable之类的其他方式处理属性操作时填LivingEntity
     *
     * @param e 事件，可为null
     * @param entity 指定生物，可为null
     */
    public abstract void function(Event e, LivingEntity entity);
}
