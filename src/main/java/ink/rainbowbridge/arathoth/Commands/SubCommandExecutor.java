package ink.rainbowbridge.arathoth.Commands;

import org.bukkit.command.CommandSender;

public abstract interface SubCommandExecutor {
    public abstract boolean command(CommandSender sender, String[] args);
}
