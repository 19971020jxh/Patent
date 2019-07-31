package patentweb.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.patent.pojo.user;

/**
 * @author:JXH
 * @date:2019年6月25日-下午3:23:49
 * -登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//uri:取出localhost:8080部分.
		String uri=request.getRequestURI();
		if(uri.indexOf("login")>=0||uri.indexOf("register")>=0) {
			return true;
		}
		HttpSession session=request.getSession();
		user user=(cn.edu.patent.pojo.user) session.getAttribute("user");
		if(user!=null) {
			return true;
		}
		//-转发到登录页面.
		request.setAttribute("msg", "请先登录！");
		request.setAttribute("status", "0");
		request.getRequestDispatcher("/login/page").forward(request, response);
		return false;
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
