package cn.edu.patent.lucene;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.stereotype.Component;

/**
 * @author:JXH
 * @date:2019年4月7日-上午10:29:56
 */
@Component
public class deleteIndex extends luceneUtil{
	/**
     * 利用patent.id做唯一标识update专利<br>
     * requestNumber 专利申请号,分词了
     *  @param patent
     *  @throws IOException 
     */
	public void delete(int id,HttpServletRequest request) throws IOException {
		Before(request);
//System.out.println("--------删除索引"+id+"------"+indexWriter);	
		indexWriter.deleteDocuments(new Term("id", String.valueOf(id)));
		//indexWriter.forceMerge(); //立即删除，无法回滚！
		After();
	}
	/**
	 * 删除全部索引！
	 * @throws IOException
	 */
	public void deleteAll(HttpServletRequest request) throws IOException {
		Before(request);
		indexWriter.deleteAll();
		//indexWriter.forceMergeDeletes();//立即删除，无法回滚！
		After();
	}
}
