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

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.mapper.ProductOptionMapper;
import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.vo.ProductOptionVO;
import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.QnAVO;
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
	
    @GetMapping("/detail")
    public String detail(Model model, HttpSession session) {
    	String productlabel = (String) session.getAttribute("productlabel");
    	
    	List<ProductVO> productlist = ProductMapper.findByName(productlabel);
    	model.addAttribute("productlist", productlist);
    	
    	for (ProductVO product : productlist) {
    		model.addAttribute("productdetailpath", product.getP_detailpath());
    	}
    	List<ProductOptionVO> productoptionlist = ProductOptionMapper.findByoptionName(productlabel);
    	model.addAttribute("productoptionlist", productoptionlist);
    	
    	List<QnAVO> QnAlist = QnAMapper.qnalist();
    	model.addAttribute("QnAlist", QnAlist);
    	
        return "/user/ShopDetail";
    }
    @PostMapping(value = "/detailprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String detailprocess(@RequestBody Map<String, String> userData, HttpSession session) {
    	String productlabel = userData.get("productlabel");
    	String heartImgSrc = userData.get("heartImgSrc");
    	session.setAttribute("productlabel", productlabel);
    	session.setAttribute("heartImgSrc", heartImgSrc);
    	List<ProductPathVO> pathlist = ProductPathMapper.findBypathName(productlabel);
    	List<String> productpathlist = new ArrayList<>();
    	for (ProductPathVO path : pathlist) {
    		productpathlist.add(path.getPp_path());
    	}
    	session.setAttribute("productpathlist", productpathlist);

        return "/user/ShopDetail";
    }
    
    //상품문의 로그인 여부
    @PostMapping(value = "/ProductQnA", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> ProductQnA(@RequestBody Map<String, String> userData, HttpSession session) {
    	String redirectUrl = userData.get("redirectUrl");
    	System.out.println("redirectUrl: "+redirectUrl);
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
    	qna.q_id = user.m_id;
    	qna.q_title = infodivlabel;
    	qna.q_content = QnAcontentBox;
    	QnAMapper.qnainsert(qna);

    	return "/user/ShopDetail";
    }
}