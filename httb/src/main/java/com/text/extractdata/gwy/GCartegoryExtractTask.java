package com.text.extractdata.gwy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.text.extractdata.gwy.util.GwyJdbcUtil;
import com.text.extractdata.vo.ZhangjieVo;

/**   
 * 类名称：				CategoryTask  
 * 类描述：  		            公务员--章节抓取
 * 创建人：				LiXin
 * 创建时间：			2015年6月3日 上午9:15:58  
 * @version 		1.0
 */
@Service
public class GCartegoryExtractTask {
	@Autowired
	private CategoryService categoryService;
	/**
	 * 抽取章节信息【不含知识点说明连接】
	 * execute  
	 * @exception
	 */
	public void execute() {
		List<ZhangjieVo> list = this.getZhangjieList();
		for(ZhangjieVo zv : list){
			Category cate = new Category();
			cate.setCid(zv.getId());
			cate.setCname(zv.getName());
			cate.setCpid(zv.getPid());
			cate.setCexplain(zv.getDescrp());
			cate.setCordernum(0);
			cate.setClevels(zv.getLevels());
			cate.setCreatetime(zv.getCreateTime());
			cate.setCreateuser(zv.getCreateUser());
			cate.setUpdatetime(zv.getUpdateTime());
			cate.setUpdateuser(zv.getUpdateUser());
			try {
				categoryService.save(cate);
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
		sql = "select PUKEY,name,descrp,prev_kp,BB103,BB105,BB106,BB108 ,node_rankfrom v_knowledge_point where bl_sub ='4'";// SQL语句
		db1 = new GwyJdbcUtil(sql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				ZhangjieVo zvo = new ZhangjieVo();
				String id = ret.getString(1);
				String name = ret.getString(2);
				String descrp = ret.getString(3);
				String pid = ret.getString(4);
				String levels  = ret.getString(8);
				System.out.println("{ id:\""+id+"\", pId:\""+pid+"\", name:\""+name+levels+"\"},");
				zvo.setId(id);
				zvo.setName(name);
				zvo.setPid(pid);
				zvo.setLevels(levels);
				zvo.setDescrp(descrp);
				zvo.setCreateTime(new Date());
				zvo.setCreateUser("admin");
				zvo.setUpdateTime(new Date());
				zvo.setUpdateUser("admin");
				zvo.setLevels(levels);
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
