package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributeLoader;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
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
    public Double[] parseNumber(ItemStack item) {
        //TODO 获取数值属性
        Double[] value = new Double[3];
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
        if(isEnable()){
            if (e instanceof PlayerItemHeldEvent) {
                PlayerItemHeldEvent event = (PlayerItemHeldEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(((PlayerItemHeldEvent) e).getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(((PlayerItemHeldEvent) e).getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(((PlayerItemHeldEvent) e).getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerSwapHandItemsEvent) {
                PlayerSwapHandItemsEvent event = (PlayerSwapHandItemsEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerDropItemEvent) {
                PlayerDropItemEvent event = (PlayerDropItemEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof InventoryCloseEvent) {
                InventoryCloseEvent event = (InventoryCloseEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerPickupItemEvent) {
                PlayerPickupItemEvent event = (PlayerPickupItemEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerInteractEvent) {
                PlayerInteractEvent event = (PlayerInteractEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerJoinEvent) {
                PlayerJoinEvent event = (PlayerJoinEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getPlayer(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getPlayer().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getPlayer().setMaxHealth(event.getPlayer().getHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof CreatureSpawnEvent) {
                CreatureSpawnEvent event = (CreatureSpawnEvent) e;
                Double health = Math.floor(config.getDouble("Settings.BaseHealth") + ArathothAPI.getNumAttributeValues(event.getEntity(),this.getName())[0] + ArathothAPI.getNumAttributeValues(event.getEntity(),this.getName())[1]*ArathothAPI.getNumAttributeValues(event.getEntity(),this.getName())[2]/100);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < config.getDouble("Settings.MinHealth")) {
                            event.getEntity().setMaxHealth(config.getDouble("Settings.MinHealth"));
                        } else {
                            event.getEntity().setMaxHealth(event.getEntity().getMaxHealth() + health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
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
        config.set(getName()+".Pattern", "Health: [VALUE]");
        config.set(getName()+".Settings.MinHealth", 1.0D);
        config.set(getName()+".Settings.BaseHealth", 20.0D);
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

//TODO 属性类内监听，不再集中处理，优先级用现成的EventPriority
    @EventHandler
    void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        if (event.isCancelled()) {
            return;
        }
        function(event);
    }
    @EventHandler
    void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event){
        if (event.isCancelled()) {
            return;
        }
        function(event);
    }
    @EventHandler
    void onInventoryCloseEvent(InventoryCloseEvent event){
        function(event);
    }
    @EventHandler
    void onPlayerDropEvent(PlayerDropItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        function(event);
    }
    @EventHandler
    void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        function(event);
    }
    @EventHandler
    void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        function(event);
    }
    @EventHandler
    void onPlayerJoinEvent(PlayerJoinEvent event) {
        function(event);
    }
    @EventHandler
    void onPlayerQuitEvent(PlayerQuitEvent event) {
        function(event);
    }
    @EventHandler
    void onEntitySpawnEvent(CreatureSpawnEvent event)  {
        if (event.isCancelled()) {
            return;
        }
        function(event);
    }
    @EventHandler
    void onEntityDeathEvent(EntityDeathEvent event) {
        function(event);
    }

}