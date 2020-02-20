package com.imooc.coupon.global;

import com.imooc.coupon.dto.R;
import com.imooc.coupon.util.JsonUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
public class ExceptionHandler implements ErrorController {

	@Override
	public String getErrorPath() {
		return "error";
	}
	
	@RequestMapping(value="/error")
	public String error(){
		return JsonUtils.toJson(R.fail("访问太多频繁，请稍后再访问！！！"));
	}

}