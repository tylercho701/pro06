package com.chemicalguysMall.controller;

import com.chemicalguysMall.dto.MemberDto;
import com.chemicalguysMall.entity.Member;
import com.chemicalguysMall.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberJoin(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/memberJoin";
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberDto memberDto, BindingResult result, Model model){

        if(result.hasErrors() || !memberDto.getPassword1().equals(memberDto.getPassword2())){
            model.addAttribute("memberDto", memberDto);
            model.addAttribute("errorMessage", "비밀번호와 비밀번호확인이 서로 다릅니다. 다시 확인하시기 바랍니다.");
            return "member/memberJoin";
        }

        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("memberDto", memberDto);
            return "member/memberJoin";
        }

        return "member/welcome";
    }


    @GetMapping("/login")
    public String loginMember(){
        return "/member/memberLogin";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLogin";
    }

    @GetMapping("/myPage")
    public String getMyInfo(MemberDto memberDto, Principal principal, Model model) {
        Member member = memberService.getMember(principal.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setAddress(member.getAddress());
        memberDto.setPassword1(member.getPassword());
        memberDto.setPassword2(member.getPassword());
        return "member/myInfo";
    }

    @PostMapping("/modify")
    public String updateMyInfo(@Valid MemberDto memberDto, BindingResult result, Model model){

        if(result.hasErrors()){
            log.info("result : 정보 수정에 실패했습니다.");
            log.info(memberDto.toString());
            return "member/myInfo";
        }

        if(!memberDto.getPassword1().equals(memberDto.getPassword2())){
            model.addAttribute("memberDto", memberDto);
            model.addAttribute("errorMessage", "비밀번호와 비밀번호확인이 서로 다릅니다. 다시 확인하시기 바랍니다.");
            return "member/myInfo";
        }

        try {
            memberService.updateMember(memberDto);
        } catch(Exception e) {
            model.addAttribute("errorMessage", "정보 수정 중 오류가 발생했습니다.");
        }

        return "redirect:/member/myPage";
    }

    @GetMapping("/delete/{email}")
    public String deleteMember(MemberDto memberDto, BindingResult result, Model model){

        memberService.deleteMember(memberDto.getEmail());

        return "redirect:/member/logout";
    }
}
