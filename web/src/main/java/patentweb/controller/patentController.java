package patentweb.controller;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.lucene.insertIndex;
import cn.edu.patent.service.AnalyzerService;
import cn.edu.patent.service.searchService;
import lombok.extern.log4j.Log4j2;
import patentweb.util.formTopatent;

/**
 * @author:JXH
 * @date:2019年4月14日-下午2:25:12
 */
@Controller
@Log4j2
public class patentController {
    @Autowired
    private searchService searchService;
    @Autowired
    private formTopatent formTopatent;
    @Autowired
    private AnalyzerService analyzerService;
    
    @RequestMapping("/")
    public String index() {
    	
    	return "index";
    }
    
	@RequestMapping("patent")
	public ModelAndView patent(@RequestParam("id")int id) {
		ModelAndView modelAndView=new ModelAndView();
		cn.edu.patent.pojo.patent patent=searchService.Selectpatent(id);
		if(patent==null) {
			//索引存在，但数据库并不存在的情况！
			modelAndView.setViewName("dbNoPatent");//dbNoPatent==>数据库没有这个专利 页面
			modelAndView.addObject("id", id);
		}else {
			modelAndView.setViewName("patent");
			modelAndView.addObject("patent",formTopatent.jsp(patent));
		}
		return modelAndView;
	}
    
  @RequestMapping("findnewword")
  @ResponseBody
  public String findNewWord(HttpServletRequest request,@RequestParam("text")String text) throws Exception {
	  if (text!=null) {
		  //判断是否乱码!
		 // if (!(Charset.forName("GBK").newEncoder().canEncode(text))) {
			//  text=new String(text.getBytes(), "UTF-8");
		 // }
		  log.log(Level.forName("work",50),"新词发现文本:\n"+text+"\n");
	  }
	 List<String> newWords = analyzerService.findNewwords(text,request);
	 JSONObject result=new JSONObject();
	 result.put("newwords", newWords);
	 return result.toJSONString();
  }
}
