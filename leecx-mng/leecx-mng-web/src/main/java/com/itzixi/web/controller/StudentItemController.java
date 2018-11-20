package com.itzixi.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itzixi.common.pojo.JqGridResult;
import com.itzixi.common.utils.LeeJSONResult;
import com.itzixi.pojo.StudentItem;
import com.itzixi.service.StudentItemService;

@Controller
@RequestMapping("student")
public class StudentItemController extends BaseController
{
	Logger logger = LoggerFactory.getLogger(DemoItemController.class);

	@Autowired
	private StudentItemService service;

	@RequestMapping("/showStudentListPage")
	public String showStudentListPage(HttpServletRequest request)
	{

		return "student/studentList";
	}

	@RequestMapping("showAddStudent")
	public String showAddStudent(HttpServletRequest request)
	{

		return "student/addStudent";
	}

	@RequestMapping("/getStudentList")
	@ResponseBody
	public JqGridResult getStudentList(Integer page)
	{

		if (page == null)
		{
			page = 1;
		}

		JqGridResult jqgridResult = service.queryItemList(page, pageSize);

		return jqgridResult;
	}

	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public LeeJSONResult saveOrUpdate(StudentItem item)
	{
		String itemId = item.getId();
		if (StringUtils.isEmpty(itemId))
		{

			// 保存商品的操作
			service.saveItem(item);
		} else
		{

			service.updateItem(item);
		}

		return LeeJSONResult.ok();
	}

	@RequestMapping("/showItemInfoPage")
	public ModelAndView showItemInfoPage(String itemId, HttpServletRequest request)
	{

		StudentItem item = service.queryItemById(itemId);

		ModelAndView mv = new ModelAndView("student/studentInfo");
		mv.addObject("item", item);

		return mv;
	}

	@RequestMapping("/showModifyItemPage")
	public ModelAndView showModifyItemPage(String itemId, HttpServletRequest request)
	{

		StudentItem item = service.queryItemById(itemId);

		ModelAndView mv = new ModelAndView("student/modifyStudent");
		mv.addObject("item", item);

		return mv;
	}

	@RequestMapping("/deleteItem")
	@ResponseBody
	public LeeJSONResult deleteItem(String itemId)
	{

		if (StringUtils.isEmpty(itemId))
		{
			return LeeJSONResult.errorMsg("商品id为空或者不存在...");
		}

		service.deleteItem(itemId);

		return LeeJSONResult.ok();
	}

	@PostMapping("iconUpload")
	@ResponseBody
	public LeeJSONResult iconUpload(@RequestParam("file") MultipartFile[] files) throws Exception
	{
		String fileSpace = "D:" + File.separator + "studenticon";
		// 保存到数据库中的相对路径
		String uploadPathDB = File.separator;

		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try
		{
			if (files != null && files.length > 0)
			{

				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName))
				{

					uploadPathDB += fileName;
					// 文件上传的最终保存路径
					String finalPath = fileSpace + uploadPathDB;
					// 设置数据库保存的路径

					File outFile = new File(finalPath);
					if (outFile.getParentFile() == null || !outFile.getParentFile().isDirectory())
					{
						// 创建父文件夹
						outFile.getParentFile().mkdirs();
					}

					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}

			} else
			{
				return LeeJSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return LeeJSONResult.errorMsg("上传出错...");
		} finally
		{
			if (fileOutputStream != null)
			{
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}

		return LeeJSONResult.ok(uploadPathDB);
	}
}
