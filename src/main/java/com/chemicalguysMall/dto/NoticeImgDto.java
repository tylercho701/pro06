package com.chemicalguysMall.dto;

import com.chemicalguysMall.entity.NoticeImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter @Setter @ToString
public class NoticeImgDto {

    private Long id;

    private String imgNm;

    private String oriImgNm;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static NoticeImgDto of(NoticeImg noticeImg) {
        return modelMapper.map(noticeImg,NoticeImgDto.class);
    }
}
