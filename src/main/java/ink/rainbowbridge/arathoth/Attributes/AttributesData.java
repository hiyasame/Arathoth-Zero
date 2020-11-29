package ink.rainbowbridge.arathoth.Attributes;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AttributesData {
    public static ConcurrentHashMap<String,HashMap<String,Double[]>> AttrData = new ConcurrentHashMap<>();
    //String uuid , Double[] AttributeValue,1 for primary,2 for regular,3 for percent
    public static HashMap<String, String> RegisteredAttr = new HashMap<>(); //String Name, Plugin plugin
    public static HashMap<String,SubAttribute> AttributesMap = new HashMap<>();
}
