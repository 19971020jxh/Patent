package patentImport.janalyze2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import cn.edu.patent.pojo.patent;
import lombok.extern.log4j.Log4j2;
import patentImport.util.DocumentUtil;


/**
 * @author:JXH
 * @date:2019年3月26日-下午2:20:22
 * Abstract.html文件的解析！
 */
@Log4j2
@Component
public class AbstractHtml extends DocumentUtil implements janalyze{
	
	  public static String targetfileString="Abstract.html";
  
	  public patent  janalyzeimpl(Map<String, String> patenturl,patent patent,Map<String, Object> resultMap){
		  //这个类需要解析的文件路径
		  String url=patenturl.get(targetfileString);//System.out.println("5"+url+" .get(\"zip\")"+patenturl.get("zip"));
		  if(url!=null) {
		  File file=new File(url);//System.out.println("6");
		  try {
		   document=	Jsoup.parse(file, "UTF-8");//System.out.println("7");
		   //属性赋值
		   patent.setCreateThing(createThing());//System.out.println("8");
		   patent.setRequestNumber(requestNumber());//System.out.println("9");
		   patent.setPublicationNumber(publicatioNumber());//System.out.println("10");
		   patent.setIpc(ipc());//System.out.println("10-1");
		   patent.setCreatePeople(createPeople());//System.out.println("11");
		   patent.setFilingDate(filingDate());//System.out.println("12");
		   patent.setPublicationDate(publicationDate());//System.out.println("13");
		   patent.setProposer(proposer());//System.out.println("14");
		   patent.setPriorityNumber(priorityNumber());//System.out.println("15");
		   patent.setRemark(remark());//System.out.println("16");
		   
		  } catch (IOException e) {
			  //System.out.println("------这里出错了---------");
			  log.log(Level.forName("work", 50),e.getMessage());
			  @SuppressWarnings("unchecked")
			  Set<String> failZip=(HashSet<String>) resultMap.get("保存失败的专利包:");
			  failZip.add(patent.getZip());
			  resultMap.put("保存失败的专利包:", failZip+" "+e.getMessage());
		  }
		  }else {//System.out.println("----缺失----");
			@SuppressWarnings("unchecked")
			Set<String> set=   (Set<String>) resultMap.get("html缺失");
			//@SuppressWarnings("unchecked")
			//Set<String> failZip=(HashSet<String>) resultMap.get("保存失败的专利包:");
			if (set==null) {
				set=new HashSet<String>();
			}
			set.add(patent.getZip());
			//failZip.add(patent.getZip());
			//文件缺失
			patent.setId(-1);
			resultMap.put("html缺失",set);
			//resultMap.put("保存失败的专利包:", failZip);
		  }
		  return patent;
	  }
}
