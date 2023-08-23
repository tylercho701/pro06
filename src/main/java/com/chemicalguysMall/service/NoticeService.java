package com.chemicalguysMall.service;

import com.chemicalguysMall.dto.NoticeDto;
import com.chemicalguysMall.dto.NoticeImgDto;
import com.chemicalguysMall.entity.Notice;
import com.chemicalguysMall.entity.NoticeImg;
import com.chemicalguysMall.repository.NoticeImgRepository;
import com.chemicalguysMall.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeImgService noticeImgService;
    private final NoticeImgRepository noticeImgRepository;

    public Page<Notice> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("regTime"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Notice> noticePage = noticeRepository.findAll(pageable);

        return noticePage;
    }

    public NoticeDto getNotice(Long id){
        List<NoticeImg> noticeImgList = noticeImgRepository.findByNoticeIdOrderByIdAsc(id);
        List<NoticeImgDto> noticeImgDtoList = new ArrayList<>();

        for(NoticeImg noticeImg : noticeImgList) {
            NoticeImgDto noticeImgDto = NoticeImgDto.of(noticeImg);
            noticeImgDtoList.add(noticeImgDto);
        }

        Notice notice = noticeRepository.findById(id)
                                        .orElseThrow(EntityNotFoundException::new);

        NoticeDto noticeDto = NoticeDto.of(notice);
        noticeDto.setNoticeImgDtoList(noticeImgDtoList);

        return noticeDto;
    }

    public Long saveNotice(NoticeDto noticeDto, List<MultipartFile> noticeImgFileList) throws Exception {

        Notice notice = noticeDto.createNotice();
        noticeRepository.save(notice);

        for(int i = 0; i < noticeImgFileList.size(); i++) {
            NoticeImg noticeImg = new NoticeImg();
            noticeImg.setNotice(notice);

            noticeImgService.saveItemImg(noticeImg, noticeImgFileList.get(i));
        }

        return notice.getId();
    }

    public void modify(Notice notice, String subject, String content){
        notice.setSubject(subject);
        notice.setContent(content);
        noticeRepository.save(notice);
    }

    public void delete(Notice notice){ noticeRepository.delete(notice); }

    public Specification<Notice> search(String keyword) {
        Specification<Notice> spec = new Specification<>() {

            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                query.distinct(true);

                return criteriaBuilder.or(
                                        criteriaBuilder.like(root.get("subject"), "%" + keyword + "%"),
                                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%")
                );
            }
        };

        return spec;
    }

    public Page<Notice> getList(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regTime"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Notice> spec = search(keyword);

        return noticeRepository.findAll(spec, pageable);
    }

}
