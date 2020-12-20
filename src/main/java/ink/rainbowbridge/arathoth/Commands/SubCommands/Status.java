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

import java.util.ArrayList;
import java.util.List;

public class Status implements SubCommandExecutor {
    /**
     *0。1。0 时代川新status，支持隐藏注册的值为0的placeholder变量
     * 0.1.1时代，命令改为查询指定玩家的属性，查询自己的可以用mystatus
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
                        List<String> papi = new ArrayList<>();
                        List<String> out = new ArrayList<>();
                        for (String str : plugin.getConfig().getStringList("StatusInfo.RegisteredPAPI")) {
                            try {
                                if (Double.valueOf(PlaceholderAPI.setPlaceholders(p, str)).equals(0.0D)) {
                                    papi.add(str);
                                }
                            } catch (NumberFormatException ex) {
                            }

                        }
                        for (String str : plugin.getConfig().getStringList("StatusInfo.Messages")) {
                            boolean iszero = false;
                            for (String placeholder : papi) {
                                if (str.contains(placeholder)) {
                                    iszero = true;
                                    break;
                                }
                            }
                            if (!iszero) {
                                out.add(str);
                            }
                        }
                        if (out.size() > plugin.getConfig().getInt("StatusInfo.MinLines")) {
                            for (String str : out) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, str)));
                            }
                        } else {
                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StatusInfo.HideTitleMessage.Title")), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StatusInfo.HideTitleMessage.SubTitle")), 10, 40, 10);
                        }
                    }
                } catch (NullPointerException e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&l[&f&lArathoth&7&l] &7玩家不在线或不存在"));
                    return true;
                }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&l[&f&lArathoth&7&l] &7参数错误"));
        return true;
            }
        }
