package ink.rainbowbridge.arathoth;

import ink.rainbowbridge.arathoth.Attributes.AttributeManager;
import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Attributes.SubAttribute;
import ink.rainbowbridge.arathoth.Attributes.sub.*;
import ink.rainbowbridge.arathoth.Commands.MainCommand;
import ink.rainbowbridge.arathoth.Listener.AttributeListener;
import ink.rainbowbridge.arathoth.Listener.StatusUpdateListeners;
import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.sub.LevelRequired;
import ink.rainbowbridge.arathoth.Rules.sub.OwnderRequest;
import ink.rainbowbridge.arathoth.Rules.sub.PermRequest;
import ink.rainbowbridge.arathoth.Utils.DrawFucker;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public final class Arathoth extends JavaPlugin {
   FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"config.yml"));
   public static boolean isDebug = false;
   private static Arathoth instance;
   public static Arathoth getInstance() {return instance;}
   public static List<SubAttribute> Priority = new ArrayList<>();
   public static Random random = new Random();
   public static DecimalFormat DecimalFormat = new DecimalFormat("0.0");


    @Override
    public void onEnable() {
       instance = this;
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();
        reloadConfig();

        File attributeFile = new File(getDataFolder(), "Attributes");
        if (!attributeFile.exists()) {
            attributeFile.mkdirs();
        }

        File RulesFile = new File(getDataFolder(), "Rules");
        if (!RulesFile.exists()) {
            RulesFile.mkdirs();
        }

        isDebug = config.getBoolean("Debug");
        SendUtils.info("&fPlugin has been enabled");
        Bukkit.getPluginCommand("Arathoth").setExecutor(new MainCommand());
        if(hasPAPI()){
            SendUtils.info("&fPlaceholderAPI &8Hook!");
        }
        else{
            SendUtils.warn("PlaceHolderAPI &4not found!");
        }
        DrawFucker.fuck();
        Bukkit.getPluginManager().registerEvents(new StatusUpdateListeners(),this);
        Bukkit.getPluginManager().registerEvents(new AttributeListener(),this);
        SolvePriority();
        boolean success = new PlaceHolderAPIHook(this).hook();
        if(success){
            SendUtils.info("PlaceHolderHook Successfully");
        }
        else{
            SendUtils.warn("Failed to PlaceHolderHook");
        }
        registerDefault();
        if (config.get("DecimalFormat") != null){
            DecimalFormat = new DecimalFormat(config.getString("DecimalFormat"));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean hasPAPI(){
        if (getServer().getPluginManager().getPlugin("PlaceHolderAPI") == null)
            return false;
        else
            return true;
    }

    public static boolean isDebug(){
        return isDebug;
    }

    public static void Debug(String Debugmsg){
        String prefix = "§8§l[§3§lArathoth§8§l] §3[§bDeBug§3] §8";
        if(isDebug()){
            Bukkit.getConsoleSender().sendMessage(prefix + Debugmsg.replaceAll("&","§"));
        }
    }

    public static void SolvePriority(){
        Priority = new ArrayList<>();
        HashMap<Integer,SubAttribute> map = new HashMap<>();
       for(SubAttribute attr : AttributesData.AttributesMap.values()){
           map.put(attr.getPriority(), attr);
       }
       //TODO 按优先级排序，超过500视作禁用
        for (int j=500;j>0;j--){
            if(!map.containsKey(j)){continue;}
            else{
                Priority.add(map.get(j));
            }
        }
    }

    public void registerDefault(){
        //TODO 本体属性注册
        AttributeManager.register(this,new AdditionalHealth());
        AttributeManager.register(this,new PhysicalDamage());
        AttributeManager.register(this,new MagicDamage());
        AttributeManager.register(this,new MonsterDamage());
        AttributeManager.register(this,new MonsterArmor());
        AttributeManager.register(this,new PhysicalDamage());
        AttributeManager.register(this,new PlayerDamage());
        AttributeManager.register(this,new TrueDamage());
        AttributeManager.register(this,new PlayerTrueDamage());
        AttributeManager.register(this,new MagicArmor());
        AttributeManager.register(this,new MagicArmor());
        //TODO 本体规则注册
        RulesManager.register(this,new LevelRequired());
        RulesManager.register(this,new OwnderRequest());
        RulesManager.register(this,new PermRequest());
    }

}
