package cn.magme.web.action.phoenix;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.constants.phoenix.PhoenixConstants;
import cn.magme.pojo.phoenix.PhoenixCategory;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.service.phoenix.PhoenixCategoryService;
import cn.magme.web.action.BaseAction;
/**
 * 
 * @author fredy
 * 			jasper
 * @since 2013-5-13,2013-5-20
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/categoryManager.ftl")})
public class PhoenixCategoryAction extends BaseAction {
	
	private static final Logger log=Logger.getLogger(PhoenixCategoryAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5273655890999777094L;
	@Resource
	private PhoenixCategoryService phoenixCategoryService;
	
	public String execute(){
		
		return SUCCESS;
	}
	
	//后台查询栏目
	public String manageChannelJson()
	{
		getSessionAppId();
		return queryChannelJson();
	}

	//得到session的appId
	private void getSessionAppId() {
		PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
		appId = phoenixUser.getAppId();
	}
	public String queryChannelJson(){
		this.jsonResult=JsonResult.getFailure();
		try {
			List<PhoenixCategory> phoenixCategoryList=phoenixCategoryService.queryByCondition(this.getSessionPhoenixUser().getAppId(), null, null, isFree, null);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("phoenixCategoryList", phoenixCategoryList);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	/**
	 * 增加栏目
	 * @return
	 */
	public String addJson(){
		getSessionAppId();
		this.jsonResult=JsonResult.getFailure();
		if(appId==null || appId<=0){
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		if(StringUtils.isBlank(this.name)){
			this.jsonResult.setMessage("类目名称不能为空");
			return JSON;
		}
		if(StringUtils.isBlank(this.description)){
			this.jsonResult.setMessage("类目描述不能为空");
			return JSON;
		}
		this.name=this.name.trim();
		
		PhoenixCategory c=new PhoenixCategory();
		c.setAppId(appId);
		c.setName(name);
		c.setDescription(description);
		if(this.parentId==null || this.parentId<=0){
			c.setParentId(0L);
		}else{
			c.setParentId(parentId);	
		}
		if(recommend==null || this.recommend<=0){
			c.setRecommend(0);
		}else{
			c.setRecommend(recommend);
		}
		
		if(categorySort==null || categorySort<=0){
			c.setCategorySort(0);
		}else{
			c.setCategorySort(categorySort);
		}
		if(isFree==null || isFree<=0){
			c.setIsFree(0);
		}else{
			c.setIsFree(isFree);
		}
		c.setStatus(PhoenixConstants.PHOENIX_CATEGORY.STATUS_OK);
		
		Long id=this.phoenixCategoryService.insert(c);
		if(id!=null && id>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("phoenixCategory", c);
		}
		return JSON;
	}
	/**
	 * 删除栏目
	 * @return
	 */
	public String delJson(){
		getSessionAppId();
		this.jsonResult=JsonResult.getFailure();
		if(id==null || id<=0){
			this.jsonResult.setMessage("id不能为空，不能小于0");
			return JSON;
		}
		if(this.phoenixCategoryService.delById(id)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	/**
	 * 删除多个栏目
	 * @return
	 */
	public String delMultiJson(){
		getSessionAppId();
		this.jsonResult=JsonResult.getFailure();
		if(ids==null || ids.length()==0){
			this.jsonResult.setMessage("id不能为空，不能小于0");
			return JSON;
		}
		if(ids!=null&&ids.length()>0)
		{
			String[] s = ids.split(",");
			for(String i:s)
			{
				this.phoenixCategoryService.delById(new Long(i));
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	/**
	 * 更新栏目
	 * @return
	 */
	public String updJson(){
		getSessionAppId();
		this.jsonResult=JsonResult.getFailure();
		if(id==null || id<=0){
			this.jsonResult.setMessage("id不能为空，不能小于0");
			return JSON;
		}
		PhoenixCategory c=this.phoenixCategoryService.queryById(id);
		if(c==null){
			this.jsonResult.setMessage("类目不存在，不能更新");
			return JSON;
		}
		if(StringUtils.isNotBlank(this.name)){
			c.setName(this.name.trim());
		}
		if(StringUtils.isNotBlank(description)){
			c.setDescription(description.trim());
		}
		c.setAppId(appId);
		c.setCategorySort(categorySort);
		c.setIsFree(isFree);
		c.setParentId(parentId);
		c.setRecommend(recommend);
		
		if(this.phoenixCategoryService.updateById(c)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("phoenixCategory", c);
		}
		return JSON;
	}
	
	private  Long id;
	/**
	 * 多个栏目ID，逗号分隔
	 */
	private String ids;
	/**
	 * appid，来自t_ios_app
	 */
	private Long appId;
	/**
	 * 类目名称(栏目名称)
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 父类目id，如果本身是根目录，则此值为0
	 */
	private Long parentId;
	
	/**
	 * 是否推荐5w 推荐 0是普通文章 1 5w文章
	 */
	private Integer recommend;
	
	/**
	 * 是否免费
	 */
	private Integer isFree;
	
	/**
	 * 类目排序,越大越靠前
	 */
	private Integer categorySort=0;
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PhoenixCategoryService getPhoenixCategoryService() {
		return phoenixCategoryService;
	}
	public void setPhoenixCategoryService(
			PhoenixCategoryService phoenixCategoryService) {
		this.phoenixCategoryService = phoenixCategoryService;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public Integer getCategorySort() {
		return categorySort;
	}
	public void setCategorySort(Integer categorySort) {
		this.categorySort = categorySort;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	

}
