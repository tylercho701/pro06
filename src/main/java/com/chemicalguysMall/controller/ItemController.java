package com.chemicalguysMall.controller;

import com.chemicalguysMall.dto.ItemDto;
import com.chemicalguysMall.dto.ItemSearchDto;
import com.chemicalguysMall.entity.Item;
import com.chemicalguysMall.repository.ItemRepository;
import com.chemicalguysMall.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final ItemRepository itemRepository;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemDto", new ItemDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String newItem(@Valid ItemDto itemDto, BindingResult result, Model model,
                          @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList) {

        if (result.hasErrors()) {
            model.addAttribute("itemDto", itemDto);
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemDto.getId() == null) {
            model.addAttribute("itemDto", itemDto);
            model.addAttribute("errorMessage", "첫 번째 이미지는 반드시 등록하세요.");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(itemDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("itemDto", itemDto);
            model.addAttribute("errorMessage", "상품 등록 중 문제가 발생했습니다.");
            return "item/itemForm";
        }
        return "redirect:/admin/mng";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try {
            ItemDto itemDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemDto", itemDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemDto", new ItemDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemDto itemDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/admin/items";
    }

    @GetMapping({"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }

    @GetMapping("/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemDto itemDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemDto);
        return "item/itemDtl";
    }
}
