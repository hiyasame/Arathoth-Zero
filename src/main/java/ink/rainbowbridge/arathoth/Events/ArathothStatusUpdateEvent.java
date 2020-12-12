package ink.rainbowbridge.arathoth.Events;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

/**
 * 属性更新事件,不再可以取消
 *
 * @author 寒雨
 * @create 2020/12/6 10:22
 */
public class ArathothStatusUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private LivingEntity en;
    private HashMap<String,Double[]> d;

    public ArathothStatusUpdateEvent(LivingEntity e, HashMap<String,Double[]> data){
        this.en = e;
        this.d = data;
    }

    public LivingEntity getLivingEntity(){
        return this.en;
    }

    public HashMap<String, Double[]> getData() {
        return d;
    }

    public void setData(HashMap<String,Double[]> data) {
        this.d = data;
        ArathothAPI.setNumAttributeData(this.en,data);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
