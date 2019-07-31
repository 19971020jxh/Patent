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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.dao.jkeep3.patentType;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.service.updateService;
import patentweb.util.formTopatent;

/**
 * @author:JXH
 * @date:2019年4月8日-下午2:58:36
 * update-
 */
@Controller
public class updateController {
   
	@Autowired
	private updateService updateService;
	@Autowired
	private patentType patentTypeService;
	@Autowired
	private patentKeep patentKeep;
	@Autowired
	private formTopatent formTopatent;
	
	@RequestMapping("update")
	public ModelAndView update(@Valid patent formPatent, BindingResult result,HttpServletRequest request) throws Exception {
		if (result.hasErrors()) {
			ModelAndView modelAndView=new ModelAndView("updateOrinsert");
			modelAndView.addObject("patent", formPatent);
			modelAndView.addObject("errors",result.getFieldError());//==>错误信息返回到页面
			modelAndView.addObject("url", "update");
			return modelAndView;
		}else {
			
			patent patent=formTopatent.convert(formPatent);
			updateService.service(patent,request);
			ModelAndView modelAndView=new ModelAndView("forward:patent?id="+patent.getId());
			return modelAndView;//跳到，patent页面！
		}
		
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("updatepage")
	public ModelAndView updatepage(int id) {
		ModelAndView modelAndView=new ModelAndView("updateOrinsert");
		patent patent=patentKeep.selectone(id);
		List<HashMap<String, String>> patentType=patentTypeService.selectAll();
		String remark="";
		String profitRequest="";
		if (patent!=null) {
			
			for(String s:patent.getRemark()) {
				remark=remark+s+"\n";
			}
			
			int length=patent.getProfitRequest().size();
			for(int i=0;i<length;i++) {
				String string=patent.getProfitRequest().get(i)+"\n";
				profitRequest=profitRequest+string;
			}
		}
		
		modelAndView.addObject("patent", patent);
		modelAndView.addObject("patentType", patentType);
		modelAndView.addObject("remark", remark);
		modelAndView.addObject("profitRequest", profitRequest);
		modelAndView.addObject("url", "update");
		return modelAndView;
	}
}
