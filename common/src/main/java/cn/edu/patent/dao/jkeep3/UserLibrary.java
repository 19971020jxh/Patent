package cn.edu.patent.dao.jkeep3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.edu.patent.pojo.word;

/**
 * @author:JXH
 * @date:2019年4月27日-下午5:13:00
 * 用户自定义词典操作
 */
public interface UserLibrary {
	  /**
	   * 查询全部,创造字典时
	   * @return
	   */
      public List<word> selectAll();
      /**
       * 显示全部字典结果,分页时
       * pagenow->当前页面,
       * pageSize->每页大小
       * @return
       */
      public List<word> selectPage(@Param("searchWhat")String searchWhat,@Param("pageNow")int pagenow,@Param("pageSize")int pageSize);
      /**
       * 统计总共有多少条结果,分页用
       */
      public int count();
      /**
       * 修改
       * @param content
       */
      public void update(word word);
      /**
       * 删除
       * @param word 
       */
      public void delete(word word);
      /**
       * 批量添加
       * @param words
       */
      public void insertBatch(@Param("words") List<word> words);
      /**
       * 单个添加
       * @param word
       */
      public void insert(word word);
      /**
       * 批量查询,这些词是否属于,关键词表中.
       * @param words
       * @return
       */
      public HashSet<String> existBatch(@Param("contents") Set<String> contents);
     
}
