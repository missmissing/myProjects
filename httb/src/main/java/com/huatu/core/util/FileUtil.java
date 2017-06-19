package com.huatu.core.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


@SuppressWarnings("rawtypes")
public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class);
	private static final int BUFFER_SIZE = 5 * 1024;
	 /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();

        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] ss = new String[number];
        for(int i=0;i<number;i++){
            ss[i] = getUUID();
        }
        return ss;
    }
    public static void UnZip2(String path1,String path2) {
    	path2 = path2.replace("file:/", "");
    	      try {
    	         BufferedOutputStream dest = null;
    	         BufferedInputStream is = null;
    	         ZipEntry entry;
				ZipFile zipfile = new ZipFile(path1);

				Enumeration e = zipfile.entries();
    	         while(e.hasMoreElements()) {
    	            entry = (ZipEntry) e.nextElement();
    	            log.info("Extracting: " +entry);
    	            is = new BufferedInputStream
    	              (zipfile.getInputStream(entry));
    	            int count;
    	            byte data[] = new byte[BUFFER_SIZE];
    	            FileOutputStream fos = new
    	              FileOutputStream(path2+"/"+entry.getName());
    	            dest = new
    	              BufferedOutputStream(fos, BUFFER_SIZE);
    	            while ((count = is.read(data, 0, BUFFER_SIZE))
    	              != -1) {
    	               dest.write(data, 0, count);
    	            }
    	            dest.flush();
    	            dest.close();
    	            is.close();
    	         }
    	      } catch(Exception e) {
    	         e.printStackTrace();
    	      }
    	}
	/**
	 * 复制文件
	 *
	 * @param srcFile
	 * @param descFile
	 */
	public static void copyFile(File srcFile, String path, String fileName) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			File file = new File(path.replaceAll("\\\\", "/"));
			if (!file.exists()) {
				file.mkdirs();
			}
			fos = new FileOutputStream(path.replaceAll("\\\\", "/") + "/"
					+ fileName);
			int i = 0;
			byte[] buf = new byte[1024];
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static String newFileName(String suffix) {
		StringBuffer sbStr = new StringBuffer("");
		Date date = new Date();
		sbStr.append(date.getTime() + suffix);
		return sbStr.toString();
	}

	/**
	 * 复制文件
	 *
	 * @param srcFile
	 * @param descFile
	 */
	public static void copyFile(File srcFile, File descFile) {
		InputStream fis = null;
		OutputStream fos = null;
		try {
			if (!descFile.exists()) {
				String path = descFile.getPath();
				descFile = new File(path);
			}

			if (!descFile.isDirectory())
				descFile.mkdirs();
			fis = new BufferedInputStream(new FileInputStream(srcFile),
					BUFFER_SIZE);
			fos = new BufferedOutputStream(new FileOutputStream(descFile),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (fis.read(buffer) > 0) {
				fos.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void copyFile(byte[] content, File descFile) {
		FileOutputStream fos = null;
		try {
			if (!descFile.exists()) {
				String path = descFile.getPath();
				descFile = new File(path);
			}
			fos = new FileOutputStream(descFile);
			if (null != content) {
				fos.write(content);
				fos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void copyFile(String srcFileName, String descFileName) {
		File srcFile = new File(srcFileName);
		File descFile = new File(descFileName);
		copyFile(srcFile, descFile);
	}

	public static void saveFile(File file, String fileName) {
		File descFile = new File(fileName);
		copyFile(file, descFile);
	}
	/**
	 * 返回当前时间的文件名称
	 *
	 * @param oldFileName
	 * @return
	 */
	public static String createNewFileName(String oldFileName) {
		String rtn = "";
		if (oldFileName != null && oldFileName.trim().length() > 0
				&& oldFileName.indexOf(".") != -1) {
			rtn = String.valueOf(System.currentTimeMillis())
					+ oldFileName.substring(oldFileName.indexOf("."));
		}
		return rtn;
	}
	/**
	 * 返回上传文件的新文件名称 为位置+后缀
	 *
	 * @param oldFileName 上传文件名 direction 位置名称
	 * @return
	 */
	public static String createNewFileName(String oldFileName,String direction) {
		String rtn = "";
		if (oldFileName != null && oldFileName.trim().length() > 0
				&& oldFileName.indexOf(".") != -1) {
			rtn = direction + oldFileName.substring(oldFileName.indexOf("."));
		}
		return rtn;
	}

	/**
	 * 返回原来名称加上'_'加上mId中的后半部分时间返回的文件名称 mId的格式:oa_fj_tfj_20081030182028239
	 *
	 * @param oldFileName
	 * @param mId
	 * @return
	 */
	public static String createNewFileName2(String oldFileName, String mId) {
		String rtn = "";
		if (oldFileName != null && oldFileName.trim().length() > 0
				&& oldFileName.indexOf(".") != -1) {
			rtn = oldFileName.substring(0, oldFileName.indexOf(".")) + "_"
					+ mId.substring(mId.lastIndexOf("_") + 1)
					+ oldFileName.substring(oldFileName.indexOf("."));
		}
		return rtn;
	}

	// /**
	// * 根据文件名路径和名称删除一个文件
	// *
	// * @param fileName包含完整路径以及名称
	// * @return
	// */
	// public static boolean deleteFile(String fileName) throws IOException {
	// boolean b = false;
	// File file = new File(fileName);
	// if (file.isDirectory()) {
	// throw new IOException(
	// "IOException -> BadInputException: not a file.");
	// }
	// if (file.exists() == false) {
	// throw new IOException(
	// "IOException -> BadInputException: file is not exist.");
	// }
	// if (file.delete() == false) {
	// throw new IOException("Cannot delete file. filename = " + fileName);
	// }
	// return b;
	// }

	/** ********************************2009-09-04新添加**************************************** */

	public static void uploadFile(File src, File dst) throws IOException {

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int offset = 0;

			while ((offset = in.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer,0,offset);
			}
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		}

	}
	public static long uploadFile2(File f1,File f2) throws Exception{
		  long time=new Date().getTime();
		  FileInputStream in=new FileInputStream(f1);
		  FileOutputStream out=new FileOutputStream(f2);
		  byte[] buffer=new byte[BUFFER_SIZE];
		  while(true){
		   int ins=in.read(buffer);
		   if(ins==-1){
		    in.close();
		    out.flush();
		    out.close();
		    return new Date().getTime()-time;
		   }else
		    out.write(buffer,0,ins);
		  }
		 }
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public static void makeDir(String directory) {
		File dir = new File(directory);

		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

	}

	public static String generateFileName(String fileName)
			throws UnsupportedEncodingException {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());
		String extension = fileName.substring(fileName.lastIndexOf("."));
		fileName = new String(fileName.getBytes("iso8859-1"), "GBK");
		return formatDate + new Random().nextInt(10000) + extension;
	}

	/**
	 * 删除文件，可以是单个文件或文件夹
	 *
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			log.info("删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {

				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			log.info("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			log.info("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 *
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.info("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			log.info("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			log.info("删除目录" + dir + "成功！");
			return true;
		} else {
			log.info("删除目录" + dir + "失败！");
			return false;
		}
	}
	/** *******************************×2011-07-04新添加结束**************************************** */
	/** *******************************×2013-03-16新添加**************************************** */
	/**
	 * 获取配置文件里的文件路径
	 *
	 * @return
	 */





	/**
	 *
	 * @param path 上传文件路径
	 * @return
	 * 返回：true  文件已存在
	 * 返回: false 文件不存在
	 */
	public static boolean checkExist(String path){
		File dst = new File(path);
		if(dst.exists()){
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param path 文件路径
	 * @return 返回一个可用的文件路径名
	 */
	public static String getUniquePath(String dirPath,String fileName){
		//前半部分
		String frontStr = "";
		//后半部分
		String endStr = "";
		String uniqueStr = fileName;
		int i = 1;
		while (FileUtil.checkExist(dirPath+uniqueStr)){
			 if(fileName.indexOf(".") != -1){
				 frontStr = fileName.substring(0,fileName.lastIndexOf(".")) ;
				 endStr = fileName.substring(fileName.lastIndexOf("."));
				 uniqueStr = frontStr+"("+i+")"+endStr;
				 i++;
			 }else{
				 break;
			 }
		}
		return uniqueStr;
	}

	/**
	 * 保存文件至服务器临时目录
	 *
	 * @param request
	 * @param upload
	 * @return
	 */
	public static File uploadFile(HttpServletRequest request, MultipartFile upload) throws Exception {
		try {
			String fileName = upload.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			byte[] bytes = upload.getBytes();
			String uploadPath = Constants.FILE_UPLOAD_MKDIRS + FileUtil.getTemporaryFileName(suffix);
			File file = new File(request.getSession().getServletContext().getRealPath(uploadPath));
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			FileCopyUtils.copy(bytes, file);
			return file;
		} catch (IOException e) {
			log.error("文件上传异常", e);
			return null;
		}
	}

	/**
	 * 获取临时文件名称
	 *
	 * @return
	 */
	public static String getTemporaryFileName(String suffix) {
		return new Date().getTime() + "." + suffix;
	}

	public void downLoadFile(HttpServletResponse response, String filePath, File file)
            throws IOException {
		String fileName = file.getName();
		//下载文件
		FileUtil.exportFile(response, filePath + fileName, fileName);
		//删除单个文件
		FileUtil.deleteFile(filePath);
	}

	  /**
	   * 下载文件
	   * @param 	response
	   * @param 	csvFilePath 	文件路径
	   * @param 	fileName 	  	文件名称
	   * @throws 	IOException
	   */
	  public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
	                                                  throws IOException {
	    response.setContentType("application/csv;charset=GBK");
	    response.setHeader("Content-Disposition",
	      "attachment;  filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));

	    InputStream in = null;
	    try {
	      in = new FileInputStream(csvFilePath);
	      int len = 0;
	      byte[] buffer = new byte[1024];
	      response.setCharacterEncoding("GBK");
	      OutputStream out = response.getOutputStream();
	      while ((len = in.read(buffer)) > 0) {
	        out.write(buffer, 0, len);
	      }
	    } catch (FileNotFoundException e) {
	    	System.out.println(e);
	    } finally {
		    if (in != null) {
		        try {
		          in.close();
		        } catch (Exception e) {
		          throw new RuntimeException(e);
		        }
		    }
	    }
	  }
}
