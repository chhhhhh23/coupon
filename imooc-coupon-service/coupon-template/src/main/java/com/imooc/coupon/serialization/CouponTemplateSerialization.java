package com.imooc.coupon.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.util.JsonUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * <h1>优惠券模板的自定义序列化器</h1>
 *
 * @author cyw
 */
public class CouponTemplateSerialization extends JsonSerializer<CouponTemplate> {
    @Override
    public void serialize(CouponTemplate template,//目标对象
                          JsonGenerator generator,//生成json的生成器
                          SerializerProvider serializers) throws IOException {
        // 开始序列化对象
        generator.writeStartObject();

        generator.writeStringField("id", template.getId().toString());
        generator.writeStringField("name", template.getName());
        generator.writeStringField("logo", template.getLogo());
        generator.writeStringField("desc", template.getDesc());
        generator.writeStringField("category",
                template.getCategory().getDescription());
        generator.writeStringField("productLine",
                template.getProductLine().getDescription());
        generator.writeStringField("count", template.getCount().toString());
        generator.writeStringField("createTime",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(template.getCreateTime()));
        generator.writeStringField("userId", template.getUserId().toString());
        generator.writeStringField("key",
                template.getKey() + String.format("%04d", template.getId()));
        generator.writeStringField("target",
                template.getTarget().getDescription());
        generator.writeStringField("rule",
                JsonUtils.toJson(template.getRule()));

        // 结束序列化对象
        generator.writeEndObject();
    }
}
