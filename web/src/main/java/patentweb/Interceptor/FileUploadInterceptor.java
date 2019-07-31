package patentweb.Interceptor;


import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author:JXH
 * @date:2019年5月10日-下午4:02:39
 */
public class FileUploadInterceptor implements HandlerInterceptor{
   @Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	 if (request!=null&&ServletFileUpload.isMultipartContent(request)) {
		ServletRequestContext  ctx=new ServletRequestContext(request);
		long requestSize=ctx.contentLength();
		if (requestSize> 1073741824) {   // -> 1G                                
			throw new SizeLimitExceededException("上传文件过大",requestSize,1073741824);
		}
	 }
	return true;
}

@Override
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	// TODO Auto-generated method stub
	
}

@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {
	// TODO Auto-generated method stub
	
}
}
