package cn.edu.patent.dao.jkeep3;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.patent.pojo.user;

/**
 * 
 * @author:JXH
 * @date:2019年6月26日-上午8:57:22
 */
public interface userdao {
	    
         user login(@Param("name")String name,@Param("password")String password);
         
         void register(@Param("user")user user);
         
         Boolean exist(@Param("name")String name);
         
         void delete(@Param("name")String name,@Param("password")String password);
         void update(@Param("name")String name,@Param("password")String password,@Param("lname")String lname,@Param("lpassword")String lpassword);
         //-构造echarts图，人工标注分类，统计.
         List<HashMap<String, String>> echarts_renG();
         
         List<HashMap<String, String>> jsTree();
         HashMap<String, String> generalView();
         List<user> find_user_table(@Param("page")int page,@Param("l")int l);
         
         List<user> find_root_table();
         
         void updateLastTime(@Param("id")int id);
         //-上传次数+1
         void addOneUp(@Param("id")int id);
         void addOneDown(@Param("id")int id);
         void shiro(@Param("id")int id);
         
}
