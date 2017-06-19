/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.service.impl  
 * 文件名：				PaperServiceImpl.java    
 * 日期：				2015年5月11日-下午4:57:47  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.paper.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Page;
import com.huatu.tb.paper.dao.PaperDao;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.service.PaperService;
import com.huatu.tb.question.model.Question;

/**   
 * 类名称：				PaperServiceImpl  
 * 类描述：  			试卷
 * 创建人：				LiXin
 * 创建时间：			2015年5月11日 下午4:57:47  
 * @version 		1.0
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {
	@Autowired
	private PaperDao pargerDao;
	@Override
	public boolean save(Paper paper) throws HttbException {
		return pargerDao.insert(paper);
	}

	@Override
	public boolean delete(String id) throws HttbException {
		return pargerDao.delete(id);
	}

	@Override
	public boolean deleteToIds(List<String> list) throws HttbException {
		return pargerDao.deleteBatch(list);
	}

	@Override
	public boolean update(Paper paper) throws HttbException {
		return pargerDao.update(paper);
	}
	@Override
	public boolean updateQuesList(Paper paper) throws HttbException {
		return pargerDao.updateQuesList(paper);
	}

	@Override
	public Paper get(String id) throws HttbException {
		return pargerDao.get(id);
	}

	@Override
	public List<Paper> findAll() throws HttbException {
		return null;
	}

	@Override
	public List<Paper> findList(Map<String, Object> condition) throws HttbException {
		List<Paper> list = new ArrayList<Paper>();
		ResultSet resultSet  = pargerDao.getPaperResultSet(condition);
		try{
			for (Row row : resultSet) {
				Paper paper = new Paper();
				paper.setPid(row.getString("pid") );
				paper.setPsubtitle(row.getString("psubtitle") );
				paper.setPtitle(row.getString("ptitle") );
				paper.setPyear(row.getString("pyear") );
				paper.setPqids(row.getList("pqids",String.class));
				paper.setPareas(row.getList("pareas",String.class));
				paper.setPtimelimit(row.getInt("ptimelimit"));
				paper.setPorgs(row.getList("porgs",String.class));
				paper.setPcategorys(row.getList("pcategorys",String.class));
				paper.setPexamtype(row.getList("pexamtype",String.class));
				paper.setPorgs(row.getList("porgs",String.class));
				paper.setPattribute(row.getString("pattribute") );
				paper.setPattrs(row.getMap("pattrs", String.class,Object.class));
				paper.setPstatus(row.getString("pstatus") );
				paper.setTombstone(row.getString("tombstone") );
				paper.setUpdatetime(row.getDate("updatetime"));
				paper.setUpdateuser(row.getString("updateuser") );
				paper.setCreatetime(row.getDate("createtime") );
				paper.setCreateuser(row.getString("createuser") );
				list.add(paper);
			}
		}catch(Exception e){
			throw new HttbException(ModelException.TYPE_TRANSFORM, this.getClass() + "ResultSet转对象时发生异常", e);
		}
		
		return list;
	}

	@Override
	public Page<Paper> findPage(Map<String, Object> condition) throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

}
