package ink.rainbowbridge.arathoth;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Attributes.AttributeLoader;
import ink.rainbowbridge.arathoth.Utils.SendUtils;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceHolderAPIHook extends EZPlaceholderHook {
    public PlaceHolderAPIHook(Arathoth plugin) {
        super(plugin, "Arathoth");
    }
    //TODO Primary Regular Percent Attribute 四类属性查询方法
    //TODO 0.1.0新时代加入Special以查询特殊属性
    @Override
    public String onPlaceholderRequest(Player p, String s) {
        if(s.startsWith("Primary_")){
            String str = s.replace("Primary_","");
            try{
                return Arathoth.DecimalFormat.format(ArathothAPI.getNumAttributeValues(p,str)[0]);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if(s.startsWith("Regular_")){
            String str = s.replace("Regular_","");
            try{
                return Arathoth.DecimalFormat.format(ArathothAPI.getNumAttributeValues(p,str)[1]);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Percent_")){
            String str = s.replace("Percent_","");
            try{
                return Arathoth.DecimalFormat.format(ArathothAPI.getNumAttributeValues(p,str)[2]);
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Attribute_")){
            String str = s.replace("Attribute_","");
            try{
                if (!(ArathothAPI.getNumAttributeValues(p,str)[0].equals(ArathothAPI.getNumAttributeValues(p,str)[1]))){
                return Arathoth.DecimalFormat.format(ArathothAPI.getNumAttributeValues(p,str)[0] + ArathothAPI.getNumAttributeValues(p,str)[0]*ArathothAPI.getNumAttributeValues(p,str)[2])+"-"+Arathoth.DecimalFormat.format(ArathothAPI.getNumAttributeValues(p,str)[1] + ArathothAPI.getNumAttributeValues(p,str)[1]*ArathothAPI.getNumAttributeValues(p,str)[2]);}
                else{
                    return Arathoth.DecimalFormat.format(ArathothAPI.SolveAttributeValue(ArathothAPI.getNumAttributeValues(p,str)));
                }
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        else if (s.startsWith("Special_")){
            String str = s.replace("Special_","");
            try{
                return (String)ArathothAPI.getSpecialAttributeValue(p,AttributeLoader.SpecialName.get(str));
            }catch (Exception e){
                SendUtils.warn("PlaceHolderException: &4"+s);
                return "PlaceHolderException";
            }
        }
        return null;
    }
}
