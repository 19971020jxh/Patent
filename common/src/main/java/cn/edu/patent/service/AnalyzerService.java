package cn.edu.patent.service;


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.ansj.dic.LearnTool;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.StopLibrary;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.logging.log4j.Level;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.stereotype.Service;

import cn.edu.patent.pojo.patent;
import lombok.extern.log4j.Log4j2;

/**
 * @author:JXH
 * @date:2019年5月8日-下午1:26:01
 * 把专利进行分词的Service
 */
@Service
@Log4j2
public class AnalyzerService {
       private static  Forest forest=null; 
       private static StopRecognition fitler=null;
       // 词性过滤-详情参考 ansj 词性.
       private static List<String> filterNatureStr=Arrays.asList(
    		   "q",//量词
    		   "qv",//动量词
    		   "qt",//时量词
    		   "m",//数词
    		   "mq",//数量词
    		   "d",//副词
    		   "p",//介词
    		   "pba",//把
    		   "pbei",//被
    		   "c",//连词
    		   "cc",//并连词
    		   "u",//助词
    		   "uzhe",//着
    		   "ule",//了 喽
    		   "ude2",// 地
    		   "ude3",//得
    		   "udeng",//等 等等
    		   "uyy",//一样 一般 似的
    		   "udh",//的话
    		   "uls",//来说
    		   "uzhi",//之
    		   "e",//叹词
    		   "y",//语气词
    		   "o",//拟声词
    		   "r",//代词
    		   "rr",//人称代词
    		   "rz",//指示代词
    		   "rzt",//时间指示代词
    		   "rzs",//处所指示代词
    		   "rzv",//谓词性指示代词
    		   "ry",//疑问代词
    		   "ryt",//时间疑问代词
    		   "rys",//处所疑问代词
    		   "ryv",//谓词性疑问代词
    		   "rg",//代词性语素
    		   "t",//时间词 ,4时,3日
    		   "en",//一些英文字母组合.
    		   "null"// ---不知道为什么会分出/null.在此处去掉.
    		   );
       
	   public static void beforeAnalyzer(HttpServletRequest request) throws Exception {
		   // 关键字词典,停用词字典
		   String url=request.getServletContext().getResource(File.separator).getPath();
		   String userDictonaryUrl=url+"Dictionary"+File.separator+"userDictionary.dic";
		   String stopwordDictionaryUrl=url+"Dictionary"+File.separator+"stopwordDictionary.dic";
		   //--应该取消为null，因为后续,字典变化,forest却无法.及时更新.-目前先这样做,后续再考虑
		  // if(forest==null) {
				 forest=Library.makeForest(userDictonaryUrl);//关键字字典
			//	 }
		 //  if (fitler==null) {
				 StopLibrary.put("stopword",stopwordDictionaryUrl);//加载路径下的停用字字典
				 fitler=StopLibrary.get("stopword");
			//	 }
		   
	   }
	   /**
	    * ansj分词,把分词结果写进patent对象,并把patent返回
	    * @param patent
	    * @return patent
	 * @throws Exception 
	    */
	   public patent ansj(patent patent,HttpServletRequest request) throws Exception {
      //  if (forest==null||fitler==null) {
			beforeAnalyzer(request);
		//}
	
		   HashSet<String> analyzerList=new HashSet<String>();
		   String text=patent.mergeToString();
		   Result result=DicAnalysis.parse(text,forest).recognition(fitler);
	    	 List<Term> termList = result.getTerms();
	         for (Term term : termList) {
	        	 //词性过滤--如果不为数词，
	        	 if(!filterNatureStr.contains(term.getNatureStr())) {
	        	 analyzerList.add(term.getName()+"/"+term.getNatureStr());
	        	 }
	        	 
	         }
	       log.log(Level.forName("work", 50) ,analyzerList);
	       patent.setAnalyzer(analyzerList); 
	  /// for(String s:analyzerList) {
	   //		System.out.println("A: "+s);
	  // 	}       
		   return patent;
	   }
	   
//	   public static void main(String[] args) {
//		   StringBuilder analyzer=new StringBuilder();
//		   for(int i=0;i<4;i++) {
//		   analyzer.append("牛").append("逼");
//		   }
//		   System.out.println(analyzer.toString());
//	}
	   
	   /**
	    * 新词发现.
	 * @throws Exception 
	    */
	   public List<String> findNewwords(String text,HttpServletRequest request) throws Exception {
		   ArrayList<String> result=new ArrayList<String>();
		   String url=request.getServletContext().getResource(File.separator).getPath();
		   String userDictonaryUrl=url+"Dictionary"+File.separator+"userDictionary.dic";
		   if(forest==null) {
				 forest=Library.makeForest(userDictonaryUrl);//关键字字典
		   }

		   LearnTool learnTool=new LearnTool();
		   NlpAnalysis nlpAnalysis=new NlpAnalysis();
		   nlpAnalysis.setLearnTool(learnTool);
		   nlpAnalysis.parseStr(text);
		   List<Entry<String, Double>> newWords = learnTool.getTopTree(0);
		   for(Entry<String, Double> word:newWords) {
			   result.add(word.getKey());
		   }
		   return result;
	   }
}
