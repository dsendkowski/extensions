package com.flopsar.extensions.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;



public class JDBCFormatter {
	
	private static final String NA = "NA";

	
	private static ThreadLocal<String> SQL = new ThreadLocal<String>(){
		protected synchronized String initialValue(){
			return NA;
		}
	};
	
	
	public static String statements(Object[] input){
		try {
			Statement statement = (Statement)input[0];
			return "URL|"+statement.getConnection().getMetaData().getURL()+"|SQL|"+input[1];
		} catch (Throwable e) {
			return NA;
		}
	}
	
	
	public static String format1(Object[] input){
		try {
			Connection conn = (Connection)input[0];
			String ssql = (String)input[1];
			SQL.set(ssql);
			
			return "URL|"+conn.getMetaData().getURL()+"|SQL|"+ssql;
		} catch (Throwable e) {
			return NA;
		}
	}
	
	
	public static String format2(Object[] input){
		try {
			PreparedStatement ps = (PreparedStatement)input[0];
			String ssql = SQL.get();
			
			return "URL|"+ps.getConnection().getMetaData().getURL()+"|SQL|"+ssql;
		} catch (Throwable e) {
			return NA;
		}
	}
	
	

	
}
