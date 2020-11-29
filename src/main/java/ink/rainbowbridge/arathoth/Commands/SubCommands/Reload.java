package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Attributes.SubAttribute;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Rules.SubRules;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Reload implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        long time = System.currentTimeMillis();
        Arathoth.getInstance().reloadConfig();
        if (!Arathoth.getInstance().getDataFolder().exists())
            Arathoth.getInstance().getDataFolder().mkdir();
        File file = new File(Arathoth.getInstance().getDataFolder(), "config.yml");
        if (!file.exists())
            Arathoth.getInstance().saveDefaultConfig();
        Arathoth.getInstance().reloadConfig();

        File attributeFile = new File(Arathoth.getInstance().getDataFolder(), "Attributes");
        if (!attributeFile.exists()) {
            attributeFile.mkdirs();
        }

        File RulesFile = new File(Arathoth.getInstance().getDataFolder(), "Rules");
        if (!RulesFile.exists()) {
            RulesFile.mkdirs();
        }
        reload();
        Arathoth.SolvePriority();
        sender.sendMessage(SendUtils.prefix + "重载完成! " + ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + (System.currentTimeMillis() - time) +"ms"+ChatColor.WHITE + "]");
        return true;
    }
    public void reload(){
        //TODO 重载属性配置
        for(SubAttribute sub: AttributesData.AttributesMap.values()){
            File File = new File(Arathoth.getInstance().getDataFolder(), "Attributes/" + sub.getName() + ".yml");
            FileConfiguration file = null;
            if(File.exists()){
                file = YamlConfiguration.loadConfiguration(File);
                sub.register(Arathoth.getInstance(),file,false);
            }
            else{
                FileWriter fw = null;
                PrintWriter out = null;
                try {
                    File.createNewFile();
                    fw = new FileWriter(File);
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File), StandardCharsets.UTF_8)));
                    out.write("# Arathoth Attributes Configuration");
                    out.write("# @Author Freeze003(寒雨)");
                    out.flush();
                    out.close();
                    fw.close();
                    file = YamlConfiguration.loadConfiguration(File);
                    sub.register(Arathoth.getInstance(),file,true);
                } catch (IOException e) {

                }
            }
            try{
                file.save(File);
            } catch (IOException e) { }
        }
        // TODO 重载规则配置
        for(SubRules sub: RulesManager.Sub.values()){
            File File = new File(Arathoth.getInstance().getDataFolder(), "Attributes/" + sub.getName() + ".yml");
            FileConfiguration file = null;
            if(File.exists()){
                file = YamlConfiguration.loadConfiguration(File);
                sub.register(Arathoth.getInstance(),file,false);
            }
            else{
                FileWriter fw = null;
                PrintWriter out = null;
                try {
                    File.createNewFile();
                    fw = new FileWriter(File);
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File), StandardCharsets.UTF_8)));
                    out.write("# Arathoth Attributes Configuration");
                    out.write("# @Author Freeze003(寒雨)");
                    out.flush();
                    out.close();
                    fw.close();
                    file = YamlConfiguration.loadConfiguration(File);
                    sub.register(Arathoth.getInstance(),file,true);
                } catch (IOException e) {

                }
            }
            try{
                file.save(File);
            } catch (IOException e) { }
        }
    }
}
