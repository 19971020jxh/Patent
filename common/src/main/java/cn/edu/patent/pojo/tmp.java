package cn.edu.patent.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * @author:JXH
 * @date:2019年4月3日-下午7:14:59
 * 缓存包,存放上传的文件!
 */
@Component
public class tmp {
  private static  Boolean isempty=true;
  /**
   * 最近一次上传的
   */
  public static List<Integer> lastUpload=new ArrayList<Integer>();;
  
   
   
   
/**
    * 查看 tmp是否,是没有使用的!
    * @return
    */
  public  Boolean isEmpty() {
	   return isempty;
   }
   /**
    * 标记为使用
    */
   synchronized public void usetmp() {
	   isempty=false;
   }
   /**
    * 归还!
    */
   synchronized public void returntmp() {
	   isempty=true;
   }
   /**
    * 遍历清空tmp包!
    * @param tmpurl
    * @return
    */
   public void becomeEmpty(File tmpfFile,String url) {
	   if (tmpfFile.isDirectory()) {
		File[] chStrings=tmpfFile.listFiles();
		for(File string:chStrings) {
			becomeEmpty(string,url);
		}
	   }
	    //防止删除 tmp包
	   // if (!tmpfFile.getPath().equals(url)) {
	    	tmpfFile.delete();
		//}
   }

   
}
