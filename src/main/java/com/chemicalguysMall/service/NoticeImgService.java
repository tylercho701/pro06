package com.chemicalguysMall.service;

import com.chemicalguysMall.entity.NoticeImg;
import com.chemicalguysMall.repository.NoticeImgRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeImgService {

    @Value("/Users/tylercho/Documents/chemicalguysMall/item")
    //  @Value("C:/shop/item")
    private String noticeImgLocation;

    private final NoticeImgRepository noticeImgRepository;

    private final FileService fileService;

    public void saveItemImg(NoticeImg noticeImg, MultipartFile noticeImgFile) throws Exception {

        String oriImgNm = noticeImgFile.getOriginalFilename();
        String imgNm = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgNm)) {
            imgNm = fileService.uploadFile(noticeImgLocation,
                                            oriImgNm,
                                            noticeImgFile.getBytes());
            imgUrl = "/images/item/" + imgNm;
        }

        noticeImg.updateNoticeImg(oriImgNm, imgNm, imgUrl);
        noticeImgRepository.save(noticeImg);
    }

    public void updateNoticeImg(Long noticeImgId, MultipartFile noticeImgFile) throws Exception{
        if(!noticeImgFile.isEmpty()){
            NoticeImg savedNoticeImg = noticeImgRepository.findById(noticeImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedNoticeImg.getImgNm())) {
                fileService.deleteFile(noticeImgLocation+"/"+
                        savedNoticeImg.getImgNm());
            }

            String oriImgNm = noticeImgFile.getOriginalFilename();
            String imgNm = fileService.uploadFile(noticeImgLocation, oriImgNm, noticeImgFile.getBytes());
            String imgUrl = "/images/item/" + imgNm;
            savedNoticeImg.updateNoticeImg(oriImgNm, imgNm, imgUrl);
        }
    }
}
