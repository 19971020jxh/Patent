package patentImport.janalyze2;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.patent.pojo.patent;
import lombok.extern.log4j.Log4j2;
import patentImport.util.getinterfances;

/**
 * @author:JXH
 * @date:2019年3月26日-下午2:24:05
 * 解析全部符合要求的文件！
 */
@Log4j2
@Component
public class janalyzeservice {
	
	@Autowired
	private getinterfances getinterfances;
     /**
      * 具体的
      * @param patents
      */
	public ArrayList<patent> janalyzeimpls(ArrayList<Map<String, String>> patents,Map<String, Object> resultMap) {
		// 处理，解析文件的实现类，他们处理相应文件的解析
		Collection<janalyze> janalyzes= getinterfances.getJanalyzeMap().values();
		ArrayList<patent> pArrayList=new ArrayList<patent>();
	
		for(Map<String, String> patenturl:patents) {
			patent patent=new patent();
		//System.out.println("--1");
			patent.setZip(patenturl.get("zip")+".zip");
			log.log(Level.forName("work", 50),"当前压缩包zip:"+patenturl.get("zip")+".zip");	
			for(janalyze janalyze:janalyzes) {
				patent= janalyze.janalyzeimpl(patenturl,patent,resultMap);
		//System.out.println("--3");
			}
			if(patent.getId()!=-1) {
			log.log(Level.forName("work", 50),"\n----------关于"+patent.getZip()+"包下的专利信息---------\n"+patent.toString().replace(",", "\n"));
			pArrayList.add(patent);//专利对象！
			}
	//System.out.println("---------为什么--------#"+"   # "+patents.size());		
		}
	//System.out.println("--------退出-----------");	
        return pArrayList;//返回处理好的专利对象集合！
	}
	
}
