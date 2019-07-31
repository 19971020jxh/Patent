package cn.edu.patent.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.logging.log4j.Level;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.dao.jkeep3.UserLibrary;
import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.lucene.searchIndex;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.tmp;
import lombok.extern.log4j.Log4j2;

/**
 * @author:JXH
 * @date:2019年4月8日-下午1:24:18
 * 处理所有的搜索服务！
 */
@Service
@Log4j2
public class searchService {
	 @Autowired
	 private patentKeep patentKeep;
	 @Autowired
	 private UserLibrary userLibrary;
     @Autowired
     private searchIndex searchLucene;
	 public JSONObject service(String what,int pagenow,int limit,HttpServletRequest request) throws IOException, ParseException{
		 return searchLucene.search(what,pagenow,limit,request);
	 }
	 public JSONObject serviceAll(int pagenow,int limit,HttpServletRequest request) throws IOException, ParseException{
		 return searchLucene.searchAll(pagenow,limit,request);
	 }
	 public JSONObject serviceStart(int pagenow,int limit,HttpServletRequest request) throws IOException, ParseException{
		 if (tmp.lastUpload.size()>=1) {
			 return searchLucene.searchById(tmp.lastUpload,pagenow,limit,request);
		 }else {
			 return null;
		 }
		 
	 }
	 public JSONObject serviceForm(
			 String requestNumber,
			 String createThing,
			 String publicationNumber,
			 String ipc,
			 String createPeople,
			 String filingDate,
			 String publicationDate,
			 String proposer,
			 String priorityNumber,
			 String patentType,
			 HttpServletRequest request,
			 int pageNow,
			 int pageSize
			 ) throws IOException, ParseException{
		 return	 searchLucene.serviceForm(requestNumber, createThing, publicationNumber, ipc, createPeople, filingDate, publicationDate, proposer, priorityNumber,patentType, request, pageNow, pageSize);
	 }
	 
  /**
	* 从数据库查找某一个！
    * @param id
	* @return
	*/
	public patent Selectpatent(int id) {
		    patent patent= patentKeep.selectone(id);
		    // --全部分词.--
		    Set<String> analyzers=patent.getAnalyzer();
		    analyzers.forEach(e->{
		    	e.replaceAll("/.*", "");
		    });
	//for(String s:analyzers) {
	//	System.out.println("A: "+s);
//	}
		    // --关键字表已经存在的.
		    HashSet<String> analyzersExist = userLibrary.existBatch(analyzers);
		    log.log(Level.forName("work", 50),"关键字表已经存在的："+analyzersExist);
		    // -analyzers中只剩下.关键字表中没记录的词.
		    analyzers.removeAll(analyzersExist);
		    patent.setAnalyzer(analyzers);
		    patent.setAnalyzersExist(analyzersExist);
		    if (tmp.lastUpload.contains(id)) {
			tmp.lastUpload.remove(new Integer(id));
			}
			return patent;
	}
	
	public static void main(String[] args) {
		HashSet<String> aHashSet=new HashSet<String>(Arrays.asList("牛逼/v(String)","牛雕/v(String)","牛肝/v(String)","牛鞭/v(String)"));
	//aHashSet.stream().
	//	System.out.println(aHashSet);
	}
	
//	public static void main(String[] args) {
//		ArrayList<Integer> strings=new ArrayList<Integer>(Arrays.asList(12,13,14));
//		strings.remove(new Integer(13));
//		System.out.println(strings.toString());
//	}
}
