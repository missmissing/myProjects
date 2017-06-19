/**
 *
 */
package com.huatu.tb.category.acion;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.api.version.util.VersionUtil;
import com.huatu.core.action.BaseAction;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.model.CategoryInputMessage;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.category.util.CategoryInput;
import com.huatu.tb.category.util.CategoryUtil;

/**
 * @ClassName: CategoryAction
 * @Description: 章节Action
 * @author LiXin
 * @date 2015年4月17日 下午4:29:55
 * @version 1.0
 *
 */
@Controller
@RequestMapping("category")
public class CategoryAction extends BaseAction{
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private TaskVersionService taskVersionService;

	@InitBinder("category")
	public void initBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("category.");
	}
	/**
	 * 进入章节知识点管理界面List
	 *
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws HttbException {
		List<Category> list = categoryService.findAll();
		JSONArray jsonArray = JSONArray.fromObject(CategoryUtil.tidyCategoryTree(list,null));
		model.addAttribute("treeJson",jsonArray);
		return "category/list";
	}

	/**
	 * 创建 章节节点
	 * @param request
	 * @param category
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Message save(HttpServletResponse response,HttpServletRequest request, Category category)throws HttbException{
		Message message = new Message();
		try {
			//当前登录者
			User currentUser = null;
			//获取session
			HtSession htsession = htSessionManager.getSession(request,response);
			//如果session不为空，则获取当前系统登录用户
			if(CommonUtil.isNotNull(htsession)){
				currentUser = (User) htsession.getAttribute(Constants.SESSION_CURRENT_USER);
			}
			category.setCreateuser(currentUser.getId());
			category.setCreatetime(new Date());
			categoryService.save(category);
			message.setSuccess(true);
			message.setMessage("节点保存成功！");
			TaskVersion taskVersion = new TaskVersion(Constants.CATEGORY,VersionUtil.getVersionNumber(),"","",new Date());
			taskVersionService.addVersion(taskVersion);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("节点保存失败！");
			e.printStackTrace();
			throw new HttbException(this.getClass() + "节点保存失败", e);
			}
		return message;
	}
	/**
	 * 修改章节节点
	 * @param request
	 * @param category
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Message update(HttpServletResponse response,HttpServletRequest request, Category category)throws HttbException{
		Message message = new Message();
		try {
			//当前登录者
			User currentUser = null;
			//获取session
			HtSession htsession = htSessionManager.getSession(request,response);
			//如果session不为空，则获取当前系统登录用户
			if(CommonUtil.isNotNull(htsession)){
				currentUser = (User) htsession.getAttribute(Constants.SESSION_CURRENT_USER);
			}
			category.setUpdateuser(currentUser.getId());//修改人
			category.setUpdatetime(new Date());
			categoryService.update(category);
			message.setSuccess(true);
			message.setMessage("节点修改成功！");
			TaskVersion taskVersion = new TaskVersion(Constants.CATEGORY,VersionUtil.getVersionNumber(),"","",new Date());
			taskVersionService.addVersion(taskVersion);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("节点修改失败！");
			e.printStackTrace();
			throw new HttbException(this.getClass() + "节点修改失败", e);
		}
		return message;
	}
	/**
	 * 删除选定章节节点
	 * @param request
	 * @param category
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Message delete(HttpServletResponse response,HttpServletRequest request, Category category)throws HttbException{
		Message message = new Message();
		try {
			boolean flag = categoryService.delete(category);
			if(flag)
			{
				message.setSuccess(true);
				message.setMessage("节点删除成功！");
				TaskVersion taskVersion = new TaskVersion(Constants.CATEGORY,VersionUtil.getVersionNumber(),"","",new Date());
				taskVersionService.addVersion(taskVersion);
			}
			else
			{
				message.setSuccess(false);
				message.setMessage("此节点已被引用，无法删除！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("节点删除失败！");
			e.printStackTrace();
			throw new HttbException(this.getClass() + "节点删除失败", e);
		}
		return message;
	}
	/**
	 * 批量删除
	 * @param request
	 * @param category
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBatch(HttpServletRequest request, Category category)throws HttbException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			jsonMap.put("success", true);
			jsonMap.put("message", "批量删除成功！");
		} catch (Exception e) {
			jsonMap.put("success", false);
			jsonMap.put("message", "批量删除失败！");
			e.printStackTrace();
			throw new HttbException(this.getClass() + "批量删除失败", e);
		}
		Gson gson = new Gson();
		return gson.toJson(jsonMap);
	}

	/**
	 *  获取章节树
	 * @param model
	 * @param checkedIds --已经选中的节点id 多个为‘,’隔开
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getCategoryTree", method = RequestMethod.POST)
	@ResponseBody
	public Message getCategoryTree(Model model,String checkedIds) throws HttbException {
		List<String> checkedList = new ArrayList<String>();
		if(CommonUtil.isNotNull(checkedIds)){
			String[] strs = checkedIds.split(",");
			for(String str : strs){
				checkedList.add(str);
			}

		}
		Message message = new Message();
		try{
			List<Category> list = categoryService.findAll();
			JSONArray jsonArray = JSONArray.fromObject(CategoryUtil.tidyCategoryTree(list,checkedList));
			message.setSuccess(true);
			message.setMessage("获取章节树成功");
			message.setData(jsonArray);
		}catch(Exception e){
			message.setSuccess(false);
			message.setMessage("获取章节树失败");
			throw new HttbException(this.getClass() + "获取章节树失败", e);
		}
		return message;
	}

	/**
	 *
	 * categoryinput				(跳到试题导入界面)
	 * @param 		model
	 * @return
	 * @throws 		HttbException
	 */
	@RequestMapping(value = "categoryinput")
	public String categoryinput(Model model) throws HttbException {
		return "category/categoryInput";
	}

	@RequestMapping(value = "upload", method = { RequestMethod.POST })
	@ResponseBody
	public Message upload(HttpServletResponse response, HttpServletRequest request, @RequestParam MultipartFile file) throws HttbException {
		Message message = new Message();
		// 取得上传的实际文件名称
		String filename = file.getOriginalFilename();
		// 记录上传文件名日志
		log.info("上传的实际文件名称：[" + filename + "]");
		// 取得文件后缀
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);

		//判断是否txt文档
		if(!suffix.equals("txt")){
			message.setSuccess(false);
			message.setMessage("导入失败:只能支持txt格式文档！");
			return message;
		}
		try {
			//当前登录者
			User currentUser = null;
			//获取session
			HtSession htsession = htSessionManager.getSession(request,response);
			//如果session不为空，则获取当前系统登录用户
			if(CommonUtil.isNotNull(htsession)){
				currentUser = (User) htsession.getAttribute(Constants.SESSION_CURRENT_USER);
			}

			CategoryInput categoryInput = new CategoryInput();
			CategoryInputMessage categoryInputMessage = categoryInput.readTxtFile(file.getInputStream(),currentUser.getId());
			List<Category> categories = categoryInputMessage.getCateList();
			//知识点集合不为空
			if(CommonUtil.isNotNull(categories)){
				//遍历知识点集合
				for (int i = 0; i < categories.size(); i++) {
					//添加试题
					categoryService.save(categories.get(i));
				}
			}

			message.setSuccess(true);
			message.setMessage("导入成功");
			message.setData(categoryInputMessage.getErrorName());
			TaskVersion taskVersion = new TaskVersion(Constants.CATEGORY,VersionUtil.getVersionNumber(),"","",new Date());
			taskVersionService.addVersion(taskVersion);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("导入失败:只能支持txt格式文档！");
			throw new HttbException(ModelException.FILE_INPUT_ERROR,e);
		}
		// 上传成功，记录临时文件保存路径日志
		log.info("文件上传成功：");
		// 返回文件路径
		return message;
	}

	/**
	 *
	 * download						(下载导入异常知识点)
	 *				 				(导入知识点异常时调用)
	 * @param 		fileName		文件名称
	 * @param 		request
	 * @param 		response
	 * @return
	 * @throws 		HttbException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/download")
	public String download(String fileName, HttpServletRequest request,
			HttpServletResponse response) throws HttbException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ fileName);

		CategoryInput categoryInput = new CategoryInput();
		try {
			//输出的错误知识分类信息
			StringBuffer sbout = new StringBuffer("");

			try {
				//存在redis缓存中的错误集合
				Object objerrors = iRedisService.get(fileName);
				if(CommonUtil.isNotNull(objerrors)){
					List<String> errors = null;
					errors = (List<String>) objerrors;
					sbout.append("=================================共有["+errors.size()+"]个知识节点错误！===========================\r\n\r\n");
					//输出日志记录
					sbout.append(categoryInput.outputErrors(errors));
				}


			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_REDIS_SEARCH,this.getClass()+"从redis中查询异常知识分类记录时发生异常",e);
			}


            byte[] buffer = sbout.toString().getBytes("utf-8");
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	         //写明要下载的文件的大小
	         response.setContentLength((int)buffer.length);
	         //设置附加文件名(解决中文乱码)
	         response.setHeader("Content-Disposition","attachment;filename=" + new String((fileName+".txt").getBytes("gbk"), "iso-8859-1"));

            toClient.write(buffer);
            toClient.flush();
            toClient.close();





		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new HttbException(this.getClass() + "下载导入异常知识点失败", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttbException(this.getClass() + "下载导入异常知识点失败", e);
		}
		// 返回值要注意，要不然就出现下面这句错误！
		return null;
	}

}
