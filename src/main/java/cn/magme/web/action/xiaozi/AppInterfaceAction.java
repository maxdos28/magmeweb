package cn.magme.web.action.xiaozi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.phoenix.PhoenixStartPic;
import cn.magme.service.IssueContentsService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.phoenix.PhoenixStartPicService;
import cn.magme.service.xiaozi.XiaoziService;
import cn.magme.web.action.BaseAction;

/**
 * 
 * @author jasper
 * @date 2013.07.16
 * 小资移动端接口
 */
public class AppInterfaceAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338319298461712794L;
	
	@Resource 
	private PhoenixStartPicService phoenixStartPicService;
	
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private IssueContentsService issueContentsService;
	
	@Resource
	private XiaoziService xiaoziService;

	/**
	 * 开机图片
	 * @return
	 */
	public String startPictureList()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		this.jsonResult = JsonResult.getFailure();
		if(appId==null){
			this.jsonResult.setMessage("appId不能为空");
			return JSON;
		}
		map.put("appId", appId);
		List<PhoenixStartPic> startPicList = phoenixStartPicService.getStartPicList(map);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("startPicList", startPicList);
		return JSON;
	}
	
	/**
	 * 杂志列表,根据APPID得到
	 * @return
	 */
	public String publicationsList()
	{
		this.jsonResult = JsonResult.getFailure();
		if(appId==null){
			this.jsonResult.setMessage("appId不能为空");
			return JSON;
		}
		List<Publication> pl = publicationService.queryByAppId(appId,3);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("publicationsList", pl);
		return JSON;
	}
	
	/**
	 * 期刊列表,需传入APPID,如果杂志ID不传入则显示全部期刊,期刊按时间倒序排列
	 * @return
	 */
	public String issuesList()
	{
		this.jsonResult = JsonResult.getFailure();
		if(appId==null){
			this.jsonResult.setMessage("appId不能为空");
			return JSON;
		}
		List<Issue> il = issueService.queryIssueBySuperId(appId, publicationId,2, null, null, null);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issuesList", il);
		return JSON;
	}
	
	/**
	 * 根据期刊ID得到期刊内容
	 * @return
	 */
//	public String issuesContentsList()
//	{
//		return JSON;
//	}
	
	/**
	 * 根据期刊得到目录和精品页预览
	 * @return
	 */
	public String issueCatalogList()
	{
		
		this.jsonResult = JsonResult.getFailure();
		if(issueId==null){
			this.jsonResult.setMessage("issueId不能为空");
			return JSON;
		}
		if(review==null){
			this.jsonResult.setMessage("review不能为空");
			return JSON;
		}
		List issueContentsList = null;
		if("c".equalsIgnoreCase(review))
		{
			issueContentsList = issueContentsService.queryAllByIssueId(issueId, 1, null);
		}
		if("p".equalsIgnoreCase(review))
		{
			issueContentsList = issueContentsService.queryAllByIssueId(issueId, null, 1);
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueContentsList", issueContentsList);
		return JSON;
	}
	
	/**
	 * 根据期刊得到栏目和目录
	 * @return
	 */
	public String issueCategoryAndCatalogList()
	{
		this.jsonResult = JsonResult.getFailure();
		if(issueId==null){
			this.jsonResult.setMessage("issueId不能为空");
			return JSON;
		}
		List l = xiaoziService.queryCategoryAndCatalog(issueId);
		
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueCategoryAndCatalogList", l);
		return JSON;
	}
	/**
	 * 应用id
	 */
	private Long appId;
	
	/**
	 * 开机图片的种类；0：固定图片；1：广告(链接地址不为空);2：操作引导性质的图片(只显示一次)
	 * @fields type
	 */
	private Integer type;
	
	/**
	 * 杂志ID
	 */
	private Long publicationId;
	
	/**
	 * c/p
	 */
	private String review;
	
	private Long issueId;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

}
