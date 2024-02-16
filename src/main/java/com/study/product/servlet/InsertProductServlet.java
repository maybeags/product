package com.study.product.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.product.dao.ProductDao;
import com.study.product.dto.InsertProductReqDto;
import com.study.product.service.ProductService;
import com.study.product.utils.RequestUtil;
import com.study.product.utils.ResponseEntity;
import com.study.product.vo.ProductVo;

@WebServlet("/product")
public class InsertProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

    public InsertProductServlet() {
        super();
        productService = ProductService.getInstance();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String reqeustJsonData = RequestUtil.getJsonData(request);					// utils 패키지를 만든 다음에 호출해온 상태
//		System.out.println(reqDto);
//		InsertProductReqDto dto = test();
		
		InsertProductReqDto reqDto = RequestUtil.convertJsonData(request, InsertProductReqDto.class);
		
		if(productService.isduplicatedProductName(reqDto.getProductName())) {
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("errorMessage", "이미 등록된 상품명입니다.");
			
			ResponseEntity.ofJson(response, 400, responseMap);
			return;
		}
		ResponseEntity.ofJson(response, 201, productService.addProduct(reqDto));
	}	
		

//		Gson gson = new Gson();
//		InsertProductReqDto reqDto = gson.fromJson(reqeustJsonData, InsertProductReqDto.class);
		
//		Map<String, Object> map = gson.fromJson(builder.toString(), Map.class);		얘도 utils 패키지 내의 RequestUtil.java로 이사감.
		
		

		
//		System.out.println(map);
//		System.out.println(map.get("product_name"));
//		System.out.println(map.get("product_price"));
//		System.out.println(map.get("product_size"));
//		
//		ProductVo product = gson.fromJson(builder.toString(), ProductVo.class);
//		System.out.println(product);
//		System.out.println(product.getProductName());
//		System.out.println(product.getProductPrice());
//		System.out.println(product.getProductSize());
//		
//		ProductDao productDao = ProductDao.getInstance();
//		
//		ProductVo findProduct = productDao.findProductByName(product.getProductName());
//	
//		if(findProduct != null) {
//			response.setStatus(400);
//			Map<String, Object> errorMap = new HashMap<>();
//			errorMap.put("errorMessage", "이미 등록된 이름입니다.");
//			response.setContentType("application/json");
//			response.getWriter().println(gson.toJson(errorMap));
//			return;
//		}
		
//		int successCount = productDao.saveProduct(product);
//		
//		Map<String, Object> responseMap = new HashMap<>();
//		responseMap.put("status", 201);
//		responseMap.put("data", "응답데이터");
//		responseMap.put("successCount", successCount);
//		
//		response.setStatus(201);
//		response.setContentType("application/json");
//		
//		PrintWriter writer = response.getWriter();
//		writer.println(gson.toJson(responseMap));
//	}
	
	public <T> T test() {
		
		T a = null;
		return a;
	}

}
