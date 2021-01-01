package ink.rainbowbridge.arathoth.Attributes.SubAttributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.SpecialAttribute;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 神域传统艺能
 * 阉割版本，只能指定每一秒修复的值
 *
 * @author 寒雨
 * @create 2020/12/20 0:04
 */
public class DurabilityFix implements SpecialAttribute {
    private boolean isEnable;
    private Pattern pattern;
    private FileConfiguration config;
    @Override
    public Object parseValue(LivingEntity e) {
        return null;
    }

    @Override
    public void function(Event e, LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player p = (Player)entity;
                List<ItemStack> items = new ArrayList<>();
                items.add(p.getInventory().getItemInMainHand());
                items.add(p.getInventory().getBoots());
                items.add(p.getInventory().getLeggings());
                items.add(p.getInventory().getChestplate());
                items.add(p.getInventory().getHelmet());
                for(ItemStack item : items){
                    if(parseItemValue(item) > 0 && item.getDurability() != 0){
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                if(item.getDurability() > parseItemValue(item)){
                                    item.setDurability((short)(item.getDurability() + parseItemValue(item)));
                                }
                                else{
                                    item.setDurability((short)0);
                                }
                            }
                        }.runTask(Arathoth.getInstance());
                    }
                }
            }
        }.runTaskAsynchronously(Arathoth.getInstance());
    }

    @Override
    public String getPlaceHolder(Player p) {
        return "该属性不支持PlaceHolder变量";
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
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
        pattern = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\d+)"));
        isEnable = config.getBoolean(getName()+".Enable",false);


    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * 获取指定物品耐久回复值
     *
     * @param item 指定物品
     * @return 值
     */
    public short parseItemValue(ItemStack item){
        int value = 0;
        for(String str : ItemUtils.getUncoloredLore(item)){
            Matcher m = pattern.matcher(str);
            if (m.find()){
                 value = Integer.parseInt(m.group(1));
            }
        }
        return (short)value;
    }
}
