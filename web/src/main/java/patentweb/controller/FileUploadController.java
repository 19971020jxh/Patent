package patentweb.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.dao.jkeep3.userdao;
import cn.edu.patent.lucene.insertIndex;
import cn.edu.patent.pojo.tmp;
import cn.edu.patent.pojo.user;
import cn.edu.patent.service.insertService;
import cn.edu.patent.service.uploadService;
import lombok.extern.log4j.Log4j2;
import patentImport.jkeep3.patentKeepimpl;

/**
 * @author:JXH
 * @date:2019年4月3日-下午1:48:30
 * 处理,文件上传!
 */
@Controller
@Log4j2
public class FileUploadController {
	
	@Autowired
	private tmp tmp;
	@Autowired
	private insertService insertService;
	@Autowired
	private patentKeepimpl keep;
	
	@Autowired
	private uploadService uploadService;
    @Autowired
    private userdao userdao;
	@RequestMapping(value="file",method=RequestMethod.POST)
	@ResponseBody
	public String FileUpload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			@RequestParam(value="radio",defaultValue="files") String radio,
			HttpSession session
			) throws MalformedURLException  {
		JSONObject result=new JSONObject();	
		String url=request.getServletContext().getResource(File.separator).getPath()+System.currentTimeMillis()+"-tmp";//request.getServletContext().getRealPath("tmp");
		
		File tmpfile=new File(url);
		if (!tmpfile.exists()) {
			tmpfile.mkdir();
		}
		String filename=file.getOriginalFilename();
		if(!filename.endsWith("zip")) {
			result.put("iszip", "请上传zip格式的压缩包!");
		}

		else {
			if (!url.isEmpty()) {//清空!
				tmp.becomeEmpty(tmpfile,url);
			}
			File newfile=new File(url, filename);
			try {
				file.transferTo(newfile);
			} catch (IllegalStateException | IOException e) {
				result.put("expection",e.getMessage());
			}
			try {
				Map<String, Object> resultmap = null;
				if(radio.equals("files")) {//批量
				resultmap=keep.work(url+File.separator+filename,url,request);
				}
				if(radio.equals("file")) {//单个
				resultmap=keep.workOne(url+File.separator+filename,url,request);
				}
				result.put("workinfo", resultmap);
				user user=(cn.edu.patent.pojo.user) session.getAttribute("user");
				//-上传次数+1
				userdao.addOneUp(user.getId());
			}catch(Exception e) {
			log.log(Level.forName("work",50),e.getMessage());
			result.put("workinfo", e.getMessage());
				//e.printStackTrace();
			}
		}
		//结束使用,
		tmp.becomeEmpty(tmpfile,url);//清空!并删除.tmp包.
		return result.toString();
	}
	
	@RequestMapping(value="filepage")
	public String filepage() {
		return "upfile";
	}
	/**
	 * 下载！
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("excel")
	public void excel(
			@RequestParam("id")Integer id,
			HttpServletResponse response,
			HttpSession session
			) throws IOException {
		uploadService.service(id, response);
		user user=(cn.edu.patent.pojo.user) session.getAttribute("user");
		//-下载次数！
		userdao.addOneDown(user.getId());
	}
}
