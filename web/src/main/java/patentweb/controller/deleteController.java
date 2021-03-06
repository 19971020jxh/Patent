package patentweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.patent.service.deleteService;

/**
 * @author:JXH
 * @date:2019年4月12日-上午11:15:55
 * 关闭当前页面！
 */
@RestController
public class deleteController {
     @Autowired
     private deleteService deleteService;
     /**
      * 删除,专利和索引
      * @param id
      * @param response
      * @return
      * @throws IOException
      */
     @RequestMapping("delete")
     public String delete(int id,HttpServletRequest request) throws IOException {
    	 deleteService.service(id,request);
         return "yes";
     }
     /**
      * 仅仅删除索引！
      * @param id
      * @return
      * @throws IOException
      */
     @RequestMapping("deleteOfIndex")
     public String deleteOfIndex(int id,HttpServletRequest request) throws IOException {
    	 deleteService.deleteOfIndex(id,request);
    	 return "yes";
     }
}
