package com.travel.seoul.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductOptionMapper;
import com.travel.seoul.mapper.CouponMapper;
import com.travel.seoul.mapper.CouponUseMapper;
import com.travel.seoul.service.CouponService;
import com.travel.seoul.vo.CouponUseVO;
import com.travel.seoul.vo.CouponVO;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/order/*")
public class OrderController {
	
	@Setter(onMethod_=@Autowired) 
	private OrderMapper OrderMapper;
	
	@Autowired
	private ProductMapper ProductMapper;
	@Autowired
	private ProductOptionMapper ProductOptionMapper;
	@Autowired
	private CouponService CouponService;
	@Autowired
	private CouponMapper CouponMapper;
	@Autowired
	private CouponUseMapper CouponUseMapper;

	//장바구니
    @GetMapping("/shopping")
    public String shopping(HttpSession session, Model model) {
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user==null) {
            return "/user/loginfirst";
    	}
    	
    	List<OrderVO> orderlist = OrderMapper.getOrderByONum(user.m_num);
    	
    	List<String> pnamelist = new ArrayList<>();
    	List<OrderVO> filteredOrderList = new ArrayList<>();
    	int totalPrice = 0;
    	//특정배송상태만 장바구니에 보여지도록
    	for (OrderVO order : orderlist) {
    		if ("장바구니".equals(order.getO_paymentstatus()) || "결제대기".equals(order.getO_paymentstatus())) { 
	            String productname = ProductMapper.getProductByNum(order.getP_num()).getP_name();
	            pnamelist.add(productname);
	            filteredOrderList.add(order);
	            
	            totalPrice += Integer.parseInt(order.getO_totalprice());
	        }
    	}
    	
    	//배송비 적용
    	int o_shippingfee=0;
    	if (totalPrice<30000) {
    		o_shippingfee =5000;
    	} 	
    	model.addAttribute("orderlist",filteredOrderList);
    	session.setAttribute("o_shippingfee",o_shippingfee);
    	session.setAttribute("pnamelist",pnamelist);
  	
    	return "/user/Shopping";
    }
    @PostMapping(value = "/shoppingprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, String>> shoppingprocess(@RequestBody Map<String, Object> userData, HttpSession session) {
    	String productname = (String) userData.get("productname");
        String productimage = (String) userData.get("productimage");
        String productprice = (String) userData.get("productprice");
        String producttotalprice = (String) userData.get("producttotalprice");
        List<Map<String, String>> option = (List<Map<String, String>>) userData.get("option");
        List<String> optionquantity = (List<String>) userData.get("optionquantity");
        List<String> optionnpricelist = (List<String>) userData.get("optionnprice");
 
        UserVO user = (UserVO) session.getAttribute("loginMember");
        Map<String, String> response = new HashMap<>();
    	if(user==null) {
    		response.put("message", "로그인 후 가능합니다");
            return ResponseEntity.ok(response);
    	}
    	
    	OrderVO order = new OrderVO();
    	Long p_num = ProductMapper.findByName(productname);
    	
    	//결제할 상품이 옵션이 없는 단일상품일 때
    	if (option.isEmpty() || option == null) {
    		order.setM_num(user.m_num);
    		order.setP_num(p_num);
    		order.setO_quantity(Integer.parseInt(optionquantity.get(0)));
    		order.setO_price(productprice);
    		order.setO_paymentstatus("장바구니");
    		order.setO_totalprice(producttotalprice);
    		order.setO_option(null);
    		order.setO_optiondetail(null);
    		order.setO_optionprice(null);
    		order.setO_image(productimage);
    		OrderMapper.orderInsert(order);
    	}
    	else {
    		for (int i = 0; i < option.size(); i++) {
                Map<String, String> optionMap = option.get(i);
                String o_option = optionMap.get("po_option");
                String o_optiondetail = optionMap.get("po_optiondetail");
                String o_optionprice = optionnpricelist.get(i);
                
                order.setO_option(o_option);
                order.setO_optiondetail(o_optiondetail);
                order.setO_optionprice(o_optionprice);
                
                order.setM_num(user.m_num);
        		order.setP_num(p_num);
        		order.setO_quantity(Integer.parseInt(optionquantity.get(i)));
        		order.setO_price(productprice);
        		order.setO_paymentstatus("장바구니");
        		order.setO_totalprice(o_optionprice);
        		order.setO_image(productimage);
        		
        		OrderMapper.orderInsert(order);
            }
    	}
    	
    	response.put("message", "장바구니에 담겼습니다");
    	
        return ResponseEntity.ok(response);
    }
    
    //상품삭제
  	@PostMapping(value = "/productdelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<String> productdelete(@RequestBody List<Map<String, String>> userData, HttpSession session) {
  		for (Map<String, String> userlist : userData) {
  			Long ordernum = Long.parseLong(userlist.get("ordernum"));
  			
  		    //관리자 DB에 값 삭제
  			OrderMapper.orderDelete(ordernum);
  		}
  		
  	    return ResponseEntity.ok("상품이 삭제되었습니다");
  	}
  	
  	//옵션변경
  	@PostMapping(value = "/optionmodify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<List<Map<String, String>>> optionmodify(@RequestParam("pname") String pname, HttpSession session) {
		Long p_num = ProductMapper.findByName(pname);
		List<Long> ponumlist=ProductOptionMapper.findOptionByPONum(p_num);
		List<Map<String, String>> optionsList = new ArrayList<>();
		for (Long ponum : ponumlist) {
		    Map<String, String> optionDetails = new HashMap<>();
		    optionDetails.put("option", ProductOptionMapper.getOptionByNum(ponum).getPo_option());
		    optionDetails.put("optiondetail", ProductOptionMapper.getOptionByNum(ponum).getPo_optiondetail());
		    optionsList.add(optionDetails);
		}
		
		return ResponseEntity.ok(optionsList);
  	}
  	@PostMapping(value = "/optionupdate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String optionupdate(@RequestBody Map<String, String> userData, HttpSession session, Model model) {
    	String selectOption = userData.get("selectOption");
    	String selectOptionDetail = userData.get("selectOptionDetail");
    	String ordertotalprice = userData.get("ordertotalprice");
    	Long quantityinput = Long.parseLong(userData.get("quantityinput"));
    	Long ordernum = Long.parseLong(userData.get("ordernum"));

    	OrderVO orderupdate = OrderMapper.getOrderByNum(ordernum);
    	orderupdate.setO_option(selectOption);
    	orderupdate.setO_optiondetail(selectOptionDetail);
    	orderupdate.setO_optionprice(ordertotalprice);
    	orderupdate.setO_quantity(quantityinput);
    	OrderMapper.orderoptionUpdate(orderupdate);
    	
    	return "/user/Shopping";
    }
  	
  	//수량변경
  	@PostMapping(value = "/quantitymodify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String quantitymodify(@RequestBody Map<String, String> userData) {
  		Long ordernum = Long.parseLong(userData.get("ordernum"));
  		Long quantity = Long.parseLong(userData.get("quantity"));
    	String buyprice = userData.get("buyprice");
    	OrderVO orderupdate = OrderMapper.getOrderByNum(ordernum);
    	orderupdate.setO_option(orderupdate.getO_option());
    	orderupdate.setO_optiondetail(orderupdate.getO_optiondetail());
    	
    	if(orderupdate.getO_option()==null) {
    		orderupdate.setO_totalprice(buyprice);
    	}
    	else {
        	orderupdate.setO_optionprice(buyprice);
    	}
    	orderupdate.setO_quantity(quantity);
    	OrderMapper.orderoptionUpdate(orderupdate);
    	
    	return "/user/Shopping";
    }
  	
  	//주문
  	@PostMapping(value = "/OrderCompleted", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<String> OrderCompleted(@RequestBody List<Map<String, String>> userData, HttpSession session) {
  		List<Long> onumlist = new ArrayList<>();
  		for (Map<String, String> userlist : userData) {
  			Long ordernum = Long.parseLong(userlist.get("ordernum"));
  			onumlist.add(ordernum);
  			
  			OrderVO pstatusupdate = OrderMapper.getOrderByNum(ordernum);
  			pstatusupdate.setO_paymentstatus("결제대기");
  			OrderMapper.paymentstatusUpdate(pstatusupdate);
  		}
  		session.setAttribute("onumlist", onumlist);
  		
		return ResponseEntity.ok("주문이 완료되었습니다");
  	}
  	
  	//상품명 클릭
  	@PostMapping(value = "/pagemove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<Long> pagemove(@RequestParam("ordernum") String ordernum) {
		Long pnum = OrderMapper.getOrderByNum(Long.parseLong(ordernum)).getP_num();
		
		return ResponseEntity.ok(pnum);
  	}
  	
  	
  	//결제페이지
  	@GetMapping("/payment")
  	public String payment(HttpSession session, Model model) {
  	    UserVO user = (UserVO) session.getAttribute("loginMember");
  	    int o_shippingfee = (int) session.getAttribute("o_shippingfee");
  	    List<String> pnamelist = (List<String>) session.getAttribute("pnamelist");
  	    List<Long> onumlist = (List<Long>) session.getAttribute("onumlist"); 
  	    List<OrderVO> paymentlist = new ArrayList<>();

  	    //totalprice를 스트림으로 계산하여 effectively final 유지
  	    int totalprice = onumlist.stream()
  	        .mapToInt(onum -> {
  	            OrderVO payment = OrderMapper.getOrderByNum(onum);
  	            paymentlist.add(payment);
				if (payment.getO_option() == null) {
					System.out.println("payment.getO_totalprice(): "+payment.getO_totalprice());
				    return Integer.parseInt(payment.getO_totalprice());
				} else {
					System.out.println("payment.getO_optionprice(): "+payment.getO_optionprice());
				    return Integer.parseInt(payment.getO_optionprice());
				}
  	        })
  	        .sum();

  	    model.addAttribute("paymentlist", paymentlist);
  	    model.addAttribute("totalprice", totalprice+o_shippingfee);

  	    //보유 쿠폰 리스트
  	    List<CouponVO> usercouponlist = CouponService.PossessionCoupon(user.getM_num());
  	    List<CouponVO> availablecouponlist = new ArrayList<>();
		
  	    for(CouponVO usercoupon : usercouponlist) {
  	    	String discountType = usercoupon.getC_discount_setting();
    		String couponType = usercoupon.getC_type();   
    		if(discountType.equals("정률할인")) {
    			if(couponType.equals("상품") && pnamelist.contains(ProductMapper.getProductByNum(usercoupon.getP_num()).getP_name())) {
    				availablecouponlist.add(usercoupon);
    			}
    			else if(couponType.equals("고객")) {
    				availablecouponlist.add(usercoupon);
    			}
    		}
    		else {
    			if(couponType.equals("상품") && pnamelist.contains(ProductMapper.getProductByNum(usercoupon.getP_num()).getP_name())) {
    				int productprice = 0;
    				Long couponPnum = usercoupon.getP_num();
    				
    				for(Long onum : onumlist) {
    					OrderVO order = OrderMapper.getOrderByNum(onum);
    					//해당 쿠폰의 상품과 일치할 때만 합산
    					if(order.getP_num()==couponPnum) {  
    	                    int o_price = Integer.parseInt(order.getO_price());
    	                    productprice += o_price * order.getO_quantity();
    	                }
    				}
    				if(productprice>=Integer.parseInt(usercoupon.getC_price())) {
    					availablecouponlist.add(usercoupon);
    				}
    			}
    			else if(couponType.equals("고객")) {
    				availablecouponlist.add(usercoupon);
    			}
    		}
  	    }
 
  	    model.addAttribute("availablecouponlist", availablecouponlist);

  	    return "/user/Payment";
  	}
  	@PostMapping(value = "/PaymentCompleted", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<String> PaymentCompleted(@RequestBody Map<String, Object> userData) {
  		String cnum = (String) userData.get("cnum");
  		String couponapply = (String)userData.get("couponapply");
  	    List<String> onumlist = (List<String>) userData.get("onumlist"); 	    
 	    String ordernumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
  	   
 	    //쿠폰적용 안했을 때
 	    if(!cnum.equals("couponnone")) {
 	  	    CouponUseVO couponuse = new CouponUseVO();
 	  	    couponuse.setC_num(Long.parseLong(cnum));
 	  	    couponuse.setCu_apply_price(Long.parseLong(couponapply));
 	    	CouponUseMapper.couponuseInsert(couponuse);
 	    	
 	    	Long cunum = CouponUseMapper.findCUNumByCNum(Long.parseLong(cnum));
 	    	
 	 	    for(String onum:onumlist) {
 	  	    	OrderVO pstatusupdate = OrderMapper.getOrderByNum(Long.parseLong(onum));
 				pstatusupdate.setO_paymentstatus("결제완료");
 				pstatusupdate.setO_number(ordernumber);
 				pstatusupdate.setCu_num(cunum);
 				
 				OrderMapper.paymentstatusUpdate(pstatusupdate);
 	  	    }
 	 	    
 	    	CouponMapper.couponuseYNUpdate(Long.parseLong(cnum));
  	    }
 	    else {
 	    	for(String onum:onumlist) {
 	  	    	OrderVO pstatusupdate = OrderMapper.getOrderByNum(Long.parseLong(onum));
 				pstatusupdate.setO_paymentstatus("결제완료");
 				pstatusupdate.setO_number(ordernumber);
 				
 				OrderMapper.paymentstatusUpdate(pstatusupdate);
 	  	    }
 	    }
 	    
		return ResponseEntity.ok("결제가 완료되었습니다");
  	}
}