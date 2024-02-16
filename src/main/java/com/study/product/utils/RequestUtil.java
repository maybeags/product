package com.study.product.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

public class RequestUtil {
	
	public static String getJsonData(HttpServletRequest request) throws IOException {
		StringBuilder builder = new StringBuilder();								// JSON data를 문자열로 변환해주기 위해서.
		String requestJsonData = null;												// 강사님 추가 코드
		
		String readData = null;
		
		BufferedReader reader = request.getReader();
		
		while((readData = reader.readLine()) != null) {
			builder.append(readData);
		}
		
		requestJsonData = builder.toString();
		
		return requestJsonData;
	}
	
	public static <T> T convertJsonData(HttpServletRequest request, Class<T> classOfT) throws IOException {
		StringBuilder builder = new StringBuilder();								// JSON data를 문자열로 변환해주기 위해서.
		String requestJsonData = null;												// 강사님 추가 코드
		
		String readData = null;
		
		BufferedReader reader = request.getReader();
		
		while((readData = reader.readLine()) != null) {
			builder.append(readData);
		}
		
		requestJsonData = builder.toString();
		
		Gson gson = new Gson();
		return gson.fromJson(requestJsonData, classOfT);
	}
}
