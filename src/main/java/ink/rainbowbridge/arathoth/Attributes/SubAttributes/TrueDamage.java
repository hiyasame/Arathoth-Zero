package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒雨
 * @create 2020/12/12 22:00
 */
public class TrueDamage implements NumberAttribute, Listener {
    private FileConfiguration config;
    private Pattern Primary;
    private Pattern Regular;
    private Pattern Percent;
    private boolean isEnable;

    @Override
    public Double[] parseNumber(ItemStack item) {
        //TODO 获取数值属性
        Double[] value = {0.0D,0.0D,0.0D};
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String str : ItemUtils.getUncoloredLore(item)) {
                    Matcher m1 = Primary.matcher(str);
                    Matcher m2 = Regular.matcher(str);
                    Matcher m3 = Percent.matcher(str);
                    if(m1.find()){
                        value[0] += Double.valueOf(m1.group(1));
                        value[1] += Double.valueOf(m1.group(1));
                    }
                    if (m2.find()){
                        value[0] += Double.valueOf(m2.group(1));
                        value[1] += Double.valueOf(m2.group(6));
                    }
                    if (m3.find()){
                        value[2] += Double.valueOf(m3.group(1));
                    }
                }
            }
        }.runTaskAsynchronously(Arathoth.getInstance());
        return value;
    }

    @Override
    public void function(Event e) {
        if(e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
            if (event.getDamager() instanceof LivingEntity) {
                Double damage = ArathothAPI.SolveAttributeValue(ArathothAPI.getNumAttributeValues((LivingEntity)event.getDamager(),getName()));
                if(((LivingEntity)event.getEntity()).getMaxHealth() <= damage){
                    ((LivingEntity)event.getEntity()).setHealth(0.0D);
                }
                else{
                    ((LivingEntity)event.getEntity()).setHealth(((LivingEntity)event.getDamager()).getHealth() - damage);
                }
            } else if (event.getDamager() instanceof Arrow) {
                //TODO 处理弓箭属性，将data当中的属性实现
                if (((Arrow) event.getDamager()).getShooter() instanceof LivingEntity) {
                    Double damage = ArathothAPI.SolveAttributeValue(ArathothAPI.getProjectileNum(event.getDamager(), getName()));
                    if (((LivingEntity) ((event.getEntity()))).getMaxHealth() <= damage) {
                        ((LivingEntity) ((event.getEntity()))).setHealth(0.0D);
                    } else {
                        ((LivingEntity) ((event.getEntity()))).setHealth(((LivingEntity) ((event.getEntity()))).getHealth() - damage);
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

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((\\-|\\+)?(\\d+(\\.\\d+)?))"));
        Regular = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((\\-|\\+)?(\\d+(\\.\\d+)?))(\\-)(\\d+(\\.\\d+)?)"));
        Percent = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((\\-|\\+)?(\\d+(\\.\\d+)?))%"));
        isEnable = config.getBoolean(getName()+".Enable",false);
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void ListenAttribute(EntityDamageByEntityEvent e){
        function(e);
    }
}
