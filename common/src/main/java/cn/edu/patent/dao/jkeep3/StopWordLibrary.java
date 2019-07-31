package cn.edu.patent.dao.jkeep3;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.edu.patent.pojo.word;

/**
 * 停用词
 * @author:JXH
 * @date:2019年5月1日-下午8:58:36
 */
public interface StopWordLibrary {
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
}
