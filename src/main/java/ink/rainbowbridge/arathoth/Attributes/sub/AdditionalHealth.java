package ink.rainbowbridge.arathoth.Attributes.sub;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Attributes.SubAttribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 第一个属性类 AdditionalHealth
 *
 * @author 寒雨
 * @create 2020/11/28 23:36
 */
public class AdditionalHealth implements SubAttribute {

    private FileConfiguration file;
    private Pattern Primary;
    private Pattern Regular;
    private Pattern Percent;
    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        if (first) {
            config.set(getName()+".Priority",0);
            config.set(getName()+".Pattern", "Health: [VALUE]");
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
                        value[1] += Double.valueOf(m1.group(1));
                        value[2] += Double.valueOf(m1.group(1));
                    }
                    if (m2.find()){
                        value[1] += Double.valueOf(m2.group(1));
                        value[2] += Double.valueOf(m2.group(6));
                    }
                    if (m3.find()){
                        value[3] += Double.valueOf(m3.group(1));
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
        if (this.getPriority() <= 500) {
            if (e instanceof PlayerItemHeldEvent) {
                PlayerItemHeldEvent event = (PlayerItemHeldEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerSwapHandItemsEvent) {
                PlayerSwapHandItemsEvent event = (PlayerSwapHandItemsEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerDropItemEvent) {
                PlayerDropItemEvent event = (PlayerDropItemEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof InventoryCloseEvent) {
                InventoryCloseEvent event = (InventoryCloseEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerPickupItemEvent) {
                PlayerPickupItemEvent event = (PlayerPickupItemEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerInteractEvent) {
                PlayerInteractEvent event = (PlayerInteractEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof PlayerJoinEvent) {
                PlayerJoinEvent event = (PlayerJoinEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getPlayer().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getPlayer().setMaxHealth(1.0D);
                        } else {
                            event.getPlayer().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            } else if (e instanceof CreatureSpawnEvent) {
                CreatureSpawnEvent event = (CreatureSpawnEvent) e;
                Double health = Math.floor(20.0D + AttributesData.AttrData.get(event.getEntity().getUniqueId().toString()).get(getName())[1]);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (health < 1.0D) {
                            event.getEntity().setMaxHealth(1.0D);
                        } else {
                            event.getEntity().setMaxHealth(health);
                        }
                    }
                }.runTask(Arathoth.getInstance());
            }
        }
    }
}
