package cn.magme.web.action.ad;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdPosition;
import cn.magme.pojo.AdUser;
import cn.magme.service.AdPositionService;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

public class AdPositionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5332609968612504057L;
	
	@Resource
	private AdPositionService adPositionService;
	
	private static final Logger log=Logger.getLogger(AdPositionAction.class);
	
	
	public String addPos(){
		AdUser adUser=this.getSessionAdUser();
		this.jsonResult=new JsonResult();
		//默认失败
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(NumberUtil.isLessThan0(posy) || NumberUtil.isLessThan0(posx) 
				|| NumberUtil.isLessThan0(pageNo) || NumberUtil.isLessThanOrEqual0(issueId) 
				|| NumberUtil.isLessThan0(status) ){
//				|| StringUtil.isBlank(keywords) || StringUtil.isBlank(title)//新版不需要添加标题和关键字
			this.jsonResult.setMessage("参数错误");
			return JSON;
		}
		
		//默认是广告商上传
		int tempuserTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
		if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADAGENCY){
			tempuserTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
		}else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
			tempuserTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
		}
		AdPosition adpos=new AdPosition();
		adpos.setIssueId(issueId);
		adpos.setKeywords(keywords);
		adpos.setPageNo(pageNo);
		adpos.setPosx(this.posx);
		adpos.setPosy(posy);
		adpos.setWidth(this.width);
		adpos.setHeight(this.height);
		adpos.setStatus(1);
		adpos.setTitle(title);
		adpos.setUserId(adUser.getId());
		adpos.setUserTypeId(tempuserTypeId);
		adpos.setDescription(description);
		adpos.setAdposType(1);
		adpos.setType(type);
		try {
			if(this.adPositionService.insert(adpos)>0){
				this.jsonResult.put("adpos", adpos);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return JSON;
	}
	
	
	private Integer status;
	private Long userId;
	private Integer userTypeId;
	private Integer pageNo;
	private Long issueId;
	private double posx;
	private double posy;
	private Float width;
	private Float height;
	private String title;
	private String keywords;
	private String description;
	private Integer type=1;
	
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	
	
	
	
	public double getPosx() {
		return posx;
	}
	public void setPosx(double posx) {
		this.posx = posx;
	}
	public double getPosy() {
		return posy;
	}
	public void setPosy(double posy) {
		this.posy = posy;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	

}
