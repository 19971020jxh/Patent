package patentweb.controller;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.poi.util.StringUtil;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import lombok.extern.log4j.Log4j2;

/**
 * @author:JXH
 * @date:2019年5月24日-下午10:25:12
 * 请求参数-本地不乱码，服务器乱码，改成服务器不乱码后，本地又乱码，<br/>
 * linux不乱码，win又乱码.
 */

@ControllerAdvice
@Log4j2
public class EncodingControlleradvice {
         @InitBinder
         public void initBinder(WebDataBinder webDataBinder) {
        	 webDataBinder.registerCustomEditor(String.class, new EncodingControlleradvice.encoding());
         }
         
         
      class encoding extends PropertyEditorSupport{

		@Override
		public String getAsText() {
			return super.getAsText();
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			log.log(Level.forName("work", 50),"检测参数是否乱码 text:"+text);
			//1.判空
			if (text!=null) {
				//2.检测乱码!
				if (!(Charset.forName("GBK").newEncoder().canEncode(text))) {
					  try {
						text=new String(text.getBytes("ISO-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			log.log(Level.forName("work", 50),"转换后的参数 text:"+text);	
			setValue(text);
		}
    	  
      }
}
