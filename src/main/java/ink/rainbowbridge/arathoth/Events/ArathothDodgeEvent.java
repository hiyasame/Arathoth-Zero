package ink.rainbowbridge.arathoth.Events;

import ink.rainbowbridge.arathoth.Arathoth;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 闪避事件
 *
 * @author 寒雨
 * @create 2020/12/12 22:35
 */
public class ArathothDodgeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private LivingEntity attacker;
    private LivingEntity entity;
    private boolean isCancelled;

    public ArathothDodgeEvent(LivingEntity attacker,LivingEntity entity){
        this.attacker = attacker;
        this.entity = entity;
        this.isCancelled = false;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public LivingEntity getEntity() {
        return entity;
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
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
