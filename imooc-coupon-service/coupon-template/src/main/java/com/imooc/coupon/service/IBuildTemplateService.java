package com.imooc.coupon.service;

import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.vo.TemplateRequest;

/**
 * @author cyw
 */
public interface IBuildTemplateService {

    /**
     * <h2>创建优惠券模板</h2>
     *
     * @param request {@link TemplateRequest} 模板请求 对象
     * @return {@link CouponTemplate} 优惠券模板实体 对象
     * @throws CouponException
     */
    CouponTemplate buildCouponTemplate(TemplateRequest request) throws CouponException;

}
