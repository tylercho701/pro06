package com.chemicalguysMall.repository;

import com.chemicalguysMall.entity.NoticeImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeImgRepository extends JpaRepository<NoticeImg, Long> {

    List<NoticeImg> findByNoticeIdOrderByIdAsc(Long itemId);

}
