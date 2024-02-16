package com.study.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.study.product.dao.ProductDao;
import com.study.product.dto.InsertProductReqDto;
import com.study.product.dto.InsertProductRespDto;
import com.study.product.dto.SearchProductRespDto;
import com.study.product.vo.ProductVo;

public class ProductService {
	private static ProductService instance;
	private ProductDao productDao;
	
	private ProductService() {
		productDao = ProductDao.getInstance();
	}
	
	public static ProductService getInstance() {
		if(instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	
	public boolean isduplicatedProductName(String productName) {
		return productDao.findProductByName(productName) != null;
	}
	
	public InsertProductRespDto addProduct(InsertProductReqDto insertProductReqDto) {
		ProductVo productVo = insertProductReqDto.toVo();
		
		int successCount = productDao.save(productVo);
		
		return productVo.toInsertDto(successCount);
	}
	
	public List<SearchProductRespDto> searchProducts() {
		List<SearchProductRespDto> searchProductRespDtos = new ArrayList<>();
		
		List<ProductVo> productVos = productDao.getProductList();
		
		for(ProductVo productVo : productVos) {
			searchProductRespDtos.add(productVo.toSearchDto());
		}
		
		return searchProductRespDtos;

//		return productVos.stream()
//			.map(vo -> vo.toSearchDto())
//			.collect(Collectors.toList());
		
//		return productDao.getProductList().stream()
//				.map(ProductVo::toSearchDto)
//				.collect(Collectors.toList());

	}
	
}
