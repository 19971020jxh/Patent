package cn.edu.patent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.dao.jkeep3.userdao;
import cn.edu.patent.pojo.user;

/**
 * @author:JXH
 * @date:2019年6月26日-上午9:13:26
 * -登录注册等
 */
@Service
public class userService {
 @Autowired
 private userdao userdao;
  public String login(String name,String password,HttpSession session,HttpServletRequest request) {
	if(session.getAttribute("user")!=null) {
		request.setAttribute("msg", "你已登录，无需重复！<a href='searchpage'>>>前往</a>");
		request.setAttribute("status", "2");
		return "forward:/login/page";
	}
	user user=  userdao.login(name, password);
	if(user!=null) {
		if(!user.getCanUse()) {
			request.setAttribute("msg", "账号被禁用！");
			request.setAttribute("status", "-1");
			return "forward:/login/page";
		}
		session.setAttribute("user", user);
		request.setAttribute("msg", "登录成功！");
		request.setAttribute("status", "1");
		//-最近登录
		userdao.updateLastTime(user.getId());
		//-默认先去搜索页面.
		return "forward:/searchpage";
	}
	if (user==null) {
		request.setAttribute("msg", "请输入正确的账号或密码！");
		request.setAttribute("status", "-1");
		return "forward:/login/page";
	}
	return "forward:/login/page";
  }
  /**
   * 注册普通用户
   * @param name
   * @param password
   * @param request
   * @return
   */
  public String register(String name,String password,HttpServletRequest request){
	  //-查存不存在，
	  Boolean u=!userdao.exist(name);
	  if(u) {
		         user  user=new user();
		    		user.setCanUse(true);
		    		user.setIsroot(false);
		    		user.setName(name);
		    		user.setPassword(password);
		    userdao.register(user);
		    request.setAttribute("msg", "注册成功！");
			request.setAttribute("status", "1");
			
	  }else{
		    request.setAttribute("msg", "账号名重复！");
			request.setAttribute("status", "-1");
	  }
	  return "login";
  }
  /**
   * 管理员注册
   */
  public String registerRoot(String name,String pass) {
	  //-查存不存在，
	  Boolean u=!userdao.exist(name);
	  if(u) {
		  user user=new user();
  		  user.setCanUse(true);
  		  user.setIsroot(true);
  		  user.setName(name);
  		  user.setPassword(pass);
          userdao.register(user);
          return "注册管理员"+name+"成功";
	  }
	  return "账号名重复！";
  }
  /**
   * 删除管理员
   */
  public void deleteRoot(String name,String pass) {
	  userdao.delete(name, pass);
  }
  public void updateRoot(String name,String pass,String lname,String lpass) {
	  userdao.update(name, pass,lname,lpass);
  }
  
  public String echartsRg() {
	  List<HashMap<String, String>> list=  userdao.echarts_renG();
	  return JSONObject.toJSONString(list);
  }
  
  public String JsTree() {
	  return JSON.toJSONString(userdao.jsTree());
  }
  
  public JSONObject find_table_user(int page,int limit) {
	  List<user> users=userdao.find_user_table((page-1)*limit,limit);
	  JSONObject jsonObject=new JSONObject();
	  jsonObject.put("code", 0);
	  jsonObject.put("count", users.size());
	  jsonObject.put("data", users);
	  
	  return jsonObject;
  }
  public JSONObject find_table_root() {
	  List<user> users=userdao.find_root_table();
	  JSONObject jsonObject=new JSONObject();
	  jsonObject.put("code", 0);
	  jsonObject.put("count", users.size());
	  jsonObject.put("data", users);
	  
	  return jsonObject;
  }
  
  public HashMap<String, String>  generalView() {
	  return userdao.generalView();
  }
  public void shiro(int id) {
	 userdao.shiro(id);
  }
  
//  public static void main(String[] args) {
//	HashMap<String, String> map=new HashMap<String, String>();
//	map.put("name", "name-1");map.put("value", "value-1");
//	HashMap<String, String> map2=new HashMap<String, String>();
//	map2.put("name", "name-2");map2.put("value", "value-2");
//	ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
//	list.add(map2);list.add(map);
//	System.out.println(JSONObject.toJSONString(list));
//}
}
