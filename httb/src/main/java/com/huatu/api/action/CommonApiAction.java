/**
 * 项目名：				httb
 * 包名：				com.huatu.api.action
 * 文件名：				CommonApiAction.java
 * 日期：				2015年6月25日-上午10:47:45
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.huatu.api.service.CategoryApiService;
import com.huatu.api.service.PaperApiService;
import com.huatu.api.service.QuestionApiService;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.ApiUtil;
import com.huatu.api.vo.CategoryVo;
import com.huatu.api.vo.PaperVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.ascpectJ.apiLog.ApiAnnotation;
import com.huatu.tb.common.enums.AreaEnum;
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.common.util.TbCommonUtil;

/**
 * 类名称：				CommonApiAction
 * 类描述：
 * 创建人：				LiXin
 * 创建时间：			2015年6月25日 上午10:47:45
 * @version 		1.0
 */
@RequestMapping("/httbapi/common")
@Controller
@Scope("prototype")
public class CommonApiAction {

	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IRedisService redisService;
	@Autowired
	private CategoryApiService categoryApiService;
	@Autowired
	private PaperApiService paperApiService;
	@Autowired
	QuestionApiService questionApiService;

	/**
	 * 获取题库版本信息	【app端从一级分类进入 如公务员-国家公务员】
	 * getTbversions
	 * @exception
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getTbversions", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取题库版本信息	【app端从一级分类进入 如公务员-国家公务员】")
	public Message getTbversions(@RequestParam String license){
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				String version = (String) redisService.get(TaskMarkKeyUtil.CATE_VER_GATHER);
				message.setSuccess(true);
				message.setMessage("获取题库版本信息成功");
				message.setData(version);
				log.info("获取题库版本信息成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("获取题库版本信息失败");
				e.printStackTrace();
				log.error("获取题库版本信息失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,获取题库版本信息失败");
			log.error("请求参数不合法,获取题库菜单失败");
		}
		return message;
	}
	/**
	 * 获取题库基础数据初始化	【app端从一级分类进入 如公务员-国家公务员】
	 * initTbBaseData
	 * @exception
	 * @param id -- 分类id  如国家公务员
	 * @param userno -- 用户no
	 * @param area -- 地区
	 * @param iscategory -- 是否返回章节树  0 返回 1 不返回
	 * @param ismoni -- 返回返回模拟题列表 0 返回 1 不返回
	 * @param iszhen -- 返回返回真题列表 0 返回 1 不返回
	 * @param license -- 认证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/initTbBaseData", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取题库版本信息	【app端从一级分类进入 如公务员-国家公务员】")
	public Message initTbBaseData(@RequestParam  String id,
								@RequestParam(value = "userno", required = false) String userno,
								@RequestParam(value = "area", required = false) String area,
								@RequestParam(value = "iscategory", required = false) String iscategory,
								@RequestParam(value = "ismoni", required = false) String ismoni,
								@RequestParam(value = "iszhen", required = false) String iszhen,
								@RequestParam String license) throws Exception{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				Map<String,Object> tbmap = new HashMap<String, Object>();

				if(AppTypePropertyUtil.APP_TYPE ==300){
					if(AppTypePropertyUtil.NATIONWIDE.equals(id)){
						area = AreaEnum.CHINA.getCode();
					}
					id =AppTypePropertyUtil.GONGWUYUAN;
		        }
				//组织试卷查询条件
				Map<String,Object> filter = new HashMap<String, Object>();
				filter.put("attribute", "1");	//属性 0==>真题，1==>模拟题
				filter.put("tclassify", id);	//一级级分类

				if(CommonUtil.isNotNull(area)){
					filter.put("area", area);	//地区
				}
				filter.put("count", 20);	//条数
				filter.put("pageindex", "1");	//第几页
				//1 章节树
				if(CommonUtil.isNotNull(iscategory) && "0".equals(iscategory)){
					CategoryVo categoryvo = categoryApiService.getSubsetById(id, userno, area);
					tbmap.put("category", categoryvo);
				}

				//2 模拟题列表
				if(CommonUtil.isNotNull(ismoni) && "0".equals(ismoni)){
					List<PaperVo> moniList = paperApiService.getPaperList(filter);
					tbmap.put("moniList", moniList);
				}
				//3 真题列表
				if(CommonUtil.isNotNull(iszhen) && "0".equals(iszhen)){
					filter.put("attribute", "0");//属性 0==>真题，1==>模拟题
					List<PaperVo> zhenList = paperApiService.getPaperList(filter);
					tbmap.put("zhenList", zhenList);
				}

				if(CommonUtil.isNotNull(userno)){
					//4 错题树
					CategoryVo errorcate = questionApiService.getWrongQuestionCategory(id, userno, area);
					tbmap.put("errorCate", errorcate);
					//5 章节排名
					Map<String,String>  cateranking = new HashMap<String,String>();
					tbmap.put("cateranking", cateranking);
				}
				//6 版本
				String version = (String) redisService.get(TaskMarkKeyUtil.CATE_VER_GATHER);
				tbmap.put("version", version);
				JSONObject initht = JSONObject.fromObject(tbmap);
				message.setData(initht);
				message.setSuccess(true);
				message.setMessage("App端题库基础数据初始化成功");
				log.info("用户"+userno+"App端题库基础数据初始化成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("App端题库基础数据初始化失败");
				e.printStackTrace();
				throw new HttbException(this.getClass() + "用户"+userno+"App端题库基础数据初始化失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,App端题库基础数据初始化失败");
			log.error("用户"+userno+"请求参数不合法,App端题库基础数据初始化失败");
		}
		return message;
	}
	/**
	 * 测试页面
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String list(Model model) {
		String apitype = "";
		if(100 == AppTypePropertyUtil.APP_TYPE){
			apitype = "医疗";
		}else if(200 == AppTypePropertyUtil.APP_TYPE){
			apitype = "金融";
		}else if(300 == AppTypePropertyUtil.APP_TYPE){
			apitype = "公务员";
		}else if(400 == AppTypePropertyUtil.APP_TYPE){
			apitype = "教师";
		}else if(500 == AppTypePropertyUtil.APP_TYPE){
			apitype = "事业单位";
		}
		model.addAttribute("apinum",AppTypePropertyUtil.APP_TYPE);//状态
		model.addAttribute("apitype",apitype);//状态
		return "apiTest";
	}
}
