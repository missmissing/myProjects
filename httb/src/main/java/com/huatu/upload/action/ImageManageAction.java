/**
 * 项目名：				httb
 * 包名：					com.huatu.upload.action
 * 文件名：				ImageManageAction.java
 * 日期：					2015年4月24日-上午11:15:13
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.upload.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.FileUtil;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.upload.enmu.PictureTypeEnmu;
import com.huatu.upload.model.Image;
import com.huatu.upload.service.ImageManageService;
import com.huatu.upload.util.HttbFileUtil;

/**
 * 类名称：				ImageManageAction
 * 类描述：				图片管理
 * 创建人：				Aijunbo
 * 创建时间：				2015年4月24日 上午11:15:13
 * @version 			0.0.1
 */
@Controller
@RequestMapping("imagemanage")
public class ImageManageAction {
	public  Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ImageManageService imageManageService;

	@InitBinder("image")
	public void initBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("image.");
	}

	/**
	 * list							(进入图片管理List页面)
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		return "image/list";
	}

	/**
	 * list							(进入图片管理List页面)
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "查看图片")
	public String detail(Model model,String imgid) throws HttbException {
		if(CommonUtil.isNotEmpty(imgid)){
			try {
				Image image = imageManageService.get(imgid);
				model.addAttribute("image", image);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询图片时发生异常", e);
			}
		}
		return "image/detail";
	}

	/**
	 *
	 * query						(分页查询)
	 * 								(Ajax-分页查询)
	 * @param 		model
	 * @param 		border			边界值
	 * @return
	 * @throws 		HttbException
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "查询图片")
	public Page<Image> query(Model model, Image image) throws HttbException {
		Page<Image> page = new Page<Image>();
		//条件集合
		Map<String, Object> condition = new HashMap<String, Object>();
		List<Image> ilist;
		try {
			//判断时间
			if(CommonUtil.isNotEmpty(image.getFromyear())){
				condition.put("fromyear", image.getFromyear());
			}
			//判断图片名称
			if(CommonUtil.isNotEmpty(image.getImgname())){
				condition.put("imgname", image.getImgname());
			}
			ilist = imageManageService.queryImages(condition);
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询图片时发生异常", e);
		}
		page.setRows(ilist);
		return page;
	}

	/**
	 * haddle						进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException
	 */
	@RequestMapping(value = "/handle")
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "编辑图片")
	public String handle(Model model,String imgid) throws HttbException {
		if(CommonUtil.isNotEmpty(imgid)){
			try {
				Image image = imageManageService.get(imgid);
				model.addAttribute("image", image);
				return "image/edit";
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询图片时发生异常", e);
			}
		}
		return "image/add";
	}

	/**
	 * add							进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "保存图片")
	public Message add(Model model,Image image) throws Exception {
		Message message = new Message();
		try {
			//如果ID为空==>添加图片
			if(!CommonUtil.isNotNull(image.getAid())){
				//生成图片主键
				image.setAid(CommonUtil.getUUID());
				image.setCreatetime(new Date());
				image.setCreateuser("admin");
				image.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
				imageManageService.save(image);
				message.setMessage("图片添加成功！");
			}else{
				//ID不为空==>修改
				image.setUpdatetime(new Date());
				image.setUpdateuser("admin");
				imageManageService.update(image);
				message.setMessage("图片修改成功！");
			}

			message.setSuccess(true);

		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("图片编辑失败！");
			throw new HttbException(this.getClass() + "图片编辑失败", e);
		}
		return message;
	}

	/**
	 *
	 * delete						(删除图片)
	 * @param 		model
	 * @param 		image
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "删除图片")
	public Message delete(Model model,String imgid) throws Exception{
		Message message = new Message();
		try {
			imageManageService.delete(imgid);
			message.setSuccess(true);
			message.setMessage("图片删除成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("图片删除失败！");
			throw new HttbException(this.getClass() + "图片删除失败", e);
		}
		return message;
	}

	/**
	 *
	 * upload						(当个文件上传，将文件上传至服务器临时目录)
	 * @param 		request 		HttpServletRequest
	 * @param 		file 			上传的文件名称
	 * @return 		String 			返回文件保存的临时路径
	 */
	@RequestMapping(value = "upload", method = { RequestMethod.POST })
	@ResponseBody
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "上传图片")
	public String upload(HttpServletRequest request, @RequestParam MultipartFile file, String imgname, String delFileName, String delStoreName) {

		// 获取系统配置的文件上传保存目录
		Map<String, Object> map = HttbFileUtil.getUploadPath(request);
		// 取得文件上传保存的临时目录
		String outFilePath = (String) map.get("filePath");

		// 取得上传的实际文件名称
		String filename = file.getOriginalFilename();
		// 记录上传文件名日志
		log.info("上传的实际文件名称：[" + filename + "]");
		// 取得文件后缀
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);

		//如果手动图片名称不为空,则以手动指定的为主
		if(CommonUtil.isNotEmpty(imgname)){
			filename = imgname;
		}else{
			filename = filename.substring(0,filename.lastIndexOf("."));
		}

		// 根据给定的uri创建file
		HttbFileUtil.createFileMkdirs(outFilePath);
		//存储服务器上的名称
		// 取得文件新名称
		String storeName = FileUtil.newFileName("."+suffix);
		try {
			// 将文件写入临时目录下
			FileCopyUtils.copy(file.getBytes(), new File(outFilePath + "/" + storeName));
		} catch (Exception e) {
			log.error("上传文件出错了：" + e.getMessage(), e);
			return null;
		}
		String imageurl = HttbFileUtil.getFileRealPath(request, map, storeName);
		// 上传成功，记录临时文件保存路径日志
		log.info("文件上传成功：[path]:[" + outFilePath + "]");
		log.info("文件上传成功：[url]:[" + imageurl + "]");
		//如果是修改方法,delFileName为null
		if(!CommonUtil.isNotNull(delFileName)){
			// 删除已上传的文件
			deleteOldFile(delStoreName, outFilePath);
		}else{
			// 删除已上传的文件
			deleteOldFile(delFileName, outFilePath);
		}

		// 返回文件路径
		return imageurl + "#" + filename + "#" + storeName ;
	}

	/**
	 *
	 * upload						ckEditor前端插件文件上传
	 * @param 		request 		HttpServletRequest
	 * @param 		file 			上传的文件名称
	 * @return 		String 			返回文件保存的临时路径
	 * @throws IOException
	 * @throws Exception 
	 */
	@RequestMapping(value = "ckEditorUpload", method = { RequestMethod.POST })
	@ResponseBody
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "上传图片")
	public String ckEditorUpload(HttpServletResponse response ,HttpServletRequest request, @RequestParam MultipartFile upload,String CKEditorFuncNum) throws IOException, Exception {
		 response.setContentType("text/html; charset=utf-8");
		// 获取系统配置的文件上传保存目录
		Map<String, Object> map = HttbFileUtil.getUploadPath(request);
		// 取得文件上传保存的临时目录
		String outFilePath = (String) map.get("filePath");
			
	     PrintWriter out = response.getWriter();
	    String imgType = upload.getContentType(); //文件类型
	    if(!imgType.contains("image/")){ //不是文件类型
	    	out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum
                    + ",'','文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
            out.println("</script>");
            return null;
	    }
	    if (upload.getSize() > 600 * 1024) {
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum
                    + ",''," + "'文件大小不得大于600k');");
            out.println("</script>");
            return null;
        }
		// 取得上传的实际文件名称
		String filename = upload.getOriginalFilename();
		// 取得文件后缀
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);

		// 记录上传文件名日志
		log.info("上传的实际文件名称：[" + filename + "]");
		// 根据给定的uri创建file
		HttbFileUtil.createFileMkdirs(outFilePath);
		//存储服务器上的名称
		// 取得文件新名称
		String storeName = FileUtil.newFileName("."+suffix);
		try {
			// 将文件写入临时目录下
			FileCopyUtils.copy(upload.getBytes(), new File(outFilePath + "/" + storeName));
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "图片上传失败", e);
		}
		String imageurl = HttbFileUtil.getFileRealPath(request, map, storeName);
		// 上传成功，记录临时文件保存路径日志
		log.info("文件上传成功：[path]:[" + outFilePath + "]");
		log.info("文件上传成功：[url]:[" + imageurl + "]");
		
		// 让用户休息3秒
		out.println("<script type=\"text/javascript\">");
		
		out.println("setTimeout(\"window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+ ",'" +imageurl + "','');\", 3000);");
		out.println("</script>");

		// 返回文件路径
		//out.println("<script type=\"text/javascript\">");
		//out.println("window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+ ",'" +imageurl + "','')");
		//out.println("</script>");
		return null;
	}

	/**
	 *
	 * showImage(读取磁盘文件，用以在JSP中显示非服务器上图片文件)
	 * @param 		request 		HttpServletRequest
	 * @param 		response 		HttpServletResponse
	 * @param 		filePath 		图片路径
	 * @throws Exception
	 */
	@RequestMapping(value = "showImage", method = RequestMethod.GET)
	@ResponseBody
	@LogAnnotation(operateFuncName = "图片模块", operateModelName = "查看图片")
	public void showImage(ModelMap model, HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception {
		// 声明输出流，用以写入文件
		OutputStream os = null;
		// 声明输入流，用以读取文件
		InputStream is = null;
		// 声明文件输入流
		FileInputStream fis = null;
		// 声明字节数组，用以保存文件
		byte[] data = null;
		try {
			if (CommonUtil.isNotEmpty(filePath)) {
				// 取得文件访问路径
				filePath = HttbFileUtil.getFileVisitPath(request, filePath);
				// 若不启用将文件保存到磁盘上则通过FileInputStream读取磁盘文件，否则通过
				if (filePath.indexOf("http") == -1) {
					// 读取磁盘文件
					data = readMagneticDiskFile(response, fis, filePath);
				} else {
					// 读取服务器文件
					data = readServerFile(response, is, filePath);
				}
				// 取得最后一次出现.的位置
				int index = filePath.lastIndexOf(".") + 1;
				// 设置返回的文件类型
				response.setContentType(PictureTypeEnmu.getTypeByKey(filePath.substring(index)));
				// 得到向客户端输出二进制数据的对象
				os = response.getOutputStream();
				// 输出数据
				os.write(data);
				// 刷新输出流
				os.flush();
			}
		} catch (FileNotFoundException e) {
			throw new HttbException(this.getClass() + "找不到指定文件：[" + filePath + "]", e);	
		} catch (IOException e) {
			throw new HttbException(this.getClass() + "读取文件异常：[" + filePath + "]", e);	
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "读取服务器文件异常：[" + e.getMessage() + "]", e);	

		} finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "读取文件关闭资源发生异常：[" + e.getMessage() + "]", e);	
			}
		}
	}

	/**
	 *
	 * deleteOldFile				(删除已上传的文件)
	 * 								(对图片进行编辑的时候，上传新图片后应该删除以前上传的图片)
	 * @param 		oldFilePath		之前上传图片名称
	 * @param 		temporary		临时文件夹名称
	 */
	private void deleteOldFile(String oldFilePath, String temporary) {
		// 如果传入原文件参数不为空则执行删除操作
		if (CommonUtil.isNotEmpty(oldFilePath)) {
			//temporary = temporary.substring(0, temporary.lastIndexOf("\\") + 1);
			// 依据传入path实例化文件
			File oldFile = new File(temporary + "\\" +oldFilePath);
			// 如果文件参数则删除文件
			if (oldFile.exists()) {
				// 删除文件
				oldFile.delete();
			}
		}
	}

	/**
	 *
	 * readMagneticDiskFile(读取磁盘文件)
	 * @param 		response 					HttpServletResponse
	 * @param 		fis 						FileInputStream
	 * @param 		os 							OutputStream
	 * @param 		filePath 					文件路径
	 * @return 		byte[] 						保存文件的字节数组
	 * @throws 		FileNotFoundException
	 * @throws 		IOException
	 */
	private byte[] readMagneticDiskFile(HttpServletResponse response, FileInputStream fis, String filePath) throws FileNotFoundException, IOException {
		// 通过FileInputStream流读取磁盘图片
		fis = new FileInputStream(filePath);
		// 得到文件大小
		int i = fis.available();
		// 定义字节数组用以保存文件
		byte data[] = new byte[i];
		// 读数据
		fis.read(data);
		// 关闭资源
		fis.close();
		// 返回字节数组data
		return data;
	}

	/**
	 *
	 * readServerFile(以流的形式读取服务器文件)
	 * @param 		response  					HttpServletResponse
	 * @param 		os 							OutputStream
	 * @param 		is 							InputStream
	 * @param 		filePath 					文件路径
	 * @return 		byte[] 						保存文件的字节数组
	 * @throws 		Exception
	 */
	private byte[] readServerFile(HttpServletResponse response, InputStream is, String filePath) throws Exception {
		// new一个URL对象
		URL url = new URL(filePath);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 取得文件大小，用以实例化字节数组，以便保存文件
		String fileSize = conn.getHeaderField("Content-Length");
		// 取得下载的文件大小
		int size = (CommonUtil.isNotEmpty(fileSize)) ? Integer.parseInt(fileSize) : 20000000;
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		is = conn.getInputStream();
		// 定义字节数组用以保存文件
		byte[] data = new byte[size];
		// 设置读取文件的起始位置
		int offset = 0;
		// 设置当前读取文件位置
		int numRead = 0;
		// 将服务器文件读取到byte[]中
		while (offset < size && (numRead = is.read(data, offset, size - offset)) >= 0) {
			// 当前读取文件位置递增
			offset += numRead;
		}
		// 关闭连接
		conn.disconnect();
		// 返回字节数组data
		return data;
	}
}
