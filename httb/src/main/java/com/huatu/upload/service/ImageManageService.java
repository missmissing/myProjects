package com.huatu.upload.service;

import java.util.List;
import java.util.Map;

import com.huatu.core.exception.HttbException;
import com.huatu.upload.model.Image;

public interface ImageManageService {

	/**
	 *
	 * save						(添加图片)
	 * @param 		params			图片信息参数
	 * @throws 		HttbException
	 */
	public boolean save(Image image) throws HttbException;

	/**
	 *
	 * delete						(根据ID删除图片)
	 * @param 		id				图片ID
	 * @throws 		HttbException
	 */
	public boolean delete(String id) throws HttbException;

	/**
	 *
	 * update						(修改图片)
	 * @param	 	params			图片信息参数
	 * @throws 		HttbException
	 */
	public boolean update(Image image) throws HttbException;

	/**
	 *
	 * get							(根据图片ID查询图片)
	 * @param 		id				图片ID
	 * @throws 		HttbException
	 */
	public Image get(String id) throws HttbException;

	/**
	 *
	 * queryImages					(根据条件查询图片)
	 * @param 		condition		条件集合
	 * @throws 		HttbException
	 */
	public List<Image> queryImages(Map<String, Object> condition) throws HttbException;


}
