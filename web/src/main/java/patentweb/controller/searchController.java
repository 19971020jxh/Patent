package patentweb.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.logging.log4j.Level;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.dao.jkeep3.patentType;
import cn.edu.patent.dao.jkeep3.userdao;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.user;
import cn.edu.patent.service.searchService;
import cn.edu.patent.service.uploadService;
import lombok.extern.log4j.Log4j2;
/**
 * @author:JXH
 * @date:2019年4月8日-下午1:01:02
 * 搜索controller
 */
@Controller
@Log4j2
public class searchController {
    @Autowired
    private searchService searchService;
    @Autowired
	private patentType patentTypeService;
    @Autowired
    private uploadService uploadService;
    @Autowired
    private userdao userdao;

    
	@RequestMapping(value="/search")
	@ResponseBody
	public String search(@RequestParam(value="what",defaultValue="") String what,@RequestParam(value="page")	Integer pagenow,@RequestParam("limit")int limit,HttpServletRequest request) throws IOException, ParseException{
	   // if (what!=null) {
	    //	what=new String(what.getBytes("ISO-8859-1"), "UTF-8");
		//}
	    log.log(Level.forName("work", 50), "搜索关键字:"+what);  
		JSONObject result=null;
		//System.out.println("what:"+what.equals("null")+"  s:"+what.length());
		if (what.equals("null")) {
			//System.out.println("1");
			result=searchService.serviceStart(pagenow, limit, request);
		}
		if (what.trim().equals("")) {//System.out.println("2");
			result=searchService.serviceAll(pagenow, limit, request);
		}
		if(!what.equals("null")&&!what.trim().equals("")){//System.out.println("3");
		    result= searchService.service(what,pagenow,limit,request);
		}
		if (result==null) {
			result=new JSONObject();
		}
		result.put("code", 0);
		result.put("msg", 0);
		return result.toJSONString();
	}
	/**
	 * 类型搜索
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/serachform")
	@ResponseBody
	public String searchForm(@RequestParam(value="requestNumber",required=false) String requestNumber,
			@RequestParam(value="createThing",required=false) String createThing,
			@RequestParam(value="publicationNumber",required=false) String publicationNumber,
			@RequestParam(value="ipc",required=false) String ipc,
			@RequestParam(value="createPeople",required=false) String createPeople,
			@RequestParam(value="filingDate",required=false) String filingDate,
			@RequestParam(value="publicationDate",required=false) String publicationDate,
			@RequestParam(value="proposer",required=false) String proposer,
			@RequestParam(value="priorityNumber",required=false) String priorityNumber,
			@RequestParam(value="patentType",required=false) String patentType,
			@RequestParam(value="page") int pageNow,
			@RequestParam(value="limit")int pageSize,
			HttpServletRequest request
			) throws IOException, ParseException {
		log.log(Level.forName("work", 50), "表单搜索关键字:requestNumber:"+requestNumber+
				" createThing:"+createThing+
				" publicationNumber:"+publicationNumber+
				" ipc:"+ipc+
				" createPeople:"+createPeople+
				" filingDate:"+filingDate+
				" publicationDate:"+publicationDate+
				" proposer:"+proposer+
				" priorityNumber:"+priorityNumber+
				"patentType:"+patentType
				);
		JSONObject result=null;
		result=searchService.serviceForm(requestNumber, createThing, publicationNumber, ipc, createPeople, filingDate, publicationDate, proposer, priorityNumber,patentType, request, pageNow, pageSize);
		
		if (result==null) {
			result=new JSONObject();
		}
		result.put("code", 0);
		result.put("msg", 0);
		return result.toJSONString();
	}
	@RequestMapping("todata")
	public void toDataBatch(
			@RequestParam("array") int[] array,
			HttpServletResponse response,
			HttpSession session) throws IOException {
		log.log(Level.forName("work", 50),"下载id,array:"+Arrays.asList(array));
		uploadService.toDataBatch(array, response);
		user user=(cn.edu.patent.pojo.user) session.getAttribute("user");
		//-下载次数！
		userdao.addOneDown(user.getId());
	}
	@RequestMapping("searchpage")
	public String searchpage(ModelMap map) {
		
		List<HashMap<String, String>> patentType=patentTypeService.selectAll();
		map.addAttribute("patentType", patentType);
		return "searchpage";
	}
}
