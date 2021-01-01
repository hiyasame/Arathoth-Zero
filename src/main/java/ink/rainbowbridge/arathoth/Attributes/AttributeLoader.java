package ink.rainbowbridge.arathoth.Attributes;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 0.1.0 时代新属性管理类
 * 0.1.3 删除statusupdate方法
 *
 * @author 寒雨
 * @create 2020/12/11 22:33
 */
public class AttributeLoader {
    //TODO 属性data
    public static HashMap<NumberAttribute, Plugin> RegisteredNum = new HashMap<>();
    public static HashMap<SpecialAttribute, Plugin> RegisteredSpecial = new HashMap<>();
    public static HashMap<String, SpecialAttribute> SpecialName = new HashMap<>();
    public static HashMap<String, NumberAttribute> NumberName = new HashMap<>();
    //TODO Register

    /**
     * 注册属性方法
     *
     * @param attr   注册的属性
     * @param plugin 执行插件
     */
    public static void Register(ArathothAttribute attr, Plugin plugin) {
        if (attr instanceof NumberAttribute) {
            attr.register(plugin);
            RegisteredNum.put((NumberAttribute) attr, plugin);
            NumberName.put(attr.getName(),(NumberAttribute) attr);
            SendUtils.info("&8NumberAttribute Register: &f" + attr.getName() + " &8Plugin: &f" + plugin.getName());
        } else if (attr instanceof SpecialAttribute) {
            attr.register(plugin);
            RegisteredSpecial.put((SpecialAttribute) attr, plugin);
            SpecialName.put(attr.getName(), (SpecialAttribute) attr);
            SendUtils.info("&8SpecialAttribute Register: &f" + attr.getName() + " &8Plugin: &f" + plugin.getName());
        }
    }

    /**
     * 0.1.3 获取生物有效属性lore方法
     *
     * @param e 生物
     * @return 有效lore(uncolored)
     */
    public static List<String> getEntityLore(LivingEntity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            List<String> uncoloredlores = new ArrayList<>();
            List<ItemStack> items = new ArrayList<>();
            HashMap<Integer, String> Slots = SlotsManager.getSlots();
            for (Integer i : SlotsManager.getSlots().keySet()) {
                if (p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getItemMeta().hasLore() && ChatColor.stripColor(p.getInventory().getItem(i).getItemMeta().getLore().get(0)).contains(Slots.get(i))) {
                    items.add(p.getInventory().getItem(i));
                }
            }
            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                    if (ChatColor.stripColor(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0)).contains(Arathoth.getInstance().getConfig().getString("Slots.MainHand"))) {
                        items.add(p.getInventory().getItemInMainHand());
                    }
                }
            }
            if (!items.isEmpty()) {
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
                            if (pass) {
                                uncoloredlores.addAll(ItemUtils.getUncoloredLore(item));
                            }
                        } else {
                            uncoloredlores.addAll(ItemUtils.getUncoloredLore(item));
                        }
                    }
                }
            }
            Arathoth.Debug("execute lore length:&f "+uncoloredlores.size());
            return uncoloredlores;
        }
        else{
            List<String> uncoloredlores = new ArrayList<>();
            uncoloredlores.addAll(ItemUtils.getUncoloredLore(e.getEquipment().getItemInOffHand()));
            uncoloredlores.addAll(ItemUtils.getUncoloredLore(e.getEquipment().getItemInMainHand()));
            uncoloredlores.addAll(ItemUtils.getUncoloredLore(e.getEquipment().getBoots()));
            uncoloredlores.addAll(ItemUtils.getUncoloredLore(e.getEquipment().getChestplate()));
            uncoloredlores.addAll(ItemUtils.getUncoloredLore(e.getEquipment().getLeggings()));
            uncoloredlores.addAll(ItemUtils.getUncoloredLore(e.getEquipment().getHelmet()));
            return uncoloredlores;
        }
    }
}