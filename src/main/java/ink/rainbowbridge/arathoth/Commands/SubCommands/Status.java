package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Status implements SubCommandExecutor {
    /**
     *0。1。0 时代川新status，支持隐藏注册的值为0的placeholder变量
     */
    @Override
    public boolean command(CommandSender sender, String[] args) {
        Plugin plugin = (Plugin)Arathoth.getPlugin(Arathoth.class);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.getConfig().getStringList("StatusInfo.Messages") == null) {
                SendUtils.send(p,"No status messages,please set it in config.yml!");
            }
            else {
                List<String> papi = new ArrayList<>();
                List<String> out = new ArrayList<>();
                for(String str : plugin.getConfig().getStringList("StatusInfo.RegisteredPAPI")){
                    if(Double.valueOf(PlaceholderAPI.setPlaceholders(p,str)).equals(0.0D)){
                        papi.add(str);
                    }
                }
                for(String str : plugin.getConfig().getStringList("StatusInfo.Messages")){
                    boolean iszero = false;
                    for(String placeholder : papi){
                        if(str.contains(placeholder)){
                            iszero = true;
                            break;
                        }
                    }
                    if(!iszero){
                        out.add(str);
                    }
                }
                if(out.size() > plugin.getConfig().getInt("StatusInfo.MinLines")){
                    for(String str : out){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',PlaceholderAPI.setPlaceholders(p,str)));
                    }
                }
                else{
                    p.sendTitle(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("StatusInfo.HideTitleMessage.Title")),ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("StatusInfo.HideTitleMessage.SubTitle")),10,40,10);
                }
            }
        }
        else{
            SendUtils.warn("Not allowed to use it at console!");
        }
        return true;
    }
}
