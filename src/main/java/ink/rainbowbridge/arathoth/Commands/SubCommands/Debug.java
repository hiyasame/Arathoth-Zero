package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;



public class Debug implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        Plugin plugin = (Plugin)Arathoth.getPlugin(Arathoth.class);
        sender.sendMessage((Arathoth.isDebug())? SendUtils.prefix + "Debug-Mode: §foff" : SendUtils.prefix + "Debug-Mode: §fon");
        Arathoth.isDebug = !Arathoth.isDebug();
        plugin.saveConfig();
        return true;
    }
}
