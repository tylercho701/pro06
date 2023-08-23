package com.chemicalguysMall.controller;

import com.chemicalguysMall.dto.NoticeDto;
import com.chemicalguysMall.entity.Notice;
import com.chemicalguysMall.service.MemberService;
import com.chemicalguysMall.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       Model model) {

        Page<Notice> paging = noticeService.getList(page, keyword);

        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);

        return "notice/notice_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        NoticeDto noticeDto = noticeService.getNotice(id);

        model.addAttribute("noticeDto", noticeDto);

        return "notice/notice_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String createNotice(NoticeDto noticeDto) {

        return "notice/notice_form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String createNotice(@Valid NoticeDto noticeDto,
                               @RequestParam("noticeImgFile") List<MultipartFile> noticeImgFileList,
                               BindingResult result, Model model) {

        if(result.hasErrors()){
            model.addAttribute("noticeDto", noticeDto);
            return "notice/notice_form";
        }

        if(noticeImgFileList.get(0).isEmpty() && noticeDto.getId() == null) {
            model.addAttribute("noticeDto", noticeDto);
            model.addAttribute("errorMessage", "첫 번째 이미지는 반드시 등록하세요.");
            return "notice/notice_form";
        }

        try {
            noticeService.saveNotice(noticeDto, noticeImgFileList);
        } catch(Exception e) {
            model.addAttribute("noticeDto", noticeDto);
            model.addAttribute("errorMessage", "공지 등록 중 문제가 발생했습니다.");
            return "notice/notice_form";
        }
        return "redirect:/notice/list";
    }
}
