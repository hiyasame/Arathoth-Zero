package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Reload implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        Plugin plugin = (Plugin)Arathoth.getPlugin(Arathoth.class);
        long time = System.currentTimeMillis();
        plugin.reloadConfig();
        sender.sendMessage(SendUtils.prefix + "Reload completely! " + ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + (System.currentTimeMillis() - time) +"ms"+ChatColor.WHITE + "]");
        return true;
    }
}
