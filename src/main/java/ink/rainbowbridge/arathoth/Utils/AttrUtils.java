package ink.rainbowbridge.arathoth.Utils;

import ink.rainbowbridge.arathoth.Arathoth;

/**
 * @author 寒雨
 * @create 2020/11/29 10:13
 */
public class AttrUtils {
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
}
