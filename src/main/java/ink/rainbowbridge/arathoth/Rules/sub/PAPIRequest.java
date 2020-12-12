package ink.rainbowbridge.arathoth.Rules.sub;

import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Utils.ItemUtils;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PAPIRequest - 通过PAPI判断自定义规则词条
 * 0。1。0 更新
 * 可以自行判断papi变量，本插件一大特色
 *
 * @author 寒雨
 * @create 2020/12/6 0:10
 */
public class PAPIRequest implements SubRules {

    private FileConfiguration file;
    private Pattern Primary;
    private HashMap<String, List<String>> Registered;

    @Override
    public void register(Plugin plugin, FileConfiguration config, boolean first) {
        file = config;
        String[] def = {"%Arathoth_Primary_PhysicalDamage% >= 10","%player_name% equals ColdRain_Moro"};
        if (first) {
            config.set(getName()+".Pattern", "PAPIRequest: [VALUE]");
            config.set(getName()+".Register.Power", Arrays.asList(def));
        }

        Primary = Pattern.compile(config.getString(getName()+".Pattern").replace("[VALUE]", "(\\S+)"));
        Registered = new HashMap<>();
        for(String str : config.getConfigurationSection(getName()+".Register").getKeys(false)){
            Registered.put(str,config.getStringList(getName()+".Register."+str));
        }
    }

    @Override
    public boolean RulesPass(Player p, ItemStack item) {
        if (RulesManager.Exist(this.getName())) {
            List<String> RequiredList = new ArrayList<>();
            for(String lore : ItemUtils.getUncoloredLore(item)){
                Matcher m = Primary.matcher(lore);
                if(m.find()){
                    if(Registered.containsKey(m.group(1))){
                        RequiredList.add(m.group(1));
                    }
                }
            }
            if(!RequiredList.isEmpty()){
                for(String str: RequiredList){
                    for(String quest : Registered.get(str)){
                        String[] Script = quest.split(" ");
                        switch(Script[1]){
                            case "equals":{
                                if(!PlaceholderAPI.setPlaceholders(p,Script[0]).equals(Script[2])){
                                    SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                    return false;
                                }
                            }
                            case "contains":{
                                if(!PlaceholderAPI.setPlaceholders(p,Script[0]).contains(Script[2])){
                                    SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                    return false;
                                }
                            }
                            case "startswith":{
                                if(!PlaceholderAPI.setPlaceholders(p,Script[0]).startsWith(Script[2])){
                                    SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                    return false;
                                }
                            }
                            case ">=":{
                                try{
                                    if(!(Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[0])) >= Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[2])))){
                                        SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                        return false;
                                    }
                                }
                                catch(Exception e){
                                    SendUtils.warn("错误的PAPIRequest: &4"+str);
                                    Registered.remove(str);
                                }

                            }
                            case "<=":{
                                try{
                                    if(!(Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[0])) <= Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[2])))){
                                        SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                        return false;
                                    }
                                }
                                catch(Exception e){
                                    SendUtils.warn("错误的PAPIRequest: &4"+str);
                                    Registered.remove(str);
                                }
                            }
                            case ">":{
                                try{
                                    if(!(Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[0])) > Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[2])))){
                                        SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                        return false;
                                    }
                                }
                                catch(Exception e){
                                    SendUtils.warn("错误的PAPIRequest: &4"+str);
                                    Registered.remove(str);
                                }
                            }
                            case "<":{
                                try{
                                    if(!(Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[0])) < Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[2])))){
                                        SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                        return false;
                                    }
                                }
                                catch(Exception e){
                                    SendUtils.warn("错误的PAPIRequest: &4"+str);
                                    Registered.remove(str);
                                }
                            }
                            case "==":{
                                try{
                                    if(!(Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[0])) == Double.parseDouble(PlaceholderAPI.setPlaceholders(p,Script[2])))){
                                        SendUtils.send(p,"物品: "+item.getItemMeta().getDisplayName()+" &7不满足条件: &f"+str+" &7，将不会计入属性");
                                        return false;
                                    }
                                }
                                catch(Exception e){
                                    SendUtils.warn("错误的PAPIRequest: &4"+str);
                                    Registered.remove(str);
                                }
                            }
                            default:{
                                SendUtils.warn("错误的PAPIRequest: &4"+str);
                                Registered.remove(str);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
