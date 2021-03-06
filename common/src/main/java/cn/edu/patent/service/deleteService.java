package cn.edu.patent.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.lucene.deleteIndex;

/**
 * @author:JXH
 * @date:2019年4月12日-上午11:10:53
 */
@Service
public class deleteService {
     @Autowired
     private patentKeep patentKeep;
     @Autowired
     private deleteIndex deleteIndex;
     
     @Transactional
	 public void service(int id,HttpServletRequest request) throws IOException {
		 //1.数据库删除
		 patentKeep.delete(id);
		 //2.索引删除
		 deleteIndex.delete(id,request);
	 }
     
     public void deleteOfIndex(int id,HttpServletRequest request) throws IOException {
    	//1.索引删除
		 deleteIndex.delete(id,request);
     }
}
