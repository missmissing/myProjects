/**
 *
 */
package com.huatu.tb.category.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.huatu.core.model.Tree;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.model.Category;

/**
 * @ClassName: CategoryUtil
 * @Description: 服务于 章节管理的工具类
 * @author LiXin
 * @date 2015年4月17日 下午4:33:14
 * @version 1.0
 *
 */
public class CategoryUtil {

	/**
	 * 组装 章节 知识点  树
	 * @param list --章节列表
	 * @param checkedIds --已经选中的节点ID列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Tree> tidyCategoryTree(List<Category> list,List<String> checkedIds){
		List<Tree> treeList = new ArrayList<Tree>();
		for(Category cg : list){
			Tree tree = new Tree();
			tree.setId(cg.getCid());
			tree.setName(cg.getCname());
			//tree.setExplain(cg.getCexplain());
			tree.setOrderNum(cg.getCordernum() == null ? 0 : cg.getCordernum());
			String pid = cg.getCpid();
			if(CommonUtil.isNull(pid)){
				pid = "0" ;//根节点
				tree.setOpen("true");
			}
			if(CommonUtil.isNotNull(checkedIds) && checkedIds.contains(cg.getCid())){
				tree.setChecked("true");
			}
			tree.setpId(pid);
			tree.setLevel(cg.getClevels());
			treeList.add(tree);
		}
		ComparatorTree comparator = new ComparatorTree();
		Collections.sort(treeList, comparator);
		//输出知识节点到控制台
	/*	for(Tree tree : treeList){
					//System.out.println("{ id:\""+tree.getId()+"\", pId:\""+tree.getpId()+"\", name:\""+tree.getId()+"-"+tree.getName()+"-"+tree.getLevel()+"\"},");
					System.out.println("{ id:\""+tree.getId()+"\", pId:\""+tree.getpId()+"\", name:\""+tree.getName()+"\"},");
		}*/
		return treeList;
	}

}
