package cn.edu.patent.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.lucene.updateIndex;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.tmp;

/**
 * @author:JXH
 * @date:2019年4月8日-下午2:59:04
 */
@Service
public class updateService {
    @Autowired
    private patentKeep patentKeep;
    @Autowired
    private updateIndex updateIndex;
    @Autowired
    private AnalyzerService analyzerService;
    @Transactional
	public void service(patent patent,HttpServletRequest request) throws Exception {
    	//分词处理
    	AnalyzerService.beforeAnalyzer(request);
	    patent=analyzerService.ansj(patent,request);
		//1.改变数据库
		patentKeep.update(patent);
		//2.改变索引！
		updateIndex.update(patent,request);
		
		if(!tmp.lastUpload.contains(patent.getId())) {
			tmp.lastUpload.remove(new Integer(patent.getId()));
		}
	}
}
