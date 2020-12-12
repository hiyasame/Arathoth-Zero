package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 监听与属性有关的一些事件
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
                ArathothAPI.setProjectileData(e.getProjectile(),ArathothAPI.getNumAttributeData(e.getEntity()));
            }
        }.runTask(Arathoth.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void UnregisterArrowAttr(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Arrow) {
            ArathothAPI.UnregisterProjectileData(e.getDamager());
        }
    }
}
