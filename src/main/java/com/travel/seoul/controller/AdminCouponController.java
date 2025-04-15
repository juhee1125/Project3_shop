package com.travel.seoul.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.CouponMapper;
import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.CouponVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminCouponController {
	
	@Setter(onMethod_=@Autowired) 
	private UserService service;

	@Autowired
    private ProductMapper ProductMapper;
	@Autowired
	private CouponMapper CouponMapper;

	
	@GetMapping("/couponList")
	public String couponList(Model model) {
		List<CouponVO> couponlist = CouponMapper.couponlist();
		model.addAttribute("couponlist", couponlist);
		
		return "/admin/couponList";
	}
	
	//쿠폰등록	
	@GetMapping("/couponupload")
	public String couponupload(Model model) {     
		model.addAttribute("productlist", ProductMapper.productlist());
		return "/admin/couponupload";
	}
	@PostMapping(value = "/couponuploadprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, String>> couponuploadprocess(@RequestParam("nameinput") String nameinput, @RequestParam("productlabel") String productlabel,
			@RequestParam(value = "productname", required = false) String productname, @RequestParam(value = "numberinput", required = false) String numberinput,
			@RequestParam("discountlabel") String discountlabel, @RequestParam("discountlist") String discountlist, @RequestParam("dateinputstart") String dateinputstart,
			@RequestParam("dateinputend") String dateinputend) {   
		Map<String, String> response = new HashMap<>();
		try {
			List<String> newdiscountlist = new ObjectMapper().readValue(discountlist, new TypeReference<List<String>>() {});
			CouponVO coupon = new CouponVO();
			if (productname!=null) {
				Long pnum = ProductMapper.findByName(productname);
				coupon.setP_num(pnum);
			}
			else {
				coupon.setP_num(null);
			}
			coupon.setC_name(nameinput);
			coupon.setC_type(productlabel);
			if (!numberinput.isEmpty()) {
				Long newnumberinput = Long.parseLong(numberinput);
				coupon.setC_count(newnumberinput);
			}
			else {
				coupon.setC_count(null);
			}
			coupon.setC_discount_setting(discountlabel);
			if (newdiscountlist.size()==1) {
				coupon.setC_discount(newdiscountlist.get(0));
				coupon.setC_price(null);
				coupon.setC_discount_price(null);
			}
			else {
				coupon.setC_discount(null);
				coupon.setC_price(newdiscountlist.get(0));
				coupon.setC_discount_price(newdiscountlist.get(1));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        java.util.Date startDate = sdf.parse(dateinputstart);
	        java.util.Date endDate = sdf.parse(dateinputend);
	        Date sqlStartDate = new Date(startDate.getTime());
	        Date sqlEndDate = new Date(endDate.getTime());
			coupon.setC_discount_start(sqlStartDate);
			coupon.setC_discount_end(sqlEndDate);
	
			CouponMapper.couponInsert(coupon);
			
			response.put("message", "쿠폰을 등록하였습니다");		
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("message", "쿠폰 등록 실패: " + e.getMessage());
			
            return ResponseEntity.badRequest().body(response);
        }
	}
	
	//쿠폰삭제
	@PostMapping(value = "/coupondelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> coupondelete(@RequestBody List<Map<String, String>> userData) {
		for(Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("couponnum"));
			CouponMapper.couponDelete(num);
		}		

	    return ResponseEntity.ok("쿠폰이 삭제되었습니다");
	}

	
	//검색
	@GetMapping("/couponsearch")
	public String couponsearch(@RequestParam("topic") String topic, @RequestParam("keyword") String keyword, HttpSession session) {	
		List<CouponVO> couponlist = CouponMapper.couponlist();
		List<CouponVO> searchList = new ArrayList<>();
		
		//키워드가 해당 주제에 포함되어있으면 List에 추가
		for (CouponVO coupon:couponlist) {
			switch (topic) {
				case "name":
					if (keyword.equals(coupon.getC_name())) {
						searchList.add(coupon);
					}
					break;
				 case "type":
					if (keyword.equals(coupon.getC_type())) {
						searchList.add(coupon);
					}
					break;
				 default:
		                System.out.println("결과없음");
		            break;
			}
		}
		session.setAttribute("coupon_searchList", searchList);
		System.out.println("User List Size: " + searchList.size());
		
		return "admin/couponList";
	}
}
