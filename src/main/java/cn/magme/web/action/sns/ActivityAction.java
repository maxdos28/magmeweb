package cn.magme.web.action.sns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.result.sns.CreativeListResult;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.sns.SnsActivityService;
import cn.magme.web.action.BaseAction;

@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/activity.ftl"),
	@Result(name="next",location="/WEB-INF/pages/sns/activity_list.ftl"),
	@Result(name="init",type="redirect",location="/sns/activity.action")
	
})
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActivityAction extends BaseAction{
	private static final long serialVersionUID = 2629455035413433073L;
	
	@Resource
	private SnsActivityService snsActivityService;
	
	@Resource
    private IssueService issueService;
	
	@Resource
	private PublicationService publicationService;
	
	
	@Override
	public String execute() throws Exception {
		if(type==null && "".equals(type)){
			type="ss";
		}else{
			if(!"ss".equals(type) && !"qc".equals(type) && !"jj".equals(type) && !"ye".equals(type) && !"ly".equals(type) && !"ms".equals(type))
				type="ss";
		}
		
		Map map = new HashMap();
		map.put("userId", getSessionUserId());
		map.put("tag", type);
		map.put("size", 20);
		map.put("begin",begin);
		creativeList=snsActivityService.getActivityList(map);
		
		if(begin>0)
			return "next";
		else{
			Map m = new HashMap();
			m.put("userId", getSessionUserId());
			m.put("type", type);
			orderScore = snsActivityService.getOrderScore(m);
			orderEnjoy = snsActivityService.getOrderEnjoy(m);
			
			if("ss".equals(type))
				publicationid=493L;
			else if("qc".equals(type))
				publicationid=548L;
			else if("jj".equals(type))
				publicationid=552L;
			else if("ye".equals(type))
				publicationid=321L;
			else if("ly".equals(type))
				publicationid=387L;
			else if("ms".equals(type))
				publicationid=499L;
			
			if(publicationid!=null){
				List<Issue> issueNewList=issueService.getByPublicationId(publicationid);
				if(issueNewList!=null && issueNewList.size()>0){
					issueid=issueNewList.get(0).getId();
					publicationName = issueNewList.get(0).getPublicationName();
				}
			}
			Publication publication=publicationService.queryById(publicationid);
			this.englishName=publication.getEnglishname();
			this.pubDomain=publication.getDomain();
		}
		
		return SUCCESS;
	}
	
	public String activity(){
		snsActivityService.activityScore();
		return "init";
	}
	
	private Long publicationid;
	private Long issueid;
	private String publicationName;
	private List<CreativeListResult> creativeList;
	private List<PublicResult> orderScore;
	private List<PublicResult> orderEnjoy;
	private Integer begin=0;
	private String type;
	private String englishName;
	private String pubDomain;
	
	
	
	

	public String getPubDomain() {
		return pubDomain;
	}

	public void setPubDomain(String pubDomain) {
		this.pubDomain = pubDomain;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public List<CreativeListResult> getCreativeList() {
		return creativeList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<PublicResult> getOrderScore() {
		return orderScore;
	}

	public List<PublicResult> getOrderEnjoy() {
		return orderEnjoy;
	}

	public Long getPublicationid() {
		return publicationid;
	}

	public void setPublicationid(Long publicationid) {
		this.publicationid = publicationid;
	}

	public Long getIssueid() {
		return issueid;
	}
	
	public String getPublicationName() {
		return publicationName;
	}
}
