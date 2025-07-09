package com.travel.seoul.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.mapper.LikeMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/my/*")
public class MypageController {
	
	@Setter(onMethod_=@Autowired) 
	private OrderMapper OrderMapper;
	
	@Autowired
	private LikeMapper LikeMapper;
	@Autowired
	private ProductMapper ProductMapper;
	@Autowired
	private ProductPathMapper ProductPathMapper;
	@Autowired
	private QnAMapper QnAMapper;
	
	//마이페이지
    @GetMapping("/my")
    public String my(HttpSession session, Model model) {
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user==null) {
    		return "/user/loginfirst";
    	}
    	
    	//배송조회
    	int paymentwaitcount = 0;
    	int paymentcompletcount = 0;
    	int preparedeliverycount = 0;
    	int deliverycount = 0;
    	int deliverycompletcount = 0;
    	List<OrderVO> orderlist = OrderMapper.getOrderByONum(user.getM_num());
    	for (OrderVO order : orderlist) {
    		if (order.getO_paymentstatus().equals("결제대기")) {
    			paymentwaitcount += 1;
    		}
    		if (order.getO_paymentstatus().equals("결제완료")) {
    			paymentcompletcount += 1;
    			
    			if (order.getO_paymentstatus().equals("배송준비중")) {
    				preparedeliverycount += 1;
        		}
    			if (order.getO_paymentstatus().equals("배송중")) {
    				deliverycount += 1;
    			}
    			if (order.getO_paymentstatus().equals("배송완료")) {
    				deliverycompletcount += 1;
    			}
    		}
    	}
    	session.setAttribute("paymentwaitcount",paymentwaitcount);
    	session.setAttribute("paymentcompletcount",paymentcompletcount);
    	session.setAttribute("preparedeliverycount",preparedeliverycount);
    	session.setAttribute("deliverycount",deliverycount);
    	session.setAttribute("deliverycompletcount",deliverycompletcount);

    	//찜한 상품
    	List<Long> lnumlist = LikeMapper.findLikeByLNum(user.getM_num());
    	List<Long> pnumlist = new ArrayList<>();
    	List<String> pnamelist = new ArrayList<>();
    	List<String> pdiscountlist = new ArrayList<>();
    	List<String> ppricelist = new ArrayList<>();
    	List<String> pppathlist = new ArrayList<>();
    	for(Long lnum : lnumlist) {
    		Long pnum = LikeMapper.getLikeByNum(lnum).getP_num();
    		pnumlist.add(pnum);
    		
    		pnamelist.add(ProductMapper.getProductByNum(pnum).getP_name());
    		pdiscountlist.add(ProductMapper.getProductByNum(pnum).getP_discount());
    		ppricelist.add(ProductMapper.getProductByNum(pnum).getP_price());
    		
    		List<String> pathlist = ProductPathMapper.getPathByPath(pnum);
			pppathlist.add(pathlist.get(0));    		
    	}
    	model.addAttribute("pnumlist",pnumlist);
    	model.addAttribute("pnamelist",pnamelist);
    	model.addAttribute("pdiscountlist",pdiscountlist);
    	model.addAttribute("ppricelist",ppricelist);
    	model.addAttribute("pppathlist",pppathlist);
    	
    	//상품문의
    	List<Long> qnumlist = QnAMapper.findMNumByQNum(user.getM_num());
    	List<String> qcontentlist = new ArrayList<>();
    	List<String> qstatuslist = new ArrayList<>();
    	for (Long qnum : qnumlist) {
    		qcontentlist.add(QnAMapper.getQnaByNum(qnum).getQ_content());
    		qstatuslist.add(QnAMapper.getQnaByNum(qnum).getQ_answerstatus());
    	}
    	model.addAttribute("qcontentlist",qcontentlist);
    	model.addAttribute("qstatuslist",qstatuslist);
    	
    	return "/user/My";
    }
}