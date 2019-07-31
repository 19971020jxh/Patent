package patentweb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.dao.jkeep3.patentType;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.service.insertService;
import patentweb.util.formTopatent;

/**
 * @author:JXH
 * @date:2019年4月8日-下午2:44:14
 * 添加controller
 */
@Controller
public class insertController {
    @Autowired
    private insertService insertService;
    @Autowired
	private patentType patentTypeService;
    @Autowired
	private formTopatent formTopatent;
    /**
     * 网页手动添加,成功后跳转到,patent页面！
     * @throws Exception 
     */
    @RequestMapping("insertbyself")
	public ModelAndView insertBySelfService(@Valid patent formPatent, BindingResult result,HttpServletRequest request) throws Exception {
    	if (result.hasErrors()) {
			ModelAndView modelAndView=new ModelAndView("updateOrinsert");
			patent patent=formTopatent.convert(formPatent);
			modelAndView.addObject("patent",patent);
			modelAndView.addObject("remark", patent.getForm_remark());
			modelAndView.addObject("profitRequest", patent.getForm_profitRequest());
			modelAndView.addObject("errors",result.getFieldError());//==>错误信息返回到页面
			modelAndView.addObject("url", "insertbyself");
			return modelAndView;
		}else {
			ModelAndView modelAndView=new ModelAndView("patent");
	    	patent patent=formTopatent.convert(formPatent);
			modelAndView.addObject("patent", insertService.insertBySelfService(patent,request));
			modelAndView.addObject("patent", patent);
			return modelAndView;
		}
    	
	}
    /*
     * zip上传添加
     */
    @RequestMapping("insertbyzip")
    @ResponseBody
    public String insertByZipService(@RequestParam("patent") patent patent) {
    	
    	return null;
    }
    /**
     * 添加页面！
     * @return
     */
    @RequestMapping("insertpage")
    public ModelAndView insertpage() {
    	ModelAndView modelAndView=new ModelAndView("updateOrinsert");
    	List<HashMap<String, String>> patentType=patentTypeService.selectAll();
    	modelAndView.addObject("patentType", patentType);
    	modelAndView.addObject("url", "insertbyself");
    	return modelAndView;
    }
}
