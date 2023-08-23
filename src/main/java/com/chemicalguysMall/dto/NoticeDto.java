package com.chemicalguysMall.dto;

import com.chemicalguysMall.entity.Notice;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class NoticeDto {

    private Long id;

    @NotEmpty(message = "제목을 입력하세요.")
    private String subject;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    private List<NoticeImgDto> noticeImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Notice createNotice(){ return modelMapper.map(this, Notice.class); }

    public static NoticeDto of(Notice notice) {return modelMapper.map(notice, NoticeDto.class); }
}
