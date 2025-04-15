package com.travel.seoul.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.travel.seoul.mapper.ReviewMapper;
import com.travel.seoul.mapper.ReviewPathMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductOptionMapper;
import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.vo.ReviewPathVO;
import com.travel.seoul.vo.ReviewVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/review/*")
public class ReviewController {
	
	@Setter(onMethod_=@Autowired) 
	private OrderMapper OrderMapper;

	@Autowired
    private ReviewMapper ReviewMapper;
	@Autowired
	private ReviewPathMapper ReviewPathMapper;
	@Autowired
	private ProductMapper ProductMapper;
	@Autowired
	private ProductOptionMapper ProductOptionMapper;

	
	@GetMapping("/review")
	public String delivery(HttpSession session, Model model) {
	    UserVO user = (UserVO) session.getAttribute("loginMember");
	    List<OrderVO> orderlist = OrderMapper.getOrderByONum(user.getM_num());

	    List<OrderVO> filteredOrderList = new ArrayList<>(); 
	    List<String> productnamelist = new ArrayList<>();

	    for (OrderVO order : orderlist) {
	        if (!"장바구니".equals(order.getO_paymentstatus()) && !"결제대기".equals(order.getO_paymentstatus())  && !"리뷰작성".equals(order.getO_reviewstatus())) { 
	            String productname = ProductMapper.getProductByNum(order.getP_num()).getP_name();
	            productnamelist.add(productname);
	            filteredOrderList.add(order);
	        }
	    }
	    model.addAttribute("orderlist", filteredOrderList);
	    model.addAttribute("productnamelist", productnamelist);

	    return "/user/Review";
	}

	//리뷰등록
	private static final String UPLOAD_DIR = "D://upload"; 
	@PostMapping(value = "/reviewprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String reviewprocess(@RequestParam("onum") Long onum, @RequestParam("file") List<MultipartFile> files, @RequestParam("countstar") Long countstar, @RequestParam("QnAcontentBox") String QnAcontentBox,
			@RequestParam("productnamelabel") String productnamelabel, @RequestParam(value="productoption", required = false) String productoption, @RequestParam(value="productdetail", required = false) String productdetail, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
		Long pnum = ProductMapper.findByName(productnamelabel);

		ReviewVO review = new ReviewVO();
		review.setM_num(user.getM_num());
		review.setP_num(pnum);
		review.setR_countstar(countstar);
		review.setR_reviewcontent(QnAcontentBox);
		if (productoption != null) {
			Long ponum = ProductOptionMapper.findOptionAndDetail(pnum, productoption, productdetail);
			review.setPo_num(ponum);
		}
		else {
			review.setPo_num(null);
		}
		ReviewMapper.reviewInsert(review);
		
		OrderVO orderupdate = OrderMapper.getOrderByNum(onum);
		orderupdate.setO_reviewstatus("리뷰작성");
		OrderMapper.paymentstatusUpdate(orderupdate);
		
		try {
			ReviewPathVO reviewpath = new ReviewPathVO();
			Long rnum = review.getR_num();
			System.out.println("rnum"+rnum);
			for (MultipartFile file : files) {
				String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
		        Path uploadPath = Paths.get(UPLOAD_DIR);
		        Path filePath = uploadPath.resolve(uniqueFileName);
		        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		        reviewpath.setR_num(rnum);
		        reviewpath.setRp_path("/display/"+uniqueFileName);
		        ReviewPathMapper.reviewpathInsert(reviewpath);
			};
		} catch (IOException e) {
        	e.printStackTrace();
        }
		return "/user/Review";
	}
}
