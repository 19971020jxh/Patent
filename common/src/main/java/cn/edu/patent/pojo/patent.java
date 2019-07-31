package cn.edu.patent.pojo;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author:JXH
 * @date:2019年3月27日-下午2:16:41
 * 专利类，储存了专利解析后的全部信息！
 * Abstract.html中摘要附图。目前没有加进来！
 */
public class patent extends BaseRowModel{
	/**
	 * id=-1,表示解析时出错,不会添加进数据库,比如找不到相应的html
	 */
	@Getter
	@Setter
	private int id;
	/**
	 * 如果保存失败，返回所在的zip
	 */
	@Getter
	@Setter
	private String zip;
	/**
	 * 申请号
	 * <br/>是否lucene索引：true
	 */
	@Getter
	@Setter
	@NotBlank
	@ExcelProperty(value="申请号",index=0)
	private String requestNumber;
	/**
	 * 发明名称
	 * <br/>是否lucene索引：true
	 */
	@Getter
	@Setter
	@ExcelProperty(value="发明名称",index=1)
	@NotBlank
	private String createThing;
	/**
	 * 公开号
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="公开号",index=2)
	@NotBlank(message="不允许为空！")
	private String publicationNumber;
	/**
	 * IPC分类号
	 * <br/>是否lucene索引：true
	 */
	@Getter
	@Setter
	@ExcelProperty(value="IPC分类号",index=3)
	@NotBlank(message="不允许为空！")
	private String ipc;
	/**
	 * 发明人
	 * <br/>是否lucene索引：true
	 */
	@Getter
	@Setter
	@ExcelProperty(value="发明人",index=4)
	@NotBlank(message="不允许为空！")
	private String createPeople;
    /**
     * 申请日
     * <br/>是否lucene索引：true
     */
	@Getter
	@Setter
	@ExcelProperty(value="申请日",index=5)
	@NotBlank(message="不允许为空！")
	@Pattern(regexp="[0-9]{4}(.|/|-)[0-9]{1,2}(.|/|-)[0-9]{1,2}",message="我们支持以下时间格式：YYYY-HH-MM或YYYY.HH.MM或YYYY/HH/MM")
	private String filingDate;
	/**
	 * 公开日
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="公开日",index=6)
	@NotBlank(message="不允许为空！")
	@Pattern(regexp="[0-9]{4}(.|/|-)[0-9]{1,2}(.|/|-)[0-9]{1,2}",message="我们支持以下时间格式：YYYY-HH-MM或YYYY.HH.MM或YYYY/HH/MM")
	private String publicationDate;
	/**
	 * 申请人
	 * <br/>是否lucene索引：true
	 */
	@Getter
	@Setter
	@ExcelProperty(value="申请人",index=7)
	@NotBlank(message="不允许为空！")
	private String proposer;
	/**
	 * 优先权号
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="优先权号",index=8)
	private String priorityNumber;
	/**
	 * 摘要
	 * <br/>是否lucene索引：true
	 */
	@Getter
	@Setter
	@ExcelProperty(value="摘要",index=9)
	private List<String> remark;
	/**
	 * 添加,或修改,用于暂时储存表单摘要内容的,目的是方便,转换,无其他作用，请不要误用！
	 */
	@Getter
	@Setter
	@NotBlank(message="不允许为空！")
	private String form_remark;
	/**
	 * 权利要求书
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="权利要求书",index=10)
	private List<String> profitRequest;
	/**
	 * 添加,或修改,用于暂时储存表单权利要求书内容的,目的是方便,转换,无其他作用，请不要误用！
	 */
	@Getter
	@Setter
	@NotBlank(message="不允许为空！")
	private String form_profitRequest;
	/**
	 * 技术领域
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="技术领域",index=11)
	@NotBlank(message="不允许为空！")
	private String  technicalField;
	/**
	 * 背景技术
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="背景技术",index=12)
	@NotBlank(message="不允许为空！")
	private String backgroundTechnology;
	/**
	 * 发明内容
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="发明内容",index=13)
	@NotBlank(message="不允许为空！")
	private String summaryInvention;
	/**
	 * 实用新型内容
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="实用新型内容",index=14)
	private String practicalContent;
	/**
	 * 附图说明
	 *  <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="附图说明",index=15)
	private String descriptionDrawings;
	/**
	 * 具体实施方式
	 * <br/>是否lucene索引：false
	 */
	@Getter
	@Setter
	@ExcelProperty(value="具体实施方式",index=16)
	private String specificMethods;
	@Getter
	@Setter
	@ExcelProperty(value="专利分词",index=17)
	private Set<String> analyzer;
	/**
	 * 已经存在,关键字表中的词.
	 */
	@Getter
	@Setter
	private Set<String> analyzersExist;
	/**
	 * 改善
	 */
	@ExcelProperty(value="改善",index=18)
	@Getter
	@Setter
	private String better;
	/**
	 * 恶化
	 */
	@ExcelProperty(value="恶化",index=19)
	@Getter
	@Setter
	private String worsen;
	@ExcelProperty(value="分类",index=20)
	@Getter
	@Setter
	private String patentType;
	@Override
	public String toString() {
		return "patent ["  + ", zip包=" + zip + ", 发明名称=" + createThing + ", 申请号=" + requestNumber
				+ ", 公开号=" + publicationNumber + ", IPC分类号=" + ipc + ", 发明人=" + createPeople
				+ ", 申请日=" + filingDate + ", 公开日=" + publicationDate + ", 申请人=" + proposer
				+ ", 优先权号=" + priorityNumber + ", 摘要=" + remark + ", 权利要求书=" + profitRequest
				+ ", 技术领域=" + technicalField + ", 背景技术=" + backgroundTechnology
				+ ", 发明内容=" + summaryInvention + ", 实用新型内容=" + practicalContent
				+ ", 附图说明=" + descriptionDrawings + ", 具体实施方式=" + specificMethods + "]";
	}
	/**
	 * 分词之前调用,把专利对象分词内容合并为一个String
	 * @return
	 */
	public String mergeToString() {
		return          createThing
				+ "," + StringUtils.join(remark, ",") 
				+ "," + StringUtils.join(profitRequest, ",")
				+ "," + technicalField 
				+ "," + backgroundTechnology
				+ "," + summaryInvention 
				+ "," + practicalContent
				+ "," + descriptionDrawings 
				+ "," + specificMethods
				;
	}
	
	
}
