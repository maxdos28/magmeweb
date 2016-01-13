/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Tag;
import cn.magme.pojo.UserImage;
import cn.magme.service.PublisherService;
import cn.magme.service.TagService;
import cn.magme.service.UserImageService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-10-9
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/publisherPic.ftl")})
public class PublisherPicAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3615646496571441343L;
	private static final Logger log=Logger.getLogger(PublisherPicAction.class);

	@Resource
	private UserImageService userImageService;
	
	@Resource
	private TagService tagService;
	@Resource
	private PublisherService publisherService;
	
	
	private static final int DEFAULT_PAGE_SIZE=20;
	
	
	public String execute(){
		try {
			publisher=publisherService.queryById(publisherId);
			//TODO　查询标签
			//查询米商图片
			userImageList=userImageService.queryUserImageByPublisherIdAndTagName(publisherId, tagName, null, begin, size);
			tagList=tagService.queryImageTagByPidOrderByCount(publisherId);
		} catch (Exception e) {
			log.error("", e);
		}
		return "success";
	}
	
	public String picJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		try {
			userImageList=userImageService.queryUserImageByPublisherIdAndTagName(publisherId, tagName, null, begin, size);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		this.jsonResult.put("userImageList", userImageList);
		return JSON;
	}
	
	private Long publisherId;
	
	private String tagName;
	
	private int begin=0;
	
	private int size=DEFAULT_PAGE_SIZE;
	
	private Publisher publisher;
	
	private List<UserImage> userImageList;
	
	private List<Tag> tagList;
	
	

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<UserImage> getUserImageList() {
		return userImageList;
	}

	public void setUserImageList(List<UserImage> userImageList) {
		this.userImageList = userImageList;
	}
	
	
	
	
	

}
