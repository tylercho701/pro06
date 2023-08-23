package com.chemicalguysMall.repository;

import com.chemicalguysMall.dto.ItemSearchDto;
import com.chemicalguysMall.dto.MainItemDto;
import com.chemicalguysMall.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}