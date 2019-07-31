package cn.edu.patent.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.pojo.patent;
import lombok.extern.log4j.Log4j2;


/**
 * @author:JXH
 * @date:2019年4月5日-下午5:18:49
 * 从数据库获取信息,创造lucene索引!
 */
@Component
@Log4j2
public class insertIndex extends luceneUtil{
	@Autowired
    private patentKeep patentKeep;
	

    /**
     * zip 批量添加索引！请务必，在调用该方法循环外部之前调用before,之后调用after
     * @throws IOException 
     */
	public void zipIndex(patent patent) throws IOException {
	        Document document=insertOrUpdate(patent);
log.log(Level.forName("work",50),"------start索引添加------------\n"+document.toString()+"---------end索引添加------------\n");
	    	indexWriter.addDocument(document);
	}
	/**
	 * 创建，单条索引
	 * @throws IOException 
	 */
	public void selfIndex(patent patent,HttpServletRequest request) throws IOException {
		Before(request);
		Document document=insertOrUpdate(patent);
    	indexWriter.addDocument(document);
  //System.out.println("-------创建索引--------");  	
    	After();//不关闭，索引文件夹，会被锁住！关闭后，不可再使用该indexWriter
	}
}
