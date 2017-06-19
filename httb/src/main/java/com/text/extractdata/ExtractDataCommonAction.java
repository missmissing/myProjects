/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.action  
 * 文件名：				CommonApiAction.java    
 * 日期：				2015年6月25日-上午10:47:45  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.util.AppTypePropertyUtil;
import com.text.extractdata.gwy.GPaperExtractTask;
import com.text.extractdata.gwy.GQuestionExtractTask;
import com.text.extractdata.js.JDan_QuestionExtractTask;
import com.text.extractdata.js.JFu_QuestionExtractTask;
import com.text.extractdata.js.JMo_PaperExtractTask;
import com.text.extractdata.js.JZhen_PaperExtractTask;
import com.text.extractdata.sydw.SDan_QuestionExtractTask;
import com.text.extractdata.sydw.SFu_QuestionExtractTask;
import com.text.extractdata.sydw.SZhen_PaperExtractTask;

/**   
 * 类名称：				CommonApiAction  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月25日 上午10:47:45  
 * @version 		1.0
 */
@RequestMapping("/httbapi/extractdata")
@Controller
@Scope("prototype")
public class ExtractDataCommonAction {
	
	public  Logger log = Logger.getLogger(this.getClass());
	/**公务员*/
	@Autowired
	private GQuestionExtractTask gQuestionExtractTask;//公务员试题导入服务
	@Autowired
	private GPaperExtractTask gPaperExtractTask;//公务员试卷导入服务
	/**教师*/
	@Autowired
	private JDan_QuestionExtractTask jDan_QuestionExtractTask;//教师单选题导入服务
	@Autowired
	private JFu_QuestionExtractTask jJFu_QuestionExtractTask;//教师复合题导入服务
	@Autowired
	private JMo_PaperExtractTask jMo_PaperExtractTask;//公务员模拟卷导入服务
	@Autowired
	private JZhen_PaperExtractTask jZhen_PaperExtractTask;//公务员真题卷卷导入服务
	/**事业单位*/
	@Autowired
	private SDan_QuestionExtractTask sDan_QuestionExtractTask;//事业单位单选题导入服务
	@Autowired
	private SFu_QuestionExtractTask sFu_QuestionExtractTask;//事业单位复合题导入服务
	@Autowired
	private SZhen_PaperExtractTask sZhen_PaperExtractTask;//事业单位真题卷卷导入服务
	
	
	
	
	/**
	 * 测试页面
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String list(Model model) {
		String apitype = "";
		if(300 == AppTypePropertyUtil.APP_TYPE){
			apitype = "公务员";
		}else if(400 == AppTypePropertyUtil.APP_TYPE){
			apitype = "教师";
		}else if(500 == AppTypePropertyUtil.APP_TYPE){
			apitype = "事业单位";
		}
		model.addAttribute("apinum",AppTypePropertyUtil.APP_TYPE);//状态
		model.addAttribute("apitype",apitype);//状态
		return "extractTest";
	}
	/**
	 * 抽取数据
	 * getdata  
	 * @exception 
	 * @param model
	 * @param pt -- 数据类型  0-试题   1-试卷
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/getdata", method = RequestMethod.POST)
	public Message getdata(Model model,String pt) throws HttbException {
		Message message = new Message();
		try{
			if(300 == AppTypePropertyUtil.APP_TYPE){
				if(pt.equals("0")){
					gQuestionExtractTask.executeDX();
					gQuestionExtractTask.executeFH();
				}else{
					gPaperExtractTask.executeMo();
					gPaperExtractTask.executeZhen();
				}
			}else if(400 == AppTypePropertyUtil.APP_TYPE){
				if(pt.equals("0")){
					jDan_QuestionExtractTask.executeDX();
					jJFu_QuestionExtractTask.executeFH();
				}else{
					jMo_PaperExtractTask.executeMo();
					jZhen_PaperExtractTask.executeZhen();
				}
			}else if(500 == AppTypePropertyUtil.APP_TYPE){
				if(pt.equals("0")){
					sDan_QuestionExtractTask.executeDX();
					sFu_QuestionExtractTask.executeFH();
				}else{
					sZhen_PaperExtractTask.executeZhen();
				}
			}
			message.setSuccess(true);
			message.setMessage("数据提取成功");
		}catch(Exception e){
			message.setSuccess(false);
			message.setMessage("数据提取失败");
		}
		
		return message;
	}
}
