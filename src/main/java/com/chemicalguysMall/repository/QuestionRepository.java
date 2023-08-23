package com.chemicalguysMall.repository;

import com.chemicalguysMall.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	@Query("select "
			+ "distinct q "
			+ "from Question q "
			+ "left outer join Member u1 on q.member = u1 "
			+ "left outer join Answer a on a.question = q "
			+ "left outer join Member u2 on a.member = u2 "
			+ "where q.subject like %:keyword% "
			+ "or q.content like %:keyword% "
			+ "or u1.name like %:keyword% "
			+ "or a.content like %:keyword% "
			+ "or u2.name like %:keyword%")
	Page<Question> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
}