package cn.edu.patent.lucene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ansj.library.AmbiguityLibrary;
import org.ansj.library.DicLibrary;
import org.ansj.library.StopLibrary;
import org.ansj.util.MyStaticValue;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.edu.patent.dao.jkeep3.StopWordLibrary;
import cn.edu.patent.dao.jkeep3.UserLibrary;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.word;
import cn.edu.patent.service.DictionaryService;
import cn.edu.patent.util.AnsjAnalyzer;
import cn.edu.patent.util.AnsjAnalyzer.TYPE;
import lombok.extern.log4j.Log4j2;



/**
 * @author:JXH
 * @date:2019年4月7日-上午9:50:22
 */
@Component
@Log4j2
public class luceneUtil {
	private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(luceneUtil.class);

	/**
	 * 默认搜索域
	 */
	@Value("#{'${lucene.searchFields}'.split(',')}")
	public String[] fields;
	//@Value("${lucene.indexPackage}")
	public String indexpackage;
	@Autowired
	private  UserLibrary userLibrary;
	@Autowired
	private  StopWordLibrary stopWordLibrary;
	//@Autowired
	//@Qualifier("SmartChineseAnalyzer")
	//private  Analyzer  SmartChineseAnalyzer;
	/**
	 * 不可交给spring管理，每次用创建
	 */
	public IndexWriter indexWriter=null;
		
	    public StringField id;
		public TextField createThing=null;
	    public StringField requestNumber=null;
	    public TextField publicatioNumber=null;
	    public TextField ipc=null;
	    public TextField createPeople=null;
	    public TextField filingDate=null;
	    public TextField publicationDate=null;
	    public TextField proposer=null;
	    public TextField priorityNumber=null;
	    public TextField remark=null;
	    public TextField profitRequest=null;
	    public TextField technicalField=null;
	    public TextField BackgroundTechnology=null;
	    public TextField summaryInvention=null;
	    public TextField practicalContent=null;
	    public TextField descriptionDrawings=null;
	    public TextField specificMethods=null;
	    public StringField patentType=null;
	    public static cn.edu.patent.util.AnsjAnalyzer analyzer=null;
	    /**
	     * 更新字典时要调用,重新创建一个.
	     * @param request
	     * @throws IOException 
	     */
	    public  void newAnalyzer(HttpServletRequest request) throws IOException {
	    	//ansj-分词
	    	 // 关键字词典,停用词字典
			   String url=request.getServletContext().getResource(File.separator).getPath();
			  System.out.println(url);
			   if (System.getProperty("os.name").toLowerCase().startsWith("win")  && indexpackage.startsWith("/")) {
				   url=url.substring(1, url.length());
			   }
			   log.log(Level.forName("work", 50), "url-getPath:"+url);
			   File dicFile=new File(url+"Dictionary");
			   if (!dicFile.exists()) {
				dicFile.mkdirs();
			   }
			   String userDictonaryUrl=url+"Dictionary"+File.separator+"userDictionary.dic";
			   File uFile=new File(userDictonaryUrl);
			   uFile.delete();
				    uFile.createNewFile();//创造
					 List<word> words=userLibrary.selectAll();
					 try(
						FileWriter writer=new FileWriter(uFile);
					    BufferedWriter out=new BufferedWriter(writer);		 
							 ){
						 for(word word:words) {
							 out.write(word.getContent()+"\t"+//词
						               word.getPartOfSpeech()+"\t"+//词性
									   "0"+//词频
						               "\r\n");
						 }
						 out.flush();
			       }
			  
			   String stopwordDictionaryUrl=url+"Dictionary"+File.separator+"stopwordDictionary.dic";
			   File sFile=new File(stopwordDictionaryUrl);
			   sFile.delete();
			  // if (!sFile.exists()) {
				   sFile.createNewFile();//创造
					 List<word> words2=stopWordLibrary.selectAll();
					 try(
						FileWriter writer=new FileWriter(sFile);
					    BufferedWriter out=new BufferedWriter(writer);		 
							 ){
						 for(word word:words2) {
							 out.write(word.getContent()+"\t"+//词
						               "\r\n");
						 }
						 out.flush();
					 }
			  // }
			   String ambiguityDictionaryUrl=url+"Dictionary"+File.separator+"ambiguity.dic";
			   File file=new File(ambiguityDictionaryUrl);
			   if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	//System.out.println(userDictonaryUrl);		  
	//System.out.println(stopwordDictionaryUrl);
			 //  DicLibrary.put(DicLibrary.DEFAULT, userDictonaryUrl);
			 //  DicLibrary.put(StopLibrary.DEFAULT,stopwordDictionaryUrl);
			  // MyStaticValue.ENV.put(DicLibrary.DEFAULT, userDictonaryUrl);
			   DicLibrary.put(DicLibrary.DEFAULT, userDictonaryUrl);
			   MyStaticValue.ENV.put(StopLibrary.DEFAULT,stopwordDictionaryUrl);
			  // StopLibrary.put("cnm",stopwordDictionaryUrl);
			 //  StopLibrary.putIfAbsent("cnm", stopwordDictionaryUrl);
//	System.out.println("luceneUtil.class.getResource(\"ambiguity.dic\").getPath(): "+luceneUtil.class.getResource("/ambiguity.dic").getPath());		   
			   MyStaticValue.ENV.put(AmbiguityLibrary.DEFAULT, ambiguityDictionaryUrl);
	//System.out.println(StopLibrary.get("，"));		   
			   analyzer=new AnsjAnalyzer(TYPE.dic_ansj,stopwordDictionaryUrl);
			  
			 //  MyStaticValue.Us
	    }
	    /**
	     * insert 或 update,delete 之前调用！
	     * @throws IOException
	     */
	    public void Before(HttpServletRequest request) throws IOException {
	    	 if(indexpackage==null) {
	    		 String url=request.getServletContext().getResource(File.separator).getPath();
	    		 indexpackage=url+"indexpackage";
	    	 }
			 newAnalyzer(request);
	    	//判断 索引文件夹是否存在！
	    	 File indexFile=new File(indexpackage);
			 if (!indexFile.exists()) {
				indexFile.mkdir();
			 }
			 System.out.println(indexpackage);
			 // window操作系统处理
			 if (System.getProperty("os.name").toLowerCase().startsWith("win") && indexpackage.startsWith("/")) {
				indexpackage=indexpackage.substring(1, indexpackage.length());
			 }
			 System.out.println(indexpackage);
			// window操作系统处理
	    	 Directory directory=FSDirectory.open(Paths.get(indexpackage));
	    	 //新建一个分词器,为什么不永if(!=null),为了保证每次都是最新的
	    // newAnalyzer(request);
	//System.out.println("ans: "+analyzer);    	 
				/*创建的时候使用那种分词器，查询时也用对应的
				 * SmartChineseAnalyzer 中文！
				 */
				//SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
				IndexWriterConfig conf=new IndexWriterConfig(analyzer);
				/*OpenMode.CREATE 覆盖索引
				 *OpenMode.CREATE_OR_APPEND 追加索引
				 */
				conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
				indexWriter=new IndexWriter(directory, conf);
	    }
	    /**
	     * insert 或 update,delete 之后调用！
	     * @throws IOException
	     */
	    public void After() throws IOException {
	    	indexWriter.commit();
	    	indexWriter.close();
	    	indexWriter=null;
	    }
	    /**
	     * 修改或更新操作，公共部分！
	     * @param patent
	     */
	    public Document insertOrUpdate(patent patent) {
		    	Document document=new Document();
		    	// id
		    	id=new StringField("id", String.valueOf(patent.getId()), Store.YES);
		    	//发明名称
		    	if (!StringUtils.isBlank(patent.getCreateThing())&ArrayUtils.contains(fields,"createThing" )) {
		    		createThing=new TextField("createThing", patent.getCreateThing(),Store.YES);
		    	//createThing.setBoost(0);
		    	}
		    	//申请号--不分词
		    	if (!StringUtils.isBlank(patent.getRequestNumber())&ArrayUtils.contains(fields,"requestNumber" )) {
		    		requestNumber=new StringField("requestNumber", patent.getRequestNumber(),Store.YES);
				}
		    	//公开号 
		    	if (!StringUtils.isBlank(patent.getPublicationNumber())&ArrayUtils.contains(fields,"publicatioNumber" )) {
		    		publicatioNumber=new TextField("publicatioNumber", patent.getPublicationNumber(),Store.YES);
				}
		    	// ipc分类号
		    	if (!StringUtils.isBlank(patent.getIpc())&ArrayUtils.contains(fields,"ipc" )) {
		    		ipc=new TextField("ipc", patent.getIpc(),Store.YES);
				}
		    	//发明人
		    	if (!StringUtils.isBlank(patent.getCreatePeople())&ArrayUtils.contains(fields,"createPeople" )) {
		    		createPeople=new TextField("createPeople", patent.getCreatePeople(),Store.YES);
				}
		    	//申请日
		    	if (!StringUtils.isBlank(patent.getFilingDate())&ArrayUtils.contains(fields,"filingDate" )) {
		    		filingDate=new TextField("filingDate", patent.getFilingDate(),Store.YES);
				}
		    	//公开日
		    	if (!StringUtils.isBlank(patent.getPublicationDate())&ArrayUtils.contains(fields,"publicationDate" )) {
		    		publicationDate=new TextField("publicationDate", patent.getPublicationDate(),Store.YES);
				}
		    	//申请人
		    	if (!StringUtils.isBlank(patent.getProposer())&ArrayUtils.contains(fields,"proposer" )) {
		    		proposer=new TextField("proposer", patent.getProposer(),Store.YES);
				}
		    	//优先权号
		    	if(!StringUtils.isBlank(patent.getPriorityNumber())&ArrayUtils.contains(fields,"priorityNumber" )) {
		    		priorityNumber=new TextField("priorityNumber", patent.getPriorityNumber(),Store.YES);
		    	}
		    	//-分类
		    	
		    	if (!StringUtils.isBlank(patent.getPatentType())&ArrayUtils.contains(fields,"patentType" )) {
		    		patentType=new StringField("patentType", patent.getPatentType(),Store.YES);
//System.out.println("储存:"+patent.getPatentType());	    		
				}
		    	//摘要
		    	if(patent.getRemark()!=null&ArrayUtils.contains(fields,"remark" )) {
		    		 String remarkString="";
		    		 List<String> list=patent.getRemark();
		    		 int length=list.size();
		 	    	for(int i=0;i<length;i++) {
		 	    		if (i!=length-1) {
		 	    			remarkString=remarkString+list.get(i)+"$";//分隔符！
						}else {
							remarkString=remarkString+list.get(i);
						}
		 	    		
		 	    	}
		 	    	remark=new TextField("remark", remarkString, Store.YES);
		    	}
		    	// 权利要求书
		        if (patent.getProfitRequest()!=null&ArrayUtils.contains(fields,"profitRequest" )) {
		        	String profitRequestString="";
		        	List<String> list2=patent.getProfitRequest();
		        	int length=list2.size();
			    	for(int i=0;i<length;i++) {
			    		if (i!=length-1) {
			    			profitRequestString=profitRequestString+list2.get(i)+"$";
						}else {
							profitRequestString=profitRequestString+list2.get(i);
						}
			    		
			    	}
			    	profitRequest=new TextField("profitRequest", profitRequestString, Store.YES);
				}
		    	//技术领域
		    	if (!StringUtils.isBlank(patent.getTechnicalField())&ArrayUtils.contains(fields,"technicalField" )) {
		    		technicalField=new TextField("technicalField", patent.getTechnicalField(), Store.YES);
				}
		    	// 背景技术
		    	if (!StringUtils.isBlank(patent.getBackgroundTechnology())&ArrayUtils.contains(fields,"BackgroundTechnology" )) {
		    		BackgroundTechnology=new TextField("BackgroundTechnology", patent.getBackgroundTechnology(), Store.YES);
				}
		    	//发明内容
		    	if (!StringUtils.isBlank(patent.getSummaryInvention())&ArrayUtils.contains(fields,"summaryInvention" )) {
		    		summaryInvention=new TextField("summaryInvention", patent.getSummaryInvention(), Store.YES);
				}
		    	//实用新型内容
		    	if (!StringUtils.isBlank(patent.getPracticalContent())&ArrayUtils.contains(fields,"practicalContent" )) {
		    		practicalContent=new TextField("practicalContent", patent.getPracticalContent(), Store.YES);
				}
		    	//附图说明
		    	if (!StringUtils.isBlank(patent.getDescriptionDrawings())&ArrayUtils.contains(fields,"descriptionDrawings" )) {
		    		descriptionDrawings=new TextField("descriptionDrawings", patent.getDescriptionDrawings(), Store.YES);
				}
		    	//具体实施方式
		    	if (!StringUtils.isBlank(patent.getSpecificMethods())&ArrayUtils.contains(fields,"specificMethods" )) {
		    		specificMethods=new TextField("specificMethods", patent.getSpecificMethods(), Store.YES);
		    		
				}
		/*
		 * //-专利分类 if
		 * (patent.getSpecificMethods()!=null&!patent.getSpecificMethods().equals("")&
		 * ArrayUtils.contains(fields,"specificMethods" )) { specificMethods=new
		 * TextField("specificMethods", patent.getSpecificMethods(), Store.YES);
		 * 
		 * }
		 */
		    	document.add(id);
		        if (createThing!=null) {
		        	document.add(createThing);
				}
		    	if (requestNumber!=null) {
		    		document.add(requestNumber);
				}
		    	if (publicatioNumber!=null) {
		    		document.add(publicatioNumber);
				}
		    	if (ipc!=null) {
		    		document.add(ipc);
				}
		    	if (createPeople!=null) {
		    		document.add(createPeople);
				}
		    	if (filingDate!=null) {
		    		document.add(filingDate);
				}
		    	if (publicationDate!=null) {
		    		document.add(publicationDate);
				}
		    	if (proposer!=null) {
		    		document.add(proposer);
				}
		    	if (priorityNumber!=null) {
		    		document.add(priorityNumber);
				}
		    	if (remark!=null) {
		    		document.add(remark);
				}
		    	if (profitRequest!=null) {
		    		document.add(profitRequest);
				}
		    	if (technicalField!=null) {
		    		document.add(technicalField);
				}
		    	if (BackgroundTechnology!=null) {
		    		document.add(BackgroundTechnology);
				}
		    	if (summaryInvention!=null) {
		    		document.add(summaryInvention);
				}
		    	if (practicalContent!=null) {
		    		document.add(practicalContent);
				}
		    	if (descriptionDrawings!=null) {
		    		document.add(descriptionDrawings);
				}
		    	if (specificMethods!=null) {
		    		document.add(specificMethods);
				}
		    	if (patentType!=null) {
					document.add(patentType);
				}
	    return document;
	    }
}
