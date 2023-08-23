package com.chemicalguysMall.service;

import com.chemicalguysMall.entity.Answer;
import com.chemicalguysMall.entity.Comment;
import com.chemicalguysMall.entity.Member;
import com.chemicalguysMall.entity.Question;
import com.chemicalguysMall.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public Comment create(Question question, Member member, String content) {
		
		Comment c = new Comment();
		c.setContent(content);
		c.setQuestion(question);
		c.setMember(member);
		c = commentRepository.save(c);
				
		return c;
	}
	
	public Comment create(Answer answer, Member member, String content) {
		
		Comment c = new Comment();
		c.setContent(content);
		c.setAnswer(answer);
		c.setMember(member);
		c = commentRepository.save(c);
				
		return c;
	}
	
	public Optional<Comment> getComment(Long id){
		return commentRepository.findById(id);
	}
	
	public Comment modify(Comment c, String content) {
		c.setContent(content);
		c = commentRepository.save(c);
		
		return c;
	}
	
	public void delete(Comment c) {
		commentRepository.delete(c);
	}
	
	
}