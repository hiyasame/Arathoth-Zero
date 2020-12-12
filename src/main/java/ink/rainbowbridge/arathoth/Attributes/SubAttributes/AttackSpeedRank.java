package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.SpecialAttribute;
import ink.rainbowbridge.arathoth.Events.ArathothAttackCDEvent;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SpecialAttribute 注册实例
 *
 * @author 寒雨
 * @create 2020/12/12 13:03
 */
public class AttackSpeedRank implements SpecialAttribute, Listener {
    private boolean isEnable;
    private Pattern pattern;
    private FileConfiguration config;
    private HashMap<String,Integer> rankList;
    @Override
    public Object parseValue(LivingEntity e) {
        if(e instanceof Player){
            Player p = (Player)e;
            String Rank = null;
            for(String str : ItemUtils.getUncoloredLore(p.getInventory().getItemInMainHand())){
                Matcher m = pattern.matcher(str);
                if (m.find()){
                    Rank = m.group(1);
                    break;
                }
            }
        if (rankList.containsKey(Rank)){
            return Rank;
        }
        else{
            return config.getString(getName()+".Settings.DefaultRank.Name","Normal");
        }
        }
        return null;
    }

    @Override
    public void function(Event e, LivingEntity entity) {
        if(isEnable()){
            if (e instanceof EntityDamageByEntityEvent){
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
                if(event.getDamager() instanceof Player){
                    Player p = (Player)event.getDamager();
                    String rank = (String)parseValue(p);
                    Bukkit.getPluginManager().callEvent(new ArathothAttackCDEvent(p,rankList.get(rank)));
                    p.setCooldown(p.getInventory().getItemInMainHand().getType(),rankList.get(rank));
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
            config.set(getName()+".Settings.DefaultRank.Name", "Normal");
            config.set(getName()+".Settings.DefaultRank.value", 20);
            config.set(getName()+".Settings.RegisterRank.Slow", 30);
            config.set(getName()+".Settings.RegisterRank.VerySlow", 40);
            config.set(getName()+".Settings.RegisterRank.Fast", 10);
            config.set(getName()+".Settings.RegisterRank.VeryFast", 5);
            ArathothAPI.saveAttributeConfig(this,config);
        }
        else{
            config = ArathothAPI.getAttributeConfig(this);
        }
        Bukkit.getPluginManager().registerEvents(this, Arathoth.getInstance());
        rankList = new HashMap<>();
        rankList.put(config.getString(getName()+".Settings.DefaultRank.Name","Normal"),config.getInt(getName()+".Settings.DefaultRank.value",20));

        pattern = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\S+)"));
        isEnable = config.getBoolean(getName()+".Enable",false);
        for(String str : config.getConfigurationSection(getName()+".Settings.RegisterRank").getKeys(false)){
            rankList.put(str,config.getInt(getName()+".Settings.RegisterRank."+str));
        }
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAttackCD(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player p = (Player)e.getDamager();
            if(p.getCooldown(p.getInventory().getItemInMainHand().getType()) > 0){
                e.setCancelled(true);
            }
            else{
                function(e,null);
            }
        }
    }
}
