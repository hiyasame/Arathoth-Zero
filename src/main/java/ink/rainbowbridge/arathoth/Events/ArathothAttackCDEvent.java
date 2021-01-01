package ink.rainbowbridge.arathoth.Events;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

/**
 * 攻击冷却事件
 * 0.1.2 Update 现在可以通过监听事件修改CD来操作CD
 *
 * @author 寒雨
 * @create 2020/12/12 13:52
 */
public class ArathothAttackCDEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Integer time;
    private LivingEntity entity;

    public ArathothAttackCDEvent(Player p,Integer ticks, LivingEntity e){
        this.player = p;
        this.time = ticks;
        this.entity = e;
    }

    public Integer getCD(){
        return this.time;
    }

    public void setCD(Integer time) {
        this.time = time;
    }

    public Player getPlayer(){
        return this.player;
    }

    public LivingEntity getEntity(){return this.getEntity();}

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
