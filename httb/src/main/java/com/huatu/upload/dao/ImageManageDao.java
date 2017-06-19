package com.huatu.upload.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.enums.DeleteFlag;

@Component
public class ImageManageDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao ;

	/** 插入sql */
	private static final String insertSql = "INSERT INTO httb.HTIMAGE (aid, imgurl, imgname, newimgname, fromyear, tombstone, createuser, createtime) VALUES (?,?,?,?,?,?,?,?);";
	/** 删除sql */
	private static final String deleteSql = "delete from  httb.HTIMAGE  where aid = ? ;";
	/** 修改sql */
	private static final String updateSql = "UPDATE httb.HTIMAGE SET imgname = ?, newimgname = ?, fromyear = ?, imgurl = ? , updateUser = ? ,updatetime = ? WHERE aid = ? ;";
	/**根据ID查询图片*/
	private static final  String selectByIdSql = "SELECT aid, imgurl, imgname, newimgname, fromyear, createtime FROM httb.HTIMAGE where tombstone = '" + DeleteFlag.NO.getDeleteFlag() + "' and aid = ? ;";
	/**条件查询SQL*/
	private static final  String selectConditionSql =  "SELECT aid, imgurl, imgname, newimgname, fromyear, createtime FROM httb.HTIMAGE where tombstone = '" + DeleteFlag.NO.getDeleteFlag() + "' ";

	/**
	 *
	 * delete						(根据ID删除图片)
	 * @param 		params			图片ID
	 * @throws 		HttbException
	 */
	public Object[] delete(Object... params) throws HttbException {
		return noSqlBaseDao.delete(deleteSql,params);
	}

	/**
	 *
	 * insert						(添加图片)
	 * @param 		params			图片信息参数
	 * @throws 		HttbException
	 */
	public Object[] insert(Object... params) throws HttbException {
		return noSqlBaseDao.insert(insertSql,params);
	}

	/**
	 *
	 * update						(修改图片)
	 * @param	 	params			图片信息参数
	 * @throws 		HttbException
	 */
	public Object[] update(Object... params) throws HttbException {
		Object[] objs = null;
		try {
			objs = noSqlBaseDao.update(updateSql,params);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "根据ID修改图片时发生异常", e);
		}
		return objs;
	}

	/**
	 *
	 * getImageResultSet			(根据条件查询图片)
	 * @param 		condition		条件集合
	 * @throws 		HttbException
	 */
	public ResultSet getImageResultSet(Map<String, Object> condition) throws HttbException{
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(selectConditionSql);
		//条件参数
		Object[] params = new Object[condition.size()];
		//图片年份是否为空
		if(CommonUtil.isNotNull(condition.get("fromyear")) && CommonUtil.isNotEmpty(condition.get("fromyear").toString())){
			sbSQL.append(" and fromyear = ? ");
			params[0] = condition.get("fromyear");
		}
		//判断图片名称是否为空
		if(CommonUtil.isNotNull(condition.get("imgname")) && CommonUtil.isNotEmpty(condition.get("imgname").toString())){
			sbSQL.append("  and imgname = ? ");
			params[condition.size()-1] = condition.get("imgname");
		}
		sbSQL.append("ALLOW FILTERING;");
		return noSqlBaseDao.getResultSet(sbSQL.toString(),params);
	}

	/**
	 *
	 * get							(根据图片ID查询图片)
	 * @param 		params			图片ID
	 * @throws 		HttbException
	 */
	public ResultSet get(Object... params) throws HttbException{
		ResultSet resultSet = null;
		try {
			resultSet = noSqlBaseDao.getResultSet(selectByIdSql, params);
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询图片时发生异常", e);
		}
		return resultSet;
	}
}
