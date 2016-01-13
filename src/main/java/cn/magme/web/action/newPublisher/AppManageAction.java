package cn.magme.web.action.newPublisher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.IosApp;
import cn.magme.service.IosAppPubService;
import cn.magme.service.IosAppService;
import cn.magme.web.action.BaseAction;
/**
 * @author devin.song
 * @date 2012-09-14
 */
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisher/appManage.ftl")})
public class AppManageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5437142526634207579L;
	
	private static final Logger log=Logger.getLogger(AppManageAction.class);
		
	private static final int SIZE=10;
	
	@Resource
	private IosAppService iosAppService;
	@Resource
	private IosAppPubService iosAppPubService;
	
	@Override
	public String execute(){
		listJson();
		return "success";
	}
	
	/**
	 * 分页内容
	 * @return
	 */
	public String listJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		IosApp iosApp =new IosApp();
		iosApp.setName(name);
		iosApp.setPublicationName(publicationName);
		iosApp.setUserType(userType);
		
		int tempCount = iosAppService.queryIosAppListCount(iosApp);
		this.pageNo = (tempCount+pageSize-1)/pageSize;
		
		iosAppList = iosAppService.queryIosAppList(iosApp, (currentPage-1)*pageSize, pageSize);
		this.jsonResult.put("iosAppList", iosAppList);
		this.jsonResult.put("currentPage", currentPage);
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 根据id获取对应的对象
	 * @return
	 */
	public String getPojoJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		this.jsonResult.put("iosAppPojo", iosAppService.queryIosAppById(iosId));
		this.jsonResult.put("iosAppPubList", iosAppPubService.queryIosAppPubcationNameByAppId(iosId));
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	private Integer currentPage=1;
	private Integer pageSize=SIZE;
	private Integer pageNo;
	private List<IosApp> iosAppList;
	private Long iosId;
	private String publicationName;
	private String name;
	private Integer userType;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public List<IosApp> getIosAppList() {
		return iosAppList;
	}

	public void setIosAppList(List<IosApp> iosAppList) {
		this.iosAppList = iosAppList;
	}

	public Long getIosId() {
		return iosId;
	}

	public void setIosId(Long iosId) {
		this.iosId = iosId;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
