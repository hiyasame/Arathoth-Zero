package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Attributes.data.AttributeData;
import ink.rainbowbridge.arathoth.Events.ArathothStatusExecuteEvent;
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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 寒雨
 * @create 2020/12/12 21:21
 */
public class LifeSteal implements NumberAttribute, Listener {
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
        if (e instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
            if (event.getDamager() instanceof Arrow){
                if(((Arrow) event.getDamager()).getShooter() instanceof LivingEntity) {
                    Double damage = ArathothAPI.getArrowData(event.getDamager(), getName()).solveData() * (1.0D - ArathothAPI.getNumAttributeData((LivingEntity) event.getEntity(), "LifeStealResist").getPrimary() / 100);
                    ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent(this.getName(),e,damage,(LivingEntity) (((Arrow) event.getDamager()).getShooter()));
                    Bukkit.getPluginManager().callEvent(eve);
                    event.setDamage(Math.floor(event.getDamage() + eve.getValue()));
                    if ((((LivingEntity) ((Arrow) event.getDamager()).getShooter()).getMaxHealth() - eve.getValue()) >= ((LivingEntity) ((Arrow) event.getDamager()).getShooter()).getHealth()) {
                        ((LivingEntity) ((Arrow) event.getDamager()).getShooter()).setHealth(((LivingEntity) ((Arrow) event.getDamager()).getShooter()).getHealth() + eve.getValue());
                    } else {
                        ((LivingEntity) ((Arrow) event.getDamager()).getShooter()).setHealth(((LivingEntity) ((Arrow) event.getDamager()).getShooter()).getMaxHealth());
                    }
                }
            }
            else{
                Double damage = ArathothAPI.getNumAttributeData((LivingEntity) event.getDamager(), getName()).solveData();
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent(this.getName(),e,damage,(LivingEntity) ((EntityDamageByEntityEvent) e).getDamager());
                Bukkit.getPluginManager().callEvent(eve);
                event.setDamage(Math.floor(event.getDamage() + eve.getValue()));
                if (event.getDamager() instanceof LivingEntity){
                    if((((LivingEntity) event.getDamager()).getMaxHealth() - eve.getValue()) >= ((LivingEntity) event.getDamager()).getHealth()){
                        ((LivingEntity) event.getDamager()).setHealth(((LivingEntity) event.getDamager()).getHealth() + eve.getValue());
                    }
                    else{
                        ((LivingEntity) event.getDamager()).setHealth(((LivingEntity) event.getDamager()).getMaxHealth());
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
    public void ListenAttribute(EntityDamageByEntityEvent e){
        function(e);
    }
}
