package cn.magme.web.action.phoenix;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.phoenix.PhoenixConstants;
import cn.magme.pojo.phoenix.PhoenixStartPic;
import cn.magme.service.phoenix.PhoenixStartPicService;
import cn.magme.util.FileOperate;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @description 凤凰周刊的开机动画Action
 * @version 1.0
 * @author devin.song
 * @update 2013-5-13 上午10:17:01
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/startPicManager.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
			"jsonResult", "contentType", "text/html" })})
public class StartPicAction extends BaseAction {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5211806919575103157L;
	
	@Resource PhoenixStartPicService phoenixStartPicService;
	private static final Logger log=Logger.getLogger(StartPicAction.class);
	
	private Integer showType;//显示类型轮播或滑动
	private String tempFile;//临时图片的地址
	
	/**
	 * 后台启动图片管理首页，查询所有的启动图片
	 * @return
	 */
	public String index(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", this.getSessionPhoenixUser().getAppId());
		phoenixStartPicList = phoenixStartPicService.getStartPicList(map);
		showType = this.phoenixStartPicService.getShowType(this.getSessionPhoenixUser().getAppId());
		return SUCCESS;
	}
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @description 获取开机动画的集合
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.jsonResult = JsonResult.getFailure();
		if(appId==null){
			this.jsonResult.setMessage("appId不能为空");
			return JSON;
		}
		map.put("appId", appId);
		showType = this.phoenixStartPicService.getShowType(appId);
		if(showType==null)
			showType = 1;
//		map.put("type", value);
		List<PhoenixStartPic> startPicList = phoenixStartPicService.getStartPicList(map);
		this.jsonResult = JsonResult.getSuccess();
		if(startPicList!=null&&startPicList.size()>0)
		{
			List<PhoenixStartPic> l = new ArrayList();
			for(int i=0;i<startPicList.size();i++)
			{
				PhoenixStartPic p = startPicList.get(i);
				//轮播
				if(showType==1)
				{
					if(p.getType()!=null&&p.getType().intValue()==PhoenixConstants.PHOENIX_START_PIC.TYPE_HD)
					continue;
				}
				//滑动
				else 
				{
					if(p.getType()==null||p.getType().intValue()!=PhoenixConstants.PHOENIX_START_PIC.TYPE_HD)
						continue;
				}
				l.add(p);
			}
			this.jsonResult.put("startPicList", l);
		}
		return JSON;
	}
	
	/**
	 * 检查图片是否符合
	 * (大小 <100k)  (滑动图片 1600x1024， 轮播图片 768x1024) 
	 * @return
	 */
	public String checkPic()
	{
		this.jsonResult=JsonResult.getFailure();
		if(showType==null||showType<=0)
		{
			this.jsonResult.setMessage("必须选择类型");
			return JSON;
		}
		if(pic==null){
			this.jsonResult.setMessage("必须上传图片");
			return JSON;
		}
		if(pic!=null&&picFileName!=null&&!picFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("图片类型必须是jpg");
			this.jsonResult.setMessage("图片类型必须是jpg");
			return "fileUploadJson";
		}
		
		try {
			BufferedImage buff1 = ImageIO.read(pic);
			this.jsonResult.put("checkPic", 1);
			//轮播
			if (showType==1) {

				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				String msg = "您上传的图片";
				if(buff1.getHeight() != 1024 || buff1.getWidth() !=768 )
				{
					log.warn("图片尺寸不符合");
					msg+="尺寸为"+buff1.getWidth()+"*"+buff1.getHeight()+",不符合标准尺寸(768*1024),";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				if(pic.length()>(100*1024))
				{
					log.warn("图片大小不符合");
					msg+="大小超过100K,";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				msg+="是否确认?";
				this.jsonResult.setMessage(msg);				
			}
			else if(showType==2)
			{
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				String msg = "您上传的图片";
				if(buff1.getHeight() != 1024 || buff1.getWidth() != 1600)
				{
					log.warn("图片尺寸不符合");
					msg+="尺寸为"+buff1.getWidth()+"*"+buff1.getHeight()+",不符合标准尺寸(1600*1024),";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				if(pic.length()>(100*1024))
				{
					log.warn("图片大小不符合");
					msg+="大小超过100K,";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				msg+="是否确认?";
				this.jsonResult.setMessage(msg);
			}
			else
			{
				this.jsonResult.setMessage("参数错误");
				return "fileUploadJson";
			}

			String staticPath = systemProp.getStaticLocalUrl()+"/temp/";
			File dir = new File(staticPath);
			if(!dir.exists())
				dir.mkdirs();
			String tempFileName = staticPath+"temp_"+System.nanoTime()+".jpg";
			FileOperate.copyFile(pic.getAbsolutePath(), tempFileName);
			this.jsonResult.put("tempFile", tempFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fileUploadJson";
	}
	
	/**
	 * @return
	 * @description 开机图片的添加
	 * @version 1.0
	 * @author devin.song
	 * @update 2013-5-14 上午10:16:32
	 */
	public String add() {
		if(StringUtil.isNotBlank(this.tempFile))
		{
			picContentType = "image/jpg";
			picFileName = this.tempFile.substring(this.tempFile.lastIndexOf("/")+1);
			this.pic = new File(this.tempFile);
		}
		checkPic();
		if(this.jsonResult.getCode()!=200)
		{
			return "fileUploadJson";
		}
		PhoenixStartPic psp = new PhoenixStartPic();
		//凤凰周刊，固定appId
		this.appId=this.getSessionPhoenixUser().getAppId();
		psp.setAppId(appId);
		psp.setPicPath(picPath);
		psp.setPicLink(picLink);
		psp.setStatus(PhoenixConstants.PHOENIX_START_PIC.STATUS_OK);
		//轮播
		if(showType==1)
		{
			if(StringUtils.isNotBlank(picLink)){
				psp.setType(PhoenixConstants.PHOENIX_START_PIC.TYPE_AD);
			}else{
				psp.setType(PhoenixConstants.PHOENIX_START_PIC.TYPE_OP);
			}
		}
		//滑动
		else
		{
			psp.setType(PhoenixConstants.PHOENIX_START_PIC.TYPE_HD);
		}
		
		this.jsonResult = phoenixStartPicService.savePojo(psp, pic, picContentType, picFileName);
		if(pic!=null)
			this.pic.delete();
		return "fileUploadJson";
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
	
	/**
	 * 
	 * @return
	 */
	public String saveShowType() {
		this.jsonResult=JsonResult.getFailure();
		if(showType==null||showType<=0)
		{
			this.jsonResult.setMessage("请选择显示方式");
			return JSON;
		}
		
		int r = phoenixStartPicService.saveShowType(showType, this.getSessionPhoenixUser().getAppId());
		if(r==0)
		{
			this.jsonResult.setMessage("保存失败");
			return JSON;
		}
		return this.index();
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
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public String getTempFile() {
		return tempFile;
	}
	public void setTempFile(String tempFile) {
		this.tempFile = tempFile;
	}
}
