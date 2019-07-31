package cn.edu.patent.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.StopLibrary;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.util.MyStaticValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.dao.jkeep3.StopWordLibrary;
import cn.edu.patent.dao.jkeep3.UserLibrary;
import cn.edu.patent.lucene.luceneUtil;
import cn.edu.patent.pojo.word;
import lombok.extern.log4j.Log4j2;

/**
 * @author:JXH
 * @date:2019年4月29日-下午4:53:08
 * 提供字典的增删改查！服务
 * 字典的更新
 */
@Service
@Log4j2
public class DictionaryService {
	@Autowired
	private UserLibrary userLibrary;
	@Autowired
	private StopWordLibrary stopWordLibrary;
	@Autowired
	private luceneUtil luceneUtil;
     /**
      * 分词字典上传！-存进数据库
      * window-\r\n
      * linux-\n
      * mac-\r
      * @param text
      */
	 public  void userDictionaryUpload(String text) {
		 ArrayList<word> words=new ArrayList<word>();
		 //获取不同操作系统的换行符
		 //String separator=  System.getProperty("line.separator");
		 
	 String[] array=text.split("\r\n");// window
	 if (array.length<=1) {//Linux
		array=text.split("\n");
	 }
	 if (array.length<=1) {//可能是mac操作系统
		array=text.split("\r");
	 }
	     //解析
		 for(String s:array) {
			 /**
			  * 上传文件格式
			  * 词词性 权重 分类
			  * 阿/n   1   类别
			  */
			String[] cString= StringUtils.split(s, "/");
			word word=new word();
			word.setContent(cString[0]);//内容
			String[] cString2=StringUtils.split(cString[1]," ");
			word.setPartOfSpeech(cString2[0]);//词性
			if (cString2.length==2) {
				word.setWeight(Integer.parseInt(cString2[1]));//权重
			}
			if (cString2.length==3) {
			word.setType(cString2[2]);//分类
			}
//	System.out.println(word);
	       //加入队列
	        words.add(word);
	       // userLibrary.insert(word);
		 }
		 //存进数据库
		userLibrary.insertBatch(words);
	 }
	 /**
	  * 停用词批量添加
	  */
	 public void stopwordDictonaryUpload(String text) {
		 ArrayList<word> words=new ArrayList<word>();
		 String[] array=text.split("\r\n");// window
		 if (array.length<=1) {//Linux
			array=text.split("\n");
		 }
		 if (array.length<=1) {//可能是mac操作系统
			array=text.split("\r");
		 }
		//解析
		 for(String s:array) {
			 /**
			  * 上传文件格式
			  * 词
			  * 阿
			  */
			word word=new word();
			word.setContent(s);//内容
		//	System.out.println(word);
		       //加入队列
		    words.add(word);
		}
	   stopWordLibrary.insertBatch(words);
	 }
	 /**
	   * Txt格式下载.关键字词典
	 * @throws IOException 
	   */
	  public void userDictionaryTxt(HttpServletResponse response) throws IOException {
		 response.setContentType("text/plain"); 
		 response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("关键字词典", "utf-8")+".dic");
		try(    
				ServletOutputStream out = response.getOutputStream();
				OutputStreamWriter writer=new OutputStreamWriter(out);
				BufferedWriter bWriter=new BufferedWriter(writer);
			){
		List<word> lists = userLibrary.selectAll();//获取全部数据
		for(word word:lists) {//             |->这里是tab不是空格
			bWriter.append(word.getContent()+"\t"+//词
		                   word.getPartOfSpeech()+"\t"//词性
					//       word.getWeight()+"\t"+//权重
		           //        word.getType()
					       ).append("\r\n");//分类
		}
		}
		
	  }
	  /**
	   * Txt格式下载,停用词字典
	 * @throws IOException 
	   */
	  public void stopWordDictionaryTxt(HttpServletResponse response) throws IOException {
		  response.setContentType("text/plain"); 
			 response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("停用字词典", "utf-8")+".dic");
			try(    
					ServletOutputStream out = response.getOutputStream();
					OutputStreamWriter writer=new OutputStreamWriter(out);
					BufferedWriter bWriter=new BufferedWriter(writer);
				){
			List<word> lists = stopWordLibrary.selectAll();//获取全部数据
			for(word word:lists) {//             |->这里是tab不是空格
				bWriter.append(word.getContent()+"\t"//词
			               //    word.getPartOfSpeech()+"\t"//词性
						//       word.getWeight()+"\t"+//权重
			           //        word.getType()
						       ).append("\r\n");//分类
			}
			}
	  }
	 /**
	  * 单条添加
	  * @param word
	  */
	 public void userDictionaryInsert(word word) {
		 userLibrary.insert(word);
	 }
	 /**
	  * 停用词单条添加
	  * @param word
	  */
	 public void stopWordDictionaryInsert(word word) {
		 stopWordLibrary.insert(word);
	 }
	 /**
	  * 显示，分页
	  */
	 public JSONObject userDictionaryPage(String searchWhat,int pagenow,int pageSize) {
		 JSONObject result=new JSONObject();
		 if (searchWhat!=null) {
			searchWhat=searchWhat.trim();
		 }
		 List<word> words= userLibrary.selectPage(searchWhat,(pagenow-1)*pageSize, pageSize);
		 result.put("code",0);//成功状态码
		 result.put("count", userLibrary.count());//总记录数
		 result.put("data", words);
		 return result;
	 }
	 /**
	  * 停用词显示，分页
	  */
	 public JSONObject stopWordLibraryPage(String searchWhat,int pagenow,int pageSize) {
		 JSONObject result=new JSONObject();
		 if (searchWhat!=null) {
			searchWhat=searchWhat.trim();
		 }
		 List<word> words= stopWordLibrary.selectPage(searchWhat,(pagenow-1)*pageSize, pageSize);
		 result.put("code",0);//成功状态码
		 result.put("count", stopWordLibrary.count());//总记录数
		 result.put("data", words);
		 return result;
	 }
	 /**
	  * 自定义词典修改
	  */
	 public int userDictionaryUpdate(word word) {
		 userLibrary.update(word);
		 //int j=1/0;
		 return word.getId();
	 }
	 /**
	  * 停用词词典修改
	  */
	 public int stopWordLibraryUpdate(word word) {
		 stopWordLibrary.update(word);
		 return word.getId();
	 }
	 /**
	  * 自定义词典删除
	  */
	 public void userDictionaryDelete(word word) {
		 userLibrary.delete(word);
	 }
	 /**
	  * 停用词词典删除
	  */
	 public void stopWordLibraryDelete(word word) {
		 stopWordLibrary.delete(word);
	 }
	 /**
	  * 关键字词典更新
	  * @return
	 * @throws Exception 
	  */
	 public  void userDictionaryrenew(HttpServletRequest request) throws Exception {
		 //项目根目录
		 String url=request.getServletContext().getResource(File.separator).getPath();
	//System.out.println("url"+url);	 
		 String userDictionaryName="userDictionary.dic";
		 File mkdirFile=new File(url+"Dictionary");
		 if (!mkdirFile.exists()) {
			mkdirFile.mkdirs();//创建文件夹
		}
		 File file=new File(url+"Dictionary"+File.separator+userDictionaryName);
		 if (file.exists()) {
			//存在删除，更新!
			 file.delete();
		 }
		 file.createNewFile();//创造
		 List<word> words=userLibrary.selectAll();
		 try(
			FileWriter writer=new FileWriter(file);
		    BufferedWriter out=new BufferedWriter(writer);		 
				 ){
			 for(word word:words) {
				 out.write(word.getContent()+"\t"+//词
			               word.getPartOfSpeech()+"\t"+//词性
						   "0"+//词频
			               "\r\n");
			 }
			 out.flush();
		 }
		 forest=Library.makeForest(url+"Dictionary"+File.separator+userDictionaryName);//关键字字典
		 //更新创建索引的分词器
		 luceneUtil.newAnalyzer(request);
	 }
	 /**
	  * 停用字词典更新
	  * @return
	 * @throws Exception 
	  */
	 public void stopWordDictionaryrenew(HttpServletRequest request) throws Exception {
		 //项目根目录
		 String url=request.getServletContext().getResource(File.separator).getPath();
		 String stopwordDictionaryName="stopwordDictionary.dic";
		 File mkdirFile=new File(url+"Dictionary");
		 if (!mkdirFile.exists()) {
			mkdirFile.mkdirs();//创建文件夹
		}
		 File file=new File(url+"Dictionary"+File.separator+stopwordDictionaryName);
		 if (file.exists()) {
			//存在删除，更新!
			 file.delete();
		 }
		 file.createNewFile();//创造
		 List<word> words=stopWordLibrary.selectAll();
		 try(
			FileWriter writer=new FileWriter(file);
		    BufferedWriter out=new BufferedWriter(writer);		 
				 ){
			 for(word word:words) {
				 out.write(word.getContent()+"\t"+//词
			               "\r\n");
			 }
			 out.flush();
		 }
		 StopLibrary.put("stopword",url+"Dictionary/"+stopwordDictionaryName);//加载路径下的停用字字典
		 fitler=StopLibrary.get("stopword");
		 //更新创建索引的分词器
		 luceneUtil.newAnalyzer(request);
	 }
	 /**
	  * 测试，关键字和停用字
	  * @return
	 * @throws Exception 
	  */
	 Forest forest=null;
	 StopRecognition fitler=null;
	 public String test(HttpServletRequest request,String text) throws Exception {
		 String rs="";
		 String url=request.getServletContext().getResource(File.separator).getPath();
		 log.log(Level.forName("work",50),"测试分词 根url:"+url);
		 String userDictonaryUrl=url+"Dictionary"+File.separator+"userDictionary.dic";
		 log.log(Level.forName("work",50),"测试分词 userDictonaryUrl:"+userDictonaryUrl);
		 String stopwordDictionaryUrl=url+"Dictionary"+File.separator+"stopwordDictionary.dic";
		 log.log(Level.forName("work",50),"测试分词 stopwordDictionaryUrl:"+stopwordDictionaryUrl);
		 //可以考虑直接从数据库动态加载
		 if(forest==null) {
		 forest=Library.makeForest(userDictonaryUrl);//关键字字典
		 }
		 if (fitler==null) {
			 StopLibrary.put("stopword",stopwordDictionaryUrl);//加载路径下的停用字字典
			 fitler=StopLibrary.get("stopword");
		 }

		 Result result=DicAnalysis.parse(text,forest).recognition(fitler);
	    	 List<Term> termList = result.getTerms();
	         for (Term term : termList) {
	             //System.out.println(term.getName() + ":" + term.getNatureStr());
	             rs=rs+term.getName()+term.getNatureStr()+"\t";
	         }
		 return rs;
	 }
	 
//	 public static void main(String[] args) {
//		System.out.println(DictionaryService.class.getClass().getResource(File.separator).getPath());
//	}
}
