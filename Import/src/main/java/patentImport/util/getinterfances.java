package patentImport.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.Data;
import patentImport.janalyze2.janalyze;

/**
 * @author:JXH
 * @date:2019年3月27日-下午1:50:38
 * 获取接口全部的实现类
 */
@Data
@Component
public class getinterfances implements ApplicationContextAware{
     private Map<String, janalyze> janalyzeMap;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		janalyzeMap=applicationContext.getBeansOfType(janalyze.class);
	}
     
	
}
