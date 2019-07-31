package cn.edu.patent.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;
import lombok.ToString;
/**
 * 自定义词,为了方便,停用词也用这个！
 * @author:JXH
 * @date:2019年4月27日-下午5:24:04
 */
@Data
@ToString
public class word {
      private int id;
      /**
       * 词
       */
     // @ExcelProperty(value="词",index=0)
      private String content;
      /**
       * 词性
       */
      //@ExcelProperty(value="词性",index=1)
      private String partOfSpeech;
      /**
       * 权重
       */
     // @ExcelProperty(value="权重",index=2)
      private int weight;
      /**
       * 词属于哪一类
       */
     // @ExcelProperty(value="类别",index=3)
      private String type;
     
}
