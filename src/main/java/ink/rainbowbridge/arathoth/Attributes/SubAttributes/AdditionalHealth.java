package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Attributes.data.AttributeData;
import ink.rainbowbridge.arathoth.Events.ArathothStatusExecuteEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从零开始的新属性
 * 之前注册的属性11个全部木大呜呜呜
 *
 * 这是属性注册方式示例
 * 可以选择直接让属性类实现Listener类
 * 傻逼优先级我不弄了，自行在EventHandler标记优先级
 *
 * 总算把属性做出自己的花样了，这样就不会被吐槽抄TabooCode4了吧
 *
 * 0.1.3 AdditionalHealth 又几把重写了
 *
 * @author 寒雨
 * @create 2020/12/11 21:17
 */
public class AdditionalHealth implements NumberAttribute , Listener {
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
                for (String str : uncoloredlores) {
                    Matcher m1 = Primary.matcher(str);
                    Matcher m2 = Regular.matcher(str);
                    Matcher m3 = Percent.matcher(str);
                    if(m1.find()){
                        value.setPrimary(value.getPrimary() + Double.valueOf(m1.group(1)));
                        value.setRegular(value.getRegular() + Double.valueOf(m1.group(1)));
                    }
                    if (m2.find()){
                        value.setPrimary(value.getPrimary() + Double.valueOf(m2.group(1)));
                        value.setRegular(value.getRegular() + Double.valueOf(m2.group(6)));
                    }
                    if (m3.find()){
                        value.setPercent(value.getPercent() + Double.valueOf(m3.group(1)));
                    }
                }
        return value;
    }

    @Override
    public void function(Event e) {
        //懒得把代码放function了
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
        config.set(getName()+".Pattern", "Health: [VALUE]");
        config.set(getName()+".Settings.MinHealth", 1.0D);
        config.set(getName()+".Settings.BaseHealth", 20.0D);
        ArathothAPI.saveAttributeConfig(this,config);
        }
        else{
            config = ArathothAPI.getAttributeConfig(this);
        }
        Bukkit.getPluginManager().registerEvents(this,Arathoth.getInstance());

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))"));
        Regular = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))(\\-)(\\d+(?:\\.\\d+)?)"));
        Percent = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))\\%"));
        isEnable = config.getBoolean(getName()+".Enable",false);
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

//TODO 属性类内监听，不再集中处理，优先级用现成的EventPriority
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        if (event.isCancelled()) {
            return;
        }
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent(getName(),event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTaskLater(Arathoth.getInstance(),2L);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event){
        if (event.isCancelled()) {
            return;
        }
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onInventoryCloseEvent(InventoryCloseEvent event){
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerDropEvent(PlayerDropItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerQuitEvent(PlayerQuitEvent event) {
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getPlayer(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getPlayer());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getPlayer().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getPlayer().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onEntitySpawnEvent(CreatureSpawnEvent event)  {
        if (event.isCancelled()) {
            return;
        }
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getEntity(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getEntity(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getEntity());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getEntity().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getEntity().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onEntityDeathEvent(EntityDeathEvent event) {
        if(!isEnable){
            return;
        }
        Double health = Math.floor(config.getDouble(getName()+".Settings.BaseHealth") + ArathothAPI.getNumAttributeData(event.getEntity(),this.getName()).getPrimary()*(1+ArathothAPI.getNumAttributeData(event.getEntity(),this.getName()).getPercent()/100));
        new BukkitRunnable() {

            @Override
            public void run() {
                ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent("AdditionalHealth",event,health,event.getEntity());
                Bukkit.getPluginManager().callEvent(eve);
                if (eve.getValue() < config.getDouble(getName()+".Settings.MinHealth")) {
                    event.getEntity().setMaxHealth(config.getDouble(getName()+".Settings.MinHealth"));
                } else {
                    event.getEntity().setMaxHealth(eve.getValue());
                }
            }
        }.runTask(Arathoth.getInstance());
    }
}