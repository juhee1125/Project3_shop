package com.travel.seoul.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.mapper.CouponMapper;
import com.travel.seoul.service.DeliveryService;
import com.travel.seoul.vo.CouponVO;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/delivery/*")
public class DeliveryController {
	
	@Setter(onMethod_=@Autowired) 
	private OrderMapper OrderMapper;

	@Autowired
    private ProductMapper ProductMapper;
	@Autowired
	private CouponMapper CouponMapper;
	@Autowired
	private DeliveryService DeliveryService;

	
	@GetMapping("/inquiry")
	public String inquiry() {
		
		return "/user/Delivery";
	}
	
	//조회
	@GetMapping("/deliveryinquirysearch")
	public ResponseEntity<HashMap<String, List>> deliveryinquirysearch(@RequestParam("date") String date, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
		
		LocalDate now = LocalDate.now();
		String[] array = date.split("개월");
		System.out.println("date: "+date);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -Integer.parseInt(array[0]));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(cal.getTime());

		List<OrderVO> datesearchlist = DeliveryService.Productinquiry(startDate, now.toString());
		
		List<OrderVO> orderlist = new ArrayList<>();
		List<String> productnamelist = new ArrayList<>();
		HashMap<String, List> newdatesearchlist = new HashMap<String, List>();
		for(OrderVO order : datesearchlist) {
			if (order.getM_num()== user.getM_num() && !order.getO_paymentstatus().equals("장바구니") && !order.getO_paymentstatus().equals("결제대기")) {
				ProductVO product = ProductMapper.getProductByNum(order.getP_num());
				productnamelist.add(product.getP_name());
				
				orderlist.add(order);
			}
		}
		newdatesearchlist.put("orderlist", orderlist);
		newdatesearchlist.put("productnamelist", productnamelist);

		return ResponseEntity.ok(newdatesearchlist);
	}
	//직접 조회
	@GetMapping("/directinquirysearch")
	public ResponseEntity<HashMap<String, List>> directinquirysearch(@RequestParam("startDateInput") String startDateInput, @RequestParam("endDateInput") String endDateInput, HttpSession session) {	
		UserVO user = (UserVO) session.getAttribute("loginMember");
		
		List<OrderVO> datesearchlist = DeliveryService.Productinquiry(startDateInput, endDateInput);
		
		List<OrderVO> orderlist = new ArrayList<>();
		List<String> productnamelist = new ArrayList<>();
		HashMap<String, List> newdatesearchlist = new HashMap<String, List>();
		for(OrderVO order : datesearchlist) {
			if (order.getM_num()== user.getM_num() && !order.getO_paymentstatus().equals("장바구니") && !order.getO_paymentstatus().equals("결제대기")) {
				ProductVO product = ProductMapper.getProductByNum(order.getP_num());
				productnamelist.add(product.getP_name());
				
				orderlist.add(order);
				System.out.println(order.getO_date());
			}
		}
		newdatesearchlist.put("orderlist", orderlist);
		newdatesearchlist.put("productnamelist", productnamelist);
		
		return ResponseEntity.ok(newdatesearchlist);
	}
	
	@GetMapping("/deliverydetail")
	public String deliverydetail(@RequestParam("orderNumber") String orderNumber, Model model ) {
		List<OrderVO> orderlist = OrderMapper.onumbersearch(orderNumber);
		List<String> productnamelist = new ArrayList<>();
		int o_shippingfee=0;
		int totalprice=0;
		Long cnum=null;
		
		for(OrderVO order:orderlist) {
			productnamelist.add(ProductMapper.getProductByNum(order.getP_num()).getP_name());			
			model.addAttribute("orderDate",order.getO_date());
			totalprice += Long.parseLong(order.getO_totalprice());
			
			cnum = order.getC_num();
		}
		model.addAttribute("totalprice",totalprice);
		
    	if (totalprice<30000) {
    		o_shippingfee =5000;
    	} 	
		
		model.addAttribute("orderNumber",orderNumber);
		model.addAttribute("orderlist",orderlist);
		model.addAttribute("productnamelist",productnamelist);
		model.addAttribute("o_shippingfee",o_shippingfee);
		
		if(cnum != null) {
			CouponVO coupon = CouponMapper.getCouponByNum(cnum);
			model.addAttribute("coupon",coupon);
		}	
	
		return "/user/Deliverydetail";
	}
}
