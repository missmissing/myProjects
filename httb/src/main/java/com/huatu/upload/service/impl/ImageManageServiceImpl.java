package com.huatu.upload.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.huatu.upload.dao.ImageManageDao;
import com.huatu.upload.model.Image;
import com.huatu.upload.service.ImageManageService;

@Service
public class ImageManageServiceImpl implements ImageManageService{
	@Autowired
	private ImageManageDao imageManageDao;

	/**
	 *
	 * save						(添加图片)
	 * @param 		params			图片信息参数
	 * @throws 		HttbException
	 */
	@Override
	public boolean save(Image image) throws HttbException {
		Object[] params = new Object[8];
		params[0] = image.getAid();
		params[1] = image.getImgurl();
		params[2] = image.getImgname();
		params[3] = image.getNewimgname();
		params[4] = image.getFromyear();
		params[5] = image.getTombstone();
		params[6] = image.getCreateuser();
		params[7] = image.getCreatetime();
		return (boolean)imageManageDao.insert(params)[0];
	}

	/**
	 *
	 * delete						(根据ID删除图片)
	 * @param 		id				图片ID
	 * @throws 		HttbException
	 */
	@Override
	public boolean delete(String id) throws HttbException {
		return (boolean)imageManageDao.delete(id)[0];
	}

	/**
	 *
	 * update						(修改图片)
	 * @param	 	params			图片信息参数
	 * @throws 		HttbException
	 */
	@Override
	public boolean update(Image image) throws HttbException {
		//imgname = ?, newimgname = ?, fromyear = ?, imgurl = ? , updateUser = ? ,updatetime = ? WHERE aid = ?
		Object[] params = new Object[7];
		params[0] = image.getImgname();
		params[1] = image.getNewimgname();
		params[2] = image.getFromyear();
		params[3] = image.getImgurl();
		params[4] = image.getUpdateuser();
		params[5] = image.getUpdatetime();
		params[6] = image.getAid();
		return (boolean)imageManageDao.update(params)[0];
	}

	/**
	 *
	 * get							(根据图片ID查询图片)
	 * @param 		id				图片ID
	 * @throws 		HttbException
	 */
	@Override
	public Image get(String id) throws HttbException {
		Object[] params = new Object[1];
		params[0] = id;
		ResultSet resultSet  = imageManageDao.get(params);
		Image image = new Image();
		if(CommonUtil.isNotNull(resultSet)){
			// 遍历结果集并且赋值
			for (Row row : resultSet) {
				image.setAid(row.getString("aid"));
				image.setImgurl(row.getString("imgurl"));
				image.setImgname(row.getString("imgname"));
				image.setNewimgname(row.getString("newimgname"));
				image.setFromyear(row.getString("fromyear"));
				image.setCreatetime(row.getDate("createtime"));
			}
		}
		return image;
	}

	/**
	 *
	 * queryImages					(根据条件查询图片)
	 * @param 		condition		条件集合
	 * @throws 		HttbException
	 */
	@Override
	public List<Image> queryImages(Map<String, Object> condition) throws HttbException {
		List<Image> lists = new ArrayList<Image>();
		ResultSet resultSet  = imageManageDao.getImageResultSet(condition);
		// 遍历结果集并且赋值
		for (Row row : resultSet) {
			Image image = new Image();
			image.setAid(row.getString("aid"));
			image.setImgurl(row.getString("imgurl"));
			image.setImgname(row.getString("imgname"));
			image.setNewimgname(row.getString("newimgname"));
			image.setFromyear(row.getString("fromyear"));
			image.setCreatetime(row.getDate("createtime"));
			lists.add(image);
		}
		return lists;
	}
}
