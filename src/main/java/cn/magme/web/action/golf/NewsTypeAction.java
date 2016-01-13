package cn.magme.web.action.golf;

import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import cn.magme.common.JsonResult;
import cn.magme.pojo.CreativeCategory;
import cn.magme.service.NewsMagageUserService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "/WEB-INF/pages/golf/newsType.ftl") })
public class NewsTypeAction extends BaseAction {
	@Resource
	private NewsMagageUserService newsMagageUserService;
	@Resource
	private CreativeCategoryService creativeCategoryService;

	public String execute() {
		creativeCategoryList = creativeCategoryService.queryCategoryGolfTree();
		return "success";
	}
	
	/**
	 * 分类的添加或者修改
	 * @return
	 */
	public String op(){
		this.jsonResult = JsonResult.getFailure();
		if(StringUtil.isBlank(name)){
			this.jsonResult.setMessage("分类名称不能为空");
			return JSON;
		}
		
		CreativeCategory category = new CreativeCategory();
		category.setName(name);
		if(pid!=null && pid>0){
			category.setParentId(pid);
		}else{
			category.setParentId(0L);
		}
		category.setStatus(1);
		category.setType(1);//高尔夫作品
		
		if(id!=null && id>0){//修改
			category.setId(id);
			int updateNum = creativeCategoryService.updateById(category);
			if(updateNum>0){
				this.jsonResult = JsonResult.getSuccess();
			}
		}else{//添加
			creativeCategoryService.insert(category);
			this.jsonResult = JsonResult.getSuccess();
		}
		return JSON;
	}
	
	/**
	 *分类的删除
	 * @return
	 */
	public String del(){
		if(creativeCategoryService.deleteById(id)){
			this.jsonResult = JsonResult.getSuccess();
		}else{
			this.jsonResult = JsonResult.getFailure();
		}
		return JSON;
	}

	/**
	 * golf一级和二级分类的集合
	 */
	private List<CreativeCategory> creativeCategoryList;
	private Long id;
	private Long pid;
	private String name;
	
	public List<CreativeCategory> getCreativeCategoryList() {
		return creativeCategoryList;
	}

	public void setCreativeCategoryList(
			List<CreativeCategory> creativeCategoryList) {
		this.creativeCategoryList = creativeCategoryList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
}
