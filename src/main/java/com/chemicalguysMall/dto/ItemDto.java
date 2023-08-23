package com.chemicalguysMall.dto;

import com.chemicalguysMall.constant.ItemSellStatus;
import com.chemicalguysMall.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class ItemDto {

    private Long id;

    @NotBlank(message = "상품명은 반드시 입력해 주세요")
    private String itemNm;

    @NotNull(message = "가격은 반드시 입력해 주세요")
    private Integer price;

    @NotBlank(message = "상품 상세는 반드시 입력해 주세요")
    private String itemDetail;

    @NotNull(message = "재고는 반드시 입력해 주세요")
    private Integer stock;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemDto of(Item item){
        return modelMapper.map(item,ItemDto.class);
    }

}
