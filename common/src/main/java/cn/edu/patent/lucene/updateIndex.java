package cn.edu.patent.lucene;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.springframework.stereotype.Component;

import cn.edu.patent.pojo.patent;

/**
 * @author:JXH
 * @date:2019年4月7日-上午9:12:16
 */
@Component
public class updateIndex extends luceneUtil{

     /**
      * 利用patent.id做唯一标识update专利<br>
      * requestNumber 专利申请号,分词了
      *  @param patent
      *  @throws IOException 
      */
	  public void update(patent patent,HttpServletRequest request) throws IOException {
		  Before(request);
		  Document document=insertOrUpdate(patent);
//System.out.println("修改:"+document.toString());		  
	//System.out.println("修改索引---\n"+patent);	  
		  indexWriter.updateDocument(new Term("id",String.valueOf(patent.getId())), document);
		  After();
	  }
}
