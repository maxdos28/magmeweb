package cn.magme.web.action.third;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SystemProp;
import cn.magme.pojo.Issue;
import cn.magme.service.IssueService;
import cn.magme.web.action.BaseAction;

/**
 * 网易采编系统杂志
 * 以RSS方式提供给网易
 * 杂志以appId方式获取,以时间戳为条件,只获取上架状态的期刊
 * @author jasper
 * @date 2013.8.12
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/rss/rssneteaseForMagiceditor.ftl",params={"contentType","text/xml"})})
public class RssIssueAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525324507718552242L;
	
	@Resource
	private IssueService issueService;
	@Resource
	private SystemProp systemProp;
	
	private Long appId = 902L;
	private String updatetime;
	private Date today=new Date();
	
	public String execute(){
		this.jsonResult = JsonResult.getFailure();
		issueList=this.issueService.searchByTimeAndAppId(appId, updatetime, null, null);
		String basePath = this.systemProp.getMagicEditorPath()+File.separator+appId+File.separator;
		List<Map> resultList = new ArrayList();
		if(issueList!=null && issueList.size()>0){ 
			for(Issue i:issueList){
		        String issuePath=basePath+i.getPublicationId()+File.separator+i.getId()+File.separator;
		        if(i.getFileName()!=null)
		        {
		        	File destFile=new File(issuePath+i.getId()+i.getFileName().toLowerCase().substring(i.getFileName().toLowerCase().lastIndexOf("."), i.getFileName().toLowerCase().length()));
		        	i.setP1Size(""+destFile.length());
		        	i.setFileName(destFile.getName());
		        }
		        i.setFileName(systemProp.getStaticServerUrl()+"/appprofile/"+appId+i.getPublicationId()+i.getId()+i.getFileName());
		        if(i.getCreatedTime()!=null&&i.getUpdatedTime()!=null&&i.getCreatedTime().equals(i.getUpdatedTime()))
		        	i.setStatus(1);//新增
		        else
		        	i.setStatus(2);//修改
		        Map m = new HashMap();
		        m.put("issueName", i.getIssueNumber());
		        m.put("issueId", i.getId());
		        m.put("publicationId", i.getPublicationId());
		        m.put("totalPage", i.getTotalPages());
		        m.put("description", i.getDescription());
		        m.put("mpresUrl", i.getFileName());
		        m.put("swfUrl", "");
		        m.put("mpresSize", i.getP1Size());
		        m.put("swfSize", 0);
		        if(i.getStatus()==1)
		        	m.put("status", "create");
		        else
		        	m.put("status", "update");
		        m.put("updateTime", i.getUpdatedTime());
		        resultList.add(m);
			}
		}
		this.jsonResult.put("issueList", resultList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	private List<Issue> issueList;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
	
	
	

	

}
