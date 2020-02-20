package com.imooc.coupon.service.impl;

import com.google.common.base.Stopwatch;
import com.imooc.coupon.constant.Constant;
import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cyw
 */
@Slf4j
@Service
public class AsyncService implements IAsyncService {

    @Autowired
    private CouponTemplateDao couponTemplateDao;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Async("taskExecutor")
    @Override
    @SuppressWarnings("all")
    public void asyncConstructCouponByTemplate(CouponTemplate template) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Set<String> couponCode = buildCouponCode(template);
        String redisKey = Constant.RedisPrefix.COUPON_TEMPLATE + template.getId();
        log.info("Push Coupon Code To Redis: {}",
                redisTemplate.opsForList().rightPushAll(redisKey, couponCode));

        template.setAvailable(true);
        couponTemplateDao.save(template);

        stopwatch.stop();
        log.info("Construct Coupon Code By CouponTemplate Cost: {} ms ",
                stopwatch.elapsed(TimeUnit.MILLISECONDS));

        //todo 因为是异步创建优惠券 所以短信或者邮件通知 运营人员 可用
        log.info("CouponTemplate({}) Is Available !",template.getId());
    }

    /**
     * <h2>构造优惠券码</h2>
     * 优惠券码(对应于每一张优惠券, 18位)
     * 前四位: 产品线 + 类型
     * 中间六位: 日期随机(190101)
     * 后八位: 0 ~ 9 随机数构成
     *
     * @param template {@link CouponTemplate} 实体类
     * @return Set<String> 与 template.count 相同个数的优惠券码
     */
    @SuppressWarnings("all")
    private Set<String> buildCouponCode(CouponTemplate template) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Set<String> result = new HashSet<>(template.getCount());
        String prefix4 = template.getProductLine().getCode() + template.getCategory().getCode();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        String suffix8 = null;

        for (Integer i = 0; i < template.getCount(); i++) {
            result.add(prefix4 + date + buildSuffix8());
        }
        while (result.size() < template.getCount()) {
            result.add(prefix4 + date + buildSuffix8());
        }

        stopwatch.stop();
        log.info("Build Coupon Code Cost: {} ms ",
                stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return result;

    }

    /**
     * 构建优惠券后八位: 0 ~ 9
     * 第一位 1~9  之后  0~9
     */
    private String buildSuffix8() {
        char[] bases = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return RandomStringUtils.random(1, bases) + RandomStringUtils.randomNumeric(7);
    }
}
