package com.chemicalguysMall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter @Setter @ToString
public class MemberDto {

    @NotBlank(message = "이름은 반드시 입력해 주세요")
    private String name;

    @NotEmpty(message = "이메일은 반드시 입력해 주세요")
    @Email(message = "이메일 형식으로 입력해 주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 반드시 입력해 주세요")
    @Length(min=4, max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해 주세요")
    private String password1;

    @NotEmpty(message = "비밀번호 확인을 반드시 입력해 주세요")
    @Length(min=4, max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해 주세요")
    private String password2;

    @NotEmpty(message = "주소는 반드시 입력해 주세요ㄴ")
    private String address;

}
