package cn.magme.web.action.newPublisher;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.danga.MemCached.MemCachedClient;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Link;
import cn.magme.service.EventOpusService;
import cn.magme.service.LinkService;




/**
 * 编辑=友情链接
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({
	@Result(name="upload_json",type="json",params={"root","eventUploadJsonResult","contentType","text/html"}),
	@Result(name="config",location="/WEB-INF/pages/newPublisher/magmeLink.ftl")})
public class EditLinkAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private LinkService linkService;
	@Resource
	private EventOpusService eventOpusService;  
	@Resource
	private MemCachedClient memCachedClient;
	
	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		Link link = linkService.getLink();
		if(link != null)
			content = link.getContent();
		else content = "";
		
		//首页页脚的友情链接
		this.doFriendLink();
		//杂志的友情链接
		this.doPubLink();
		
		return "config";
	}
	

	/**
	 * 根据杂志id获取对应的期刊
	 * @return
	 */
	public String doGetJson(){
		this.jsonResult = new JsonResult();
		content = linkService.getLink().getContent();
		this.jsonResult.put("content", content);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String doUpdate(){
		Link link = new Link();
		link.setStatus(PojoConstant.TLINK.STATUS_OK);
		link.setType(PojoConstant.TLINK.index_link);
		content = content.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
		link.setContent(content);
		linkService.updateLink(link);
		
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 首页页脚的数据查询
	 * @return
	 */
	public String doFriendLink(){
		Link link = new Link();
		link.setStatus(PojoConstant.TLINK.STATUS_OK);
		link.setType(PojoConstant.TLINK.index_footer_link);
		this.jsonResult = new JsonResult();
		Link dbLink = linkService.getLink(link);
		if(dbLink!=null){
			indexContent = dbLink.getContent();
		}
		this.jsonResult.put("indexContent", indexContent);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 首页页脚的数据更新
	 * @return
	 */
	public String doFLUpdate(){
		this.jsonResult = new JsonResult();
		Link link = new Link();
		link.setStatus(PojoConstant.TLINK.STATUS_OK);
		link.setType(PojoConstant.TLINK.index_footer_link);
		if(indexContent!=null){
			indexContent = indexContent.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
			link.setContent(indexContent);
			linkService.updateLink(link);
			
			if(memCachedClient!=null){
				Calendar cl = Calendar.getInstance();
				cl.add(Calendar.HOUR, CacheConstants.CACHE_TWO_HOUR);
				memCachedClient.set(CacheConstants.INDEX_FOOTER+"friendLink", link,cl.getTime());
			}
			
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 杂志页脚的数据查询
	 * @return
	 */
	public String doPubLink(){
		Link link = new Link();
		link.setStatus(PojoConstant.TLINK.STATUS_OK);
		link.setType(linkType);
		this.jsonResult = new JsonResult();
		Link dbLink = linkService.getLink(link);
		if(dbLink!=null){
			pubContent = dbLink.getContent();
		}
		this.jsonResult.put("indexContent", pubContent);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 杂志页脚的数据更新
	 * @return
	 */
	public String doPubUpdate(){
		this.jsonResult = new JsonResult();
		Link link = new Link();
		link.setStatus(PojoConstant.TLINK.STATUS_OK);
		link.setType(linkType);
		if(pubContent!=null){
			pubContent = pubContent.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
			link.setContent(pubContent);
			linkService.updateLink(link);
			
			if(memCachedClient!=null){
				Calendar cl = Calendar.getInstance();
				cl.add(Calendar.HOUR, CacheConstants.CACHE_TWO_HOUR);
				memCachedClient.set(CacheConstants.INDEX_FOOTER+"pubLink", link,cl.getTime());
			}
			
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	
	/**
	 * 图片上传
	 */
	public String uploadImg(){
		 this.eventUploadJsonResult=eventOpusService.saveEventUploadAdmin(imgFile,
	                imgFileFileName,imgFileContentType,this.getSessionAdmin().getId());
//		int MAX_IMG_SIZE =2048;
//		long userid = 1;
//		File imgFile = null;
//		if(imgFile!=null){
//			if(imgFile.length() > 2048){
//				this.generateJsonResult("error", "file_too_large");
//				this.generateJsonResult("message", MAX_IMG_SIZE/1024);
//				return "upload_json";
//			}
//		}
//		String uri = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/IMG_" + RandomStringUtils.randomAlphanumeric(4) 
//				+ '_' 
//				+ userid
//				+ '.' 
//				+ FilenameUtils.getExtension(imgFile.getName()).toLowerCase();
//		
//		this.generateJsonResult("error", 0);
//		this.generateJsonResult("message", uri);
		return "upload_json";
	}
	
	private String content;
	private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;
    private Map<String,Object> eventUploadJsonResult;
    
    
    private String indexContent;
    
    private Integer linkType=3;
    private String pubContent;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public Map<String, Object> getEventUploadJsonResult() {
		return eventUploadJsonResult;
	}

	public void setEventUploadJsonResult(Map<String, Object> eventUploadJsonResult) {
		this.eventUploadJsonResult = eventUploadJsonResult;
	}
	private String friendlink;

	public String getFriendlink() {
		return friendlink;
	}

	public void setFriendlink(String friendlink) {
		this.friendlink = friendlink;
	}


	public String getIndexContent() {
		return indexContent;
	}


	public void setIndexContent(String indexContent) {
		this.indexContent = indexContent;
	}


	public Integer getLinkType() {
		return linkType;
	}


	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}


	public String getPubContent() {
		return pubContent;
	}


	public void setPubContent(String pubContent) {
		this.pubContent = pubContent;
	}
	
	

}
