package cn.magme.web.action.look;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.IssueContents;
import cn.magme.pojo.look.LooAdminUser;
import cn.magme.pojo.look.LooArticle;
import cn.magme.pojo.look.LooManyLook;
import cn.magme.service.look.LookArticleService;
import cn.magme.util.DateUtil;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * LOOK预发布文章
 * 
 * @author jasper
 * @date 2013.12.17
 * 
 */
@Results({
		@Result(name = "success", location = "/WEB-INF/pages/looker/admin/planArticleManager.ftl")})
public class LookPlanArticleAction extends BaseAction {
	@Resource
	private LookArticleService lookArticleService;
	private static final Logger log = Logger
			.getLogger(LookCategoryAction.class);

	private String title;
	private String articleIds;
	private String planDate;
	private Integer planTime;
	private Long itemId;
	private Long categoryId;
	private Integer plan;

	private Long appId = 903L;// 默认APP ID

	public String execute() {
		return SUCCESS;
	}

	// 查询文章
	public String searchPlanArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		boolean b = false;
		if(plan!=null&&plan==1)
			b = true;
		List<Map> l = lookArticleService.searchPlanLooArticle(itemId,categoryId,title,b);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("articleList", l);
		return JSON;
	}

	// 保存文章
	public String savePlanArticleJson() {
		this.jsonResult = JsonResult.getFailure();

		if (StringUtil.isBlank(this.articleIds)) {
			log.warn("请选择文章");
			this.jsonResult.setMessage("请选择文章");
			return JSON;
		}
		if (StringUtil.isBlank(this.planDate)) {
			log.warn("请填写日期");
			this.jsonResult.setMessage("请填写日期");
			return JSON;
		}
		if(planTime==null||planTime<=0)
		{
			log.warn("请选择时间");
			this.jsonResult.setMessage("请选择时间");
			return JSON;
		}
		int r = lookArticleService.savePlanLooArticle(articleIds, DateUtil.parse(planDate,
				"yyyy-MM-dd"), planTime);
		if (r <= 0) {
			this.jsonResult.setMessage("保存失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleIds() {
		return articleIds;
	}

	public void setArticleIds(String articleIds) {
		this.articleIds = articleIds;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public Integer getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Integer planTime) {
		this.planTime = planTime;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}
	
}
