package patentweb.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.edu.patent.pojo.patent;

/**
 * @author:JXH
 * @date:2019年4月15日-下午4:05:17
 * 将表单传过来的form_patent转换为patent,并返回
 * 主要是将form_remark-》remark,form_profitRequest-》profitRequest
 */
@Component
public class formTopatent {
    /**
     * 添加或修改时使用！
     * @param formPatent
     * @return
     */
	public patent convert(patent formPatent) {
		//摘要转换!
		String formremark=formPatent.getForm_remark();
		ArrayList<String> remark=new ArrayList<String>();
		remark.add(formremark);
		formPatent.setRemark(remark);
		//权利要求书转换!
		String  fromprofitRequest=formPatent.getForm_profitRequest();
		ArrayList<String> profitRequest=new ArrayList<String>();
		String[] array=fromprofitRequest.split("[1-999]{1,3}.");
		for(String s:array) {
			profitRequest.add(s);
		}
		formPatent.setProfitRequest(profitRequest);
		
		return formPatent;
	}
	/**
	 * 查询，显示详情页面使用！
	 * @param patent
	 * @return
	 */
	public patent jsp(patent Patent) {
		//摘要转换!
				List<String> reList=Patent.getRemark();
				String form_remark="";
				for(String s:reList) {
					form_remark=form_remark+s+"\n";
				}
				Patent.setForm_remark(form_remark);
				//权利要求书转换!
				List<String>  paList=Patent.getProfitRequest();
				String form_ProfitRequest="";
				for(int i=0;i<paList.size();i++) {
	System.out.println("jsp:"+paList.get(i));				
					form_ProfitRequest=form_ProfitRequest+paList.get(i)+"<br/>";
				}
				Patent.setForm_profitRequest(form_ProfitRequest);
				return Patent;
	}
}
