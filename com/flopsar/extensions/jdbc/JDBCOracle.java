package com.flopsar.extensions.jdbc;

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
	
	
}
