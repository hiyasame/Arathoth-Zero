package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.Arathoth;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 处理属性提示信息
 *
 * @author 寒雨
 * @create 2020/12/12 22:55
 */
public class AttributeMessageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(Arathoth.getInstance().getConfig().getBoolean("ActionBarMessageRemind",false)){
            if (e.getDamager() instanceof Player){
                Player p = (Player)e.getDamager();
            }
        }
    }
}
