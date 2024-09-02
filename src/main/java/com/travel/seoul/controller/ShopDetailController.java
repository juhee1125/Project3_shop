package com.travel.seoul.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.mapper.ProductOptionMapper;
import com.travel.seoul.vo.ProductOptionVO;
import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;

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
	
    @GetMapping("/detail")
    public String detail(Model model, HttpSession session) {
    	String productlabel = (String) session.getAttribute("productlabel");
    	String heartImgSrc = (String) session.getAttribute("heartImgSrc");
    	
    	List<ProductVO> productpathlist = (List<ProductVO>) session.getAttribute("productpathlist");
    	
    	List<ProductVO> productlist = ProductMapper.findByName(productlabel);
    	model.addAttribute("productlist", productlist);
    	
    	List<ProductOptionVO> productoptionlist = ProductOptionMapper.findByoptionName(productlabel);
    	System.out.println("productoptionlist: " + productoptionlist);
    	model.addAttribute("productoptionlist", productoptionlist);
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
}