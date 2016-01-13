package cn.magme.web.action.newPublisher;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.IosApp;
import cn.magme.pojo.IosPush;
import cn.magme.service.IosAppService;
import cn.magme.service.IosPushService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * @author devin.song
 * @date 2012-09-14
 */
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisher/appMessage.ftl")})
public class AppMessageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5437142526634207579L;
	
	private static final Logger log=Logger.getLogger(AppMessageAction.class);
		
	private static final int SIZE=10;
	
	@Resource
	private IosPushService iosPushService;
	@Resource
	private IosAppService iosAppService;
	
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
		IosPush iosPush = new IosPush();
		iosPush.setStatus(status);
		iosPush.setUserType(userType);
		
		int tempCount = iosPushService.queryIosPushListCount(iosPush);
		this.pageNo = (tempCount+pageSize-1)/pageSize;
		
		iosPushList = iosPushService.queryIosPushList(iosPush,(currentPage-1)*pageSize,pageSize);
		this.jsonResult.put("iosPushList", iosPushList);
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
		if(status==null){
			return JSON;
		}
		IosPush iosPush = new IosPush();
		iosPush.setStatus(status);
		if(StringUtil.isNotBlank(content)){
			iosPush.setContent(content);
		}else{
			this.jsonResult.setMessage("发送内容不能为空.");
			return JSON;
		}
		if(status==1){
			iosPush.setSendTime(new Date());
		}else{
			iosPush.setSendTime(sendTime);
		}
		iosPush.setUserId(this.getSessionAdmin().getId());
		iosPush.setUserType(0);//magme用户
		if(iosId!=null){
			iosPush.setId(iosId);
			iosPushService.updateIosPush(iosPush);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			if(appIdStr==null){
				return JSON;
			}
			//iosPushService.addIosPush(iosPush);
			if(status==1){//立即发送消息
				try{
				Long[] appIdArray = StringUtil.splitToLongArray(appIdStr);
				if(appIdArray!=null&&appIdArray.length>0){
					int k =0;
					k=iosPushService.iosPushMessage(0,this.getSessionAdmin().getId(), content,null,2,appIdArray);
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
			}else{
				try{
				Long[] appIdArray = StringUtil.splitToLongArray(appIdStr);
				if(appIdArray!=null&&appIdArray.length>0){
					for (int i = 0; i < appIdArray.length; i++) {
						Long appId = appIdArray[i];
						IosApp ia = iosAppService.queryIosAppById(appId);
						if(ia!=null){
							iosPush.setAppKey(ia.getAppKey());
							iosPushService.addIosPush(iosPush);
						}
					}
				}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				}catch (Exception e) {
					return JSON;
				}
			}
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
	
	private String appIdStr;
	private Integer userType=0;

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

	public String getAppIdStr() {
		return appIdStr;
	}

	public void setAppIdStr(String appIdStr) {
		this.appIdStr = appIdStr;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
