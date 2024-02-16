package com.study.product.dto;

import com.study.product.vo.ProductVo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsertProductReqDto {
	
	private String productName;
	private int productPrice;		// 혹시 나중에 int로 바꿔야 할 수도 있음.
	private String productSize;
	
	public ProductVo toVo() {
		return ProductVo.builder()
				.productName(productName)
				.productPrice(productPrice)
				.productSize(productSize)
				.build();
	}
}
