package com.imooc.coupon.service.impl;

import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.dto.CouponTemplateSDK;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.exception.ServiceException;
import com.imooc.coupon.service.ITemplateBaseService;
import com.imooc.coupon.util.BeanUtils;
import com.imooc.coupon.util.CheckUtils;
import com.imooc.coupon.vo.TemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cyw
 */
@Service
public class TemplateBaseService implements ITemplateBaseService {

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Override
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {
        CheckUtils.checkNull(id,"id");
        Optional<CouponTemplate> template = couponTemplateDao.findById(id);
        if (!template.isPresent()){
            throw new ServiceException("非法请求参数");
        }
        return template.get();
    }

    @Override
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        return couponTemplateDao.findAllByAvailableAndExpired(true, false).stream()
                .map(this::template2TemplateSDK)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids) {
        return couponTemplateDao.findAllByAvailableAndExpired(true, false).stream()
                .map(this::template2TemplateSDK)
                .collect(Collectors.toMap(
                        CouponTemplateSDK::getId, Function.identity()
                ));
    }

    @Override
    public void deleteById(Integer id) {
        couponTemplateDao.deleteById(id);
    }

    //@Override
    //public void batchDelete(Collection<Integer> ids) {
    //    couponTemplateDao.deleteAllInBatch();
    //}
    //
    //@Override
    //public void update(TemplateRequest templateRequest) {
    //    couponTemplateDao.save(templateRequest);
    //}
    private CouponTemplateSDK template2TemplateSDK(CouponTemplate template) {

        return new CouponTemplateSDK(
                template.getId(),
                template.getName(),
                template.getLogo(),
                template.getDesc(),
                template.getCategory().getCode(),
                template.getProductLine().getCode(),
                template.getKey(),  // 并不是拼装好的 Template Key
                template.getTarget().getCode(),
                template.getRule()
        );
    }
}
