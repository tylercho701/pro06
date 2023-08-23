package com.chemicalguysMall.controller;

import com.chemicalguysMall.dto.AnswerDto;
import com.chemicalguysMall.dto.QuestionDto;
import com.chemicalguysMall.entity.Question;
import com.chemicalguysMall.entity.Member;
import com.chemicalguysMall.service.QuestionService;
import com.chemicalguysMall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	private final MemberService memberService;
	
	@GetMapping("/list")
	public String list(@RequestParam(value="page", defaultValue="0") int page,
					   @RequestParam(value="keyword", defaultValue="") String keyword,
					   Model model) {
		
		//	질문 글 전체 조회
		Page<Question> paging = questionService.getList(page, keyword);
		
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		
		return "qna/question_list";

	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model,
						 AnswerDto answerDto) {
		
		Question question = questionService.getQuestion(id);
		
		model.addAttribute("question", question);
		
		return "qna/question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String createQuestion(QuestionDto questionDto) {
		
		return "qna/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createQuestion(@Valid QuestionDto questionDto,
								 BindingResult result, Principal principal) {
		
		Member member = memberService.getMember(principal.getName());
		
		if(result.hasErrors()) {
			return "qna/question_form";
		}
		
		//	질문 DB에 저장
		questionService.saveQuestion(questionDto.getSubject(), questionDto.getContent(), member);
		
		return "redirect:/question/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionDto questionDto, @PathVariable("id") Long id,
								 Principal principal) {
		
		Question question = questionService.getQuestion(id);
		if(!question.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		
		questionDto.setSubject(question.getSubject());
		questionDto.setContent(question.getContent());
		
		return "qna/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionDto questionDto,
								 @PathVariable("id") Long id, Principal principal,
								 BindingResult result) {
		
		if(result.hasErrors()) {
			return "qna/question_form";
		}
		
		Question question = questionService.getQuestion(id);
		if(!question.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		
		questionService.modify(question, questionDto.getSubject(), questionDto.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Long id,
								 Principal principal) {
		
		Question question = questionService.getQuestion(id);
		if(!question.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		
		questionService.delete(question);

		return "redirect:/";
	}
	
	//	추천 저장
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(@PathVariable("id") Long id, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		Member member = memberService.getMember(principal.getName());
		questionService.vote(question, member);
		
		return String.format("redirect:/question/detail/%s", id);
		
	}

}