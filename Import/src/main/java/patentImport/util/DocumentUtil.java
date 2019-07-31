package patentImport.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.Data;

/**
 * @author:JXH
 * @date:2019年3月27日-下午5:19:50
 * 获取html后，获得patent属性
 */

@Data
public class DocumentUtil {
     public Document document;
     /**
 	 * 发明名称
 	 */
     public String createThing() {
    	return document.getElementById("patentTitle").getElementsByClass("title").get(0).text().substring(6);
     }
     /**
 	 * 申请号
 	 */
     public String requestNumber() {
//    	 Elements elements=document.select("[bgcolor='#FFFFFF']");
//    	 for (int i = 0; i < elements.size(); i++) {
//			System.out.println("i="+i+" "+elements.get(i).text());
//		}
    	 
//    	 return
//    			 document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			       .getElementsByTag("td").get(2).text();
    	return document.select("[bgcolor='#FFFFFF']").get(0).text();
     }
     /**
 	 * 公开号
 	 */
     public String publicatioNumber() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(8).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(2).text();
     }
     /**
 	 * IPC分类号
 	 */
     public String ipc() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(14).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(4).text();
     }
     /**
 	 * 发明人
 	 */
     public String createPeople() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(20).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(6).text();
     }
     /**
      * 申请日
      */
     public String filingDate() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(5).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(1).text();
     }
     /**
 	 * 公开日
 	 */
     public String publicationDate() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(11).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(3).text();
     }
     /**
 	 * 申请人
 	 */
     public String proposer() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(17).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(5).text();
     }
     /**
 	 * 优先权号
 	 */
     public String priorityNumber() {
//    	 return document.getElementById("abstractItemList").getElementsByTag("table").get(0)
//    			 .getElementsByTag("td").get(23).text();
    	 return document.select("[bgcolor='#FFFFFF']").get(7).text();
     }
     /**
 	 * 摘要
 	 */
     public List<String> remark() {
    	 ArrayList<String> lStrings=new ArrayList<String>();
    	// org.jsoup.select.Elements elements= document.select("p[num]");
    	// ListIterator<Element> list= elements.listIterator();
    	// while(list.hasNext()) {
    		
    	//	 lStrings.add(list.next().text());
    	// }
    	 lStrings.add(document.select("[num]").text().trim());
    	 return lStrings;
     }
//      public static void main(String[] args) throws IOException {
//    	  File input = new File("/home/jxh/桌面/20/CN200410069908_CN1580882A_CN/Full text.html");
//    	  Document doc = Jsoup.parse(input, "UTF-8");
//    	  Elements elements= doc.getElementsByTag("table").get(1).getElementsByTag("td");
//    	  ListIterator<Element> listIterator= elements.listIterator();
//    	  System.out.println(elements.size());
//    	  while (true) {
//			if () {
//				
//			} listIterator.hasNext()
//		  }
//	   }
     
     /**
 	 * 权利要求书
 	 */
     public List<String> profitRequest(){
    	 ArrayList<String> list=new ArrayList<String>();
    	Elements elements= document.getElementsByTag("table").get(0).getElementsByTag("td");
    	ListIterator<Element> eIterator= elements.listIterator();
    	eIterator.next();
    	while(eIterator.hasNext()) {
    	 list.add(eIterator.next().text());	
   // 	 System.out.println("-----re-----"); 	 
    	}
    //	System.out.println("-----出来了-----");
    	return list;
     }
     /**
 	 * 技术领域
 	 */
     public String  technicalField() {
    	String result="";
    	Elements elements= document.getElementsByTag("table").get(1).getElementsByTag("td");
    	ListIterator<Element> listIterator= elements.listIterator();
    	listIterator.next();
    	while(listIterator.hasNext()) {
    		String string=listIterator.next().text().trim();
    		//System.out.println("技术领域A:"+string+" "+string.matches("^技术领域.{0,}")); 		
    		if (string.matches("^技术领域.{0,}")) {
				result=listIterator.next().text().trim();
	// System.out.println("技术领域:"+result); 	
				if (string.length()>8) {
				listIterator.previous();
					result=string.substring(4, string.length());
				}   
				
				break;
			}
    		
    	}
    	return result;
     }
     /**
 	 * 背景技术
 	 */
     public String BackgroundTechnology() {
    	String result="";
     	Elements elements= document.getElementsByTag("table").get(1).getElementsByTag("td");
     	ListIterator<Element> listIterator= elements.listIterator();
     	listIterator.next();
     	while(listIterator.hasNext()) {
     		String string=listIterator.next().text().trim();
    // 	System.out.println("爱爱： "+string);	
     		if (string.matches("^背景技术.{0,}")) {
 				result=listIterator.next().text().trim();
 				if (string.length()>8) {
 					listIterator.previous();
					result=string.substring(4, string.length());
				}
 				break;
 			}
     		 if (!listIterator.hasNext()) {
					break;//退出
				}
     	}
     	return result;
     }
     /**
 	 * 发明内容
 	 */
     public String summaryInvention() {
    	String result="";
      	Elements elements= document.getElementsByTag("table").get(1).getElementsByTag("td");
      	ListIterator<Element> listIterator= elements.listIterator();
      	
      	listIterator.next();
      	while(listIterator.hasNext()) {
      		String string=listIterator.next().text().trim();
      		if (string.matches("^发明内容.{0,}")) {
      			String r=listIterator.next().text().trim();
   				result=result+r;
   				String str=r.trim();
   				while(!((str.length()==4&&str.startsWith("附图"))||
   						(str.matches("^.{0,5}实用新型.{0,}"))||
   						str.startsWith("具体实施方"))
   					 ) {
   				    if (!listIterator.hasNext()) {
						break;//退出
					}
   					 str=listIterator.next().text().trim();
   					
   					 if (!((str.length()==4&&str.startsWith("附图"))||
   	   						(str.matches("^.{0,5}实用新型.{0,}"))||
   	   					str.startsWith("具体实施方"))
      					 ) {
   						 result=result+str;
					}
   					
   				}
   				if (string.length()>8) {
   					listIterator.previous();
					result=string.substring(4, string.length());
				}
  				break;
  			}
      	}
      	return result;
     }
     /**
 	 * 实用新型内容
 	 */
     public String practicalContent() {
    	 String result="";
       	Elements elements= document.getElementsByTag("table").get(1).getElementsByTag("td");
       	ListIterator<Element> listIterator= elements.listIterator();
       	listIterator.next();
       	while(listIterator.hasNext()) {
       		String string=listIterator.next().text().trim();
       		if (string.matches("^.{0,5}实用新型.{0,}")) {
   				String r=listIterator.next().text().trim();
   				result=result+r;
   				String str=r.trim();
   				
   				
   				while(!((str.matches("^附图.{0,}"))||
   						(str.matches("^.{0,5}实用新型.{0,}"))||
   						str.startsWith("具体实施方"))
  					 ) {
   					if (!listIterator.hasNext()) {
						break;//退出
					}
   					 str=listIterator.next().text().trim();
   					
   					 if (!((str.matches("^附图.{0,}"))||
   	   						(str.matches("^.{0,5}实用新型.{0,}"))||
      						 str.startsWith("具体实施方"))
      					 ) {
   						 result=result+str;
					}
   				
   				}
   				if (string.length()>15) {
   					listIterator.previous();
   					result=string.substring(string.indexOf("实用新型")+2, string.length());
   				}
   				break;
   			}
       		
       	}
  //  	System.out.println("草拟吗3");	
       	return result;
     }
     /**
 	 * 附图说明
 	 */
     public String descriptionDrawings() {
    	 String result="";
        	Elements elements= document.getElementsByTag("table").get(1).getElementsByTag("td");
        	ListIterator<Element> listIterator= elements.listIterator();
        	listIterator.next();
        	while(listIterator.hasNext()) {
        		String str=listIterator.next().text().trim();
        		if (str.matches("^附图.{0,}")) {
        			String r=listIterator.next().text().trim();
       				result=result+r;
       				while(!r.trim().startsWith("具体实施方")) {
       					if (!listIterator.hasNext()) {
       						break;//退出
      					}
       					 r=listIterator.next().text().trim();
       					 if (!r.trim().equals("具体实施方")) {
       						 result=result+r;
    					}
       					
       				}
    				if (str.length()>8) {
    					listIterator.previous();
						result=str.substring(4, str.length());
					}
    				break;
    			}
        		if (!listIterator.hasNext()) {
					break;//退出
				}
        	}
        	return result;
     }
     /**
 	 * 具体实施方式/方案
 	 */
     public String specificMethods() {
    	 String result="";
     	Elements elements= document.getElementsByTag("table").get(1).getElementsByTag("td");
     	ListIterator<Element> listIterator= elements.listIterator();
     	listIterator.next();
     	while(listIterator.hasNext()) {
     		String juti=listIterator.next().text().trim();
     		if (juti.startsWith("具体实施方")) {
     			
 				//result=listIterator.next().text().trim();
     			
 				while(listIterator.hasNext()) {
 					result=result+listIterator.next().text().trim();
 				}
 				if (result.equals("")) {
					result=juti.substring(5, juti.length());
				}
 				break;
 			}
     		if (!listIterator.hasNext()) {
				break;//退出
			}
     	}
     	return result;
     }
     
     public static void main(String[] args) {
    	 String str="" + 
    	 		"技术领域本实用新型涉及的是适用于工业生产或城市生活污水处理中，对污水池内液位测量的一种智能污水液位控制器。";
    	System.out.println(str.matches("^.{0,10}实用新型.{0,}")); ;
	}
}
