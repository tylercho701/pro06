package com.chemicalguysMall.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CartItemDto {

    private Long itemId;

    @Min(value = 1, message = "최소 수량은 1개입니다.")
    private int count;
}
