package patentweb.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
/**
 * @author:JXH
 * @date:2019年4月3日-下午2:01:22
 */
@Configuration
@ComponentScan(basePackages= {"patentweb.controller"})
public class MvcConfig {
 
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartFile=new CommonsMultipartResolver();
		multipartFile.setDefaultEncoding("UTF-8");
		//multipartFile.setMaxUploadSize(104857600);
		return multipartFile;
	}
	@Bean
	public InternalResourceViewResolver JspView() {
		InternalResourceViewResolver jViewResolver=new InternalResourceViewResolver();
		jViewResolver.setPrefix(File.separator+"WEB-INF"+File.separator);
		jViewResolver.setSuffix(".jsp");
		
		return jViewResolver;
	}
}
