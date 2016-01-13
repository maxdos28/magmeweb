package cn.magme.web.action.third;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.TextPageDto;
import cn.magme.service.FpageEventService;
import cn.magme.service.TextPageService;
import cn.magme.web.action.BaseAction;

@Results({@Result(name="success",location="/WEB-INF/pages/rss/rsskindledetail.ftl")})
public class RssEventDetail extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6286025883562864607L;
	
	private static final Logger log=Logger.getLogger(RssEventDetail.class);
	
	@Resource
	private FpageEventService fpageEventService;
	
	@Resource
	private TextPageService textPageService;
	
	public String execute(){
		try {
			textPageList=new ArrayList<TextPageDto>();
			event=fpageEventService.getFpageEventById(eventId);
			int startPageNo=event.getPageNo();
			int endPageNo=event.getEndPageNo();
			for(int i=startPageNo;i<=endPageNo;i++){
				TextPageDto textPage=textPageService.queryNormalByIssueIdAndPageNo(event.getIssueId(), i);
				textPageList.add(textPage);
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
		return SUCCESS;
	}
	
	
	private Long eventId;
	
	private FpageEvent event;
	
	private List<TextPageDto> textPageList;
	
	public List<TextPageDto> getTextPageList() {
		return textPageList;
	}


	public void setTextPageList(List<TextPageDto> textPageList) {
		this.textPageList = textPageList;
	}


	public FpageEvent getEvent() {
		return event;
	}


	public void setEvent(FpageEvent event) {
		this.event = event;
	}


	public Long getEventId() {
		return eventId;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	
	

}
