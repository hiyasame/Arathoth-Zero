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
    public static HashMap<String,String> Patterns = new HashMap<>();
    public static HashMap<String,String> Rules = new HashMap<>();
    public static void register(String Name, Plugin plugin){
        File File = new File(Arathoth.getInstance().getDataFolder(), "Rules/" + Name + ".yml");
        FileConfiguration file = null;
        if(File.exists()){
            file = YamlConfiguration.loadConfiguration(File);
        }
        else{
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                File.createNewFile();
                fw = new FileWriter(File);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File), StandardCharsets.UTF_8)));
                out.write("# Arathoth Rules Configuration");
                out.write("# @Author Freeze003(寒雨)");
                out.flush();
                out.close();
                fw.close();
                file = YamlConfiguration.loadConfiguration(File);
                Arathoth.ConfigurationDefaultSet(Name,file);
            } catch (IOException e) {

            }
        }
        try{
            file.save(File);
        } catch (IOException e) {

        }
        if(file.getString(Name + ".Pattern") != null && file.get(Name + ".Enable") != null) {
            if(file.getBoolean(Name + ".Enable")) {
                SendUtils.info("&fRules Register : &8"+Name+"&f From: &8"+plugin.getName());
                Patterns.put(Name,file.getString(Name + ".Pattern"));
                Rules.put(Name, plugin.getName());
            }
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
