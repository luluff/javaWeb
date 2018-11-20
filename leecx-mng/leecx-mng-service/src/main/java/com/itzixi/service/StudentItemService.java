package com.itzixi.service;

import com.itzixi.common.pojo.JqGridResult;
import com.itzixi.pojo.StudentItem;

/*
 * 
 */
public interface StudentItemService
{
	public void saveItem(StudentItem item);

	public void updateItem(StudentItem item);

	public JqGridResult queryItemList(Integer page, Integer pageSize);

	public StudentItem queryItemById(String itemId);

	public void deleteItem(String itemId);
}
