package ink.rainbowbridge.arathoth.Rules;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RulesManager {
    public static HashMap<String,SubRules> Sub = new HashMap<>();
    public static HashMap<String,String> Rules = new HashMap<>();
    public static void register(Plugin plugin,SubRules rules) {
        File File = new File(Arathoth.getInstance().getDataFolder(), "Rules/" + rules.getName() + ".yml");
        FileConfiguration file = null;
        if(File.exists()){
            file = YamlConfiguration.loadConfiguration(File);
            rules.register(plugin,file,false);
        }
        else{
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                File.createNewFile();
                fw = new FileWriter(File);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File), StandardCharsets.UTF_8)));
                out.write("# Arathoth Rules Configuration\n");
                out.write("# @Author Freeze003(寒雨)");
                out.flush();
                out.close();
                fw.close();
                file = YamlConfiguration.loadConfiguration(File);
                rules.register(plugin,file,true);
            } catch (IOException e) {

            }
        }
        try{
            file.save(File);
        } catch (IOException e) {

        }
        if(!Rules.containsKey(rules.getName())){
            SendUtils.info("&8注册规则: &f"+rules.getName()+" &8From: &f"+plugin.getName());
            Rules.put(rules.getName(), plugin.getName());
            Sub.put(rules.getName(),rules);
        }
        else{
            SendUtils.warn("&4规则: &c"+rules.getName()+" &4已经存在，已经自动覆盖!");
            SendUtils.info("&8注册规则: &f"+rules.getName()+" &8From: &f"+plugin.getName());
            Rules.put(rules.getName(), plugin.getName());
            Sub.put(rules.getName(),rules);
        }
    }
    public static boolean Exist(String Rule){
        if(Rules.containsKey(Rule)){
            return true;
        }
        else{
            return false;
        }
    }
}
