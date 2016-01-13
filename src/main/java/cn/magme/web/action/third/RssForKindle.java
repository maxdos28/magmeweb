package cn.magme.web.action.third;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.FpageEventDto;
import cn.magme.service.FpageEventService;
import cn.magme.web.action.BaseAction;

@Results({@Result(name="success",location="/WEB-INF/pages/rss/rsskindle.ftl",params={"contentType","text/xml"})})
public class RssForKindle extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5014133785837896329L;
	
	@Resource 
	private FpageEventService fpageEventService;

	public String execute(){
		fpageEventList=this.fpageEventService.getHomeEventList(null, null, "mobile", false, 0, 25);
		return SUCCESS;
	}
	
	private List<FpageEventDto> fpageEventList;
	
	private Date today=new Date();

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public List<FpageEventDto> getFpageEventList() {
		return fpageEventList;
	}

	public void setFpageEventList(List<FpageEventDto> fpageEventList) {
		this.fpageEventList = fpageEventList;
	}
	
	

}
