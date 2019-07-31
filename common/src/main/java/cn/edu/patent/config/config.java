package cn.edu.patent.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.collections4.set.CompositeSet;
import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @author:JXH
 * @date:2019年4月7日-上午10:08:46
 */
//@Configuration
public class config {
	
	//@Value("#{'${lucene.stopwords}'.split(',')}")
	//private String[] stopwords;
      
	 
	  /**
	   * 中文分词器,被ansj替代了
	   * @return
	   */
	 // @Bean
//	  public SmartChineseAnalyzer SmartChineseAnalyzer() {
//		  CharArraySet stopset=new CharArraySet(0, true);
//		  Iterator<Object> defaultset=SmartChineseAnalyzer.getDefaultStopSet().iterator();
//		  while(defaultset.hasNext()) {//添加默认停用词
//			  stopset.add(defaultset.next());
//		  }
//		  for(String s:stopwords) {//添加自定义停用词！
//			  stopset.add(s);
//		  }
//		  SmartChineseAnalyzer sChineseAnalyzer= new SmartChineseAnalyzer(stopset);
//		  return sChineseAnalyzer;
//	  }
}
