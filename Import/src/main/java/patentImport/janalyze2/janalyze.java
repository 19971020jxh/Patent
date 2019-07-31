package patentImport.janalyze2;

import java.util.Map;

import cn.edu.patent.pojo.patent;

/**
 * @author:JXH
 * @date:2019年3月27日-下午1:41:16
 * 所有解析文件的类，请实现这个接口
 */
public interface janalyze {
	/**
	 * 实现类，需要解析的文件名
	 */
	public static String targetfileString=null;
    /**
     * 实现类，具体的实现方法<br/>
     * 输入map,获取map里的文件路径,将解析的信息写进patent.并返回，<br/>
     * 交给下一个janalyze实现类解析<br/>
     * @param patenturl 专利文件url
     */
	public patent  janalyzeimpl(Map<String, String> patentul,patent patent,Map<String, Object> resultMap);
}
