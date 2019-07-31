package patentweb.controller;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * @author:JXH
 * @date:2019年4月12日-上午11:09:13
 * 全局异常处理！
 */
@ControllerAdvice
public class ExceptionController  {
           // FileUploadBase$SizeLimitExceededException
	 /**
	  * 上传文件过大!
	  * @return
	  */
	 @ResponseBody
	 @ExceptionHandler(value=SizeLimitExceededException.class)
	 public String SizeLimit(Exception e) {//System.out.println("-------->");
		 JSONObject rs=new JSONObject();
		 rs.put("msg", "请控制文件大小在1G以内");
		 rs.put("Exception", e.getMessage());
		 return rs.toJSONString();
	 }
}
