

/**
 *
 */
package com.huatu.tb.category.service;

import java.util.List;
import java.util.Map;

import com.huatu.core.exception.HttbException;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;

/**
 * @ClassName: CategoryService
 * @Description: 章节知识点 Service接口
 * @author LiXin
 * @date 2015年4月17日 下午4:31:11
 * @version 1.0
 *
 */
public interface CategoryService {
	/**
	 * 添加
	 * @param 对象
	 * @return
	 * @throws HttbException
	 */
	public boolean save(Category category)throws HttbException;

	/**
	 * 删除
	 * @param 对象
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(Category category)throws HttbException;
	/**
	 * 删除 通过ID
	 * @param 对象id
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(String id)throws HttbException;
	/**
	 * 修改
	 * @param condition
	 * @return
	 * @throws HttbException
	 */
	public boolean update(Category category) throws HttbException;

	 /**
	  * 获取全都数据
	  * @return  List
	  * @exception
	  * @since  1.0.0
	  */
	public List<Category> findAll()throws HttbException ;

	/**
	 * 查询集合
	 * @param condition 条件过滤Map
	 * @return
	 * @throws HttbException
	 */
	public List<Category> findList(Map<String, Object> condition) throws HttbException ;

	/**
	 * 批量保存章节试题关系
	 * saveBatchToCateQues
	 * @exception
	 * @param list
	 * @return
	 * @throws HttbException
	 */
	public boolean saveBatchToCateQues(List<CateQues> list) throws HttbException ;
	/**
	 *
	 * saveCateQues					(保存试题与知识点关系)
	 * 								(创建试题时需要保存试题和知识点关系)
	 * @param 		params			试题ID-知识点ID
	 * @throws 		HttbException
	 */
	public boolean saveCateQues(List<Object[]> params) throws HttbException ;

	/**
	 *
	 * getAllPid					(获取所有父节点ID)
	 * 								(需要根据当前节点ID获取父节点ID时调用)
	 * @param 		cid				当前知识分类节点ID
	 * @return						父节点ID集合
	 * @throws 		HttbException
	 */
	public List<String> getAllPid(String cid)throws HttbException;

	/**
	 *
	 * getRootId					(获取顺序答题排序知识分类id)
	 * 								(需要根据当前节点ID获取对应排序知识分类节点时调用)
	 * @param 		cid				当前知识分类节点ID
	 * @return						顺序答题排序知识节点id
	 * @throws 		HttbException
	 */
	public String getRootId(String cid)throws HttbException;

	/**
	 *
	 * getAllPid					(获取所有父节点ID)
	 * 								(需要根据当前节点ID获取父节点ID时调用)
	 * @param 		cid				当前知识分类节点ID
	 * @return						父节点ID集合
	 * @throws 		HttbException
	 */
	public List<String> getAllPidRedis(String cid) throws HttbException;
	/**
	 *
	 * getAllPid					(获取所有父节点ID)
	 * 								(需要根据当前节点ID获取父节点ID时调用)
	 * @param 		categorylist	知识列表
	 * @param 		cid				当前知识分类节点ID
	 * @return						父节点ID集合
	 * @throws 		HttbException
	 */
	public List<String> getAllPid(List<Category> categorylist ,String cid) throws HttbException;

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

	/**
	 *
	 * getRootCate					(获取知识节点根节点)
	 * 								(需要获取知识分类根节点时调用)
	 * @return
	 * @throws 		HttbException
	 */
	public List<Category> getRootCates() throws HttbException;
}
