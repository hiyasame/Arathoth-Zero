package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributeManager;
import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Attributes.SubAttribute;
import ink.rainbowbridge.arathoth.Utils.AttrUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 属性集中处理监听类
 *
 * @author 寒雨
 * @create 2020/11/29 10:19
 */
public class AttributeListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
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

    @EventHandler
    public void onBowShootEvent(EntityShootBowEvent e){
        new BukkitRunnable(){

            @Override
            public void run() {
                AttributeManager.setProjectileData(e.getProjectile().getUniqueId().toString(), AttrUtils.getAttributesData(e.getEntity().getUniqueId().toString()));
            }
        }.runTask(Arathoth.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void UnregisterArrowAttr(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Arrow) {
            AttributesData.AttrData.remove(e.getDamager().getUniqueId().toString());
        }
    }
}
