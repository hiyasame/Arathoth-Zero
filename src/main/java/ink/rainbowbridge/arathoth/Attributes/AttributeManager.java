package ink.rainbowbridge.arathoth.Attributes;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.AttrDecls;
import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Events.ArathothStatusUpdateEvent;
import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Rules.sub.LevelRequired;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttributeManager {
    public static Plugin plugin = Arathoth.getInstance();
    public static void register(Plugin plugin,SubAttribute sub){
        File File = new File(Arathoth.getInstance().getDataFolder(), "Attributes/" + sub.getName() + ".yml");
        FileConfiguration file = null;
        if(File.exists()){
            file = YamlConfiguration.loadConfiguration(File);
            sub.register(plugin,file,false);
        }
        else{
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                File.createNewFile();
                fw = new FileWriter(File);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File), StandardCharsets.UTF_8)));
                out.write("# Arathoth Attributes Configuration\n");
                out.write("# @Author Freeze003(寒雨)");
                out.flush();
                out.close();
                fw.close();
                file = YamlConfiguration.loadConfiguration(File);
                sub.register(plugin,file,true);
            } catch (IOException e) {

            }
        }
        try{
            file.save(File);
        } catch (IOException e) { }

        //TODO Register Attributes
        if(!AttributesData.RegisteredAttr.containsKey(sub.getName())){
            SendUtils.info("&8注册属性: &f"+sub.getName()+" &8From: &f"+plugin.getName()+" &8优先级: &f"+sub.getPriority());
            AttributesData.RegisteredAttr.put(sub.getName(), plugin.getName());
            AttributesData.AttributesMap.put(sub.getName(),sub);
        }
        else{
            SendUtils.warn("&4属性: &c"+sub.getName()+" &4已经存在，已经自动覆盖!");
            SendUtils.info("&8注册属性: &f"+sub.getName()+" &8From: &f"+plugin.getName()+" &8优先级: &f"+sub.getPriority());
            AttributesData.RegisteredAttr.put(sub.getName(), plugin.getName());
            AttributesData.AttributesMap.put(sub.getName(),sub);
        }
    }

    /**
     * 快捷获取属性方法
     *
     * @param e 生物
     * @return 属性data
     */
    public static Double[] getAttrData(LivingEntity e,String attr){
        return AttributesData.AttrData.get(e.getUniqueId().toString()).get(attr);
    }

    public static void StatusUpdate(LivingEntity e){
        ArathothStatusUpdateEvent eve = new ArathothStatusUpdateEvent(e);
        Bukkit.getServer().getPluginManager().callEvent(eve);
        if (!eve.isCancelled()) {
            if (!(e instanceof Player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        long time = System.currentTimeMillis();
                        HashMap<String, Double[]> data = new HashMap<>();
                        List<ItemStack> items = new ArrayList<>();
                        List<String> uncoloredlores = new ArrayList<>();
                        items.add(e.getEquipment().getBoots());
                        items.add(e.getEquipment().getLeggings());
                        items.add(e.getEquipment().getChestplate());
                        items.add(e.getEquipment().getHelmet());
                        items.add(e.getEquipment().getItemInMainHand());
                        items.add(e.getEquipment().getItemInOffHand());
                        for (ItemStack item : items) {
                            if (ItemUtils.hasLore(item)) {
                                List<String> coloredlores = item.getItemMeta().getLore();
                                for (String lore : coloredlores) {
                                    uncoloredlores.add(ChatColor.stripColor(lore));
                                }
                            }
                            for (String attr : AttributesData.RegisteredAttr.keySet()) {
                                data.put(attr, AttributesData.AttributesMap.get(attr).parse(uncoloredlores));
                            }
                            AttributesData.AttrData.put(e.getUniqueId().toString(), data);
                        }
                        Arathoth.Debug("Status Update: &f" + (System.currentTimeMillis() - time) + "ms &8(" + e.getType() + ")");
                    }
                }.runTaskAsynchronously(plugin);

            } else {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        long time = System.currentTimeMillis();
                        HashMap<String, Double[]> data = new HashMap<>();
                        List<ItemStack> items = new ArrayList<>();
                        Player p = (Player) e;
                        List<String> uncoloredlores = new ArrayList<>();
                        HashMap<Integer, String> Slots = SlotsManager.getSlots();
                        for (Integer i : Slots.keySet()) {
                            if (p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getItemMeta().hasLore() && ChatColor.stripColor(p.getInventory().getItem(i).getItemMeta().getLore().get(1)).contains(Slots.get(i))) {
                                items.add(p.getInventory().getItem(i));
                            }
                        }
                        if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getItemMeta().hasLore() && ChatColor.stripColor(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(1)).contains(plugin.getConfig().getString("Slots.MainHand"))) {
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
                                }
                                if (pass) {
                                    List<String> coloredlores = item.getItemMeta().getLore();
                                    for (String lore : coloredlores) {
                                        uncoloredlores.add(ChatColor.stripColor(lore));
                                    }
                                }
                            }
                            for (String attr : AttributesData.RegisteredAttr.keySet()) {
                                data.put(attr, AttributesData.AttributesMap.get(attr).parse(uncoloredlores));
                            }
                            AttributesData.AttrData.put(e.getUniqueId().toString(), data);
                        }
                        Arathoth.Debug("Status Update: &f" + (System.currentTimeMillis() - time) + "ms &8(" + p.getName() + ")");
                    }
                }.runTaskAsynchronously(plugin);

            }
        }
    }

    /**
     * 设置抛射物属性
     *
     * @param uuid uuid
     * @param data AttributeData
     */
    public static void setProjectileData(String uuid,HashMap<String,Double[]> data){
        AttributesData.AttrData.put(uuid,data);
    }
}
