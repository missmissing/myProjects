package com.huatu.tb.quesInput.action;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.SpringContextHolder;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.service.PaperService;
import com.huatu.tb.quesInput.model.QuesConstant;
import com.huatu.tb.quesInput.model.QuesError;
import com.huatu.tb.quesInput.model.QuesInputMessage;
import com.huatu.tb.quesInput.service.QuesInput;
import com.huatu.tb.quesInput.service.QuesOutput;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;
import com.sun.org.apache.bcel.internal.generic.BIPUSH;

@Controller
@RequestMapping("quesinput")
public class QuesInputAction {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private QuesInput quesInput;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HtSessionManager htSessionManager;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private PaperService paperService;

	@RequestMapping(value = "upload", method = { RequestMethod.POST })
	@ResponseBody
	@LogAnnotation(operateFuncName = "试题模块", operateModelName = "试题导入")
	public Message upload(HttpServletResponse response, HttpServletRequest request, @RequestParam MultipartFile file, String qcscore) throws HttbException {

		Message message = new Message();
	    long fileSize = file.getSize();
	    int size = (int)fileSize/1024;
	    if(size>1024){
	    	message.setSuccess(false);
			message.setMessage("导入失败:文件不能超过1M！");
			return message;
	    }

		//默认分值
		int score = 1;
		//如果导入题目时赋值了，则按新值为主
		if(CommonUtil.isNotEmpty(qcscore)){
			//赋值
			score = Integer.parseInt(qcscore);
		}

		//导题批次号
		String qbatchNum = QuesConstant.BATCH_NUM+CommonUtil.getUUID();
		//当前登录者
		User currentUser = null;
		//获取session
		HtSession htsession = htSessionManager.getSession(request,response);
		//如果session不为空，则获取当前系统登录用户
		if(CommonUtil.isNotNull(htsession)){
			currentUser = (User) htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}


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
			//读取文件内容
			QuesInputMessage quesReturnList = quesInput.readTxtFile(file.getInputStream(),qbatchNum,score,currentUser);
			//加载试题集合
			List<Question> questions = quesReturnList.getQuestions();
			//试题集合不为空
			if(questions != null && questions.size() > 0){

				log.info("导入时间:"+CommonUtil.getNowFullDate()+"--此次导入批次号："+qbatchNum);
				//缓存到内存中（缓存一天）
				iRedisService.putEX(qbatchNum, questions, 60*60*24);
			}
			message.setSuccess(true);
			message.setMessage(questions.size()+"#"+qbatchNum);
			message.setData(quesReturnList.getErrorName());
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
	 * clearCache					(清除试题导入缓存)
	 * 								(根据批次号，清除该批次试题缓存)
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value="clearCache", method = {RequestMethod.POST})
	@ResponseBody
	@LogAnnotation(operateFuncName = "清除导入试题缓存", operateModelName = "试题导入")
	public Message clearCache(Model model,String batchnum) throws HttbException{
		Message message = new Message();
		//如果批次号不为空
		if(CommonUtil.isNotEmpty(batchnum)){
			try {
				//清除缓存
				boolean flag = iRedisService.remove(batchnum);
				if(flag){
					message.setSuccess(true);
					message.setMessage("撤销成功");
				}else{
					message.setSuccess(true);
					message.setMessage("撤销失败");
				}
			} catch (Exception e) {
				message.setSuccess(true);
				message.setMessage("撤销失败");
				throw new HttbException(ModelException.JAVA_REDIS_DELETE,this.getClass()+"清除试题导入缓存时发生异常",e);
			}
		}
		return message;
	}

	/**
	 *
	 * submit2Nosql					(提交缓存中导入的试题集合)
	 * @param 		model
	 * @param 		batchnum		导入批次号
	 * @param 		pageFrom		来源页面
	 * @return
	 * @throws 		HttbException
	 */
	@RequestMapping(value = "submit2Nosql", method = {RequestMethod.POST})
	@ResponseBody
	@LogAnnotation(operateFuncName = "缓存数据同步到Nosql", operateModelName = "试题导入")
	public Message submit2Nosql(HttpServletRequest request,HttpServletResponse response,Model model, String batchnum, String pageFrom, String pid) throws HttbException{
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		//判断批次号是否为空
		if(CommonUtil.isNotEmpty(batchnum)){
			//获取缓存对象
			Object quesojb;
			try {
				//试题集合
				quesojb = iRedisService.get(batchnum);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_REDIS_SEARCH,this.getClass()+"清除试题导入缓存时发生异常",e);
			}
			//缓存对象不为空
			if(CommonUtil.isNotNull(quesojb)){
				List<Question> questions = (List<Question>) quesojb;

				//从试卷页面进入，需要保存试卷
				if(pageFrom != null && pageFrom.equals("paper")){
					List<String> ids = new ArrayList<String>();
					for (int i = 0; i < questions.size(); i++) {
						ids.add(questions.get(i).getQid());
					}
					if(ids.size() > 0){
						Paper paper = paperService.get(pid);
						if(paper != null){
							paper.setPqids(ids);
							paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
							paper.setUpdatetime(new Date());
							paper.setUpdateuser(userid);
							paper.setPstatus(StatusEnum.BJZ.getCode());//试卷状态 发布
							paperService.updateQuesList(paper);
						}
					}
				}

				//遍历试题集合
				boolean flag = questionService.saveBatch(questions);
				//保存知识点和试题关系(暂时注释，在试题审批同意后才建立关系)
				saveCateQues(questions);
				if(flag){
					iRedisService.remove(batchnum);
					message.setSuccess(true);
					message.setMessage("同步成功");
				}else{
					message.setSuccess(true);
					message.setMessage("同步失败");
				}
			}
		}
		return message;
	}

	/**
	 *
	 * saveCateQues					(保存试题和试题分类关系)
	 * 								(试题导入时调用)
	 * @param 		qlist			试题集合
	 * @throws 		HttbException
	 */
	private void saveCateQues(List<Question> qlist) throws HttbException{
		if(qlist != null && qlist.size() > 0){
			List<Object[]> params = new ArrayList<Object[]>();
			//遍历试题集合
			for (int i = 0; i < qlist.size(); i++) {
				//拿到试题知识点集合
				List<String> qpoints = qlist.get(i).getQpoint();
				//判断是否为空
				if(qpoints != null){
					//遍历知识点
					for (int j = 0; j < qpoints.size(); j++) {
						//知识点所有根节点
						List<String> allRoots = categoryService.getAllPidRedis(qpoints.get(j));
						//遍历知识点根节点
						for (int k = 0; k < allRoots.size(); k++) {
							Object[] param = new Object[4];
							//知识点ID
							param[0] = allRoots.get(k);
							//试题ID
							param[1] = qlist.get(i).getQid();
							//属性
							param[2] = "";
							List<String> clist2 = new ArrayList<String>();
							for(ChildQuestion c : qlist.get(i).getQchild()){
								if(qlist.get(i).getQtype().equals("0")||qlist.get(i).getQtype().equals("1")){
									continue;
								}
								clist2.add(c.getQcid());
							}
							param[3] = clist2;
							params.add(param);
						}
					}
				}
			}
			categoryService.saveCateQues(params);
		}
	}

	/**
	 *
	 * quesinput					(跳到试题导入界面)
	 * @param 		model
	 * @return
	 * @throws 		HttbException
	 */
	@RequestMapping(value = "quesinput")
	public String quesinput(Model model) throws HttbException {
		return "question/quesinput";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/download")
	@LogAnnotation(operateFuncName = "试题模块", operateModelName = "下载错误试题")
	public String download(String fileName, HttpServletRequest request,
			HttpServletResponse response) throws HttbException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
		try {
				//输出的错误试题信息
				StringBuffer sbout = new StringBuffer("");

				try {
					//存在redis缓存中的错误试题集合
					Object objerrors = iRedisService.get(fileName+QuesConstant.QUESERRORS_REDIS);
					if(CommonUtil.isNotNull(objerrors)){
						List<QuesError> errors = null;
						errors = (List<QuesError>) objerrors;
						sbout.append("=================================共有["+errors.size()+"]道试题必填项错误！===========================\r\n\r\n");
						//输出日志记录
						sbout.append(QuesOutput.outputQuesErrors(errors));
					}

					//存在redis缓存中的错误记录集合
					Object objrecords = iRedisService.get(fileName+QuesConstant.QUESRECORDS_REDIS);
					if(CommonUtil.isNotNull(objrecords)){
						List<String> records = null;
						records = (List<String>) objrecords;
						sbout.append("=================================下列试题未按照导题模板格式填写===========================\r\n\r\n");
						sbout.append(QuesOutput.outputRecordLine(records));
					}
				} catch (Exception e) {
					throw new HttbException(ModelException.JAVA_REDIS_SEARCH,this.getClass()+"从redis中查询异常试题记录时发生异常",e);
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
			throw new HttbException(this.getClass() + "下载错误试题失败", e);
		} catch (IOException e) {
			throw new HttbException(this.getClass() + "下载错误试题失败", e);
		}

		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;
	}
}
