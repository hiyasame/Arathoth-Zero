package ink.rainbowbridge.arathoth.Attributes;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AttributesData {
    public static ConcurrentHashMap<String,HashMap<String,Double[]>> AttrData = new ConcurrentHashMap<>();
    //String uuid , Double[] AttributeValue,1 for primary,2 for regular,3 for percent
    public static HashMap<String, String> RegisteredAttr = new HashMap<>(); //String Name, Plugin plugin
    public static HashMap<String,String> Patterns = new HashMap<>();//Sting Name, String Pattern
    public static HashMap<String,Integer> Priority = new HashMap<>();//String Name,Integer Priority
    public static List<String> Disabled = new ArrayList<>();//Disabled Plugin name
}
