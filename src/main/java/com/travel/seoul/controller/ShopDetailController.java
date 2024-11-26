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
	@Autowired
	private UserMapper Mapper;
	
	@PostMapping("/saveHeartImg")
	public ResponseEntity<?> saveHeartImg(HttpSession session, @RequestParam("heartImgSrc") String heartImgSrc) {
	    session.setAttribute("heartImgSrc", heartImgSrc);
	    return ResponseEntity.ok().build();
	}
    @GetMapping("/detail")
    public String detail(Model model, HttpSession session, @RequestParam("num") String numStr) {
    	long num = Long.parseLong(numStr);
    	
    	String heartImgSrc = (String) session.getAttribute("heartImgSrc");
    	session.setAttribute("heartImgSrc", heartImgSrc);
    	
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
    	
    	return "/user/ShopDetail";
    }
}