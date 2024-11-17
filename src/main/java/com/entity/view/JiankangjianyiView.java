package com.entity.view;

import com.entity.JiankangjianyiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 健康建议
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-05-04 17:19:01
 */
@TableName("jiankangjianyi")
public class JiankangjianyiView  extends JiankangjianyiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public JiankangjianyiView(){
	}
 
 	public JiankangjianyiView(JiankangjianyiEntity jiankangjianyiEntity){
 	try {
			BeanUtils.copyProperties(this, jiankangjianyiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
