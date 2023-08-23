package com.chemicalguysMall.repository;

import com.chemicalguysMall.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findBySubject(String subject);

    Notice findBySubjectAndContent(String subject, String content);

    List<Notice> findBySubjectLike(String subject);
    Page<Notice> findAll(Pageable pageable);

    Page<Notice> findAll(Specification<Notice> spec, Pageable pageable);

    @Query("select "
            + "distinct n "
            + "from Notice n "
            + "where n.subject like %:keyword% "
            + "or n.content like %:keyword% ")
    Page<Notice> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
