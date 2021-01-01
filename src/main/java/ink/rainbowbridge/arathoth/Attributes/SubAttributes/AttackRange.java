package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.SpecialAttribute;
import ink.rainbowbridge.arathoth.Events.ArathothAttackCDEvent;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 寒雨
 * @Since 2021/1/1 14:07
 */
public class AttackRange implements SpecialAttribute, Listener {
    private boolean isEnable;
    private Pattern pattern;
    private FileConfiguration config;
    @Override
    public Object parseValue(LivingEntity e) {
        if(e instanceof Player){
            Player p = (Player)e;
            String value = null;
            for(String str : ItemUtils.getUncoloredLore(p.getInventory().getItemInMainHand())){
                Matcher m = pattern.matcher(str);
                if (m.find()){
                    value = m.group(1);
                    return Double.valueOf(value);
                }
            }
        }
        return null;
    }

    @Override
    public void function(Event e, LivingEntity entity) {
        if(isEnable()){
            if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
                if (event.getDamager() instanceof LivingEntity) {
                    List<Entity> entities = new ArrayList<>();
                    try{
                        entities.addAll(event.getDamager().getNearbyEntities((Double) parseValue((LivingEntity) event.getDamager()),(Double) parseValue((LivingEntity) event.getDamager()),(Double) parseValue((LivingEntity) event.getDamager())));
                        entities.remove(event.getDamager());
                        entities.remove(event.getEntity());
                    }catch (NullPointerException ignored){}
                    for(Entity en : entities){
                        if(en instanceof LivingEntity){
                            ((LivingEntity) en).damage(event.getDamage());
                        }
                    }
                }
                else if (event.getDamager() instanceof Arrow){
                    List<Entity> entities = new ArrayList<>();
                    try{
                        entities.addAll(event.getDamager().getNearbyEntities((Double) parseValue((LivingEntity) event.getDamager()),(Double) parseValue((LivingEntity) event.getDamager()),(Double) parseValue((LivingEntity) event.getDamager())));
                        entities.remove(event.getDamager());
                        entities.remove(event.getEntity());
                    }catch (NullPointerException ignored){}
                    for(Entity en : entities){
                        if(en instanceof LivingEntity){
                            ((LivingEntity) en).damage(event.getDamage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getPlaceHolder(Player p) {
        return Arathoth.DecimalFormat.format((Double)parseValue(p));
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void register(Plugin plugin) {
// TODO 载入配置
        if(ArathothAPI.AttributeConfigDefaultSet(this)){
            config = ArathothAPI.getAttributeConfig(this);
            config.set(getName()+".Enable", true);
            config.set(getName()+".Pattern", getName()+": [VALUE]");
            ArathothAPI.saveAttributeConfig(this,config);
        }
        else{
            config = ArathothAPI.getAttributeConfig(this);
        }
        Bukkit.getPluginManager().registerEvents(this, Arathoth.getInstance());
        pattern = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\d+)"));
        isEnable = config.getBoolean(getName()+".Enable",false);

    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAttackRange(EntityDamageByEntityEvent e){
        if(e.isCancelled()){
            return;
        }
        function(e,null);
    }
}
