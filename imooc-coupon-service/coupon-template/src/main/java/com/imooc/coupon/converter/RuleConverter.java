package com.imooc.coupon.converter;

import com.imooc.coupon.dto.TemplateRule;
import com.imooc.coupon.util.JsonUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * <h1>优惠券规则属性转换器</h1>
 * Created by Qinyi.
 */
@Converter
public class RuleConverter
        implements AttributeConverter<TemplateRule, String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JsonUtils.toJson(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JsonUtils.toObject(rule, TemplateRule.class);
    }
}
