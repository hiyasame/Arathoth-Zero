package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class Status implements SubCommandExecutor {
    /**
     *0。1。0 时代川新status，支持隐藏注册的值为0的placeholder变量
     * 0.1.1时代，命令改为查询指定玩家的属性，查询自己的可以用mystatus
     * 0.1.2时代，命令查询不再有嘲讽信息，也不会隐藏，因为无必要
     */
    @Override
    public boolean command(CommandSender sender, String[] args) {
        Plugin plugin = (Plugin) Arathoth.getPlugin(Arathoth.class);
        if (args.length == 2) {
                try {
                    Player p = Bukkit.getPlayer(args[1]);
                    if (plugin.getConfig().getStringList("StatusInfo.Messages") == null) {
                        SendUtils.send(p, "No status messages,please set it in config.yml!");
                    } else {
                        for(String str : plugin.getConfig().getStringList("StatusInfo.Messages")){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',PlaceholderAPI.setPlaceholders(p,str)));
                        }
                    }
                } catch (NullPointerException e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&l[&f&lArathoth&7&l] &7玩家不在线或不存在"));
                    return true;
                }
        }
        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[&f&lArathoth&7&l] &7参数错误"));
        }
        return true;
            }
        }
