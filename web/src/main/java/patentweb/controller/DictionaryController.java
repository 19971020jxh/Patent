package patentweb.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.pojo.word;
import cn.edu.patent.service.DictionaryService;
import lombok.extern.log4j.Log4j2;
import patentweb.util.UnicodeReader;

/**
 * @author:JXH
 * @date:2019年4月29日-下午12:58:13
 * 字典的Controller
 */
@Controller
@RequestMapping("dictionary")
@Log4j2
public class DictionaryController {
	  @Autowired
	  private DictionaryService dictionaryService;
	  @RequestMapping("/jsp")
	  public ModelAndView dictionaryJsp(@RequestParam(value="dictonary",required=false,defaultValue="user")String dictonary) throws UnsupportedEncodingException {
		 // dictonary=new String(dictonary.getBytes("ISO-8859-1"),"UTF-8");
		  ModelAndView model=new ModelAndView("Dictionary");
		  if (dictonary.equals("user")) {
			  model.addObject("Dictionary", "user");//表明,是关键字字典    
			model.addObject("DictionaryUpload", "user/upload");//自定义字典批量上传
			model.addObject("DictionaryInsert", "user/insert");//自定义字典单条添加
			model.addObject("Dictionaryupdate","user/update");//自定义字典单条修改
			model.addObject("Dictionarydelete","user/delete");//自定义字典单条删除
			model.addObject("DictionaryPage","user/page");//分页
			model.addObject("DictionaryTxt","user/txt");//导出
			model.addObject("DictionaryRenew","user/renew");//更新字典
			model.addObject("table", 
					        "{field:'id', title:'ID', width:150, fixed: 'left',align:'center',  sort: true}\n" + 
					        ",{field:'content', title:'词',edit: 'text', align:'center'}\n" + 
					        ",{field:'partOfSpeech', title:'词性',edit: 'text', width:150,align:'center', }\n" + 
					        ",{field:'weight', title:'权重',edit: 'text', width:150,align:'center',}\n" + 
					        ",{field:'type', title:'分类', edit: 'text',width:150,align:'center',}\n" + 
					        ",{fixed: 'right', title:'操作',align:'center', toolbar: '#barDemo', width:150}");//表格
		}
		  if (dictonary.equals("stopword")) {
			model.addObject("Dictionary", "stopword");//表明,是停用词字典  
			model.addObject("DictionaryUpload", "stopword/upload");//停用词字典批量上传
			model.addObject("DictionaryInsert", "stopword/insert");//停用词字典单条添加
			model.addObject("Dictionaryupdate","stopword/update");//停用词字典单条修改
			model.addObject("Dictionarydelete","stopword/delete");//停用词字典单条删除
			model.addObject("DictionaryPage","stopword/page");//分页
			model.addObject("DictionaryTxt","stopword/txt");//导出
			model.addObject("DictionaryRenew","stopword/renew");//更新字典
			model.addObject("table", 
					        "{field:'id', title:'ID', width:150, fixed: 'left',align:'center',  sort: true}\n" + 
					        ",{field:'content', title:'词',edit: 'text', align:'center'}\n" + 
					        ",{fixed: 'right', title:'操作',align:'center', toolbar: '#barDemo', width:150}");
		}
		  return model;
	  }
	  /**
	   * 显示所有,用户自定义字典,分页
	   * @return
	 * @throws UnsupportedEncodingException 
	   */
	  @RequestMapping("user/page")
	  @ResponseBody
	  public String userDictionaryPage(
			  @RequestParam(value= "searchWhat",required=false) String searchWhat,
			  @RequestParam("page")int pagenow,
			  @RequestParam("limit")int pageSize
			  ) throws UnsupportedEncodingException {
		 // if (searchWhat!=null) {                     //   本地乱码，服务器不乱码！
			//  searchWhat=new String(searchWhat.getBytes("ISO-8859-1"), "UTF-8");
		//  }
		  log.log(Level.forName("work",50),"关键字搜索:"+searchWhat);
		  return dictionaryService.userDictionaryPage(searchWhat,pagenow, pageSize).toJSONString();
	  }
         /**
          * 分词字典上传!
         * @throws IOException 
          */
	  @SuppressWarnings("resource")
	  @RequestMapping("user/upload")
	  @ResponseBody
	  public String userDictionaryUpload(@RequestParam("file") MultipartFile file) throws IOException {  
	  UnicodeReader reader=	  new UnicodeReader(file.getInputStream(), "utf-8");
	  //--除去bom头.
	  int ch=0;
	  StringBuffer buffer=new StringBuffer();
	  while((ch= reader.read())!=-1) {
		 buffer.append((char)ch);
	  }
	
	  String text=buffer.toString();
		  //读取文件内容！
		//  byte[] bytes= file.getBytes();
		 // System.out.println(bytes[0]+" "+new String());	
		 dictionaryService.userDictionaryUpload(text);
		  return "{\"code\":\"1\"}";
	  }
	  
	  /**
	   * 停用词上传
	 * @throws IOException 
	   */
	  @RequestMapping("stopword/upload")
	  @ResponseBody
	  public String stopWordDictionaryUpload(@RequestParam("file") MultipartFile file) throws IOException {
		//读取文件内容！
		  byte[] bytes= file.getBytes();
		  String text=new String(bytes);
		 //System.out.println(text);
		  dictionaryService.stopwordDictonaryUpload(text);
		  return "{\"code\":\"1\"}";
	  }
	  /**
	   * Txt格式下载.关键字词典
	 * @throws IOException 
	   */
	  @RequestMapping("user/txt")
	  public void userDictionaryTxt(HttpServletResponse response) throws IOException {
		  dictionaryService.userDictionaryTxt(response);
	  }
	  /**
	   * Txt格式下载,停用词字典
	 * @throws IOException 
	   */
	  @RequestMapping("stopword/txt")
	  public void stopWordDictionaryTxt(HttpServletResponse response) throws IOException {
		  dictionaryService.stopWordDictionaryTxt(response);
	  }
	  /**
	   * 显示所有,停用词字典,分页
	   * @return
	 * @throws UnsupportedEncodingException 
	   */
	  @RequestMapping("stopword/page")
	  @ResponseBody
	  public String stopWordDictionaryPage(
			  @RequestParam(value= "searchWhat",required=false) String searchWhat,
			  @RequestParam("page")int pagenow,
			  @RequestParam("limit")int pageSize
			  ) throws UnsupportedEncodingException {
		 // if (searchWhat!=null) {
			//  searchWhat=new String(searchWhat.getBytes(), "UTF-8");
		//  }
		  log.log(Level.forName("work",50),"停用词搜索:"+searchWhat);
		  return dictionaryService.stopWordLibraryPage(searchWhat,pagenow, pageSize).toJSONString();
	  }
	  /**
	   * 分词字典单条添加
	 * @throws UnsupportedEncodingException 
	   */
	  @RequestMapping(value="user/insert")
	  @ResponseBody
	  public String userDictionaryInsert(
			  @RequestParam(value="content",required=true) String content,
			  @RequestParam(value="partOfSpeech",required=true) String partOfSpeech,
			  @RequestParam(value="weight",required=false,defaultValue="0")Integer weight,
			  @RequestParam(value="type",required=false) String type
			  ) throws UnsupportedEncodingException {
		//  if (content!=null) {
			//  content=new String(content.getBytes(), "UTF-8");
		//  }
		 // if (partOfSpeech!=null) {
			//  partOfSpeech=new String(partOfSpeech.getBytes(), "UTF-8");
		// }
		//  if (type!=null) {
			//  type=new String(type.getBytes(), "UTF-8");
		//  }
		  log.log(Level.forName("work",50),"添加关键字: content:"+content+" partofspeech:"+partOfSpeech+" type:"+type);
		  word word=new word();
		  word.setContent(content);
		  word.setPartOfSpeech(partOfSpeech);
		  word.setWeight(weight);
		  if (type!=null&&!type.trim().equals("")) {
			word.setType(type);
		  }
		  dictionaryService.userDictionaryInsert(word);
		  return "1";
	  }
	  /**
	   * 停用词单条添加
	 * @throws UnsupportedEncodingException 
	   */              
	  @RequestMapping("stopword/insert")
	  @ResponseBody
	  public String stopWordDictionaryInsert(String content) throws UnsupportedEncodingException {//System.out.println("------stop--------");
		//  if (content!=null) {
		//	  content=new String(content.getBytes(), "UTF-8");
		 // }
		  log.log(Level.forName("work",50),"停用字添加:"+content);
	      word word=new word();
	      word.setContent(content);
		  dictionaryService.stopWordDictionaryInsert(word);
		  return "1";
	  }
	  /**
	   * 分词字典单条修改
	   */
	  @RequestMapping("user/update")
	  @ResponseBody
	  public String userDictionaryupdate(word word) {
		return String.valueOf(dictionaryService.userDictionaryUpdate(word));
	  }
	  /**
	   * 停用词字典单条修改
	   */
	  @RequestMapping("stopword/update")
	  @ResponseBody
	  public String stopWordDictionaryupdate(word word) {
		  return String.valueOf(dictionaryService.stopWordLibraryUpdate(word));  
	  }
	  /**
	   * 分词字典单条删除
	   */
	  @RequestMapping("user/delete")
	  @ResponseBody
	  public String userDictionarydelete(word word) {
		  dictionaryService.userDictionaryDelete(word);
		  return "1";
	  }
	  /**
	   * 停用词字典单条删除
	   */
	  @RequestMapping("stopword/delete")
	  public String stopWordDictionarydelete(word word) {
		  dictionaryService.stopWordLibraryDelete(word);
		  return "1";
	  }
	  /**
	   * 关键字词典更新
	   * @return
	 * @throws Exception 
	   */
	  @RequestMapping("user/renew")
	  @ResponseBody
	 public String userDictionaryrenew(HttpServletRequest request) throws Exception {
			dictionaryService.userDictionaryrenew(request);
		  return "1";
	 }
		 /**
		  * 停用字词典更新
		  * @return
		 * @throws Exception 
		  */
	  @RequestMapping("stopword/renew")
	  @ResponseBody
	 public String stopWordDictionaryrenew(HttpServletRequest request) throws Exception {
		  dictionaryService.stopWordDictionaryrenew(request);
		  return "1";
	}
	@RequestMapping("test")
	@ResponseBody
	public String test(HttpServletRequest request, String text) throws Exception {
	//	if (text!=null) {
		//	text=new String(text.getBytes(), "UTF-8");
		//}
		log.log(Level.forName("work",50),"测试分词:"+text);
		String result= dictionaryService.test(request, text);
		log.log(Level.forName("work",50),"测试分词结果:"+result); 
		return result;
	}
	
	
	
	
}
