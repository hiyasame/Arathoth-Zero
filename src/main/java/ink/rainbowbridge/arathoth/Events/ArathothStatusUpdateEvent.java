package ink.rainbowbridge.arathoth.Events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 属性更新事件
 *
 * @author 寒雨
 * @create 2020/12/6 10:22
 */
public class ArathothStatusUpdateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private LivingEntity en;

    private boolean cancelledFlag = false;
    public ArathothStatusUpdateEvent(LivingEntity e){
        this.en = e;
    }

    public LivingEntity getLivingEntity(){
        return this.en;
    }

    @Override
    public boolean isCancelled() {
        return cancelledFlag;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelledFlag = true;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
