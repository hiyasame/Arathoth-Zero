package ink.rainbowbridge.arathoth.API;

import ink.rainbowbridge.arathoth.Arathoth;
import ink.rainbowbridge.arathoth.Attributes.ArathothAttribute;
import ink.rainbowbridge.arathoth.Attributes.AttributeLoader;
import ink.rainbowbridge.arathoth.Attributes.NumberAttribute;
import ink.rainbowbridge.arathoth.Attributes.SpecialAttribute;
import ink.rainbowbridge.arathoth.Attributes.data.AttributeData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Arathoth-API
 * 0.1.3 删除了大部分旧属性操作方法
 *
 * @author 寒雨
 * @create 2020/12/11 21:24
 */
public class ArathothAPI {
    /**
     * 处理指定属性配置，没有自动生成一个空文件
     *
     * @param attr 当前属性
     * @return 是否初次
     */
    public static boolean AttributeConfigDefaultSet(ArathothAttribute attr){
        File File = new File(Arathoth.getInstance().getDataFolder(), "Attributes/" + attr.getName() + ".yml");
        FileConfiguration file = null;
        boolean first = false;
        if(!File.exists()){
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                File.createNewFile();
                fw = new FileWriter(File);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File), StandardCharsets.UTF_8)));
                out.write("# Arathoth Attributes Configuration\n");
                out.write("# @Author Freeze003(寒雨)");
                out.flush();
                out.close();
                fw.close();
                file = YamlConfiguration.loadConfiguration(File);
                first = true;
            } catch (IOException e) {

            }
        }
        else{
            file = YamlConfiguration.loadConfiguration(File);
        }
        return first;
    }

    /**
     * 直接获取属性文件，配合AttributeConfigDefaultSet方法使用
     *
     * @param attr 指定属性
     * @return 配置
     */
    public static FileConfiguration getAttributeConfig(ArathothAttribute attr){
        return YamlConfiguration.loadConfiguration(new File(Arathoth.getInstance().getDataFolder(), "Attributes/" + attr.getName() + ".yml"));
    }

    /**
     * 保存属性文件
     *
     * @param attr 指定属性
     * @param edited 二次编辑过的属性配置
     */
    public static void saveAttributeConfig(ArathothAttribute attr,FileConfiguration edited){
        File file = new File(Arathoth.getInstance().getDataFolder(), "Attributes/" + attr.getName() + ".yml");
        try {
            edited.save(file);
        }
        catch(IOException e){}
    }

    /**
     * 取随机数方法
     *
     * @param value1 primary
     * @param value2 regular
     * @return 浮动值
     */
    public static Double getRandom(Double value1,Double value2){
        Double random = Math.floor(value1 + Arathoth.random.nextDouble()*(value2-value1));
        return random;
    }

    /**
     * 合并数值属性data
     * 不安全的方法，极易导致NullPointerException，0.1.2时代的遗留方法
     *
     * @param data1 data1
     * @param data2 data2
     * @return data
     */
    @Deprecated
    public static Double[] DataPutIn(Double[] data1 , Double[] data2){
        Double[] data = {0.0D,0.0D,0.0D};
        data[0] = data1[0] + data2[0];
        data[1] = data1[1] + data2[1];
        data[2] = data1[2] + data2[2];
        return data;
    }


    /**
     * 直接获取指定属性值
     * 若不存在则直接更新属性再获取
     *
     * @param e 目标生物
     * @param AttributeName 指定属性
     * @return primary,regular,percent
     */
    public static AttributeData getNumAttributeData(LivingEntity e, String AttributeName){
        NumberAttribute na = AttributeLoader.NumberName.get(AttributeName);
        return na.parseNumber(AttributeLoader.getEntityLore(e));
    }

    /**
     * 获取特殊属性值
     *
     * @param e 生物
     * @param attr 属性
     * @return Object
     */
    public static Object getSpecialAttributeValue(LivingEntity e,SpecialAttribute attr){
        return attr.parseValue(e);
    }

    /**
     * 概率判断方法
     *
     * @param rate 概率
     * @return 是否通过
     */
    public static boolean Chance(Double rate){
        if (Arathoth.random.nextDouble() < rate){
            return true;
        }
        return false;
    }

    /**
     * 获取特殊属性占位符变量
     *
     * @param name 属性名
     * @param p 玩家
     * @return value
     */
    public static String getSpecialAttributePlaceHolder(String name, Player p){
        HashMap<String,SpecialAttribute> attrs = new HashMap<>();
       for(SpecialAttribute sa : AttributeLoader.RegisteredSpecial.keySet()){
           attrs.put(sa.getName(),sa);
       }
        return attrs.get(name).getPlaceHolder(p);
    }

    /**
     * 为弓箭设置元数据
     *
     * @param e 弓箭
     * @param name 属性名
     * @param data 属性data
     */
    public static void setArrowData(Entity e,String name,AttributeData data){
        e.setMetadata(name,new FixedMetadataValue(Arathoth.getInstance(),data));
    }

    /**
     *  获取弓箭data
     *
     * @param e 弓箭
     * @param name 属性名
     * @return 属性data
     */
    public static AttributeData getArrowData(Entity e,String name){
        return (AttributeData) Arathoth.getMetadata(e,name,Arathoth.getInstance());
    }
}
