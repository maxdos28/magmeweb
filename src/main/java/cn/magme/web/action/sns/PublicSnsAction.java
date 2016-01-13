package cn.magme.web.action.sns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Category;
import cn.magme.pojo.Sort;
import cn.magme.result.sns.PublicResult;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.CategoryService;
import cn.magme.service.SortService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.web.action.BaseAction;

public class PublicSnsAction extends BaseAction  {
	private static final long serialVersionUID = 8837357838930607179L;
	private static final Logger log=Logger.getLogger(PublicSnsAction.class);
	
	@Resource
	private SnsPublicService snsPublicService;
	
	@Resource
	private SortService sortService;
	
	@Resource
	private SnsUserIndexService snsUserIndexService;
	
	@Resource
	private CategoryService categoryService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String userList(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		try{
			Map map = new HashMap();
			map.put("userId", getSessionUserId());
			userlist=snsPublicService.getFriendListByUserId(map);
			this.jsonResult.put("userlist", userlist);
			if(userlist!=null && userlist.size()>0)
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String uInfo(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		if(userId!=null && userId>0){
			Map m=new HashMap();
			m.put("id", userId);
			
			m.put("u", this.getSessionUserId()==null?0:this.getSessionUserId());
			UserInfoResult ui = snsUserIndexService.getUserInfo(m);
			this.jsonResult.put("info", ui);
			if(ui!=null && ui.getId()>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			}
		}
		if(getSessionUserId()==null || getSessionUserId()<0){
			this.jsonResult.put("usersign", 0);
		}else{
			this.jsonResult.put("usersign", 1);
			if(userId==getSessionUserId().intValue())
				this.jsonResult.put("self", 1);
		}
		
		return JSON;
	}
	public String getTags(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		List<Category> list = categoryService.queryAllChildrenByParentId(1l);
		List<Sort> allHomeSortList = sortService.getLstWithLatestEvent(PojoConstant.SORT.NEW_SORT_TYPE);
		for (Sort sort : allHomeSortList) {
			int i=0;
			for (Category category : list) {
				if(sort.getName().equals(category.getName()))
					i++;
			}
			if(i==0){
				Category c = new Category();
				c.setName(sort.getName());
				list.add(c);
			}
		}
		this.jsonResult.put("list",list);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	
	private Integer userId;
	private List<PublicResult> userlist;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<PublicResult> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<PublicResult> userlist) {
		this.userlist = userlist;
	}
	
}
