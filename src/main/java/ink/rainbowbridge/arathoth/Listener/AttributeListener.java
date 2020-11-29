package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.SubAttribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 属性集中处理监听类
 *
 * @author 寒雨
 * @create 2020/11/29 10:19
 */
public class AttributeListener implements Listener {
    @EventHandler
    public void onEDBEEvent(EntityDamageByEntityEvent e){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(SubAttribute sa : Arathoth.Priority){
                    sa.Action(e);
                }
            }
        }.runTask(Arathoth.getInstance());
    }
}
