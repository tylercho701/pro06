package com.chemicalguysMall.service;

import com.chemicalguysMall.entity.Answer;
import com.chemicalguysMall.entity.Member;
import com.chemicalguysMall.entity.Question;
import com.chemicalguysMall.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public Answer createAnswer(Question question, String content, Member member) {
		
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setQuestion(question);
		answer.setMember(member);
		
		Answer a1 = answerRepository.save(answer);
		
		return a1;
	}
	
	//	답글 조회
	public Answer getAnswer(Long id) {
		
		Answer answer = answerRepository.findById(id)
										.orElseThrow(() -> new EntityNotFoundException("답글이 없습니다."));
									//	.orElseThrow(EntityNotFoundException::new);
		
		return answer;
	}
	
	//	답글 수정
	public void modify(Answer answer, String content) {
		
		answer.setContent(content);
		answerRepository.save(answer);
		
	}
	
	//	답글 삭제
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}
	
	//	추천 저장
	public void vote(Answer answer, Member member) {
		answer.getVoter().add(member);
		answerRepository.save(answer);
	}
	
}
