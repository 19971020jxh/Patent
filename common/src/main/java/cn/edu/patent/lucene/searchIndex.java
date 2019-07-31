package cn.edu.patent.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermsQuery;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FieldComparator.NumericComparator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.util.AnsjAnalyzer;
import cn.edu.patent.util.ApplcationUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @author:JXH
 * @date:2019年4月5日-下午6:14:32
 * 搜索!
 */
@Component
@Log4j2
public class searchIndex extends luceneUtil{
	
	
	/*
	 * private String[] fields1= {"createThing", "requestNumber",
	 * "publicatioNumber", "ipc", "createPeople", "filingDate", "publicationDate",
	 * "proposer", "priorityNumber", "remark", "profitRequest", "technicalField",
	 * "BackgroundTechnology", "summaryInvention", "practicalContent",
	 * "descriptionDrawings", "specificMethods"
	 * 
	 * };
	 */
	
	
	//@Autowired(required=false)
	//@Qualifier("IndexSearcher")
	private IndexSearcher searcher;
	@Autowired
	private luceneUtil luceneUtil;
	 /**
	   * 搜索,不能交给spring管理,否则当我删除一个后,还能被搜到,!每次查询,new一个
	   * @return
	   * @throws IOException
	   */
	  public IndexSearcher IndexSearcher(HttpServletRequest request) throws IOException {
		  if(indexpackage==null) {
	    		 String url=request.getServletContext().getResource(File.separator).getPath();
	    		 indexpackage=url+"indexpackage";
log.log(Level.forName("work", 50),"创造IndexSearcher，indexpackage的url:"+url);	 
	      }
		   // window操作系统处理
			 if (System.getProperty("os.name").startsWith("win")) {
				indexpackage=indexpackage.substring(1, indexpackage.length());
			 }
			// window操作系统处理
		  Directory directory=FSDirectory.open(Paths.get(indexpackage));
		  log.log(Level.forName("work", 50),"创造IndexSearcher，directory:"+directory.toString());		  
		  IndexReader reader=DirectoryReader.open(directory);
		  IndexSearcher searcher=new IndexSearcher(reader);
		  return searcher;
	  }
	
	public JSONObject search(String query,int pageNow,int pageSize,HttpServletRequest request) throws IOException, ParseException {
		log.log(Level.forName("work", 50),"---进入通过关键字搜索模块---");
		searcher=IndexSearcher(request);
		log.log(Level.forName("work", 50),"searcher:"+searcher);	
		//ArrayList<String> patents=new ArrayList<String>();
		JSONObject result=new JSONObject();
		JSONArray patents=new JSONArray();
		 /*多字段查询
		 * 表示多个条件之间的关系！
		 * BooleanClause.Occur.SHOULD or
		 * BooleanClause.Occur.MUST   and
		 * BooleanClause.Occur.MUST_NOT not
		 */
	    BooleanClause.Occur[] occur=new BooleanClause.Occur[fields.length];
	    log.log(Level.forName("work", 50),"搜索域:"+fields);	
	    log.log(Level.forName("work", 50),"搜索字:"+query);	
	    Arrays.fill(occur, BooleanClause.Occur.SHOULD);
	    AnsjAnalyzer analyzer=luceneUtil.analyzer;
	    if (analyzer==null) {
			luceneUtil.newAnalyzer(request);
			analyzer=luceneUtil.analyzer;
		}
 //System.out.println(query+" "+fields+" "+occur+" "+analyzer);
		Query seQuery=MultiFieldQueryParser.parse(query,fields,occur, analyzer);
		
	//	Query seQuery=new TermQuery(new Term("createThing", query)); 单域查询！
		//TopDocs topDocs=searcher.search(seQuery,pageSize); 一次显示多少条
		//分页查询
		int start=(pageNow-1)*pageSize;
		//获取上一页最后一个元素
		ScoreDoc preDoc=null;
		if (start>0) {
			TopDocs lasTopDocs=searcher.search(seQuery,start);
			preDoc=lasTopDocs.scoreDocs[start-1];
		}
		TopDocs topDocs=searcher.searchAfter(preDoc, seQuery, pageSize);
		 int totalcount= topDocs.totalHits;
		 log.log(Level.forName("work", 50),"搜索出来的结果总数:"+totalcount);
		//分页查询
		ScoreDoc[] hDocs= topDocs.scoreDocs;
		for(ScoreDoc scoreDoc:hDocs) {
			 Document document=searcher.doc(scoreDoc.doc);
			 log.log(Level.forName("work", 50),"查询出来的文档:"+document);	
			// patent patent=new patent();
			 //专利id
			// patent.setId(Integer.parseInt(document.get("id")));
			 //专利名称
			// patent.setCreateThing(document.get("createThing"));
			 //专利申请号
			// patent.setRequestNumber(document.get("requestNumber"));
			 //ipc分类号
			// patent.setIpc(document.get("ipc"));
			 //申请人
			// patent.setProposer(document.get("proposer"));
			 //申请日
			// patent.setFilingDate(document.get("filingDate"));
			 //摘要
			// patent.setRemark(Arrays.asList(document.get("remark").split("$")));
			 //加入结果
			// patents.add(patent);
			 
			 String div="<div class='patent-div' patent-id="+document.get("id")+" style='font-size:13px;'>\n" + 
			 		"<p class='patent-title'>\n" + 
			 		"<span style='margin-left:2px;margin-right:2px;'>📌[发明]</span>\n" + 
			 		"<span style='font-size:16px;'>"+document.get("createThing")+"</sapn>-<span>申请号:</span><span>"+document.get("requestNumber")+"</span>\n" + 
			 		"</p>\n" + 
			 		"<p style='color:#31708f;font-size:12px''>\n" + 
			 		"<span>👤申请人：</span><span>"+document.get("proposer")+"<span>-\n" + 
			 		"<span>📅申请日：</span><span>"+document.get("filingDate")+"<span>-\n" + 
			 		"<span>🗄️主分类号：</span><span>"+document.get("ipc")+"<span>-\n" + 
			 		"</p>\n" + 
			 		"<p>\n" + 
			 		"<span>📜摘要:</span><sapn>"+Arrays.asList(document.get("remark").split("$"))+"</sapn>\n" + 
			 		"</p>\n" + 
			 		"</div>";
			 //自定义高亮
			 div=div.replace(query,"<span style='color:#a94442;'>"+query+"</span>");
			 JSONObject divObject=new JSONObject();
			 divObject.put("div", div);
			 patents.add(divObject);
		 }
		result.put("count", totalcount);//总记录数
		result.put("data", patents);
		searcher=null;
		log.log(Level.forName("work", 50),"---end通过关键字搜索模块---");
		return result;
	}
	
	public JSONObject searchById(List<Integer> ids,int pageNow,int pageSize,HttpServletRequest request) throws IOException, ParseException {
		searcher=IndexSearcher(request);
		JSONObject result=new JSONObject();
		JSONArray patents=new JSONArray();
		
	    if (analyzer==null) {
			luceneUtil.newAnalyzer(request);
		}
		//Query seQuery=new TermQuery(new Term("createThing", querys)); //单域查询！
	    //-范围查询.查询id[0]-id[]                                                     上下边界
		Query seQuery=TermRangeQuery.newStringRange("id", ids.get(0)+"", ids.get(ids.size()-1)+"", true, true);
		//TopDocs topDocs=searcher.search(seQuery,pageSize); 一次显示多少条
		//分页查询
		int start=(pageNow-1)*pageSize;
		//获取上一页最后一个元素
		ScoreDoc preDoc=null;
		if (start>0) {
			TopDocs lasTopDocs=searcher.search(seQuery,start);
			preDoc=lasTopDocs.scoreDocs[start-1];
		}
		TopDocs topDocs=searcher.searchAfter(preDoc, seQuery, pageSize);
		 int totalcount= topDocs.totalHits;
		//分页查询
		ScoreDoc[] hDocs= topDocs.scoreDocs;
		for(ScoreDoc scoreDoc:hDocs) {
			 Document document=searcher.doc(scoreDoc.doc);
			 String div="<div class='patent-div' patent-id="+document.get("id")+" style='font-size:13px;'>\n" + 
			 		"<p class='patent-title'>\n" + 
			 		"<span style='margin-left:2px;margin-right:2px;'>📌[发明]</span>\n" + 
			 		"<span style='font-size:16px;'>"+document.get("createThing")+"</sapn>-<span>申请号:</span><span>"+document.get("requestNumber")+"</span>\n" + 
			 		"</p>\n" + 
			 		"<p style='color:#31708f;font-size:12px''>\n" + 
			 		"<span>👤申请人：</span><span>"+document.get("proposer")+"<span>-\n" + 
			 		"<span>📅申请日：</span><span>"+document.get("filingDate")+"<span>-\n" + 
			 		"<span>🗄️主分类号：</span><span>"+document.get("ipc")+"<span>-\n" + 
			 		"</p>\n" + 
			 		"<p>\n" + 
			 		"<span>📜摘要:</span><sapn>"+Arrays.asList(document.get("remark").split("$"))+"</sapn>\n" + 
			 		"</p>\n" + 
			 		"</div>";
			 //自定义高亮
			 //div=div.replace(query,"<span style='color:#a94442;'>"+query+"</span>");
			 JSONObject divObject=new JSONObject();
			 divObject.put("div", div);
			 patents.add(divObject);
		 }
		result.put("count", totalcount);//总记录数
		result.put("data", patents);
		searcher=null;
		return result;
	}
	public JSONObject searchAll(int pageNow,int pageSize,HttpServletRequest request) throws IOException, ParseException {
		if(indexpackage==null) {
   		 String url=request.getServletContext().getResource(File.separator).getPath();
   		 indexpackage=url+"indexpackage";
        }
		// window操作系统处理---有待考究
		 if (System.getProperty("os.name").startsWith("win")) {
			indexpackage=indexpackage.substring(1, indexpackage.length());
		 }
		// window操作系统处理
		Directory directory=FSDirectory.open(Paths.get(indexpackage));
		IndexReader reader=DirectoryReader.open(directory);
		searcher = new IndexSearcher(reader);
		int max=reader.maxDoc();
		JSONObject result=new JSONObject();
		JSONArray patents=new JSONArray();
		
		int start=(pageNow-1)*pageSize;
		int count=start+pageSize;
		if (count>max) {
			count=max;
		}
//System.out.println("max :"+max+" count:"+count+" start:"+start+" pas:"+pageSize);		
		for(int i=start;i<count;i++) {
			 Document document=searcher.doc(i);
//System.out.println("发明:"+document.get("createThing"));			 
			 String div="<div class='patent-div' patent-id="+document.get("id")+" style='font-size:13px;'>\n" + 
			 		"<p class='patent-title'>\n" + 
			 		"<span style='margin-left:2px;margin-right:2px;'>📌[发明]</span>\n" + 
			 		"<span style='font-size:16px;'>"+document.get("createThing")+"</sapn>-<span>申请号:</span><span>"+document.get("requestNumber")+"</span>\n" + 
			 		"</p>\n" + 
			 		"<p style='color:#31708f;font-size:12px''>\n" + 
			 		"<span>👤申请人：</span><span>"+document.get("proposer")+"<span>-\n" + 
			 		"<span>📅申请日：</span><span>"+document.get("filingDate")+"<span>-\n" + 
			 		"<span>🗄️主分类号：</span><span>"+document.get("ipc")+"<span>-\n" + 
			 		"</p>\n" + 
			 		"<p>\n" + 
			 		"<span>📜摘要:</span><sapn>"+Arrays.asList(document.get("remark").split("$"))+"</sapn>\n" + 
			 		"</p>\n" + 
			 		"</div>";
			 JSONObject divObject=new JSONObject();
			 divObject.put("div", div);
			 patents.add(divObject);
		 }
		result.put("count", max);//总记录数
		result.put("data", patents);
		searcher=null;
		return result;
	}
	
	
	
	public JSONObject serviceForm(
			 String requestNumber,
			 String createThing,
			 String publicationNumber,
			 String ipc,
			 String createPeople,
			 String filingDate,
			 String publicationDate,
			 String proposer,
			 String priorityNumber,
			 String patentType,
			 HttpServletRequest request,
			 int pageNow,
			 int pageSize
			 ) throws IOException, ParseException {
		Query requestNumberQ=null;
		Query createThingQ=null;
		Query publicationNumberQ=null;
		Query ipcQ=null;
		Query createPeopleQ=null;
		Query filingDateQ=null;
		Query publicationDateQ=null;
		Query proposerQ=null;
		Query priorityNumberQ=null;
		Query patentTypeQ=null;
		 if (analyzer==null) {
				luceneUtil.newAnalyzer(request);
			}
		log.log(Level.forName("work", 50),"---进入通过表单搜索模块---");
		searcher=IndexSearcher(request);
		log.log(Level.forName("work", 50),"searcher:"+searcher);	
		JSONObject result=new JSONObject();
		JSONArray patents=new JSONArray();
		BooleanQuery.Builder builder=new BooleanQuery.Builder(); 
		if (!StringUtils.isBlank(requestNumber)) {
			//--精确匹配，完全匹配,储存字段不能分词.
			TermQuery query=new TermQuery(new Term("requestNumber", requestNumber));
			//QueryParser requestNumberR=new QueryParser("requestNumber",analyzer);
			requestNumberQ=query;//requestNumberR.parse(requestNumber);
			builder.add(requestNumberQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(createThing)) {
			QueryParser createThingR=new QueryParser("createThing",analyzer);
			createThingQ=createThingR.parse(createThing);
			builder.add(createThingQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(publicationNumber)) {
			QueryParser publicationNumberR=new QueryParser("publicationNumber",analyzer);
			publicationNumberQ=publicationNumberR.parse(publicationNumber);
			builder.add(publicationNumberQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(ipc)) {
			QueryParser ipcR=new QueryParser("ipc",analyzer);
			ipcQ=ipcR.parse(ipc);
			builder.add(ipcQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(createPeople)) {
			QueryParser createPeopleR=new QueryParser("createPeople",analyzer);
			createPeopleQ=createPeopleR.parse(createPeople);
			builder.add(createPeopleQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(filingDate)) {
			QueryParser filingDateR=new QueryParser("filingDate",analyzer);
			filingDateQ=filingDateR.parse(filingDate);
			builder.add(filingDateQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(publicationDate)) {
			QueryParser publicationDateR=new QueryParser("publicationDate",analyzer);
			publicationDateQ=publicationDateR.parse(publicationDate);
			builder.add(publicationDateQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(proposer)) {
			QueryParser proposerR=new QueryParser("proposer",analyzer);
			 
			proposerQ=proposerR.parse(proposer);
			builder.add(proposerQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(priorityNumber)) {
			QueryParser priorityNumberR=new QueryParser("priorityNumber",analyzer);
			priorityNumberQ=priorityNumberR.parse(priorityNumber);
			builder.add(priorityNumberQ,BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isBlank(patentType)) {
			//--精确匹配，完全匹配,储存字段不能分词.
			TermQuery query=new TermQuery(new Term("patentType", patentType));
			patentTypeQ=query;
			builder.add(patentTypeQ,BooleanClause.Occur.MUST);
		}
//	    AnsjAnalyzer analyzer=luceneUtil.analyzer;
//	    if (analyzer==null) {
//			luceneUtil.newAnalyzer(request);
//			analyzer=luceneUtil.analyzer;
//		}
//		Query seQuery=MultiFieldQueryParser.parse(query,fields,occur, analyzer);
		BooleanQuery seQuery=builder.build();
	//	Query seQuery=new TermQuery(new Term("createThing", query)); 单域查询！
		//TopDocs topDocs=searcher.search(seQuery,pageSize); 一次显示多少条
		//分页查询
		int start=(pageNow-1)*pageSize;
		//获取上一页最后一个元素
		ScoreDoc preDoc=null;
		if (start>0) {
			TopDocs lasTopDocs=searcher.search(seQuery,start);
			preDoc=lasTopDocs.scoreDocs[start-1];
		}
		TopDocs topDocs=searcher.searchAfter(preDoc, seQuery, pageSize);
		 int totalcount= topDocs.totalHits;
		//分页查询
		ScoreDoc[] hDocs= topDocs.scoreDocs;
		int no=0;
		for(ScoreDoc scoreDoc:hDocs) {
			 Document document=searcher.doc(scoreDoc.doc);
			 log.log(Level.forName("work", 50),"查询出来的文档:"+document);
			 if(!searchFormFilter(document, requestNumber, createThing, publicationNumber, ipc, createPeople, filingDate, publicationDate, proposer, priorityNumber,patentType)) {
				 no++;//-减去过滤掉的.
				 continue; 
			 }
			// patent patent=new patent();
			 //专利id
			// patent.setId(Integer.parseInt(document.get("id")));
			 //专利名称
			// patent.setCreateThing(document.get("createThing"));
			 //专利申请号
			// patent.setRequestNumber(document.get("requestNumber"));
			 //ipc分类号
			// patent.setIpc(document.get("ipc"));
			 //申请人
			// patent.setProposer(document.get("proposer"));
			 //申请日
			// patent.setFilingDate(document.get("filingDate"));
			 //摘要
			// patent.setRemark(Arrays.asList(document.get("remark").split("$")));
			 //加入结果
			// patents.add(patent);
			 
			 String div="<div class='patent-div' patent-id="+document.get("id")+" style='font-size:13px;'>\n" + 
			 		"<p class='patent-title'>\n" + 
			 		"<span style='margin-left:2px;margin-right:2px;'>📌[发明]</span>\n" + 
			 		"<span style='font-size:16px;'>"+document.get("createThing")+"</sapn>-<span>申请号:</span><span>"+document.get("requestNumber")+"</span>\n" + 
			 		"</p>\n" + 
			 		"<p style='color:#31708f;font-size:12px''>\n" + 
			 		"<span>👤申请人：</span><span>"+document.get("proposer")+"<span>-\n" + 
			 		"<span>📅申请日：</span><span>"+document.get("filingDate")+"<span>-\n" + 
			 		"<span>🗄️主分类号：</span><span>"+document.get("ipc")+"<span>-\n" + 
			 		"</p>\n" + 
			 		"<p>\n" + 
			 		"<span>📜摘要:</span><sapn>"+Arrays.asList(document.get("remark").split("$"))+"</sapn>\n" + 
			 		"</p>\n" + 
			 		"</div>";
			 //自定义高亮
			// div=div.replace(query,"<span style='color:#a94442;'>"+query+"</span>");
			 JSONObject divObject=new JSONObject();
			 divObject.put("div", div);
			 patents.add(divObject);
		 }
		result.put("count", totalcount-no);//总记录数
		result.put("data", patents);
		searcher=null;
		log.log(Level.forName("work", 50),"搜索出来的结果总数:"+(totalcount-no));
		log.log(Level.forName("work", 50),"---end通过表单搜索模块---");
		return result;
	}
	/**
	 * ----结果过滤
	 * @param document
	 * @param requestNumber
	 * @param createThing
	 * @param publicationNumber
	 * @param ipc
	 * @param createPeople
	 * @param filingDate
	 * @param publicationDate
	 * @param proposer
	 * @param priorityNumber
	 * @return
	 */
	private Boolean searchFormFilter(Document document,
			 String requestNumber,
			 String createThing,
			 String publicationNumber,
			 String ipc,
			 String createPeople,
			 String filingDate,
			 String publicationDate,
			 String proposer,
			 String priorityNumber,
			 String patentType) {
		Boolean ok=false;
		if (!StringUtils.isBlank(requestNumber)&&ok==false) {
			ok=document.get("requestNumber").contains(requestNumber);
		}
		if (!StringUtils.isBlank(createThing)&&ok==false) {
			ok=document.get("createThing").contains(createThing);
		}
		if (!StringUtils.isBlank(publicationNumber)&&ok==false) {
			ok=document.get("publicationNumber").contains(publicationNumber);
		}
		if (!StringUtils.isBlank(ipc)&&ok==false) {
			ok=document.get("ipc").contains(ipc);
		}
		if (!StringUtils.isBlank(createPeople)&&ok==false) {
			ok=document.get("createPeople").contains(createPeople);
		}
		if (!StringUtils.isBlank(filingDate)&&ok==false) {
			ok=document.get("filingDate").contains(filingDate);
		}
		if (!StringUtils.isBlank(publicationDate)&&ok==false) {
			ok=document.get("publicationDate").contains(publicationDate);
		}
		if (!StringUtils.isBlank(proposer)&&ok==false) {
			ok=document.get("proposer").contains(proposer);
		}
		if (!StringUtils.isBlank(priorityNumber)&&ok==false) {
			ok=document.get("priorityNumber").contains(priorityNumber);
		}
		if (!StringUtils.isBlank(patentType)&&ok==false) {
			ok=document.get("patentType").contains(patentType);
		}
		return ok;
	}
}
