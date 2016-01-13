package cn.magme.web.action.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.Issue;
import cn.magme.service.IssueService;
import cn.magme.web.action.BaseAction;
/**
 * 给114使用的接口
 * @author liufosheng
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/rss/rss114la.ftl",params={"contentType","text/xml"})})
public class RssFor114Action extends BaseAction {
	
	private static final Logger log=Logger.getLogger(RssFor114Action.class);
	
	private static final String cname=RssFor114Action.class.getName();
	
	@Resource
	private IssueService issueService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 363282617839771391L;
	
	
	public String execute(){
		log.info(cname+",114调用开始");
		issueMapList=new HashMap<Long,List<Issue>>();
		try {
			issueList=this.issueService.queryLastestIssuesWithCategory(null, null);
			for(Issue i:issueList){
				if(issueMapList.get(i.getPublicationId())!=null){
					issueMapList.get(i.getPublicationId()).add(i);
				}else{
					List<Issue> tempList=new ArrayList<Issue>();
					tempList.add(i);
					issueMapList.put(i.getPublicationId(), tempList);
				}
			}
			
		} catch (Exception e) {
			log.info(cname+",114调用发生异常",e);
		}
		log.info(cname+",114调用结束");
		return SUCCESS;
	}

	
	List<Issue> issueList;
	
	private Map<Long,List<Issue>> issueMapList;
	


	public Map<Long, List<Issue>> getIssueMapList() {
		return issueMapList;
	}


	public void setIssueMapList(Map<Long, List<Issue>> issueMapList) {
		this.issueMapList = issueMapList;
	}


	public List<Issue> getIssueList() {
		return issueList;
	}


	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}
	
	
}
