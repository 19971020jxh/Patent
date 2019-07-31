package web;


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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class Test {
@Autowired
WebApplicationContext context;
MockMvc mockMvc;

//@Before
public void initMvc() {
	mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
}
@org.junit.Test
public void search() throws Exception {
	mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/search").param("what", "聚合物").param("pagenow", "2")).andReturn();
	MockHttpServletResponse re=   mvcResult.getResponse();
	 ServletInputStream ris= mvcResult.getRequest().getInputStream();
	
	StringBuilder content = new StringBuilder();
	byte[] b = new byte[1024];
	int lens = -1;
	while ((lens = ris.read(b)) > 0) {
		content.append(new String(b, 0, lens));
	}
System.out.println("-----------------------------------\n\n");
System.out.println(content.toString());

}
}
