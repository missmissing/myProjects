package com.huatu.ou;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.analysis.init.InitSXRankAnalysis;
import com.huatu.api.task.CategoryTask;
import com.huatu.api.task.ExamAnalysisTask;
import com.huatu.api.task.MoniTiPaperTask;
import com.huatu.api.task.QuesToCateTask;
import com.huatu.api.task.QuestionAnalysisTask;
import com.huatu.api.task.ZhenTiPaperTask;
import com.huatu.core.action.BaseAction;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;

@Controller
@RequestMapping("directpublish")
public class DirectpublishAction extends BaseAction {

	@Autowired
	private CategoryTask categoryTask;
	@Autowired
	private MoniTiPaperTask moniTiPaperTask;
	@Autowired
	private QuesToCateTask quesToCateTask;
	@Autowired
	private ZhenTiPaperTask zhenTiPaperTask;
	@Autowired
	private ExamAnalysisTask examAnalysisTask;
	@Autowired
	private QuestionAnalysisTask questionAnalysisTask;
	@Autowired
	private InitSXRankAnalysis initSXRankAnalysis;
	

	/**
	 * 实时发布
	 * 
	 * @param model
	 *            参数集合
	 * @return String 返回页面路径
	 * @throws HttbException
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseBody
	public Message directpublish(HttpServletRequest request, String type) throws HttbException {
		Message message = new Message();
		try {

			switch (type) {
			case "categoryTask": {
				categoryTask.execute();
				break;
			}
			case "moniTiPaperTask": {
				moniTiPaperTask.execute();
				break;
			}
			case "quesToCateTask": {
				quesToCateTask.execute();
				break;
			}
			case "zhenTiPaperTask": {
				zhenTiPaperTask.execute();
				break;
			}
			case "examAnalysisTask": {
				examAnalysisTask.execute();
				break;
			}
			case "questionAnalysisTask": {
				questionAnalysisTask.execute();
				break;
			}
			case "initSXRankAnalysis": {
				initSXRankAnalysis.initSXRankAnalysis();
				break;
			}
			default:
				break;
			}
			message.setSuccess(true);
			message.setMessage("实时刷新成功");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("实时刷新失败！");
			throw new HttbException(this.getClass() + "实时刷新"+type+"失败", e);
		}
		return message;
	}

}
