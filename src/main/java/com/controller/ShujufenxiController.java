package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ShujufenxiEntity;
import com.entity.view.ShujufenxiView;

import com.service.ShujufenxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

/**
 * 数据分析
 * 后端接口
 * @author 
 * @email 
 * @date 2023-05-04 17:19:01
 */
@RestController
@RequestMapping("/shujufenxi")
public class ShujufenxiController {
    @Autowired
    private ShujufenxiService shujufenxiService;
    @Autowired
    private JavaSparkContext javaSparkContext;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShujufenxiEntity shujufenxi,
		HttpServletRequest request){
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();

		PageUtils page = shujufenxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shujufenxi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShujufenxiEntity shujufenxi, 
		HttpServletRequest request){
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();

		PageUtils page = shujufenxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shujufenxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShujufenxiEntity shujufenxi){
       	EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shujufenxi, "shujufenxi")); 
        return R.ok().put("data", shujufenxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShujufenxiEntity shujufenxi){
        EntityWrapper< ShujufenxiEntity> ew = new EntityWrapper< ShujufenxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shujufenxi, "shujufenxi")); 
		ShujufenxiView shujufenxiView =  shujufenxiService.selectView(ew);
		return R.ok("查询数据分析成功").put("data", shujufenxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShujufenxiEntity shujufenxi = shujufenxiService.selectById(id);
        return R.ok().put("data", shujufenxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShujufenxiEntity shujufenxi = shujufenxiService.selectById(id);
        return R.ok().put("data", shujufenxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShujufenxiEntity shujufenxi, HttpServletRequest request){
    	shujufenxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shujufenxi);
        shujufenxiService.insert(shujufenxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShujufenxiEntity shujufenxi, HttpServletRequest request){
    	shujufenxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shujufenxi);
        shujufenxiService.insert(shujufenxi);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ShujufenxiEntity shujufenxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shujufenxi);
        shujufenxiService.updateById(shujufenxi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shujufenxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();
        List<Map<String, Object>> result = shujufenxiService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        List<Map<String, Object>> result2 = new ArrayList<Map<String,Object>>();
        for(Map<String, Object> m : result) {
            List<Tuple2<String, Object>> data = new ArrayList<>();
            for(String s : m.keySet()) {
                data.add(new Tuple2<>(s, m.get(s)));
            }
            JavaPairRDD<String, Object> originRDD = javaSparkContext.parallelizePairs(data);
            result2.add(originRDD.collectAsMap());
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计(多)）
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = shujufenxiService.selectValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();
        List<Map<String, Object>> result = shujufenxiService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        List<Map<String, Object>> result2 = new ArrayList<Map<String,Object>>();
        for(Map<String, Object> m : result) {
            List<Tuple2<String, Object>> data = new ArrayList<>();
            for(String s : m.keySet()) {
                data.add(new Tuple2<>(s, m.get(s)));
            }
            JavaPairRDD<String, Object> originRDD = javaSparkContext.parallelizePairs(data);
            result2.add(originRDD.collectAsMap());
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计）时间统计类型(多)
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,@RequestParam String yColumnNameMul,HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = shujufenxiService.selectTimeStatValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        EntityWrapper<ShujufenxiEntity> ew = new EntityWrapper<ShujufenxiEntity>();
        List<Map<String, Object>> result = shujufenxiService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        List<Map<String, Object>> result2 = new ArrayList<Map<String,Object>>();
        for(Map<String, Object> m : result) {
            List<Tuple2<String, Object>> data = new ArrayList<>();
            for(String s : m.keySet()) {
                data.add(new Tuple2<>(s, m.get(s)));
            }
            JavaPairRDD<String, Object> originRDD = javaSparkContext.parallelizePairs(data);
            result2.add(originRDD.collectAsMap());
        }
        return R.ok().put("data", result2);
    }






}
