package patentImport.janalyze2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import cn.edu.patent.pojo.patent;
import lombok.extern.log4j.Log4j2;
import patentImport.util.DocumentUtil;

/**
 * @author:JXH
 * @date:2019年3月26日-下午2:20:55
 * Fulltext.html文件的解析
 */
@Log4j2
@Component
public class FulltextHtml extends DocumentUtil implements janalyze{
	
	public static String targetfileString="Full text.html";
         
	  public patent  janalyzeimpl(Map<String, String> patenturl,patent patent,Map<String, Object> resultMap){
		  //这个类需要解析的文件路径
		  String url=patenturl.get(targetfileString);//System.out.println("17");
		  if (url!=null) {
		  File file=new File(url);//System.out.println("18");
		  try {
		   document=	Jsoup.parse(file, "UTF-8");//System.out.println("19");
		   //属性赋值
		   patent.setProfitRequest(profitRequest());//System.out.println("20");
		   patent.setTechnicalField(technicalField());//System.out.println("21");
		   patent.setBackgroundTechnology(BackgroundTechnology());//System.out.println("22"+url);
		   patent.setSummaryInvention(summaryInvention());//System.out.println("23");
		   patent.setPracticalContent(practicalContent());//System.out.println("24");
		   patent.setDescriptionDrawings(descriptionDrawings());//System.out.println("25");
		   patent.setSpecificMethods(specificMethods());//System.out.println("26");
		  } catch (IOException e) {
			  //System.out.println("------这里出错了FULL---------");
			//e.printStackTrace();
			log.log(Level.forName("work", 50),e.getMessage());
			@SuppressWarnings("unchecked")
			Set<String> failZip=(HashSet<String>) resultMap.get("保存失败的专利包:");
			failZip.add(patent.getZip());
			resultMap.put("保存失败的专利包:", failZip+" "+e.getMessage());
			
		  }
		  }else {
			  @SuppressWarnings("unchecked")
			  Set<String> set=   (Set<String>) resultMap.get("html缺失");
			  
				if (set==null) {
					set=new HashSet<String>();
				}
				set.add(patent.getZip());
				//文件缺失
				patent.setId(-1);
				resultMap.put("html缺失",set);
				
		  }
		  return patent;
	  }
}
