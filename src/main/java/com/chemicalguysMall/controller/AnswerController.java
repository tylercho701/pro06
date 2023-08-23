package com.chemicalguysMall.controller;

import com.chemicalguysMall.dto.AnswerDto;
import com.chemicalguysMall.entity.Answer;
import com.chemicalguysMall.entity.Question;
import com.chemicalguysMall.entity.Member;
import com.chemicalguysMall.service.AnswerService;
import com.chemicalguysMall.service.QuestionService;
import com.chemicalguysMall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final MemberService memberService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Long id, 
							   @Valid AnswerDto answerDto,
							   BindingResult result, Model model,
							   Principal principal) {
		
		Question question = questionService.getQuestion(id);
		Member member = memberService.getMember(principal.getName());
		
		if(result.hasErrors()) {
			model.addAttribute("question", question);
			return "qna/question_detail";
		}
		
		Answer answer = answerService.createAnswer(question, answerDto.getContent(), member);
		
		return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(@PathVariable("id") Long id, AnswerDto answerDto, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		
		if(!answer.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		
		answerDto.setContent(answer.getContent());
		
		return "qna/answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@PathVariable("id") Long id, 
							   @Valid AnswerDto answerDto, BindingResult result,
							   Principal principal) {
		
		if(result.hasErrors()) {
			return "qna/answer_form";
		}
		
		Answer answer = answerService.getAnswer(id);
		if(!answer.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		
		answerService.modify(answer, answerDto.getContent());
		
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(@PathVariable("id") Long id,
							   Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		if(!answer.getMember().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		
		answerService.delete(answer);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	//	추천 저장
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(@PathVariable("id") Long id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		Member member = memberService.getMember(principal.getName());
		answerService.vote(answer, member);
		
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
		
	}
	
}
