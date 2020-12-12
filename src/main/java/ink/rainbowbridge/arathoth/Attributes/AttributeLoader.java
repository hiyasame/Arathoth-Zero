package ink.rainbowbridge.arathoth.Attributes;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Events.ArathothStatusUpdateEvent;
import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 0.1.0 时代新属性管理类
 *
 * @author 寒雨
 * @create 2020/12/11 22:33
 */
public class AttributeLoader {
    //TODO 属性data
    public static HashMap<UUID,HashMap<String,Double[]>> NumAttributeData = new HashMap<>();
    public static HashMap<NumberAttribute, Plugin> RegisteredNum = new HashMap<>();
    public static HashMap<SpecialAttribute, Plugin> RegisteredSpecial = new HashMap<>();
    public static HashMap<String,SpecialAttribute> SpecialName = new HashMap<>();
    public static HashMap<UUID,HashMap<String,Double[]>> ArrowData = new HashMap<>();
    //TODO Register

    /**
     * 注册属性方法
     *
     * @param attr 注册的属性
     * @param plugin 执行插件
     */
    public static void Register(ArathothAttribute attr,Plugin plugin){
        if (attr instanceof NumberAttribute){
            attr.register(plugin);
            RegisteredNum.put((NumberAttribute)attr,plugin);
            SendUtils.info("&8NumberAttribute Register: &f"+attr.getName()+" &8Plugin: &f"+plugin.getName());
        }
        else if (attr instanceof SpecialAttribute){
            attr.register(plugin);
            RegisteredSpecial.put((SpecialAttribute)attr,plugin);
            SpecialName.put(attr.getName(),(SpecialAttribute)attr);
            SendUtils.info("&8SpecialAttribute Register: &f"+attr.getName()+" &8Plugin: &f"+plugin.getName());
        }
    }

    /**
     * 属性更新方法
     *
     * @param e 更新的生物
     */
    public static void StatusUpdate(LivingEntity e){
        new BukkitRunnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                if(e instanceof Player){
                    HashMap<String,Double[]> data = new HashMap<>();
                    Player p = (Player)e;
                    List<ItemStack> items = new ArrayList<>();
                    HashMap<Integer, String> Slots = SlotsManager.getSlots();
                    for (Integer i : SlotsManager.getSlots().keySet()){
                        if (p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getItemMeta().hasLore() && ChatColor.stripColor(p.getInventory().getItem(i).getItemMeta().getLore().get(0)).contains(Slots.get(i))) {
                            items.add(p.getInventory().getItem(i));
                        }
                    }
                    if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getItemMeta().hasLore() && ChatColor.stripColor(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0)).contains(Arathoth.getInstance().getConfig().getString("Slots.MainHand"))) {
                        items.add(p.getInventory().getItemInMainHand());
                    }
                    for (ItemStack item : items) {
                        if (ItemUtils.hasLore(item)) {
                            //TODO RulesPassorNot
                            boolean pass = true;
                            if (!RulesManager.Rules.isEmpty()) {
                                for (SubRules r : RulesManager.Sub.values()) {
                                    if (!r.RulesPass(p, item)) {
                                        pass = false;
                                        break;
                                    }
                                }
                                if (pass){
                                    for(NumberAttribute attr : RegisteredNum.keySet()){
                                        if(data.get(attr.getName()) == null){
                                            data.put(attr.getName(), attr.parseNumber(item));
                                        }
                                        else{
                                            data.put(attr.getName(), ArathothAPI.DataPutIn(data.get(attr.getName()),attr.parseNumber(item)));
                                        }
                                    }
                                }
                            }
                            else{
                                for(NumberAttribute attr : RegisteredNum.keySet()) {
                                    if (data.get(attr.getName()) == null) {
                                        data.put(attr.getName(), attr.parseNumber(item));
                                    } else {
                                        data.put(attr.getName(), ArathothAPI.DataPutIn(data.get(attr.getName()), attr.parseNumber(item)));
                                    }
                                }
                            }
                            NumAttributeData.put(p.getUniqueId(),data);
                            Bukkit.getPluginManager().callEvent(new ArathothStatusUpdateEvent(e,data));
                            Arathoth.Debug("Status Update: &f" + (System.currentTimeMillis() - time) + "ms &8(" + p.getName() + ")");
                        }
                    }
                }
                else{
                    HashMap<String,Double[]> data = new HashMap<>();
                    List<ItemStack> items = new ArrayList<>();
                    items.add(e.getEquipment().getBoots());
                    items.add(e.getEquipment().getLeggings());
                    items.add(e.getEquipment().getChestplate());
                    items.add(e.getEquipment().getHelmet());
                    items.add(e.getEquipment().getItemInMainHand());
                    items.add(e.getEquipment().getItemInOffHand());
                    for(ItemStack item : items){
                        for(NumberAttribute attr : RegisteredNum.keySet()){
                            if(data.get(attr.getName()) == null){
                                data.put(attr.getName(), attr.parseNumber(item));
                            }
                            else{
                                data.put(attr.getName(), ArathothAPI.DataPutIn(data.get(attr.getName()),attr.parseNumber(item)));
                            }
                        }
                    }
                    NumAttributeData.put(e.getUniqueId(),data);
                    Bukkit.getPluginManager().callEvent(new ArathothStatusUpdateEvent(e,data));
                    Arathoth.Debug("Status Update: &f" + (System.currentTimeMillis() - time) + "ms &8(" + e.getType() + ")");
                }

            }
        }.runTaskAsynchronously(Arathoth.getInstance());
    }
}
