package cn.edu.patent.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author:JXH
 * @date:2019年4月14日-下午4:35:59
 * 获取spring bean
 */
@Component
public class ApplcationUtil implements ApplicationContextAware{
    private static ApplicationContext application;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		application=applicationContext;
	}
    
	public static ApplicationContext getApplicationContext() {
		return application;
	}
}
