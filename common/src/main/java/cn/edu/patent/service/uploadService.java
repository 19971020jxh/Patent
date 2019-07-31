package cn.edu.patent.service;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import cn.edu.patent.dao.jkeep3.UserLibrary;
import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.word;
import cn.edu.patent.util.AlibabaExcel;

/**
 * @author:JXH
 * @date:2019年4月13日-下午4:49:17
 * 下载Excel服务
 * 
 */
@Service
public class uploadService {
     @Autowired
     private patentKeep patentKeep;
     @Autowired
     private UserLibrary userLibrary;
     @Autowired
     private AlibabaExcel alibabaExcel;
	 public void service(int id,HttpServletResponse response) throws IOException {
		 ArrayList<patent> patents=new ArrayList<patent>();
		 patent patent=patentKeep.selectone(id);
		 patents.add(patent);
		 response.setContentType("application / vnd.ms-excel");
		 response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(patent.getCreateThing(),"utf-8")+".xls");
		 alibabaExcel.get(response.getOutputStream(), patents);
	 }
	 
	  
	 public void toDataBatch(int array[],HttpServletResponse response) throws IOException { 
		ArrayList<List<String>> sList=new ArrayList<List<String>>();
		List<word> words= userLibrary.selectAll();
	    List<patent> patents=	patentKeep.toDataBatch(array);
	    patents.forEach(patent ->{
	    Set<String> s=patent.getAnalyzer();
	    ArrayList<String> rs=new ArrayList<String>();
	    words.forEach(word->{
	    	 rs.add(s.contains(word.getContent()+"/"+word.getPartOfSpeech())?"1":"0");
	    });
	    sList.add(rs);
	    });
	    response.setContentType("application / vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("降维Excel","utf-8")+".xls");
		OutputStream out = null;
		try {
		out= response.getOutputStream();
	    ExcelWriter writer=new ExcelWriter(out,ExcelTypeEnum.XLSX,false);
		Sheet sheet=new Sheet(1, 0);
		writer.write0(sList, sheet);
		writer.finish();
		} finally {
		out.close();
		}
		
	 }
	 
	 public static void main(String[] args) throws IOException {
		String[] s= {"1","2","3"};
	    ArrayList<List<String>> sList=new ArrayList<List<String>>();
		sList.add(Arrays.asList(s));
		OutputStream out =new FileOutputStream("/home/jxh/桌面/test.xlsx");
		ExcelWriter writer=new ExcelWriter(out,ExcelTypeEnum.XLSX,false);
		Sheet sheet=new Sheet(1, 0);
		writer.write0(sList, sheet);
		writer.finish();
		out.close();
		 
	}
}
