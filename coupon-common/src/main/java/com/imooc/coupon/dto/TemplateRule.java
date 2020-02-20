package com.imooc.coupon.dto;

import com.imooc.coupon.constant.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <h1>优惠券规则</h1>
 * @author cyw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {
    /** 优惠券过期规则 */
    private Expiration expiration;
    /** 优惠券折扣规则 */
    private Discount discount;
    /** 每人最多能领取几张 */
    private Integer limitation;
    /** 优惠券使用范围 : 地区 + 商品类型 */
    private Usage usage;
    /** 权重(可以和哪些优惠券叠加使用, 同一类优惠券一定不能叠加使用！) list[]-> 保存优惠券的唯一编码 */
    private String weight;

    public boolean validate() {
        return expiration.validate() && discount.validate() && usage.validate()
                && limitation > 0 && StringUtils.isNotEmpty(weight);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    /*<h2>有效期规则</h2>*/
    public static class Expiration{
        /** 有效期规则,对应 PeriodType 的 code 字段 */
        private Integer period;
        /** 有效间隔，只对变动性有效期有效 */
        private Integer gap;
        /** 优惠券模板的失效时间 对两种都有效 */
        private Long deadline;

        boolean validate() {
            PeriodType type = PeriodType.of(period);
            if (null == type) {
                return false;
            }
            switch (type) {
                case REGULAR:
                    return deadline > 0;
                case SHIFT:
                    return deadline > 0 && gap > 0;
                default:
                    return false;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    /*<h2>折扣，需要配合优惠券类型来使用</h2>*/
    public static class Discount{
        //private Integer category; 1、因为优惠券的分类是每个优惠券都有的属性 2、优惠券的类型在数据库中有专门的列 且 不属于规则
        /** 额度：满减(20):得到门槛减去20 折扣(85):85折 满减(10):立减10 */
        private Integer quota;
        /** 基准:只对满减券有效 */
        private Integer base;

        boolean validate() {
            return quota > 0 && base > 0;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    /*<h2>优惠券使用范围</h2>*/
    public static class Usage{
        /** 省份 */
        private String province;
        /** 城市 */
        private String city;
        /** 商品类型:生鲜 家具 或支持多种分类 List[生鲜,家具] */
        private String goodsType;

        boolean validate() {
            return !StringUtils.isAllEmpty(province,city,goodsType);
        }
    }

}
