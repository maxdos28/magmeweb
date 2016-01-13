package cn.magme.web.action.golf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.IosApp;
import cn.magme.pojo.IosPush;
import cn.magme.pojo.NewsManageUser;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.IosAppService;
import cn.magme.service.IosPushService;
import cn.magme.service.sns.CreativeService;
import cn.magme.util.DateUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * @author devin.song
 * @date 2012-09-14
 */
@Results({@Result(name="success",location="/WEB-INF/pages/golf/iosManage.ftl")})
public class IosManageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5437142526634207579L;
	
	private static final Logger log=Logger.getLogger(IosManageAction.class);
		
	private static final int SIZE=10;
	
	@Resource
	private IosPushService iosPushService;
	@Resource
	private IosAppService iosAppService;
	@Resource
	private CreativeService creativeService;
	
	@Override
	public String execute(){
		listJson();
		return "success";
	}
	
	/**
	 * 每日推送一条新闻
	 * @return
	 */
	public String oneNewsByDay(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(checkSendNewsByDay()){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	/**
	 * 是否允许发送新闻 每日只允许发送一条新闻
	 * @return
	 */
	protected boolean checkSendNewsByDay() {
		Date d = null;
		IosPush iosPush = new IosPush();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			d = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {}
		iosPush.setSendTime(d);
		iosPush.setUserType(2);
		iosPush.setStatus(1);
		int newsCount = iosPushService.queryIosPushListCount(iosPush);
		return newsCount==0;
	}
	
	/**
	 * 分页内容
	 * @return
	 */
	public String listJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
//		IosApp iosApp = new IosApp();
//		iosApp.setUserType(2);
//		appList = iosAppService.queryIosAppList(iosApp, 0, 0);
//		
		IosPush iosPush = new IosPush();
		iosPush.setUserType(2);
		iosPush.setStatus(1);
		NewsManageUser nmu = this.getSessionGolfUser();
		if(nmu!=null){
			iosPush.setUserId(nmu.getId());
		}else{
			this.jsonResult.setMessage("请重新登录golf新闻管理平台");
			return JSON;
		}
		int tempCount = iosPushService.queryIosPushListCount(iosPush);
		this.pageNo = (tempCount+pageSize-1)/pageSize;
		
		iosPushList = iosPushService.queryIosPushList(iosPush,(currentPage-1)*pageSize,pageSize);
		this.jsonResult.put("iosPushList", iosPushList);
		this.jsonResult.put("currentPage", currentPage);
		this.jsonResult.put("pageNo", pageNo);
//		this.jsonResult.put("appList", appList);
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
		this.jsonResult.put("iosPushPojo", iosPushService.queryIosPushById(iosId));
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 更新iosPush对象
	 * @return
	 */
	public String updateStatusIosPush(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		IosPush iosPush = new IosPush();
		iosPush.setStatus(status);
		iosPush.setId(iosId);
		iosPushService.updateIosPush(iosPush);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 添加iosPush对象
	 * @return
	 */
	public String addIosPush(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
	
		IosPush iosPush = new IosPush();
		iosPush.setStatus(1);
		iosPush.setSendTime(new Date());
		if(cid!=null){
			Creative cre = creativeService.queryById(cid);
			if(cre!=null){
				content = cre.getTitle();
				iosPush.setContent(cre.getTitle());
			}else{
				this.jsonResult.setMessage("查找新闻失败");
				return JSON;
			}
		}else{
			this.jsonResult.setMessage("发送内容不能为空.");
			return JSON;
		}
		
		NewsManageUser nmu = this.getSessionGolfUser();
		if(nmu!=null){
			iosPush.setUserId(nmu.getId());
			appIdStr = nmu.getAppId();
		}else{
			this.jsonResult.setMessage("请重新登录golf新闻管理平台");
			return JSON;
		}
		
		if(getAppIdStr()==null){
			this.jsonResult.setMessage("appKey获取失败");
			return JSON;
		}
		
		//iosPushService.addIosPush(iosPush);
		//立即发送消息
			try{
			Long[] appIdArray = StringUtil.splitToLongArray(getAppIdStr());
			if(appIdArray!=null&&appIdArray.length>0){
				int k =0;
				List<String> listStr = new ArrayList<String>();
				listStr.add(cid+"");
				k=iosPushService.iosPushMessage(2,nmu.getId(), content,listStr,2,appIdArray);
				if(k==0){
					this.jsonResult.setMessage("发送失败");
					return JSON;
				}
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}catch (Exception e) {
				return JSON;
			}
		return JSON;
	}


	private Integer currentPage=1;
	private Integer pageSize=SIZE;
	private Integer pageNo;
	private List<IosPush> iosPushList;
	private Integer status;
	private Long iosId;
	private String  content;
	private Date sendTime;
	private Long appId;
	private String appKey;
	private List<IosApp> appList;
	private String appIdStr;
	private Long cid;

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

	public List<IosPush> getIosPushList() {
		return iosPushList;
	}

	public void setIosPushList(List<IosPush> iosPushList) {
		this.iosPushList = iosPushList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getIosId() {
		return iosId;
	}

	public void setIosId(Long iosId) {
		this.iosId = iosId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public List<IosApp> getAppList() {
		return appList;
	}

	public void setAppList(List<IosApp> appList) {
		this.appList = appList;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppIdStr() {
		return appIdStr;
	}

	public void setAppIdStr(String appIdStr) {
		this.appIdStr = appIdStr;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	
}
