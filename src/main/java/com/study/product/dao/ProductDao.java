package com.study.product.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.study.product.config.DBConfig;
import com.study.product.config.DBConnectionMgr;
import com.study.product.vo.ProductVo;

public class ProductDao {
	private static ProductDao instance;
	private DBConnectionMgr pool;
	
	private ProductDao() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductDao getInstance() {
		if(instance == null) {
			instance = new ProductDao();
		}
		return instance;
	}
	
	public List<ProductVo> getProductList() {
		List<ProductVo> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from product_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductVo productVo = ProductVo.builder()
							.productId(rs.getInt(1))
							.productName(rs.getString(2))
							.productPrice(rs.getInt(3))
							.productSize(rs.getString(4))
							.build();
				
				list.add(productVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
	
	public ProductVo findProductByName(String productName) {
		ProductVo productVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from product_tb where product_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productVo = ProductVo.builder()
							.productId(rs.getInt(1))
							.productName(rs.getString(2))
							.productPrice(rs.getInt(3))
							.productSize(rs.getString(4))
							.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return productVo;
	}
	
	public int save(ProductVo productVo) {
		
		int successCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "insert into product_tb values(0, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, productVo.getProductName());
			pstmt.setInt(2, productVo.getProductPrice());
			pstmt.setString(3, productVo.getProductSize());
			
			successCount = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				productVo.setProductId(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return successCount;
	}

//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");	// 데이터베이스 커넥터 드라이브 클래스 이름
//
//			con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
//			String sql = "select * from product_tb where product_name = ?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, productName);
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				productVo = ProductVo.builder()
//						.productId(rs.getInt(1))
//						.productName(rs.getString(2))			// 여기 숫자가 2인 이유 : column 번호
//						.productPrice(rs.getInt(3))
//						.productSize(rs.getString(4))
//						.build();
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {					
//					pstmt.close();
//				}
//				if(con != null) {					
//					con.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return productVo;
//	}
	
//	public int saveProduct(ProductVo productVo) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		int successCount = 0;
//		
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");	// 데이터베이스 커넥터 드라이브 클래스 이름
//			
//			con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
//			String sql = "insert into product_tb(product_name, product_price, product_size) values(?, ?, ?)";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, productVo.getProductName());
//			pstmt.setInt(2, productVo.getProductPrice());
//			pstmt.setString(3, productVo.getProductSize());
//			successCount = pstmt.executeUpdate();
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		} 
//		
//		return successCount;
//	}
	
//	public List<ProductVo> getProductListAll() {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<ProductVo> products = new ArrayList<>();
//		
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");	// 데이터베이스 커넥터 드라이브 클래스 이름
//
//			con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
//			String sql = "select * from product_tb";
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				ProductVo product = ProductVo.builder()
//						.productId(rs.getInt(1))
//						.productName(rs.getString(2))			// 여기 숫자가 2인 이유 : column 번호
//						.productPrice(rs.getInt(3))
//						.productSize(rs.getString(4))
//						.build();
//				
//				products.add(product);
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {					
//					pstmt.close();
//				}
//				if(con != null) {					
//					con.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return products;
//	}
}
