/**
 *
 */
package com.huatu.tb.category.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.api.task.service.RCategoryService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.dao.CategoryDao;
import com.huatu.tb.category.enums.DeleteFlag;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.question.dao.QuestionDao;
import com.huatu.tb.userfavor.dao.UserFavorDao;

/**
 * @ClassName: CategoryServiceImpl
 * @Description: 章节知识点分类Service
 * @author LiXin
 * @date 2015年4月17日 下午4:33:53
 * @version 1.0
 *
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private  RCategoryService rCategoryService;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private UserFavorDao userFavorDao;

	@Override
	public boolean save(Category category) throws HttbException {
		boolean flag = (boolean)categoryDao.insert(category);
		//如果添加成功，刷新缓存更新标记
		if(flag){
			Constants.REDIS_CATEGORY_REFRESHFLAG = true;
		}
		return flag;
	}

	@Override
	public boolean delete(Category category) throws HttbException {
		
		// 删除成功true， 无法删除false 
		boolean flag = false;
		/**
		 * 查询此知识点是否已被使用
		 */
		//试题表里引用次数
		long c1 = questionDao.queryCategoryUsedCount(category);
		//答题记录表里引用次数
		long c2 = questionDao.queryHthueshisCatUsedCount(category);
		//错题记录表里引用次数
		long c3 = questionDao.queryHterrorCatUsedCount(category);
		//用户收藏表里引用次数
		
		long categoryNotUsed = DeleteFlag.CATEGORYNOTUSED.getCategoryNotUsed();
		
		if(!(categoryNotUsed==c1&&categoryNotUsed==c2&&categoryNotUsed==c3))
		{
			return flag;
		}
		
		flag = (boolean)categoryDao.delete(category.getCid());
		//如果删除成功，刷新缓存更新标记
		if(flag){
			Constants.REDIS_CATEGORY_REFRESHFLAG = true;
		}
		return flag;
	}

	@Override
	public boolean delete(String id) throws HttbException {
		Boolean flag = categoryDao.delete(id);
		//如果删除成功，刷新缓存更新标记
		if(flag){
			Constants.REDIS_CATEGORY_REFRESHFLAG = true;
		}
		return flag;
	}

	@Override
	public boolean update(Category category) throws HttbException {
		boolean flag = categoryDao.update(category);
		//如果修改成功，刷新缓存更新标记
		if(flag){
			Constants.REDIS_CATEGORY_REFRESHFLAG = true;
		}
		return flag;
	}

	@Override
	public List<Category> findAll() throws HttbException {
		List<Category> lists = new ArrayList<Category>();
		List<Row> rows  = categoryDao.getCategoryResultSet(null);
		for (Row row : rows) {
			Category category = new Category();
			category.setCid(row.getString("cid"));
			category.setCname(row.getString("cname"));
			category.setCpid(row.getString("cpid"));
			category.setCexplain(row.getString("cexplain"));
			category.setClevels(row.getString("clevels"));
			category.setCordernum(row.getInt("cordernum"));
			category.setCreatetime(row.getDate("createtime"));
			category.setCreateuser(row.getString("createuser"));
			category.setUpdatetime(row.getDate("updatetime"));
			category.setUpdateuser(row.getString("updateuser"));
			category.setTombstone(row.getString("tombstone"));
			lists.add(category);
		}
		return lists;
	}

	@Override
	public List<Category> findList(Map<String, Object> condition) throws HttbException {
		List<Category> lists = new ArrayList<Category>();
		List<Row> rows  = categoryDao.getCategoryResultSet(condition);
		// 遍历结果集并且赋值
		for (Row row : rows) {
			Category category = new Category();
			category.setCid(row.getString("cid"));
			category.setCname(row.getString("cname"));
			category.setCpid(row.getString("cpid"));
			category.setCexplain(row.getString("cexplain"));
			category.setCordernum(row.getInt("cordernum"));
			category.setCreatetime(row.getDate("createtime"));
			category.setCreateuser(row.getString("createuser"));
			category.setUpdatetime(row.getDate("updatetime"));
			category.setUpdateuser(row.getString("updateuser"));
			category.setTombstone(row.getString("tombstone"));
			lists.add(category);
		}
		return lists;
	}

	@Override
	public boolean saveCateQues(List<Object[]> params) throws HttbException {
		return categoryDao.saveCateQues(params);
	}

	/**
	 *
	 * getAllPid					(获取所有父节点ID)
	 * 								(需要根据当前节点ID获取父节点ID时调用)
	 * @param 		cid				当前知识分类节点ID
	 * @return						父节点ID集合
	 * @throws 		HttbException
	 */
	@Override
	public List<String> getAllPid(String cid) throws HttbException {
		List<String> pidList = null;
		//判断当前节点是否为空
		if(CommonUtil.isNotEmpty(cid)){
			List<Category> cateList = this.findAll();
			//知识分类ID和父节点ID集合
			Map<String, String> cpidMap = new HashMap<String, String>();
			//遍历知识分类集合
			for (int i = 0; i < cateList.size(); i++) {
				//将分类节点ID和父节点ID保存到MAP中
				cpidMap.put(cateList.get(i).getCid(), cateList.get(i).getCpid());
			}
			pidList = new ArrayList<String>();
			//添加当前节点ID
			pidList.add(cid);
			//遍历标识
			boolean flag = true;
			//循环添加父节点
			while (flag) {
				//父节点ID
				cid = cpidMap.get(cid);
				//如果父节点不是根节点,则保存到集合中
				if(CommonUtil.isNotNull(cid) && !cid.equals("0")){
					pidList.add(cid);
				}else{
					flag = false;
				}
			}
		}
		return pidList;
	}

	@Override
	public List<String> getAllPid(List<Category> cateList, String cid) throws HttbException {
		List<String> pidList = null;
		//判断当前节点是否为空
		if(CommonUtil.isNotEmpty(cid)){
			//知识分类ID和父节点ID集合
			Map<String, String> cpidMap = new HashMap<String, String>();
			//遍历知识分类集合
			for (int i = 0; i < cateList.size(); i++) {
				//将分类节点ID和父节点ID保存到MAP中
				cpidMap.put(cateList.get(i).getCid(), cateList.get(i).getCpid());
			}
			pidList = new ArrayList<String>();
			//添加当前节点ID
			pidList.add(cid);
			//遍历标识
			boolean flag = true;
			//循环添加父节点
			while (flag) {
				//父节点ID
				cid = cpidMap.get(cid);
				//如果父节点不是根节点,则保存到集合中
				if(CommonUtil.isNotNull(cid) && !cid.equals("0")){
					pidList.add(cid);
				}else{
					flag = false;
				}
			}
		}
		return pidList;
	}
	/**
	 *
	 * getAllPid					(获取所有父节点ID)
	 * 								(需要根据当前节点ID获取父节点ID时调用)
	 * @param 		cid				当前知识分类节点ID
	 * @return						父节点ID集合
	 * @throws 		HttbException
	 */
	@Override
	public List<String> getAllPidRedis(String cid) throws HttbException {
		//知识分类对象
		List<Category> categorys = (List<Category>) iRedisService.get(Constants.ANALYSIS_CATEGORY);
		//如果缓存中为空，则从数据库中加载并存入缓存
		if(!CommonUtil.isNotNull(categorys)){
			categorys = findAll();
			//存入缓存中
			iRedisService.put(Constants.ANALYSIS_CATEGORY, categorys);
		}
		List<String> pidList = null;
		//判断当前节点是否为空
		if(CommonUtil.isNotEmpty(cid)){
			//知识分类ID和父节点ID集合
			Map<String, String> cpidMap = new HashMap<String, String>();
			//遍历知识分类集合
			for (int i = 0; i < categorys.size(); i++) {
				//将分类节点ID和父节点ID保存到MAP中
				cpidMap.put(categorys.get(i).getCid(), categorys.get(i).getCpid());
			}
			pidList = new ArrayList<String>();
			//添加当前节点ID
			pidList.add(cid);
			//遍历标识
			boolean flag = true;
			//循环添加父节点
			while (flag) {
				//父节点ID
				cid = cpidMap.get(cid);
				//如果父节点不是根节点,则保存到集合中
				if(CommonUtil.isNotNull(cid) && !cid.equals("0")){
					pidList.add(cid);
				}else{
					flag = false;
				}
			}
		}
		return pidList;
	}

	@Override
	public boolean saveBatchToCateQues(List<CateQues> list) throws HttbException {
		return categoryDao.insertBatchToCateQues(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRootId(String cid) throws HttbException {
		String cateRoot = null;
		//判断当前节点是否为空
		if(CommonUtil.isNotEmpty(cid)){
			List<Category> cateList = null;
			//知识分类对象
			Object objCates = iRedisService.get(Constants.ANALYSIS_CATEGORY);
			//如果为空，则去数据库查找
			if(CommonUtil.isNull(objCates)){
				cateList = this.findAll();
				//将查询出的分类集合放入缓存
				iRedisService.put(Constants.ANALYSIS_CATEGORY,cateList);
			}else{
				cateList = (List<Category>) objCates;
			}

			//知识分类对象
			Object objPids = iRedisService.get("ANALYSIS_CATEGORY_PIDMAP");
			Map<String, Category> cateMap = null;
			//如果存在则从缓存中取
			if(CommonUtil.isNotNull(objPids)){
				cateMap = (Map<String, Category>) objPids;
			}else{
				//知识分类ID和父节点ID集合
				cateMap = new HashMap<String, Category>();
				//遍历知识分类集合
				for (int i = 0; i < cateList.size(); i++) {
					//将分类节点ID和父节点ID保存到MAP中
					cateMap.put(cateList.get(i).getCid(), cateList.get(i));
				}
				iRedisService.putEX("ANALYSIS_CATEGORY_PIDMAP", cateMap);
			}
			//cateRoot
			String rootLevel = "";
			if(AppTypePropertyUtil.APP_TYPE == 300){
				rootLevel = "2";
			}else{
				rootLevel = "3";
			}
			//知识分类层级
			String level = null;
			//父节点Id
			String pid = null;
			//遍历标识
			boolean flag = true;
			Category cate = cateMap.get(cid);
			//循环添加父节点
			while (flag) {

				if(CommonUtil.isNotNull(cate)){
					//当前节点层级
					level = cate.getClevels();
					pid = cate.getCpid();

					//如果当前知识分类节点层级等于需要排序的层级，则返回当前知识分类节点id
					if(CommonUtil.isNotEmpty(level) && level.trim().equals(rootLevel)){
						cateRoot = cate.getCid();
						flag = false;
					}else if(CommonUtil.isNotNull(pid) && pid.equals("0")){
						//如果父节点不是根节点,则保存到集合中
						flag = false;
					}else{
						cate = cateMap.get(pid);
					}
				}else{
					//如果父节点不是根节点,则保存到集合中
					flag = false;
				}
			}
		}
		return cateRoot;
	}

	/**
	 *
	 * getRootCate					(获取知识节点根节点)
	 * 								(需要获取知识分类根节点时调用)
	 * @return
	 * @throws 		HttbException
	 */
	@Override
	public List<Category> getRootCates() throws HttbException {
		Map<String, Object> filter = new HashMap<String, Object>();
		List<Category> cateList = null;

		//知识分类根节点对象
		Object objCates = iRedisService.get(Constants.ANALYSIS_CATEGORY_ROOTNODE);
		//如果为空，则去数据库查找
		if(CommonUtil.isNull(objCates)){
			cateList = new ArrayList<Category>();
			//默认查第三级
			String rootLevel = "3";
			if(AppTypePropertyUtil.APP_TYPE == 300){
				rootLevel = "2";
			}else{
				rootLevel = "3";
			}
			//加入级别判断
			filter.put("clevels", rootLevel);
			List<Row> rowlist = categoryDao.getCategoryResultSet(filter);
			//遍历知识点集合
			for (Row row : rowlist) {
				Category category = new Category();
				category.setCid(row.getString("cid"));
				category.setCname(row.getString("cname"));
				category.setCpid(row.getString("cpid"));
				category.setCexplain(row.getString("cexplain"));
				category.setCordernum(row.getInt("cordernum"));
				category.setCreatetime(row.getDate("createtime"));
				category.setCreateuser(row.getString("createuser"));
				category.setUpdatetime(row.getDate("updatetime"));
				category.setUpdateuser(row.getString("updateuser"));
				category.setTombstone(row.getString("tombstone"));
				cateList.add(category);
			}
			//将查询出的分类集合放入缓存
			iRedisService.putEX(Constants.ANALYSIS_CATEGORY_ROOTNODE,cateList);
		}else{
			cateList = (List<Category>) objCates;
		}
		return cateList;
	}

	@Override
	public boolean deleteCateQues(String cid, String qid) throws HttbException {

		return categoryDao.deleteCateQues(cid, qid);
	}





}