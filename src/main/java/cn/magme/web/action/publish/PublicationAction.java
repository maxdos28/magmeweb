/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.LuceneService;
import cn.magme.service.PublicationService;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.manager.cache.PublicationCacheService;

/**
 * @author fredy.liu
 * @date 2011-5-25
 * @version $id$
 */
@Results({@Result(name="uploadIndexSuccess",location="/WEB-INF/pages/publishadmin/manageMgzUpload.ftl")})
public class PublicationAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4153317849011930124L;
	@Resource
	private PublicationService publicationService;
	@Resource
	private PublicationCacheService publicationCacheService;
	/*@Resource 
	private PublisherService publisherService;*/
	private static final Logger log=Logger.getLogger(PublicationAction.class);
	
	//@Resource
	//private LuceneService luceneService;
	/**
	 * 创建杂志
	 * @return
	 */
	public String createPub(){
		try {
			Publisher publisherObj=this.getSessionPublisher();
			//Publisher publisher=this.publisherService.queryById(1039L);
			Publication pub=new Publication();
			pub.setCategoryId(categoryId);
			pub.setDescription(description);
			pub.setLanguageId(languageId);
			pub.setName(name);
			pub.setWhratio(whratio);
			pub.setStatus(PojoConstant.PUBLICATION.STATUS.NOT_AUDIT.getCode());
			pub.setPublisherId(publisherObj.getId());
			pub.setIssueType(issueType);
			pub.setIssn(issn);
			pub.setPublisher(publisher);
			pub.setNeedPrint(0);//默认需要打印
			this.jsonResult=this.publicationService.validatePublication(pub);
			if(this.jsonResult.getCode()!=JsonResult.CODE.SUCCESS){
				return JSON;
			}
			this.jsonResult=this.publicationCacheService.addPublication(pub);
			this.jsonResult.put("pub", pub);
			//luceneService.addIndex(pub);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult=new JsonResult();
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	public String queryNormalPub(){
		Publisher publisher=this.getSessionPublisher();
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<Publication> pubs=this.publicationService.queryAuditByPublisherId(publisher.getId());
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("pubs", pubs);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	/**
	 * 删除杂志,就是下架
	 */
	public String delPubJson(){
		this.jsonResult = new JsonResult();
		try {
			Publisher publisher=this.getSessionPublisher();
			this.jsonResult=publicationCacheService.delByPublisher(id, publisher.getId());
		} catch (Exception e) {
			log.error("delPub", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 上架杂志，上架杂志的时候不会上架期刊
	 * @return
	 */
	public String upshelfPubJson(){
		this.jsonResult=new JsonResult();
		try {
			Publisher publisher=this.getSessionPublisher();
			Publication publicaion=this.publicationService.queryById(id);
			if(publicaion.getPublisherId().longValue()!=publisher.getId()){
				//只能上架自己的杂志
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("不能上架其他人的杂志");
				return JSON;
			}
			this.jsonResult=publicationCacheService.updateStatusNoLimitById(id, PojoConstant.PUBLICATION.STATUS.ON_SHELF.getCode());
			publicaion.setStatus(PojoConstant.PUBLICATION.STATUS.ON_SHELF.getCode());
			//luceneService.updateIndex(publicaion);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
		
	}
	
	/**
	 * 修改杂志的名称，描述，分类，语言
	 * @return
	 */
	public String updatePubJson(){
		try {
			this.jsonResult=new JsonResult();
			//默认失败
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			Publication pub=publicationService.queryById(id);
			
			if(StringUtil.isNotBlank(name)){
				pub.setName(name);
			}else{
				this.jsonResult.setMessage("杂志名称不能为空");
				return JSON;
			}
			if(StringUtil.isNotBlank(description)){
				pub.setDescription(description);
			}
			if(this.issueType!=null){
				pub.setIssueType(issueType);
			}
			pub.setWhratio(whratio);
			pub.setIssn(issn);
			pub.setPublisher(publisher);
			//categoryId>0
			if(!NumberUtil.isLessThanOrEqual0(categoryId)){
				pub.setCategoryId(categoryId);
			}else{
				this.jsonResult.setMessage("分类不能为空或小于0");
				return JSON;
			}
			//languageId>0
			if(!NumberUtil.isLessThanOrEqual0(languageId)){
				pub.setLanguageId(languageId);
			}else{
				this.jsonResult.setMessage("语言不能为空或小于0");
				return JSON;
			}
			this.jsonResult=this.publicationService.validatePublication(pub);
			if(this.jsonResult.getCode()!=JsonResult.CODE.SUCCESS){
				return JSON;
			}
			this.jsonResult=this.publicationCacheService.updateByIdAndPublisherId(pub);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			
			//luceneService.updateIndex(pub);
		} catch (Exception e) {
			log.error("update publication error", e);
			if(this.jsonResult == null){
				this.jsonResult = new JsonResult();
			}
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		
		return JSON;
	}
	/**
	 * 通过id查杂志
	 * @return
	 */
	public String getPublicationById(){
		this.jsonResult = new JsonResult();
		try {
			Publication pub = this.publicationCacheService.queryById(id);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.put("publication", pub);
			this.jsonResult.setMessage("success");
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	public String uploadIndex(){
		this.queryNormalPub();
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
			publicationList=(List<Publication>)this.jsonResult.getData().get("pubs");
		}
		return "uploadIndexSuccess";
	}
	
	private Long id;
	private Date createdTime;
	private Date updatedTime;
	private String name;
	private String description;
	private Long categoryId;
	private Long languageId;
	private Long popularIdx;
	private Integer status;
	private Long publisherId;
	private Integer issueType;
	private Integer whratio;
	private String issn;
	private String publisher;
	
	private List<Publication> publicationList;
	
	


	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public Integer getWhratio() {
		return whratio;
	}

	public void setWhratio(Integer whratio) {
		this.whratio = whratio;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	public Long getPopularIdx() {
		return popularIdx;
	}
	public void setPopularIdx(Long popularIdx) {
		this.popularIdx = popularIdx;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

	
	
	
	
}
