package cn.magme.web.action.newPublisher;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Admin;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;




/**
 * 嵌入式阅读器
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/emReader.ftl")})
public class EmReaderAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource
	private PublicationService publicationService;
	@Resource
	private IssueService issueService;
	

	/**
	 * 阅读器 数据初始化
	 * @return
	 */
	public String to(){
		Publisher publisher=this.getSessionPublisher();
		Admin admin= this.getSessionAdmin();
		if(publisher!=null
				&&(publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_0)
				||publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_1))){
			this.publicationList=publicationService.getListByNameAndPublisherId(null, publisher.getId(), null, null,PojoConstant.PUBLICATION.PUBTYPE.MAGAZINE.getCode());
			//此处不处理互动杂志
			if(this.publicationList!=null)
			{
				for(int i=0;i<this.publicationList.size();i++)
				{
					Publication p = this.publicationList.get(i);
					if(p.getPubType().equals(PojoConstant.PUBLICATION.PUBTYPE.ACTIVE_MAGAZINE.getCode()))
					{
						this.publicationList.remove(i);
						i--;
					}
				}
			}
			return "config";
		}else if(admin!=null){
			this.publicationList=publicationService.getListByNameAndPublisherId(null, null, null, null,PojoConstant.PUBLICATION.PUBTYPE.MAGAZINE.getCode());
			return "config";
		}else{
			return "deny";
		}
	}
	
	/**
	 * 根据杂志id获取对应的期刊
	 * @return
	 */
	public String doIssueJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(pubId!=null && pubId>0){
			issueList=issueService.getByPublicationId(pubId);
			this.jsonResult.put("issueList", issueList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	
	
	private List<Publication> publicationList;
	private Long pubId;
	private List<Issue> issueList;


	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public Long getPubId() {
		return pubId;
	}

	public void setPubId(Long pubId) {
		this.pubId = pubId;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}
	
	
	
}
