package com.itzixi.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itzixi.common.pojo.JqGridResult;
import com.itzixi.mapper.StudentItemMapper;
import com.itzixi.pojo.StudentItem;
import com.itzixi.pojo.StudentItemExample;
import com.itzixi.service.StudentItemService;

@Service
public class StudentItemServiceImpl implements StudentItemService
{
	@Autowired
	private StudentItemMapper mapper;

	@Autowired
	private Sid sid;

	@Override
	public void saveItem(StudentItem item)
	{
		String itemId = sid.nextShort();
		item.setId(itemId);
		mapper.insert(item);

	}

	@Override
	public void updateItem(StudentItem item)
	{
		mapper.updateByPrimaryKeySelective(item);

	}

	@Override
	public JqGridResult queryItemList(Integer page, Integer pageSize)
	{
		PageHelper.startPage(page, pageSize);

		StudentItemExample demoItemExample = new StudentItemExample();
		List<StudentItem> result = mapper.selectByExample(demoItemExample);

		PageInfo<StudentItem> pageList = new PageInfo<StudentItem>(result);

		JqGridResult grid = new JqGridResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(result);
		grid.setPage(pageList.getPageNum());
		grid.setRecords(pageList.getTotal());

		return grid;
	}

	@Override
	public StudentItem queryItemById(String itemId)
	{
		StudentItem re = mapper.selectByPrimaryKey(itemId);
		return re;
	}

	@Override
	public void deleteItem(String itemId)
	{
		mapper.deleteByPrimaryKey(itemId);
	}

}
