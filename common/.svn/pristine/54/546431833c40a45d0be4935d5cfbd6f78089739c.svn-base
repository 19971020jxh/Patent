package org.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.patent.dao.jkeep3.patentKeep;
import cn.edu.patent.lucene.insertIndex;
import cn.edu.patent.lucene.deleteIndex;
import cn.edu.patent.lucene.searchIndex;
import cn.edu.patent.pojo.patent;
import cn.edu.patent.pojo.tmp;


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:patent.xml"})
public class AppTest {
  @Autowired
  private patentKeep keep;
  
  @Autowired
  private tmp tmp;
  
  @Autowired
  private searchIndex searchIndex;
  @Autowired
  private insertIndex createIndex;
  
  @Autowired
  private deleteIndex deleteIndex;
  
  @Autowired
  private org.mybatis.spring.SqlSessionFactoryBean  s;
  ///@Test
  public void test() throws Exception {
//	  patent patent=new patent();
//	  System.out.println(keep);
//	  System.out.println(s.getObject().getConfiguration().toString());
//	  keep.insert_patent(patent);
	  
	  String url="/home/jxh/hebei/tmp";
	  File tFile=new File(url);
	  tmp.becomeEmpty(tFile,url);
  }
  @Test
  public void t() {
	 ;
	 //System.out.println(keep.exist("CN210520034612"));.
	 int j=0;
	 for(int i=0;i<10;i++) {
		 try {
			 j++;
			 if (i==6) {
				int k=1/0;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	 }
	 System.out.println(j);
  }
 // @Test
  public void lucene() throws IOException, ParseException {
 // createIndex.startIndex(); //聚合物多层分注井下流量控制装置
	// searchIndex.search("的",1,10);
	// deleteIndex.delete(30);
	//  System.out.println(keep.selectAll().toString());
 //deleteIndex.deleteAll();
	// String string="1.船用隐形梯道，由固定梯道和活动梯道组成，其特征在于活动梯道和固定梯道之阶梯可相互啮合，活动梯道和固定梯道通过与液压驱动的浮动连杆机构的连接实现折叠或展开，当受液压缸拉动，活动梯道与固定梯道折叠归位，隐蔽于甲板下、船舷内侧的舱体中，其活动梯道的底面、背面分别作为船体甲板、侧舷面的一部分与船体相应甲板面、侧舷面吻合为一体；当受液压缸推动，活动梯道展开时，活动梯道相对固定梯道向船侧舷外翻180°，构成向下延伸至水面的一体连续梯道。$2.根据权利要求1所述的船用隐形梯道，其特征在于浮动连杆机构由浮动连杆和曲柄组成，浮动连杆的一端经销轴与液压油缸连接，其另一端经销轴与活动梯道的铰链铰接连接，曲柄的一端经销轴与浮动连杆连接，另一端则经销轴与固定梯道的铰支座连接。$3.根据权利要求1所述的船用隐形梯道，其特征在于梯道阶梯表面铺设有防滑网。$4.根据权利要求1所述的船用隐形梯道，其特征在于上述活动梯道展开构成的一体连续梯道设有扶手";
	// String[] aStrings=string.split("\\$");
	// for (int i = 0; i < aStrings.length; i++) {
	//	System.out.println(aStrings[i]);
	//}
	  String[] s={"1","1"};
	  patent patent=new patent();
	  patent.setId(84);
	  patent.setRequestNumber("修改");
	  patent.setCreateThing("修改成功");
	  patent.setPublicationNumber("1");
	  patent.setIpc("1");
	  patent.setCreatePeople("1");
	  patent.setFilingDate("1");
	  patent.setPublicationDate("1");
	  patent.setProposer("1");
	  patent.setPriorityNumber("1");
	  patent.setRemark(Arrays.asList(s));
	  patent.setProfitRequest(Arrays.asList(s));
	  patent.setTechnicalField("1");
	  patent.setBackgroundTechnology("1");
	  patent.setSummaryInvention("1");
	  patent.setPracticalContent("1");
	  patent.setDescriptionDrawings("1");
	  patent.setSpecificMethods("1");
	  keep.update(patent);
   }
}
