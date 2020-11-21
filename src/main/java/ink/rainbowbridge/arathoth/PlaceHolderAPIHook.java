package ink.rainbowbridge.arathoth;

import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlaceHolderAPIHook extends EZPlaceholderHook {
    public PlaceHolderAPIHook(Arathoth plugin) {
        super(plugin, "Arathoth");
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {
        try {
            return AttributesData.AttrData.get(p.getUniqueId().toString()).get(s)[1].toString();
        }
        catch (Exception e) {
            SendUtils.warn("错误的变量: &4" + s);
            return "变量错误";
        }
    }
}
