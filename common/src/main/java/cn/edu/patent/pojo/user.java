package cn.edu.patent.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * @author:JXH
 * @date:2019年6月25日-下午3:29:51
 * -用户类
 */
@Data
@ToString
public class user {
      
	private int id;
	private String name;
	private String password;
	/**
	 * 是否为管理员.
	 */
	private Boolean isroot;
	/**
	 * 最后登录
	 */
	private LocalDate lastTime;
	/**
	 * 上传次数.
	 */
	private int uploadCount;
	/**
	 * 下载次数.
	 */
	private int downCount;
	/**
	 * 能否使用.
	 */
	private Boolean canUse;
}
