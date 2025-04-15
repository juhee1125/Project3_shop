package com.travel.seoul.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.service.CouponService;
import com.travel.seoul.vo.CouponVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/coupon/*")
public class CouponController {
	
	@Setter(onMethod_=@Autowired) 
	private CouponService CouponService;
	
	@Autowired
	private ProductMapper ProductMapper;

	
	@GetMapping("/coupon")
	public String delivery(HttpSession session, Model model) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
	
		List<CouponVO> usercouponlist = CouponService.PossessionCoupon(user.getM_num());
		List<String> couponpnamelist = new ArrayList<>();
		for(CouponVO coupon:usercouponlist) {
			if(coupon.getP_num()!=null) {
				String couponpname = ProductMapper.getProductByNum(coupon.getP_num()).getP_name();
				couponpnamelist.add(couponpname);
			}
		}
		
		model.addAttribute("couponlist", usercouponlist);
		model.addAttribute("couponpnamelist", couponpnamelist);
		
	    return "/user/Coupon";
	}
}
