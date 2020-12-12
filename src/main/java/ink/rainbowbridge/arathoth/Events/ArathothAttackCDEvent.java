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
 *
 * @author 寒雨
 * @create 2020/12/12 13:52
 */
public class ArathothAttackCDEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Integer time;

    public ArathothAttackCDEvent(Player p,Integer ticks){
        this.player = p;
        this.time = ticks;
        Arathoth.Debug("取消攻击事件 (AttackCD) &f["+p.getName()+"]");
    }

    public Integer getCD(){
        return this.time;
    }

    public LivingEntity getPlayer(){
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
