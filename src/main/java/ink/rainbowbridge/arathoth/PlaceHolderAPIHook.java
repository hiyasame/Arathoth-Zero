package ink.rainbowbridge.arathoth;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Attributes.data.PlaceHolderType;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceHolderAPIHook extends EZPlaceholderHook {
    public PlaceHolderAPIHook(Arathoth plugin) {
        super(plugin, "Arathoth");
    }
    //TODO Primary Regular Percent Attribute 四类属性查询方法
    //TODO 0.1.0新时代加入Special以查询特殊属性
    //TODO 0.1.3简化代码
    @Override
    public String onPlaceholderRequest(Player p, String s) {
        if(s.startsWith("Primary_")){
            String str = s.replace("Primary_","");
            try{
                return ArathothAPI.getNumAttributeData(p,str).getPlaceHolder(PlaceHolderType.PRIMARY);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if(s.startsWith("Regular_")){
            String str = s.replace("Regular_","");
            try{
                return ArathothAPI.getNumAttributeData(p,str).getPlaceHolder(PlaceHolderType.REGULAR);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Percent_")){
            String str = s.replace("Percent_","");
            try{
                return ArathothAPI.getNumAttributeData(p,str).getPlaceHolder(PlaceHolderType.PERCENT);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Attribute_")){
            String str = s.replace("Attribute_","");
            try{
                return ArathothAPI.getNumAttributeData(p,str).getPlaceHolder(PlaceHolderType.ATTRIBUTE);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Special_")){
            String str = s.replace("Special_","");
            try{
                return ArathothAPI.getSpecialAttributePlaceHolder(str,p);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        return null;
    }
}
