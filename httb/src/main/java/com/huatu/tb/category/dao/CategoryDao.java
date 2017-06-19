/**
 *
 */
package com.huatu.tb.category.dao;

import java.util.List;
import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.question.model.Question;

/**
 * @ClassName: CategoryDao
 * @Description: 知识点分类DAO
 * @author LiXin
 * @date 2015年4月24日 上午9:22:44
 * @version 1.0
 *
 */
public interface CategoryDao {
	/**
	 * 获取对象
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public Category get(String id)throws HttbException;
	/**
	 * 删除
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(String id) throws HttbException;
	/**
	 * 批量删除
	 * @return
	 * @throws HttbException
	 */
	public boolean deleteBatch(List<String> ids) throws HttbException;
	/**
	 * 插入
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean insert(Category category) throws HttbException;
	/**
	 * 修改
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean update(Category category) throws HttbException;
	/**
	 * 获取ResultSet对象
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public List<Row> getCategoryResultSet(Map<String,Object> filter) throws HttbException;

	/**
	 * 获取ResultSet对象分页
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public ResultSet getCategoryResultSet(Page page,Map<String,Object> filter) throws HttbException;
	/**
	 *
	 * saveQuesCate					(保存试题与知识点关系)
	 * 								(创建试题时需要保存试题和知识点关系)
	 * @param 		params			试题ID-知识点ID
	 * @throws 		HttbException
	 */
	public boolean saveCateQues(List<Object[]> params) throws HttbException ;
	/**
	 * 批量保存章节 和是试题关系对象
	 * insertBatchToCateQues
	 * @exception
	 * @param list
	 * @return
	 * @throws HttbException
	 */
	public boolean insertBatchToCateQues(List<CateQues> list) throws HttbException;

	/**
	 * 删除关系 -----通过知识点id和试题id
	 * 说明： 知识点 ID 必填【为第一联合主键】
	 *     试   题 ID 选填 【为第二联合主键】
	 * @exception
	 * @param cid -- 知识点Id
	 * @param qid -- 试题ID
	 * @return
	 * @throws HttbException
	 */
	public boolean deleteCateQues(String cid,String qid)throws HttbException;

}
