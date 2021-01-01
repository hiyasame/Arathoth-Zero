package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributeLoader;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 监听与属性有关的一些事件
 * 0.1.3 重写，现在弓箭数据用metadata储存
 *
 * @author 寒雨
 * @create 2020/12/12 11:09
 */
public class AttributeListener implements Listener {
    @EventHandler
    public void onBowShootEvent(EntityShootBowEvent e){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(NumberAttribute na : AttributeLoader.RegisteredNum.keySet()){
                    ArathothAPI.setArrowData(e.getProjectile(),na.getName(),na.parseNumber(AttributeLoader.getEntityLore(e.getEntity())));
                }
            }
        }.runTask(Arathoth.getInstance());
    }
}
