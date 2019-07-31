package cn.edu.patent.dao.jkeep3;
/**
 * 
 * @author:JXH
 * @date:2019年6月13日-下午2:48:46
 * --专利类别操作.
 */

import java.util.HashMap;
import java.util.List;

public interface patentType {
	/**
	 * 返回所有。
	 * @return id-类别
	 */
   List<HashMap<String, String>>  selectAll();
}
