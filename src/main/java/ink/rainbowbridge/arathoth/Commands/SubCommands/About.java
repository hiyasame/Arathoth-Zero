package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class About implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).sendTitle("Arathoth Zero", "by.寒雨", 20, 40, 20);
            SendUtils.send((Player) sender, "&fHologram Display");
            SendUtils.send((Player) sender, "&f&lArathoth &7- &8&lZero");
            SendUtils.send((Player) sender, "Version: &f0.0.1-SNAPSHOT");
        } else {
            SendUtils.info("&f&lArathoth &7- &8&lZero");
            SendUtils.info("Version: &f0.0.1-SNAPSHOT");
        }
        return true;
    }
}