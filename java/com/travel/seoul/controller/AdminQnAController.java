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

import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.QnAVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminQnAController {
	
	@Setter(onMethod_=@Autowired) 
	private UserService service;

	@Autowired
    private QnAMapper QnAMapper;

	
	@GetMapping("/QnA")
	public String QnA(Model model) {
		List<QnAVO> qnalist = QnAMapper.qnalist();
		
		List<UserVO> qnauser = new ArrayList<>();
		for (QnAVO mnum : qnalist) {
			qnauser.add(service.getByNum(mnum.getM_num()));
		}
		model.addAttribute("qnauser", qnauser);
		model.addAttribute("qnalist", qnalist);
		return "/admin/qnaList";
	}
	
	//문의 답변작성
	@GetMapping("/QnAUpdate")
	public String QnAUpdate(@RequestParam("num") String numStr, Model model, HttpSession session) {
		long num = Long.parseLong(numStr);
		session.setAttribute("q_num", num);
		QnAVO qnaclicklist = QnAMapper.getQnaByNum(num);
		model.addAttribute("qnaclicklist", qnaclicklist);
	
		model.addAttribute("qnauser",service.getByNum(qnaclicklist.getM_num()).getM_id());
		//답변일, 답변내용 불러옴
		model.addAttribute("qnarevisiondate", qnaclicklist.getQ_revisiondate());
		model.addAttribute("qnaanswer", qnaclicklist.getQ_answer());

		return "/admin/qnaupdate";
	}
	@PostMapping(value = "/QnAUpdateprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, String>> adminupdate(@RequestBody Map<String, String> userData, HttpSession session, Model model) {
        String textbox = userData.get("textbox");
        Long q_num = (Long) session.getAttribute("q_num");
        
        UserVO user = (UserVO) session.getAttribute("loginMember");
        
        QnAVO qnaupdate = QnAMapper.getQnaByNum(q_num);
        qnaupdate.setA_num(user.m_num);
        qnaupdate.setQ_answer(textbox);
        QnAMapper.QnAUpdate(qnaupdate);
        
		Map<String, String> response = new HashMap<>();
	    response.put("message", "답변 완료하였습니다");
	    System.out.println("관리자 답변완료");
	    return ResponseEntity.ok(response);
	}
	
	//검색
	@GetMapping("/qnasearch")
	public String search(@RequestParam("topic") String topic, @RequestParam("keyword") String keyword, HttpSession session) {
		List<QnAVO> qnalist = QnAMapper.qnalist();
		List<QnAVO> searchList = new ArrayList<>();
		
		//키워드가 해당 주제에 포함되어있으면 List에 추가
		for (QnAVO qna:qnalist) {
			switch (topic) {
				case "title":
	                if (keyword.equals(qna.getQ_title())) {
	                	searchList.add(qna);
	                }
	                break;
				case "id":
					if (keyword.equals(service.getByNum(qna.getM_num()).getM_id())) {
						searchList.add(qna);
					}
					break;
				 case "date":
					if (keyword.equals(qna.getQ_date())) {
						searchList.add(qna);
					}
					break;
				 case "status":
					if (keyword.equals(qna.getQ_answerstatus())) {
						searchList.add(qna);
					}
					break;
				 default:
		                System.out.println("결과없음");
		            break;
			}
		}
		session.setAttribute("qna_searchList", searchList);
		
		return "admin/qnaList";
	}
}
