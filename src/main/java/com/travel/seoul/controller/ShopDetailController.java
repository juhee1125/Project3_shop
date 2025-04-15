package com.travel.seoul.controller;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.mapper.ProductOptionMapper;
import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.mapper.UserMapper;
import com.travel.seoul.mapper.ReviewMapper;
import com.travel.seoul.mapper.ReviewPathMapper;
import com.travel.seoul.mapper.CouponMapper;
import com.travel.seoul.mapper.CouponDownloadMapper;
import com.travel.seoul.vo.CouponDownloadVO;
import com.travel.seoul.vo.CouponVO;
import com.travel.seoul.vo.ProductOptionVO;
import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.QnAVO;
import com.travel.seoul.vo.ReviewPathVO;
import com.travel.seoul.vo.ReviewVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/detail/*")
public class ShopDetailController {
	
	@Setter(onMethod_=@Autowired) 
	private ProductMapper ProductMapper;
	
	@Autowired
	private ProductPathMapper ProductPathMapper;
	@Autowired
	private ProductOptionMapper ProductOptionMapper;
	@Autowired
	private QnAMapper QnAMapper;
	@Autowired
	private UserMapper Mapper;
	@Autowired
	private ReviewMapper ReviewMapper;
	@Autowired
	private ReviewPathMapper ReviewPathMapper;
	@Autowired
	private CouponMapper CouponMapper;
	@Autowired
	private CouponDownloadMapper CouponDownloadMapper;
	
	@PostMapping("/saveHeartImg")
	public ResponseEntity<?> saveHeartImg(HttpSession session, @RequestParam("heartImgSrc") String heartImgSrc) {
	    session.setAttribute("heartImgSrc", heartImgSrc);
	    return ResponseEntity.ok().build();
	}
    @GetMapping("/detail")
    public String detail(Model model, HttpSession session, @RequestParam("num") String numStr) {
    	long num = Long.parseLong(numStr);
    	session.setAttribute("shopdetailPnum", num);
    	
    	String heartImgSrc = (String) session.getAttribute("heartImgSrc");
    	System.out.println("heartImgSrc: "+heartImgSrc);
    	if(heartImgSrc == null) {
    		session.setAttribute("heartImgSrc", "/resources/img/icon/채운찜.png/");
    	}else {
    		session.setAttribute("heartImgSrc", heartImgSrc);
    	}
    	
    	ProductVO productlist = ProductMapper.getProductByNum(num);
    	model.addAttribute("productlist", productlist);
    	
    	model.addAttribute("productdetailpath", productlist.getP_detailpath());

    	List<Long> po_num_list = ProductOptionMapper.findOptionByPONum(num);
    	List<ProductOptionVO> productoptionlist = new ArrayList<>();
    	for (Long po_num : po_num_list) {
    		productoptionlist.add(ProductOptionMapper.getOptionByNum(po_num));
    	}
    	model.addAttribute("productoptionlist", productoptionlist);
    	
    	List<QnAVO> QnAlist = QnAMapper.qnalist();
    	model.addAttribute("QnAlist", QnAlist);
    	
    	String p_name = ProductMapper.getProductByNum(num).getP_name();
    	List<Long> q_num_list = QnAMapper.findQnAByQNum(p_name);
    	List<QnAVO> QnAlistdetail = new ArrayList<>();
    	List<UserVO> QnAUser = new ArrayList<>();
    	List<String> QnAAnswer = new ArrayList<>();
    	for (Long q_num : q_num_list) {
    		QnAlistdetail.add(QnAMapper.getQnaByNum(q_num));
    		QnAUser.add(Mapper.getByNum(QnAMapper.getQnaByNum(q_num).getM_num()));
    		if (QnAMapper.getQnaByNum(q_num).getQ_answer()!=null) {
    			QnAAnswer.add(QnAMapper.getQnaByNum(q_num).getQ_answer().replaceAll("\r\n|\r|\n", "\\\\n"));
    		}
    	}
    	model.addAttribute("QnAlistdetail", QnAlistdetail);
    	model.addAttribute("QnAUser", QnAUser);
    	ObjectMapper objectMapper = new ObjectMapper();
    	String QnAAnswerJson = null;
    	try {
    	    // QnAAnswer 리스트를 JSON 문자열로 변환
    	    QnAAnswerJson = objectMapper.writeValueAsString(QnAAnswer);
    	} catch (JsonProcessingException e) {
    	    e.printStackTrace();  // 예외 처리 로직, 필요 시 로그나 다른 방식으로 처리
    	}
    	model.addAttribute("QnAAnswer", QnAAnswerJson);

    	List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(num);
    	List<ProductPathVO> pathlist = new ArrayList<>();
    	for(Long p_num : pp_num_list) {
    		pathlist.add(ProductPathMapper.getPathByNum(p_num));
    	}
    	
    	List<String> productpathlist = new ArrayList<>();
    	for (ProductPathVO path : pathlist) {
    		productpathlist.add(path.getPp_path());
    	}
    	session.setAttribute("productpathlist", productpathlist);
    	
    	// 리뷰
    	List<ReviewVO> reviewlist = ReviewMapper.findReviewByNum(num);
    	List<String> usernamelist = new ArrayList<>();
    	List<String> optionlist = new ArrayList<>();
    	List<List<String>> newreviewpathlist = new ArrayList<>();
    	for (ReviewVO review : reviewlist) {
    		String username = Mapper.getByNum(review.getM_num()).getM_name();
    		usernamelist.add(username);
    	
    		if (review.getPo_num() == null) {
    			optionlist.add("");
    		}
    		else {
    			ProductOptionVO productoption = ProductOptionMapper.getOptionByNum(review.getPo_num());
        		String optionAndDetail = productoption.getPo_option() +" "+ productoption.getPo_optiondetail();
        		optionlist.add(optionAndDetail);
    		}
    		
    		List<ReviewPathVO> reviewpathlist = ReviewPathMapper.findPathByNum(review.getR_num());
    		List<String> imgpath = new ArrayList<>();
    		for (ReviewPathVO reviewpath : reviewpathlist) {
    			imgpath.add(reviewpath.getRp_path());
    		}
    		newreviewpathlist.add(imgpath);
    	}
    	
    	model.addAttribute("usernamelist", usernamelist);
    	model.addAttribute("optionlist", optionlist);
    	model.addAttribute("reviewlist", reviewlist);
    	model.addAttribute("newreviewpathlist", newreviewpathlist);
    	
    	// 쿠폰
    	List<CouponVO> couponlist = CouponMapper.couponlist();
    	for(CouponVO coupon : couponlist) {
    	   	System.out.println("coupon.getP_num(): "+coupon.getP_num());
    	   	System.out.println("num: "+num);
    		if (coupon.getP_num()!=null && coupon.getP_num()==num) {
    			long cnum = CouponMapper.findCNumByPNum(num);
    			CouponVO productcoupon = CouponMapper.getCouponByNum(cnum);
    			
    			model.addAttribute("productcoupon", productcoupon);
    		}   	    	
    	}
    	
        return "/user/ShopDetail";
    }

    
    //상품문의 로그인 여부
    @PostMapping(value = "/ProductQnA", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> ProductQnA(@RequestBody Map<String, String> userData, HttpSession session) {
    	Map<String, String> response = new HashMap<>();
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user==null) {
    		response.put("message", "로그인페이지로 이동");
            return ResponseEntity.ok(response);
    	}
    	response.put("message", "정보입력");
        return ResponseEntity.ok(response);
    }
    //상품문의
    @PostMapping(value = "/ProductQnAprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String ProductQnAprocess(@RequestBody Map<String, String> userData, HttpSession session) {
    	String infodivlabel = userData.get("infodivlabel");
    	String QnAcontentBox = userData.get("QnAcontentBox");
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");

    	QnAVO qna = new QnAVO();
    	qna.setM_num(user.getM_num());
    	qna.setQ_title(infodivlabel);
    	qna.setQ_content(QnAcontentBox);
    	QnAMapper.qnaInsert(qna);
    	System.out.println("QnAcontentBox:"+QnAcontentBox);
    	return "/user/ShopDetail";
    }
    
    
    //쿠폰 다운로드
    @PostMapping(value = "/coupondownload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> coupondownload(@RequestBody Map<String, Long> userData, HttpSession session) {
    	Map<String, String> response = new HashMap<>();
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	long pnum = (long) session.getAttribute("shopdetailPnum");

       	if(user==null) {
       		response.put("message", "로그인페이지로 이동");
            return ResponseEntity.ok(response);
    	}
       	else {
      		long cnum = userData.get("cnum");
       		CouponVO coupon = CouponMapper.getCouponByNum(cnum);
       		List<CouponDownloadVO> coupondownloadlist = CouponDownloadMapper.getCouponByCnum(cnum);

       		if(coupondownloadlist!=null && coupon.getP_num()==pnum) {
       			for(CouponDownloadVO download:coupondownloadlist) {
           			if(download.getM_num()==user.getM_num()) {
           				response.put("message", "이미 다운받은 쿠폰입니다");
           				return ResponseEntity.ok(response);
           			}
           		}
       		}
       		
    		CouponDownloadVO coupondownload = new CouponDownloadVO();
    		coupondownload.setC_num(cnum);
    		coupondownload.setM_num(user.getM_num());
    		CouponDownloadMapper.coupondownloadInsert(coupondownload);
        	
        	response.put("message", "다운완료");
            return ResponseEntity.ok(response);
    	}
    }
}