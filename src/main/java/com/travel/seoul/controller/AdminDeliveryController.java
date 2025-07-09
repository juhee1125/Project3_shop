package com.travel.seoul.controller;

import java.util.ArrayList;
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

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.UserMapper;
import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminDeliveryController {
	
	@Setter(onMethod_=@Autowired) 
	private OrderMapper OrderMapper;

	@Autowired
	private UserMapper UserMapper;
	@Autowired
    private ProductMapper ProductMapper;

	//배송관리
	@GetMapping("/delivery")
	public String delivery(Model model) {
		List<OrderVO> orderlist = OrderMapper.orderlist();
		model.addAttribute("orderlist", orderlist);
		
		List<String> mnamelist = new ArrayList<>();
		for (OrderVO order: orderlist) {
			UserVO list = UserMapper.getByNum(order.getM_num());
			mnamelist.add(list.getM_name());
		}
		model.addAttribute("mnamelist", mnamelist);
		
		List<String> pnamelist = new ArrayList<>();
		for (OrderVO order: orderlist) {
			ProductVO plist = ProductMapper.getProductByNum(order.getP_num());
			pnamelist.add(plist.getP_name());
		}
		model.addAttribute("pnamelist", pnamelist);
		
		return "/admin/delivery";
	}
	
	//결제대기
	@PostMapping(value = "/paymentwaitcount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> paymentwaitcount(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			OrderVO paymentupdate = OrderMapper.getOrderByNum(num);
			paymentupdate.setO_paymentstatus("결제대기");
			OrderMapper.paymentstatusUpdate(paymentupdate);
		}
	    return ResponseEntity.ok("상품 결제대기");
	}
	//결제완료
	@PostMapping(value = "/paymentcompletcount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> paymentcompletcount(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			OrderVO paymentupdate = OrderMapper.getOrderByNum(num);
			paymentupdate.setO_paymentstatus("결제완료");
			OrderMapper.paymentstatusUpdate(paymentupdate);
		}
		return ResponseEntity.ok("상품 결제완료");
	}
	//배송준비중
	@PostMapping(value = "/preparedeliverycount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> preparedeliverycount(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			OrderVO paymentupdate = OrderMapper.getOrderByNum(num);
			paymentupdate.setO_paymentstatus("배송준비중");
			OrderMapper.paymentstatusUpdate(paymentupdate);
		}
		return ResponseEntity.ok("상품 배송준비중");
	}
	//배송중
	@PostMapping(value = "/deliverycount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> deliverycount(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			OrderVO paymentupdate = OrderMapper.getOrderByNum(num);
			paymentupdate.setO_paymentstatus("배송중");
			OrderMapper.paymentstatusUpdate(paymentupdate);
		}
		return ResponseEntity.ok("상품 배송중");
	}
	//배송완료
	@PostMapping(value = "/deliverycompletcount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> deliverycompletcount(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			OrderVO paymentupdate = OrderMapper.getOrderByNum(num);
			paymentupdate.setO_paymentstatus("배송완료");
			OrderMapper.paymentstatusUpdate(paymentupdate);
		}
		return ResponseEntity.ok("상품 배송완료");
	}
	
	
	//검색
	@GetMapping("/deliverysearch")
	public String search(@RequestParam("topic") String topic, @RequestParam("keyword") String keyword, HttpSession session) {
		List<OrderVO> orderlist = OrderMapper.orderlist();
		List<OrderVO> searchList = new ArrayList<>();
		
		for (OrderVO user:orderlist) {
			switch (topic) {
				case "paymentstatus":
	                if (keyword.equals(user.getO_paymentstatus())) {
	                	searchList.add(user);
	                }
	                break;
				 case "name":
					if (keyword.equals(UserMapper.getByNum(user.getM_num()).getM_name())) {
						searchList.add(user);
					}
					break;
				 case "productname":
					 if (keyword.equals(ProductMapper.getProductByNum(user.getP_num()).getP_name())) {
						 searchList.add(user);
					 }
					 break;
				 default:
		                System.out.println("결과없음");
		            break;
			}
		}
		session.setAttribute("order_searchList", searchList);
		return "admin/delivery";
	}
}
