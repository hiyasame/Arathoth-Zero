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
    //TODO Primary Regular Percent Attribute 四类属性查询方法
    @Override
    public String onPlaceholderRequest(Player p, String s) {
        if(s.startsWith("Primary_")){
            String str = s.replace("Primary_","");
            try{
                return AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[1].toString();
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if(s.startsWith("Regular_")){
            String str = s.replace("Regular_","");
            try{
                return AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[2].toString();
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Percent_")){
            String str = s.replace("Percent_","");
            try{
                return AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[3].toString();
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Attribute_")){
            String str = s.replace("Attribute_","");
            try{
                if (!(AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[1] == AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[2])){
                return AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[1].toString()+"-"+AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[1].toString();}
                else{
                    return AttributesData.AttrData.get(p.getUniqueId().toString()).get(str)[1].toString();
                }
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        return null;
    }
}
