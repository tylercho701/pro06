package com.chemicalguysMall.controller;

import com.chemicalguysMall.dto.CommentDto;
import com.chemicalguysMall.entity.Answer;
import com.chemicalguysMall.entity.Comment;
import com.chemicalguysMall.entity.Question;
import com.chemicalguysMall.entity.Member;
import com.chemicalguysMall.service.AnswerService;
import com.chemicalguysMall.service.CommentService;
import com.chemicalguysMall.service.QuestionService;
import com.chemicalguysMall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
	
	private final CommentService commentService;
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final MemberService memberService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create/question/{id}")
	public String createComment(@PathVariable("id") Long id,
								CommentDto commentDto) {
		return "qna/comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/question/{id}")
	public String createComment(@PathVariable("id") Long id,
								@Valid CommentDto commentFormDto,
								BindingResult result, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		Member users = memberService.getMember(principal.getName());
		
		if(result.hasErrors()) {
			return "qna/comment_form";
		}
		
		Comment c = commentService.create(question, users, commentFormDto.getContent());
		
		return String.format("redirect:/question/detail/%s", c.getQuestionId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create/answer/{id}")
	public String createCommentA(@PathVariable("id") Long id,
								CommentDto commentDto) {
		return "qna/comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/answer/{id}")
	public String createCommentA(@PathVariable("id") Long id,
								 @Valid CommentDto commentDto,
								 BindingResult result, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		Member member = memberService.getMember(principal.getName());
		
		if(result.hasErrors()) {
			return "qna/comment_form";
		}
		
		Comment c = commentService.create(answer, member, commentDto.getContent());
		
		return String.format("redirect:/question/detail/%s#answer_%s", c.getQuestionId(), c.getAnswer().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyComment(@PathVariable("id") Long id,
								CommentDto commentDto,
								Principal principal) {
		
		Optional<Comment> comment = commentService.getComment(id);
		
		if(comment.isPresent()) {
			Comment c = comment.get();
			if(!c.getMember().getEmail().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한 없음");
			}
			commentDto.setContent(c.getContent());
		}
		return "qna/comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyComment(@PathVariable("id") Long id,
								@Valid CommentDto commentDto,
								BindingResult result, Principal principal) {
		
		if(result.hasErrors()) {
			return "qna/comment_form";
		}
		
		Optional<Comment> comment = commentService.getComment(id);
		
		if(comment.isPresent()) {
			
			Comment c = comment.get();
			
			if(!c.getMember().getEmail().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한 없음");
			}
			c = commentService.modify(c, commentDto.getContent());

			if(c.getAnswer() != null) {
				return String.format("redirect:/question/detail/%s#answer_%s", c.getQuestionId(), c.getAnswer().getId());
			}
			
			return String.format("redirect:/question/detail/%s", c.getQuestionId());
			
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한 없음");
		}	
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteComment(@PathVariable("id") Long id,
								Principal principal) {
		
		Optional<Comment> comment = commentService.getComment(id);
		
		if(comment.isPresent()) {
			Comment c = comment.get();
			if(!c.getMember().getEmail().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한 없음");
			}
			
			commentService.delete(c);
			
			return String.format("redirect:/question/detail/%s", c.getQuestionId());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "권한 없음");
		}	
	}
	
	
}
