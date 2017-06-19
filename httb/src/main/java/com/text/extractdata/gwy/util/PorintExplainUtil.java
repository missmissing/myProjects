/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.gongwuyuan  
 * 文件名：				PorintExplainUtil.java    
 * 日期：				2015年6月29日-上午10:06:30  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.gwy.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.text.extractdata.vo.ZhangjieVo;

/**   
 * 类名称：				PorintExplainUtil  
 * 类描述：  			知识点描述导入
 * 创建人：				LiXin
 * 创建时间：			2015年6月29日 上午10:06:30  
 * @version 		1.0
 */
@Service
public class PorintExplainUtil {
	@Autowired
	private CategoryService categoryService;
	public void execute() {
		List<ZhangjieVo> list = this.getZhangjieList();
		for(ZhangjieVo zv : list){
			Category cate = new Category();
			cate.setCid(zv.getId());
			cate.setCexplain(zv.getDescrp());
			try {
				categoryService.update(cate);
			} catch (HttbException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取章节列表
	 * getZhangjieList  
	 * @exception 
	 * @return
	 */
    private List<ZhangjieVo> getZhangjieList(){
    	List<ZhangjieVo> list = new ArrayList<ZhangjieVo>();
    	String sql = null;
		GwyJdbcUtil db1 = null;
		ResultSet ret = null;
		sql = "select id,kpcontent from ht_knowledge_point";// SQL语句
		db1 = new GwyJdbcUtil(sql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				ZhangjieVo zvo = new ZhangjieVo();
				String id = ret.getString(1);
				String descrp = ret.getString(2);
				System.out.println(descrp);
				zvo.setId(id);
				zvo.setDescrp(descrp);
				list.add(zvo);
			}// 显示数据
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }


}
