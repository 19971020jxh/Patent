package cn.edu.patent.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author:JXH
 * @date:2019年3月30日-上午9:43:19
 * Mybatis保存时，类型转换器<br/>
 * List<String>与String$String....之间的转换
 * 
 */
@MappedTypes(value= {List.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class LisStringHandler implements TypeHandler<List<String>>{
    //存进数据库时的转换
	@Override
	public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
			throws SQLException {
		//System.out.println("------自定义Handler--------");
		String rString="";
		int length=parameter.size();
		for (int j = 0; j < length; j++) {
			//rString=parameter.get(j);
			if (j<length-1) {
				rString=rString+parameter.get(j)+"$";
			}else {
				rString=rString+parameter.get(j);
			}
			//System.out.println(rString);
		}
		//System.out.println("处理结果:"+rString);
		ps.setString(i, rString);//转换为String，存进数据库
		
	}
    //从数据库取出来时的转换
	@Override
	public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
	 String liString=	rs.getString(columnName);
	 String[] array=  liString.split("\\$");
//System.out.println(array.toString());	 
		return Arrays.asList(array);
	}
	//从数据库取出来时的转换
	@Override
	public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
		String liString=rs.getString(columnIndex);
		String[] array=  liString.split("\\$");
//System.out.println(array.toString());
		return Arrays.asList(array);
	}
	//从数据库取出来时的转换
	@Override
	public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String liString=cs.getString(columnIndex);
		String[] array=  liString.split("\\$");
//System.out.println(array.toString());		
		return Arrays.asList(array);
	}

}
