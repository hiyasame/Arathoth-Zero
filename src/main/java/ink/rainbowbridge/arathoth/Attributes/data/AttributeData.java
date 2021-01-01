package ink.rainbowbridge.arathoth.Attributes.data;

import com.sun.istack.internal.NotNull;
import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Arathoth;

/**
 * 0.1.3新增的储存属性值的对象
 * 替代拉胯的Double[]
 *
 * @Author 寒雨
 * @Since 2020/12/31 23:00
 */
public class AttributeData {
    private Double primary;
    private Double regular;
    private Double percent;

    /**
     * 初始化构造方法
     */
    public AttributeData(){
        this.primary = 0.0D;
        this.regular = 0.0D;
        this.percent = 0.0D;
    }

    /**
     * 初始赋值构造方法
     *
     * @param primary 最小值
     * @param regular 最大值
     * @param percent 百分比
     */
    public AttributeData(@NotNull Double primary,@NotNull Double regular,@NotNull Double percent){
        this.primary = primary;
        this.regular = regular;
        this.percent = percent;
    }

    /**
     * 解析data获得可以直接使用的属性值
     *
     * @return 结果
     */
    public Double solveData(){
        if(this.primary.equals(this.regular)){
            return primary*(1+percent/100);
        }
        else{
            return ArathothAPI.getRandom(primary,regular)*(1+percent/100);
        }
    }

    /**
     * 转换为PlaceHolder变量
     *
     * @param type 转换的类型
     * @return Placeholder
     */
    public String getPlaceHolder(PlaceHolderType type){
        switch (type){
            case PRIMARY:{
                return Arathoth.DecimalFormat.format(primary);
            }
            case REGULAR:{
                return Arathoth.DecimalFormat.format(regular);
            }
            case PERCENT:{
                return Arathoth.DecimalFormat.format(percent);
            }
            case ATTRIBUTE:{
                if (!(primary.equals(regular))){
                    return Arathoth.DecimalFormat.format(primary*(1+percent/100))+"-"+Arathoth.DecimalFormat.format(regular*(1+percent/100));}
                else{
                    return Arathoth.DecimalFormat.format(primary*(1+percent/100));
                }
            }
        }
        return null;
    }
    public Double getPercent() {
        return percent;
    }

    public Double getPrimary() {
        return primary;
    }

    public Double getRegular() {
        return regular;
    }

    public void setPrimary(@NotNull Double primary) {
        this.primary = primary;
    }

    public void setPercent(@NotNull Double percent) {
        this.percent = percent;
    }

    public void setRegular(@NotNull Double regular) {
        this.regular = regular;
    }

}
