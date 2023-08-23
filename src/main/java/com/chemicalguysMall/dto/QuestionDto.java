package com.chemicalguysMall.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class QuestionDto {

    @NotEmpty(message = "제목을 입력하세요.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
}
