package cn.edu.patent.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import cn.edu.patent.pojo.patent;

/**
 * @author:JXH
 * @date:2019年4月13日-下午4:02:51
 * patent->excel工具类
 */
@Component
public class AlibabaExcel {
      
	public void get(OutputStream out,List<patent> paList) throws IOException {
		ExcelWriter writer=new ExcelWriter(out, ExcelTypeEnum.XLSX);
		Sheet sheet=new Sheet(1, 0, patent.class);
		writer.write(paList,sheet);
		writer.finish();
		
		out.close();
	}
}
