package cn.edu.patent.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.lucene.insertIndex;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.tmp;

/**
 * @author:JXH
 * @date:2019年4月8日-下午2:34:16
 * 添加专利服务，(压缩包上传添加，网页手动添加)
 */
@Component
public class insertService {
	@Autowired
	private patentKeep patentKeep;
	@Autowired
	private insertIndex insertIndex;
	@Autowired
	private AnalyzerService analyzerService;
	
	
       /**
        * 手动添加！一次只能添加一条
        * <br/>1.加进数据库
        * <br/>2.加入lucene索引 
        * <br/>3.专利分词.
     * @throws Exception 
        */
	 @Transactional
	 public patent insertBySelfService(patent patent,HttpServletRequest request) throws Exception {
	   //判断数据库存不存在.
	   if (!patentKeep.exist(patent.getRequestNumber())) {
		  
		 //分词处理
		 AnalyzerService.beforeAnalyzer(request);  
		 patent=analyzerService.ansj(patent,request);  
		 patentKeep.insert_patent(patent);//保存进数据库
		 insertIndex.selfIndex(patent,request);// 添加索引
		 tmp.lastUpload.add(patent.getId()); 
		}
		 return patent;
	 }
	 /**
	  * zip包添加，在{@link Import.patentImport.jkeep3.patentKeepimpl}中
	 * @throws Exception 
	  */
	 @Transactional
	 public int batchOfZip(patent patent,HttpServletRequest request) throws Exception {
		    //判断数据库存不存在.
			if (!patentKeep.exist(patent.getRequestNumber())) {
				//分词处理
			    patent=analyzerService.ansj(patent,request);
				//先查询存不存在,已经存在的专利不添加,根据申请号判断!
				patentKeep.insert_patent(patent);//添加进数据库
				insertIndex.zipIndex(patent); //添加索引
				
				return patent.getId();
			}
		 return -1;
	 }
}
