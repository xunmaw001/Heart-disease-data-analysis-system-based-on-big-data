package com.entity.model;

import com.entity.XinwenqikanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 新闻期刊
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2023-05-04 17:19:01
 */
public class XinwenqikanModel  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 新闻类型
	 */
	
	private String xinwenleixing;
		
	/**
	 * 新闻来源
	 */
	
	private String xinwenlaiyuan;
		
	/**
	 * 新闻内容
	 */
	
	private String xinwenneirong;
		
	/**
	 * 发布时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date fabushijian;
		
	/**
	 * 新闻封面
	 */
	
	private String xinwenfengmian;
				
	
	/**
	 * 设置：新闻类型
	 */
	 
	public void setXinwenleixing(String xinwenleixing) {
		this.xinwenleixing = xinwenleixing;
	}
	
	/**
	 * 获取：新闻类型
	 */
	public String getXinwenleixing() {
		return xinwenleixing;
	}
				
	
	/**
	 * 设置：新闻来源
	 */
	 
	public void setXinwenlaiyuan(String xinwenlaiyuan) {
		this.xinwenlaiyuan = xinwenlaiyuan;
	}
	
	/**
	 * 获取：新闻来源
	 */
	public String getXinwenlaiyuan() {
		return xinwenlaiyuan;
	}
				
	
	/**
	 * 设置：新闻内容
	 */
	 
	public void setXinwenneirong(String xinwenneirong) {
		this.xinwenneirong = xinwenneirong;
	}
	
	/**
	 * 获取：新闻内容
	 */
	public String getXinwenneirong() {
		return xinwenneirong;
	}
				
	
	/**
	 * 设置：发布时间
	 */
	 
	public void setFabushijian(Date fabushijian) {
		this.fabushijian = fabushijian;
	}
	
	/**
	 * 获取：发布时间
	 */
	public Date getFabushijian() {
		return fabushijian;
	}
				
	
	/**
	 * 设置：新闻封面
	 */
	 
	public void setXinwenfengmian(String xinwenfengmian) {
		this.xinwenfengmian = xinwenfengmian;
	}
	
	/**
	 * 获取：新闻封面
	 */
	public String getXinwenfengmian() {
		return xinwenfengmian;
	}
			
}
