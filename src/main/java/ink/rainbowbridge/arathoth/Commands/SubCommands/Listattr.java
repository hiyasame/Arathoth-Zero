package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.command.CommandSender;

public class Listattr implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        sender.sendMessage(SendUtils.prefix +(!AttributesData.RegisteredAttr.isEmpty()?"&f&lRegistered Attributes:".replaceAll("&","§") : "&4No Registered Attributes!".replaceAll("&","§")));
        sender.sendMessage(" ");
        if(!AttributesData.RegisteredAttr.isEmpty()){
        for(String name : AttributesData.RegisteredAttr.keySet()){
            sender.sendMessage((SendUtils.prefix + "&7Name:&f "+name+" &7Plugin: &f"+AttributesData.RegisteredAttr.get(name)).replaceAll("&","§"));
        }
        }
        return true;
    }
}
