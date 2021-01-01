package ink.rainbowbridge.arathoth.Events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 0.1.3时代起不再使用StatusUpdateEvent操作属性
 * 而是使用它来操作属性执行时的各种数据
 * 取消它之后本次属性execute不会生效
 * 该事件不包括SpecialAttribute的功能触发
 *
 * @Author 寒雨
 * @Since 2020/12/31 23:44
 */
public class ArathothStatusExecuteEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Double value;
    private Event event;
    private String AttributeName;
    private LivingEntity trigger;
    private boolean isCancelled;

    /**
     * 事件构造方法
     *
     * @param AttributeName 属性名，更改无效
     * @param event 当前事件，不可更改
     * @param value 数值，可用set方法修改，修改后数值将作用于属性execute
     * @param trigger 触发者对象
     */
    public ArathothStatusExecuteEvent(String AttributeName,Event event,Double value,LivingEntity trigger){
        this.event = event;
        this.trigger = trigger;
        this.AttributeName = AttributeName;
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public Event getEvent() {
        return event;
    }

    public LivingEntity getTrigger() {
        return trigger;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
