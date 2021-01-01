package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Attributes.data.AttributeData;
import ink.rainbowbridge.arathoth.Events.ArathothDodgeEvent;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒雨
 * @create 2020/12/12 17:08
 */
public class DodgeRate implements NumberAttribute, Listener {
    private FileConfiguration config;
    private Pattern Primary;
    private Pattern Regular;
    private Pattern Percent;
    private boolean isEnable;

    @Override
    public AttributeData parseNumber(List<String> uncoloredlores) {
        /*
         * 0.1.3 时代新parse方法
         */
        AttributeData value = new AttributeData();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String str : uncoloredlores) {
                    Matcher m1 = Primary.matcher(str);
                    Matcher m2 = Regular.matcher(str);
                    Matcher m3 = Percent.matcher(str);
                    if(m1.find()){
                        value.setPrimary(value.getPrimary() + Double.parseDouble(m1.group(1)));
                        value.setRegular(value.getRegular() + Double.parseDouble(m1.group(1)));
                    }
                    if (m2.find()){
                        value.setPrimary(value.getPrimary() + Double.parseDouble(m2.group(1)));
                        value.setRegular(value.getRegular() + Double.parseDouble(m2.group(6)));
                    }
                    if (m3.find()){
                        value.setPercent(value.getPercent() + Double.parseDouble(m3.group(1)));
                    }
                }
            }
        }.runTaskAsynchronously(Arathoth.getInstance());
        return value;
    }

    @Override
    public void function(Event e) {
        if (isEnable) {
            if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
                if(!(event.getDamager() instanceof Arrow)) {
                    if (ArathothAPI.Chance(ArathothAPI.getNumAttributeData((LivingEntity) event.getEntity(), this.getName()).getPrimary() - ArathothAPI.getNumAttributeData((LivingEntity) event.getDamager(), "HitRate").getPrimary())) {
                        ArathothDodgeEvent eve = new ArathothDodgeEvent((LivingEntity) event.getDamager(), (LivingEntity) event.getEntity());
                        if (!eve.isCancelled()) {
                            Arathoth.Debug("闪避事件 &f["+eve.getAttacker().getType()+" -> "+eve.getAttacker().getType()+"]");
                            event.setCancelled(true);
                            if (event.getDamager() instanceof Player) {
                                ((Player) event.getDamager()).sendTitle(ChatColor.translateAlternateColorCodes('&', " "), ChatColor.translateAlternateColorCodes('&', "&7遭闪避"), 10, 30, 10);
                            }
                            if (event.getEntity() instanceof Player) {
                                ((Player) event.getEntity()).sendTitle(ChatColor.translateAlternateColorCodes('&', " "), ChatColor.translateAlternateColorCodes('&', "&3闪避"), 10, 30, 10);
                            }
                        }
                    }
                }
                else{
                    if(((Arrow) event.getDamager()).getShooter() instanceof LivingEntity) {
                        if (ArathothAPI.Chance((ArathothAPI.getArrowData(event.getDamager(), this.getName()).getPrimary() - ArathothAPI.getNumAttributeData((LivingEntity) event.getEntity(), "HitRate").getPrimary()))) {
                            ArathothDodgeEvent eve = new ArathothDodgeEvent((LivingEntity) (((Arrow) event.getDamager()).getShooter()), (LivingEntity) event.getEntity());
                            if (!eve.isCancelled()) {
                                Arathoth.Debug("闪避事件 &f["+eve.getAttacker().getType()+" -> "+eve.getAttacker().getType()+"]");
                                event.setCancelled(true);
                                if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
                                    ((Player) event.getDamager()).sendTitle(ChatColor.translateAlternateColorCodes('&', " "), ChatColor.translateAlternateColorCodes('&', "&7遭闪避"), 10, 30, 10);
                                }
                                if ((event.getEntity()) instanceof Player) {
                                    ((Player) event.getEntity()).sendTitle(ChatColor.translateAlternateColorCodes('&', " "), ChatColor.translateAlternateColorCodes('&', "&3闪避"), 10, 30, 10);
                                }
                            }
                        }
                    }
                   }
                }
            }
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
        Bukkit.getPluginManager().registerEvents(this,Arathoth.getInstance());
        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))"));
        Regular = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))(\\-)(\\d+(?:\\.\\d+)?)"));
        Percent = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))%"));
        isEnable = config.getBoolean(getName()+".Enable",false);
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void ListenAttribute(EntityDamageEvent e){
        function(e);
    }
}
