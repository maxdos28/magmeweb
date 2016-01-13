package cn.magme.web.action.newPublisher;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.phoenix.PhoenixConstants;
import cn.magme.pojo.phoenix.PhoenixStartPic;
import cn.magme.service.phoenix.PhoenixStartPicService;
import cn.magme.service.xiaozi.XiaoziService;
import cn.magme.web.action.BaseAction;

/**
 * @description 开机动画Action,从凤凰周刊版本复制过来
 * @version 1.0
 * @author jasper
 * @update 2013-7-17
 */
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisher/startPicManager.ftl")
,@Result(name="uploadJson",type="json",params={"root","jsonResult","contentType","text/html;charset=utf-8"})})
public class StartPicAction extends BaseAction {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5211806919575103157L;
	
	@Resource PhoenixStartPicService phoenixStartPicService;
	@Resource
	private XiaoziService xiaoziService;
	
	/**
	 * 后台启动图片管理首页，查询所有的启动图片
	 * @return
	 */
	public String index(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Long> l = xiaoziService.queryAppIdByPublisherId(this.getSessionPublisher().getId());
		if(l==null||l.size()==0)
			return SUCCESS;
		map.put("appId", l.get(0));
		phoenixStartPicList = phoenixStartPicService.getStartPicList(map);
		return SUCCESS;
	}
	
	
	/**
	 * @return
	 * @description 开机图片的添加
	 * @version 1.0
	 * @author devin.song
	 * @update 2013-5-14 上午10:16:32
	 */
	public String add() {
		this.jsonResult=JsonResult.getFailure();
		if(pic==null){
			this.jsonResult.setMessage("必须上传文件");
			return "uploadJson";
		}
		PhoenixStartPic psp = new PhoenixStartPic();
		//凤凰周刊，固定appId
		List<Long> l = xiaoziService.queryAppIdByPublisherId(this.getSessionPublisher().getId());
		if(l==null||l.size()==0)
		{
			this.jsonResult.setMessage("无对应的APPID");
			return "uploadJson";
		}
		this.appId=l.get(0);
		psp.setAppId(appId);
		psp.setPicPath(picPath);
		psp.setPicLink(picLink);
		psp.setStatus(PhoenixConstants.PHOENIX_START_PIC.STATUS_OK);
		if(StringUtils.isNotBlank(picLink)){
			psp.setType(PhoenixConstants.PHOENIX_START_PIC.TYPE_AD);
		}else{
			psp.setType(PhoenixConstants.PHOENIX_START_PIC.TYPE_OP);
		}
		this.jsonResult = phoenixStartPicService.savePojo(psp, pic, picContentType, picFileName);
		return "uploadJson";
	}
	
	/**
	 * @return
	 * @description 开机图片的更新
	 * @version 1.0
	 * @author devin.song
	 * @update 2013-5-14 上午10:19:33
	 */
	public String update() {
		this.jsonResult = JsonResult.getFailure();
		if(sortOrder!=null){
			sortOrder=0;
		}
		if(status==null){
			this.jsonResult.setMessage("状态不能为空");
			return JSON;
		}
		
		PhoenixStartPic psp = new PhoenixStartPic();
		psp.setAppId(appId);
		psp.setPicLink(picLink);
		psp.setSortOrder(sortOrder);
		psp.setStatus(status);
		psp.setType(type);
		this.jsonResult = phoenixStartPicService.savePojo(psp, pic, picContentType, picFileName);
		
		return JSON;
	}
	/**
	 * 删除(下架)启动图片
	 * @return
	 */
	public String delJson(){
		this.jsonResult=JsonResult.getSuccess();
		this.phoenixStartPicService.delById(id);
		return JSON;
	}
	/**
	 * 移动
	 * @return
	 */
	public String move(){
		this.jsonResult=JsonResult.getSuccess();
		PhoenixStartPic currentPic=this.phoenixStartPicService.getStartPicById(id);
		PhoenixStartPic opPic=null;
		if(moveLeft==1){
			opPic=this.phoenixStartPicService.queryNeighbourBySortOrder(currentPic.getId(), false);	
		}else{
			opPic=this.phoenixStartPicService.queryNeighbourBySortOrder(currentPic.getId(), true);	
		}
		if(currentPic!=null && opPic!=null){
			this.sortOrder=currentPic.getSortOrder();
			currentPic.setSortOrder(opPic.getSortOrder());
			opPic.setSortOrder(this.sortOrder);
			this.phoenixStartPicService.updateStartPic(currentPic);
			this.phoenixStartPicService.updateStartPic(opPic);
		}else{
			this.jsonResult=JsonResult.getFailure();
		}
		return JSON;
	}
	
	private Long id;
	
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 图片路径
	 */
	private String picPath;
	/**
	 * 图片跳转链接
	 */
	private String picLink;
	/**
	 * 图片顺序，数字大排在前面
	 */
	private Integer sortOrder;
	
	private List<PhoenixStartPic> phoenixStartPicList;
	
	/**
	 * 状态 0 无效 1 有效
	 */
	private Integer status;
	
	/**
	 * 开机图片的种类；0：固定图片；1：广告(链接地址不为空);2：操作引导性质的图片(只显示一次)
	 * @fields type
	 */
	private Integer type;
	/**
	 * 向左移1 向右移2
	 */
	private Integer moveLeft;
	
	private File pic;
	private String picContentType;
	private String picFileName;
	
	public List<PhoenixStartPic> getPhoenixStartPicList() {
		return phoenixStartPicList;
	}
	public Long getAppId() {
		return appId;
	}


	public void setAppId(Long appId) {
		this.appId = appId;
	}


	public String getPicPath() {
		return picPath;
	}


	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}


	public String getPicLink() {
		return picLink;
	}


	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}


	public Integer getSortOrder() {
		return sortOrder;
	}


	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public File getPic() {
		return pic;
	}


	public void setPic(File pic) {
		this.pic = pic;
	}


	public String getPicContentType() {
		return picContentType;
	}


	public void setPicContentType(String picContentType) {
		this.picContentType = picContentType;
	}


	public String getPicFileName() {
		return picFileName;
	}


	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getMoveLeft() {
		return moveLeft;
	}
	public void setMoveLeft(Integer moveLeft) {
		this.moveLeft = moveLeft;
	}
	
	
	
	
	

}
