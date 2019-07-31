package cn.edu.patent.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author:JXH
 * @date:2019年5月15日-下午4:30:20
 */
@MappedTypes(value= {Set.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class SetStringHandler implements TypeHandler<Set<String>>{
    //存进数据库时的转换
	@Override
	public void setParameter(PreparedStatement ps, int i, Set<String> parameter, JdbcType jdbcType)
			throws SQLException {
		//System.out.println("------自定义Handler--------");
		String rString="";
		//int length=parameter.size();
		Iterator<String> it = parameter.iterator();
		while(it.hasNext()) {
			rString=rString+it.next() +"$";
		}
		rString=rString.substring(0,rString.length()-1);
		//for (int j = 0; j < length; j++) {
			//rString=parameter.get(j);
		//	if (j<length-1) {
			//	rString=rString+ +"$";
			//}else {
			//	rString=rString+parameter.get(j);
		//	}
			//System.out.println(rString);
		//}
		//System.out.println("处理结果:"+rString);
		ps.setString(i, rString);//转换为String，存进数据库
//System.out.println("存:"+rString);		
	}
    //从数据库取出来时的转换
	@Override
	public Set<String> getResult(ResultSet rs, String columnName) throws SQLException {
	 String Setring=	rs.getString(columnName);
	 String[] array=  Setring.split("\\$");
//	 for (int i = 0; i < array.length; i++) {
//		 System.out.print(array[i]+",");	
//	}
	 
		return new HashSet<String>(Arrays.asList(array));
	}
	//从数据库取出来时的转换
	@Override
	public Set<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
		String Setring=rs.getString(columnIndex);
		String[] array=  Setring.split("\\$");
//		for (int i = 0; i < array.length; i++) {
//			 System.out.print(array[i]+",");	
//		}
		return new HashSet<String>(Arrays.asList(array));
	}
	//从数据库取出来时的转换
	@Override
	public Set<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String Setring=cs.getString(columnIndex);
		String[] array=  Setring.split("\\$");
//		for (int i = 0; i < array.length; i++) {
//			 System.out.print(array[i]+",");	
//		}	
		return new HashSet<String>(Arrays.asList(array));
	}
}
