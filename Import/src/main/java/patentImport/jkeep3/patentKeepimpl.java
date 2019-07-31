package patentImport.jkeep3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.lucene.insertIndex;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.tmp;
import cn.edu.patent.service.AnalyzerService;
import cn.edu.patent.service.insertService;
import lombok.extern.log4j.Log4j2;
import patentImport.janalyze2.janalyzeservice;
import patentImport.jread1.readzip;

/**
 * @author:JXH
 * @date:2019年3月28日-下午8:30:09
 */
@Log4j2
@Repository
public class patentKeepimpl {
	@Autowired
	private readzip readzip;
	@Autowired
	private janalyzeservice janalyzeservice;

	@Autowired
	private insertIndex insertIndex;
	@Autowired
	private insertService insertService;
	
	
	
	//@Transactional
	/**
	 * 如果专利保存失败了，记录这个下载失败的zip,返回给前台
	 * @throws Exception 
	 */
	public Map<String, Object> insert_patents(ArrayList<patent> patents,String url,HttpServletRequest request,Map<String, Object> resultMap) throws Exception {
		
		@SuppressWarnings("unchecked")
		HashSet<String> failzip=(HashSet<String>) resultMap.get("保存失败的专利包:");
		int success=0;
		insertIndex.Before(request);
		//新添加之前清空上次的.
		//tmp.lastUpload.clear();
	//AnalyzerService.beforeAnalyzer(request);----这个有什么用？
		for(patent patent:patents) {
			try {
				if (patent.getId()!=-1) {
				 int patentId=insertService.batchOfZip(patent,request);
				 if (patentId!=-1) {
					tmp.lastUpload.add(patentId);
				 }
					success++;
				}
			}catch (Exception e) {//e.printStackTrace();
			//	e.printStackTrace();
				log.log(Level.forName("work", 50), patent.getZip()+"，保存失败！");
				//e.printStackTrace();//--不能执行这句,会中断循环,导致后面的包添加失败！
				//可以执行 e.getMessage() 这句记录异常
				if (patent.getId()!=-1) {
					failzip.add(patent.getZip()+" "+e.getMessage());
				}
				
			}
		}
        insertIndex.After();
       // @SuppressWarnings("unchecked")
		//Set<String> nofindhtml=   (Set<String>) resultMap.get("html缺失");
		resultMap.put("检测到的专利数:", patents.size());
		resultMap.put("成功上传数据库:", success);
		resultMap.put("保存失败的专利包:",failzip);
		
		return resultMap;
	}
	/**
	 * 调用这个!,返回处理结果
	 * 为什么需要,request?<br/>
	 * 项目部署在tomcat下,<br/>
	 * 项目结构:<br/>
	 * 项目文件夹----代码<br/>
	 *          |<br/>
	 *          ---dictionary文件夹--自定义字典<br/>
	 * 需要在后面分词的时候,获取到项目里的自定义字典,<br/>
	 * String url=request.getServletContext().getResource("/").getPath();         
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> work(String path,String url,HttpServletRequest request) throws Exception{
		//返回保存结果
		Map<String, Object> resultMap=new HashMap<String, Object>();
		//失败zip个数
		HashSet<String> failzip=new HashSet<String>();
		resultMap.put("保存失败的专利包:",failzip);
		//1.第一步解压,返回文件路径信息
		ArrayList<Map<String, String>> listZip=readzip.result(path);
		//2.返回专利对象
		ArrayList<patent> patents=janalyzeservice.janalyzeimpls(listZip,resultMap);
		//3.返回处理结果!
		Map<String, Object> result=insert_patents(patents,url,request,resultMap);
		return result;
	}
	/**
	 * 单个上传
	 * @param path
	 * @param url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> workOne(String path,String url,HttpServletRequest request) throws Exception{
		//返回保存结果
		Map<String, Object> resultMap=new HashMap<String, Object>();
		//失败zip个数
		HashSet<String> failzip=new HashSet<String>();
		resultMap.put("保存失败的专利包:",failzip);
		//1.第一步解压,返回文件路径信息
		ArrayList<Map<String, String>> listZip=readzip.resultOne(path);
		//2.返回专利对象
		ArrayList<patent> patents=janalyzeservice.janalyzeimpls(listZip,resultMap);
		//3.返回处理结果!
		Map<String, Object> result=insert_patents(patents,url,request,resultMap);
		return result;
	}

}
