/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.constants.CommonSeparator;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdPosition;
import cn.magme.pojo.InteractiveContent;
import cn.magme.service.InteractiveContentService;
import cn.magme.util.ConvertVideoToFlv;
import cn.magme.util.FileOperate;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-5-22
 * @version $id$
 */
public class InteractiveContentAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 645695837785028737L;
	
	@Resource
	private InteractiveContentService interactiveContentService;
	
	private static final Logger log=Logger.getLogger(InteractiveContentAction.class);
	
	private static final String INTERACTIVE_VIDEO_TYPE = ".*\\.(avi|mpg|mp4|mov)$";
	
	private static final Pattern INTERACTIVE_VIDOE_PATTERN=Pattern.compile(INTERACTIVE_VIDEO_TYPE,Pattern.CASE_INSENSITIVE);
	
	private static final String INTERACTIVE_PIC_AND_FLV_TYPE = ".*\\.(flv|jpg|jpeg|pjpeg|gif|png)$";
	
	private static final Pattern INTERACTIVE_PIC_AND_FLV_PATTERN=Pattern.compile(INTERACTIVE_PIC_AND_FLV_TYPE,Pattern.CASE_INSENSITIVE);
	
	public String execute(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.issueId==null || this.issueId<=0){
			log.error("期刊id必需大于0");
			return JSON;
		}
		try {
			List<InteractiveContent> contentList=interactiveContentService.queryByStatusAndIssueid(1,this.issueId);
			if(contentList!=null && contentList.size()>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("contentList", contentList);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String add(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		if(NumberUtil.isLessThan0(height) || NumberUtil.isLessThan0(width)){
			this.jsonResult.setMessage("内容的宽和高不能小于0");
			return JSON;
		}
		if(NumberUtil.isLessThan0(issueId) || NumberUtil.isLessThan0(pageNo)){
			this.jsonResult.setMessage("期刊和页码不能小于0");
			return JSON;
		}
		if(NumberUtil.isLessThan0(posx) || NumberUtil.isLessThan0(posy)){
			this.jsonResult.setMessage("位置的x和y不能小于0");
			return JSON;
		}
		if(StringUtil.isBlank(this.serverPath)){
			this.jsonResult.setMessage("serverPath为空");
			return JSON;
		}
		
		
		//检查上传文件
		String tmpContentLocal=this.systemProp.getInteractiveContentLocalUrl()+File.separator+this.getSessionAdUserId()+File.separator+"tmp"+File.separator;
		String contentLocal=this.systemProp.getInteractiveContentLocalUrl()+File.separator+this.getSessionAdUserId()+File.separator;
		File tmpcontentLocalDir=new File(tmpContentLocal);
//		if(StringUtil.isBlank(tmpContentLocal) || !tmpcontentLocalDir.exists() || !tmpcontentLocalDir.isDirectory()){
//			this.jsonResult.setMessage("没有上传文件");
//			return JSON;
//		}
//		
//		//转换文件
//		File []contentFiles=tmpcontentLocalDir.listFiles();
//		if(contentFiles==null || contentFiles.length<=0){
//			this.jsonResult.setMessage("没有上传文件或文件不存在");
//			return JSON;
//		}
		String contentServerUrl=this.systemProp.getInteractiveContentServerUrl()+"/"+this.getSessionAdUserId()+"/";
		StringBuilder contentUrls=new StringBuilder("");
		
		String [] serverPathArr=serverPath.split(CommonSeparator.SEMICONLON);
		Set<String> serverPathSet=new HashSet<String>();
		for(String serverPathTmp:serverPathArr){
			serverPathSet.add(serverPathTmp);
		}
		int count=0;
		for(String contentFileStr:serverPathSet){
			File contentFile=new File(contentFileStr);
			if(!contentFile.exists()){
				this.jsonResult.setMessage("文件不存在"+contentFileStr);
				return JSON;
			}
			
			if(!INTERACTIVE_VIDOE_PATTERN.matcher(contentFile.getName()).matches()
					&& !INTERACTIVE_PIC_AND_FLV_PATTERN.matcher(contentFile.getName()).matches()){
				this.jsonResult.setMessage("只能上传视频文件或图片文件，支持格式:avi,mpg,mp4,mov,flv,jpg,jpeg,pjpeg,gif,png");
				return JSON;
			}
			//图片格式或flv格式
			if(INTERACTIVE_PIC_AND_FLV_PATTERN.matcher(contentFile.getName()).matches()){
				String destFile=contentLocal+System.currentTimeMillis()+count+contentFile.getName().substring(contentFile.getName().lastIndexOf("."));
				//拼接服务器地址
				count++;
				contentUrls.append(contentServerUrl+new File(destFile).getName());
				contentUrls.append(CommonSeparator.SEMICONLON);
				FileOperate.copyFile(contentFile.getAbsolutePath(), destFile);
				FileOperate.delFile(contentFile.getAbsolutePath());
			}else{
				//视频格式
				String destFile=contentLocal+System.currentTimeMillis()+count+".flv";
				count++;
				//拼接服务器地址
				contentUrls.append(contentServerUrl+new File(destFile).getName());
				contentUrls.append(CommonSeparator.SEMICONLON);
				ConvertVideoToFlv convertor=new ConvertVideoToFlv(contentFile.getAbsolutePath(),this.systemProp,destFile);
				convertor.start();
			}
			
			
		}
		
		
		//创建广告位
		AdPosition adpos=new AdPosition();
		adpos.setAdposType(3);
		adpos.setDescription("互动广告");
		adpos.setKeywords("互动广告");
		adpos.setHeight(height);
		adpos.setWidth(width);
		adpos.setIssueId(issueId);
		adpos.setPageNo(pageNo);
		adpos.setPosx(posx);
		adpos.setPosy(posy);
		adpos.setStatus(1);
		adpos.setType(3);
		adpos.setUserId(this.getSessionAdUserId());
		adpos.setUserName(this.getSessionAdUser().getName());
		adpos.setTitle(title);
		Integer usertypeid=PojoConstant.InteractiveContent.USER_TYPE_ADMIN;
		if(this.getSessionAdUser().getLevel()==1){
			usertypeid=PojoConstant.InteractiveContent.USER_TYPE_PUBLISHER;
		}
		adpos.setUserTypeId(usertypeid);
		
		//创建互动内容
		InteractiveContent content=new InteractiveContent();
		content.setContentUrls(contentUrls.toString());
		content.setDescription(description);
		content.setIssueId(issueId);
		//content.setLinkurl(linkurl);
		content.setPageNo(pageNo);
		content.setStatus(PojoConstant.InteractiveContent.STATUS_ON);//版本恢复
		content.setUserId(getSessionAdUserId());
		content.setUserTypeId(usertypeid);
		content.setAdPosition(adpos);
		content.setTitle(title);
		Long contentid=interactiveContentService.insert(content);
		if(!NumberUtil.isLessThan0(contentid)){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		
		return JSON;
	}
	
	private String description;
	private Long issueId;
	private Integer pageNo;
	private Double posx;
	private Double posy;
	private Float height;
	private Float width;
	private String title;
	
	private String serverPath;
	
	
	
	

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public InteractiveContentService getInteractiveContentService() {
		return interactiveContentService;
	}

	public void setInteractiveContentService(
			InteractiveContentService interactiveContentService) {
		this.interactiveContentService = interactiveContentService;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Double getPosx() {
		return posx;
	}

	public void setPosx(Double posx) {
		this.posx = posx;
	}

	public Double getPosy() {
		return posy;
	}

	public void setPosy(Double posy) {
		this.posy = posy;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	
}
