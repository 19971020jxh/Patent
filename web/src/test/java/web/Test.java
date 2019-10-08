package web;


import java.io.File;

import javax.servlet.ServletInputStream;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * @author:JXH
 * @date:2019年4月13日-上午10:28:20
 * 模拟请求测试
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-mvc.xml"})
//@WebAppConfiguration
public class Test {



@org.junit.Test
public void search() throws Exception {


System.out.println("File.pathSeparatorChar    "+File.separator   );
}
}
