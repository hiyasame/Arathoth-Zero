package ink.rainbowbridge.arathoth.Attributes.sub;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributeManager;
import ink.rainbowbridge.arathoth.Attributes.SubAttribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒雨
 * @create 2020/12/6 9:52
 */
public class PlayerTrueDamage implements SubAttribute {
    private FileConfiguration file;
    private Pattern Primary;
    private Pattern Regular;
    private Pattern Percent;
    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        if (first) {
            config.set(getName()+".Priority",250);
            config.set(getName()+".Pattern", getName()+": [VALUE]");
        }

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((\\-|\\+)?(\\d+(\\.\\d+)?))"));
        Regular = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((\\-|\\+)?(\\d+(\\.\\d+)?))(\\-)(\\d+(\\.\\d+)?)"));
        Percent = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((\\-|\\+)?(\\d+(\\.\\d+)?))%"));

    }

    @Override
    public Double[] parse(List<String> uncoloredlores) {
        Double[] value = new Double[3];
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String str : uncoloredlores) {
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
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Integer getPriority() {
        return file.getInt(getName()+".Priority");
    }

    @Override
    public void Action(Event e) {
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
            if (event.getEntity() instanceof Player) {
                if (event.getDamager() instanceof LivingEntity) {
                    Double damage1 = AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[0] + AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[1] * AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[2] / 100;
                    Double damage2 = AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[0] + AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[1] * AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[2] / 100;
                    Double damage3 = damage1 + Arathoth.random.nextDouble() * (damage2 - damage1);
                    ((LivingEntity) event.getEntity()).setHealth(Math.floor(((LivingEntity) event.getEntity()).getHealth() - damage3));
                } else if (event.getDamager() instanceof Arrow) {
                    //TODO 处理弓箭属性，将data当中的属性实现
                    Double damage1 = AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[0] + AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[1] * AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[2] / 100;
                    Double damage2 = AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[0] + AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[1] * AttributeManager.getAttrData((LivingEntity) event.getDamager(), getName())[2] / 100;
                    Double damage3 = damage1 + Arathoth.random.nextDouble() * (damage2 - damage1);
                    ((LivingEntity) event.getEntity()).setHealth(Math.floor(((LivingEntity) event.getEntity()).getHealth() - damage3));
                }
            }
        }
    }
}
