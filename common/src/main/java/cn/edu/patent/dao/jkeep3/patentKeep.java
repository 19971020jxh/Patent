package cn.edu.patent.dao.jkeep3;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.patent.pojo.patent;

/**
 * @author:JXH
 * @date:2019年3月28日-下午8:30:27
 * 保存patent,进数据库
 */
public interface patentKeep {
    /*
     * 添加单条专利
    */
	public void insert_patent(patent patent);
	/**
	 * 查询所有
	 * @return
	 */
	public List<patent> selectAll();
	
	public void update(patent patent);
	
	public void delete(int id);
	
	public patent selectone(int id);
	
	/**
	 * 专利号=ZL+(申请号-CN)
	 * 所以申请号是唯一的！
	 * 查询存不存在.
	 * @param requestNumber
	 * @return
	 */
	public Boolean exist(String requestNumber);
	
	 /**
     * 批量降维.
     * @param array
     * @return
     */
    public List<patent> toDataBatch( int[] array);
}
