package ink.rainbowbridge.arathoth.Commands.SubCommands;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.AttributeLoader;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Attributes.SlotsManager;
import ink.rainbowbridge.arathoth.Attributes.SpecialAttribute;
import ink.rainbowbridge.arathoth.Commands.SubCommandExecutor;
import ink.rainbowbridge.arathoth.Rules.RulesManager;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import org.bukkit.command.CommandSender;

public class Listattr implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        sender.sendMessage(SendUtils.prefix +(!AttributeLoader.RegisteredNum.isEmpty() ?"&f&lRegistered NumberAttributes:".replaceAll("&","§") : "&4No Registered NumberAttributes!".replaceAll("&","§")));
        sender.sendMessage(" ");
        if(!AttributeLoader.RegisteredNum.isEmpty()){
        for(NumberAttribute attr : AttributeLoader.RegisteredNum.keySet()){
            sender.sendMessage(((SendUtils.prefix + "&7Name:&f "+attr.getName()+" &7Plugin: &f"+AttributeLoader.RegisteredNum.get(attr).getName())+" &7Enable: &f"+attr.isEnable()).replaceAll("&","§"));
        }
        }
        sender.sendMessage(" ");

        sender.sendMessage(SendUtils.prefix +(!AttributeLoader.RegisteredSpecial.isEmpty() ?"&f&lRegistered SpecialAttributes:".replaceAll("&","§") : "&4No Registered SpecialAttributes!".replaceAll("&","§")));
        sender.sendMessage(" ");
        if(!AttributeLoader.RegisteredSpecial.isEmpty()){
            for(SpecialAttribute attr : AttributeLoader.RegisteredSpecial.keySet()){
                sender.sendMessage(((SendUtils.prefix + "&7Name:&f "+attr.getName()+" &7Plugin: &f"+AttributeLoader.RegisteredSpecial.get(attr).getName())+" &7Enable: &f"+attr.isEnable()).replaceAll("&","§"));
            }
        }
        sender.sendMessage(" ");

        sender.sendMessage(SendUtils.prefix +(!RulesManager.Rules.isEmpty()?"&f&lRegistered Rules:".replaceAll("&","§") : "&4No Registered Rules!".replaceAll("&","§")));
        sender.sendMessage(" ");
        if(!RulesManager.Rules.isEmpty()){
            for(String name : RulesManager.Rules.keySet()){
                sender.sendMessage((SendUtils.prefix + "&7Name:&f "+name+" &7Plugin: &f"+RulesManager.Rules.get(name)).replaceAll("&","§"));
            }
        }
        sender.sendMessage(" ");

        sender.sendMessage(SendUtils.prefix +(!SlotsManager.getSlots().isEmpty()?"&f&lRegistered Slots:".replaceAll("&","§") : "&4No Registered Slotss!".replaceAll("&","§")));
        sender.sendMessage(" ");
        sender.sendMessage((SendUtils.prefix + "&7Location:&f MainHand &7Name: &f" + Arathoth.getInstance().getConfig().get("Slots.MainHand")).replaceAll("&", "§"));
        if(!SlotsManager.getSlots().isEmpty()){
                for(Integer i : SlotsManager.getSlots().keySet()) {
                    sender.sendMessage((SendUtils.prefix + "&7Location:&f " + i + " &7Name: &f" + SlotsManager.getSlots().get(i)).replaceAll("&", "§"));
                }
        }
        return true;
    }
}
