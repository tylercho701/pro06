package com.chemicalguysMall.service;

import com.chemicalguysMall.entity.ItemImg;
import com.chemicalguysMall.repository.ItemImgRepository;
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
public class ItemImgService {

    @Value("/Users/tylercho/Documents/chemicalguysMall/item")
    //  @Value("C:/shop/item")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

        String oriImgNm = itemImgFile.getOriginalFilename();
        String imgNm = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgNm)) {
            imgNm = fileService.uploadFile(itemImgLocation,
                                            oriImgNm,
                                            itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgNm;
        }

        itemImg.updateItemImg(oriImgNm, imgNm, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgNm())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getImgNm());
            }

            String oriImgNm = itemImgFile.getOriginalFilename();
            String imgNm = fileService.uploadFile(itemImgLocation, oriImgNm, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgNm;
            savedItemImg.updateItemImg(oriImgNm, imgNm, imgUrl);
        }
    }
}
