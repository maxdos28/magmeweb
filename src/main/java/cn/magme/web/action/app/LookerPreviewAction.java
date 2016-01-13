package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.look.LooUser;
import cn.magme.pojo.look.LooUserEgg;
import cn.magme.pojo.look.LooUserFeedback;
import cn.magme.service.look.LookAdminUserService;
import cn.magme.service.look.LookArticleService;
import cn.magme.service.look.LookIfService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.action.look.LookCategoryAction;

/**
 * LOO客接口
 * 
 * @author jasper
 * @date 2013.10.10
 * 
 */
public class LookerPreviewAction extends BaseAction {
	

	@Resource
	private LookIfService lookIfService;
	@Resource
	private LookArticleService lookArticleService;
	@Resource
	private LookAdminUserService lookAdminUserService;
	private static final Logger log = Logger
			.getLogger(LookerPreviewAction.class);
	private Long appId = 203L;
	private Long articleId;
	private Long itemId;
	private Integer trend;//文章查询趋势
	private Integer limit;//文章查询条数
	private String userName;
	private String password;
	private Byte status;
	
	/**
	 * 预览工具登录
	 * @return
	 */
	public String login()
	{
		this.jsonResult = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("app为空");
			return JSON;
		}
		if(StringUtil.isBlank(userName)||StringUtil.isBlank(password))
		{
			this.jsonResult.setMessage("用户名或密码为空");
			return JSON;
		}
		this.jsonResult=lookAdminUserService.login(appId, userName,password);
		return JSON;
	}

	/**
	 * 栏目列表
	 * 参数
	 * appid应用id 
	 * 返回： Json
	 * 
	 * @return
	 */
	public String itemList() {
		this.jsonResult = JsonResult.getFailure();
		
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}
			Integer s = null;
			if(status!=null)
				s = status.intValue();
			this.jsonResult = this.lookIfService.itemListForPreview(appId,s);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}

	

	/**
	 * 栏目下文章列表
	 * 参数： appid itemid 栏目id articleid 当前文章id，如果是初始进入，赋值0 trend
	 * 文章趋势，向后加载-1，向前加载1，默认是向前 返回： Json
	 * {文章id，页数，大小，图片url，标题，描述，文章上架时间，LIST{序号，标题，链接}}
	 * 
	 * @return
	 */
	public String articleList() {
		this.jsonResult = JsonResult.getFailure();
		try {
			if(appId==null||appId<=0)
			{
				this.jsonResult.setMessage("app为空");
				return JSON;
			}

			if(itemId==null||itemId<=0)
			{
				this.jsonResult.setMessage("栏目为空");
				return JSON;
			}
			if(this.articleId==null)
				this.articleId = 0L;
			if(this.trend==null)
				this.trend = 1;
			if(this.limit==null)
				this.limit = 20;
			Integer s = null;
			if(status!=null)
				s = status.intValue();
			this.jsonResult = this.lookIfService.articleListForPreview(appId, itemId, articleId, trend, limit,s);
		} catch (Exception e) {
			this.jsonResult.setMessage(e.getMessage());
		}
		return JSON;
	}


	/**
	 * 文章审核通过
	 * 参数： Param是一个集合，按栏目id和最后一次进入文章id依次进入 返回： Json
	 * {itemid，图片url，增量（数字），type（杂志，文章） }
	 * 
	 * @return
	 */
	public String acceptArticle() {
		this.jsonResult = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("app为空");
			return JSON;
		}
		if (articleId == null || articleId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (status == null || status.intValue() <= 0) {
			log.warn("状态为空");
			this.jsonResult.setMessage("状态为空");
			return JSON;
		}
		if (StringUtil.isBlank(userName)) {
			log.warn("用户为空");
			this.jsonResult.setMessage("用户为空");
			return JSON;
		}
		this.jsonResult = this.lookArticleService.acceptArticle(status,articleId, userName);
		return JSON;
	}
	

	/**
	 * 文章审核不通过
	 * 参数： Param是一个集合，按栏目id和最后一次进入文章id依次进入 返回： Json
	 * {itemid，图片url，增量（数字），type（杂志，文章） }
	 * 
	 * @return
	 */
	public String refuseArticle() {
		this.jsonResult = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("app为空");
			return JSON;
		}
		if (articleId == null || articleId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (status == null || status.intValue() <= 0) {
			log.warn("状态为空");
			this.jsonResult.setMessage("状态为空");
			return JSON;
		}
		if (StringUtil.isBlank(userName)) {
			log.warn("用户为空");
			this.jsonResult.setMessage("用户为空");
			return JSON;
		}
		this.jsonResult = this.lookArticleService.refuseArticle(status,articleId, userName);
		return JSON;
	}
	
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Integer getTrend() {
		return trend;
	}
	public void setTrend(Integer trend) {
		this.trend = trend;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	
}
