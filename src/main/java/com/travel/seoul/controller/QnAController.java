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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.service.DeliveryService;
import com.travel.seoul.vo.QnAVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/qna/*")
public class QnAController {
	
	@Setter(onMethod_=@Autowired) 
	private OrderMapper OrderMapper;

	@Autowired
    private QnAMapper QnAMapper;
	@Autowired
	private ProductMapper ProductMapper;
	@Autowired
	private ProductPathMapper ProductPathMapper;
	@Autowired
	private DeliveryService DeliveryService;

	//마이페이지(상품문의내역)
	@GetMapping("/qnainquiry")
	public String delivery() {
		
		return "/user/QnA";
	}
	
	//조회
	@GetMapping("/qnainquirysearch")
	public ResponseEntity<HashMap<String, List>> qnainquirysearch(@RequestParam("date") String date, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
		
		LocalDate now = LocalDate.now();
		String[] array = date.split("개월");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -Integer.parseInt(array[0]));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(cal.getTime());
		
		List<QnAVO> datesearchlist = DeliveryService.Productqnainquiry(startDate, now.toString());	
		
		List<QnAVO> qnalist = new ArrayList<>();
		HashMap<String, List> newdatesearchlist = new HashMap<String, List>();
		List<String> pathlist = new ArrayList<>();
		for(QnAVO qna : datesearchlist) {
			if (qna.getM_num()== user.getM_num()) {
				QnAVO qnavo = QnAMapper.getQnaByNum(qna.getQ_num());
				qnalist.add(qnavo);
				
				Long pnum = ProductMapper.findByName(qna.getQ_title());
				String path = ProductPathMapper.getPathByPath(pnum).get(0);
				pathlist.add(path);
			}
		}
		newdatesearchlist.put("qnalist", qnalist);
		newdatesearchlist.put("pathlist", pathlist);
		
		return ResponseEntity.ok(newdatesearchlist);
	}
	//직접 조회
	@GetMapping("/directqnainquirysearch")
	public ResponseEntity<HashMap<String, List>> directqnainquirysearch(@RequestParam("startDateInput") String startDateInput, @RequestParam("endDateInput") String endDateInput, HttpSession session) {	
		UserVO user = (UserVO) session.getAttribute("loginMember");
		
		List<QnAVO> datesearchlist = DeliveryService.Productqnainquiry(startDateInput, endDateInput);
		
		List<QnAVO> qnalist = new ArrayList<>();
		HashMap<String, List> newdatesearchlist = new HashMap<String, List>();
		List<String> pathlist = new ArrayList<>();
		for(QnAVO qna : datesearchlist) {
			if (qna.getM_num()== user.getM_num()) {
				qnalist.add(qna);
				
				Long pnum = ProductMapper.findByName(qna.getQ_title());
				String path = ProductPathMapper.getPathByPath(pnum).get(0);
				pathlist.add(path);
			}
		}
		newdatesearchlist.put("qnalist", qnalist);
		newdatesearchlist.put("pathlist", pathlist);
		
		return ResponseEntity.ok(newdatesearchlist);
	}
}
