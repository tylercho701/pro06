package com.chemicalguysMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="notice_img")
@Getter @Setter @ToString
public class NoticeImg extends BaseEntity {

    @Id
    @Column(name="notice_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgNm; //이미지 파일명

    private String oriImgNm; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    public void updateNoticeImg(String oriImgNm, String imgNm, String imgUrl){
        this.oriImgNm = oriImgNm;
        this.imgNm = imgNm;
        this.imgUrl = imgUrl;
    }
}
