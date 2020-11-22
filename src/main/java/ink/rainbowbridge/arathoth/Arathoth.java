package ink.rainbowbridge.arathoth;

import ink.rainbowbridge.arathoth.Attributes.AttributeManager;
import ink.rainbowbridge.arathoth.Commands.MainCommand;
import ink.rainbowbridge.arathoth.Utils.DrawFucker;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class Arathoth extends JavaPlugin {
   FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"config.yml"));
   public static boolean isDebug = false;
   private static Arathoth instance;
   public static Arathoth getInstance() {return instance;}


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
        AttributeManager.register("ExampleAttr",this);
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

    public static void ConfigurationDefaultSet(String Name,FileConfiguration file){
       file.set(Name + ".Pattern","[VALUE] " + Name);
       file.set(Name + ".Enable",true);
    }
}
