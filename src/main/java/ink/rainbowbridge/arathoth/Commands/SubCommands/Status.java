package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Status implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        Plugin plugin = (Plugin)Arathoth.getPlugin(Arathoth.class);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.getConfig().getStringList("Status") == null) {
                SendUtils.send(p,"No status messages,please set it in config.yml!");
            }
            else {
                for (String str : plugin.getConfig().getStringList("Status")) {
                    PlaceholderAPI.setPlaceholders(p, str.replaceAll("&", "§"));
                }
            }
        }
        else{
            SendUtils.warn("Not allowed to use it at console!");
        }
        return true;
    }
}
