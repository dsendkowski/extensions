package com.flopsar.extensions.jdbc;

import java.lang.reflect.Field;
import oracle.jdbc.internal.OraclePreparedStatement;


public class JDBCOracle {


	
	
	
	public static String psql(Object[] o){
		try {
			OraclePreparedStatement ps = (OraclePreparedStatement)o[0];	
			
			return "URL|"+ps.getConnection().getMetaData().getURL()+"|SQL|"+ps.getOriginalSql();
		}
		catch(Throwable ex){
			return "NA";
		}
	}
	



	public static String param_psql(Object[] o){
		try {
			
			OraclePreparedStatement ps = (OraclePreparedStatement)o[0];	
			StringBuffer sb = new StringBuffer("URL|");
			sb.append(ps.getConnection().getMetaData().getURL());
			sb.append("|SQL|");
			sb.append(ps.getOriginalSql());
			
			Class<?> c = ps.getClass();
			Field params = null;

			try {
				params = c.getDeclaredField("parameterString");
			}
			catch(Throwable t){
				c = c.getSuperclass();
			}
			
			try { 
				params = c.getDeclaredField("parameterString");
			} 
			catch(Throwable t){
				return sb.toString();
			} 
			
			params.setAccessible(true);
			String[][] pa = (String[][])params.get(ps);

			if(pa != null){
				for(int i=0;i<pa.length;i++){
					for(int j=0;j<pa[i].length; j++){
						if(pa[i][j] == null)
							continue;
						sb.append(String.format("|P_%d|%s",(j+1),pa[i][j]));
					}
				}
			} 
			
			params = c.getDeclaredField("parameterInt");
			params.setAccessible(true);
			int[][] pi = (int[][])params.get(ps);
			
			if(pi != null){			
				for(int i=0;i<pi.length;i++){
					for(int j=0;j<pi[i].length; j++){
						if(pi[i][j] == 0)
							continue;
						sb.append(String.format("|P_%d|%d",(j+1),pi[i][j]));
					}
				}
			}
						
			return sb.toString();
		}
		catch(Throwable ex){
			return "EXCEPTION|"+ex.getMessage();
		}
	}

	
}
