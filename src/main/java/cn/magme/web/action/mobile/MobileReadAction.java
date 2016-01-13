/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.FPAGEEVENT;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.TextPageDto;
import cn.magme.service.AdvertiseService;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.TextPageService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-11-29
 * @version $id$
 */
@Results({@Result(name="phoneread",location="/WEB-INF/pages/mobile/phoneread.ftl")
	,@Result(name="ipadread",location="/WEB-INF/pages/mobile/ipadread.ftl")})
public class MobileReadAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4802251966124352848L;

	private static final Logger log=Logger.getLogger(MobileReadAction.class);
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private TextPageService textPageService;
	
	@Resource
	private FpageEventService fpageEventService;
	
	@Resource
	private AdvertiseService advertiseService;
	
	/**
	 * android手机阅读器
	 */
	private static final String ANDROID_PHONE_READ_PAGE="phoneread";
	/**
	 * iphone阅读器
	 */
	private static final String IPHONE_READ_PAGE="phoneread";
	
	/**
	 * ipad阅读器
	 */
	private static final String IPAD_READ_PAGE="ipadread";
	
	
	
	

	/**
	 * 设备类型，默认为iphone
	 * device=pc
	 * device=ipad
	 * device=iphone
	 * device=android
	 */
	private String device="iphone";
	
	private Long eventId=0L;
	
	//插页广告ID
	private Long adId;
	
	//当前显示的是广告的左边半页还是右半边页
	private Integer curLR;
	//当前显示的是插页广告的左半边页
	private static final Integer curLR_LEFT=1;
	//当前显示的是插页广告的右半边页
	private static final Integer curLR_RIGHT=2;
	
	
	//翻页方向:向左翻页还是向右翻页
	private Integer direct;
	//向左翻页
	private static final Integer direct_LEFT=1;
	//向右翻页
	private static final Integer direct_RIGHT=2;
	
	private Long evetnAdId;
	private Advertise eventAdvertise;

	
	private Advertise advertise;
	
    public Advertise getAdvertise() {
		return advertise;
	}

	public void setAdvertise(Advertise advertise) {
		this.advertise = advertise;
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Integer getCurLR() {
		return curLR;
	}

	public void setCurLR(Integer curLR) {
		this.curLR = curLR;
	}

	public Integer getDirect() {
		return direct;
	}

	public void setDirect(Integer direct) {
		this.direct = direct;
	}

	/**
     * 类目id
     */
    private Long sortId;	
	
	/**
	 * 页码
	 * 默认页码1
	 */
	private Integer pageNo;
	
	private Integer lastPageNo;
	
	private Integer startPageNo;
	
	private TextPageDto textPage;
	
	private FpageEvent fpageEvent;
	
	private Long issueId;
	
	private Issue issue;
	
	private String returnPage;
	private List<Advertise> insertAdList=null;
	
	public List<Advertise> getInsertAdList() {
		return insertAdList;
	}

	public void setInsertAdList(List<Advertise> insertAdList) {
		this.insertAdList = insertAdList;
	}

	public String execute(){
		this.returnPage=this.getPage();
		this.getPageContentJson();
		
		if(!IPAD_READ_PAGE.equals(this.returnPage)){
			this.getInsertAd();
		}else{
			insertAdList=this.advertiseService.queryInsertAdsByIssueId(issueId,new String[]{"5"});
		}
		return returnPage;
	}
	
	public String getPageContentJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(issueId==null){
			if(eventId==null || eventId<=0){
				log.error("eventId must bigger than zero");
				this.jsonResult.setMessage("eventId must bigger than zero");
				return JSON;
			}
			
			fpageEvent=fpageEventService.getFpageEventById(this.eventId);
			if(fpageEvent==null||fpageEvent.getStatus()!=FPAGEEVENT.STATUS_OK.intValue()){
				log.error("fpageEvent 事件状态不为正常状态");
				this.jsonResult.setMessage("fpageEvent 事件状态不为正常状态");
				return JSON;
			}
			startPageNo=fpageEvent.getPageNo();
			lastPageNo=fpageEvent.getEndPageNo();
			if(this.pageNo==null || this.pageNo<=0){
				this.pageNo=startPageNo;
			}
			this.issueId=fpageEvent.getIssueId();
			this.jsonResult.put("eventTitle", this.fpageEvent.getTitle());
			this.evetnAdId=fpageEvent.getAdId();
		}
		//issueId
		issue=this.issueService.queryById(issueId);
		if(issue==null || issue.getStatus()!=PojoConstant.ISSUE.STATUS.ON_SALE.getCode()){
			log.error("issue not exist or issue not on shelf");
			this.jsonResult.setMessage("issue not exist or issue not on shelf");
			return JSON;
		}
		
		if(this.pageNo==null || this.pageNo<=0){
			this.pageNo=1;
		}
		textPage=textPageService.queryNormalByIssueIdAndPageNo(issue.getId(), this.pageNo);
		this.jsonResult.put("textPage", textPage);
		
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);

		return JSON;
	}
	
	private void getInsertAd(){
		//翻页方向为NULL,表示要查看这个插页广告或事件或杂志页,优先级是插页广告>事件>杂志页
		if(this.direct==null){
			Advertise ad=advertiseService.queryInsertAdsById(adId);
			if(ad==null&&this.fpageEvent!=null){
				ad=advertiseService.queryInsertAdsById(this.fpageEvent.getAdId());
			}
			
			this.advertise=ad;
			//默认是看广告的左边页,如果广告为NULL,这个参数无意义
			this.curLR=this.curLR_LEFT;
			return;
		}
		
		List<Advertise> insertAdList=this.advertiseService.queryInsertAdsByIssueId(issueId,new String[]{"5"});
		List<Advertise> adList=new ArrayList<Advertise>();
		for(Advertise insertAd:insertAdList){
			//向左翻页的插页广告
			if(this.direct_LEFT.equals(this.direct)){
				if(insertAd.getPageNo()==(this.pageNo.intValue()+1)){
					adList.add(insertAd);
					if(insertAd.getId().equals(this.evetnAdId)){
						eventAdvertise=insertAd;
					}
				}				
			//向右翻页的插页广告
			}else{
				if(insertAd.getPageNo()==(this.pageNo.intValue())){
					adList.add(insertAd);
					if(insertAd.getId().equals(this.evetnAdId)){
						eventAdvertise=insertAd;
					}
				}
			}
		}
		
		//如果找不到插页广告列表,直接返回
		if(adList.isEmpty()){
			this.advertise=null;
			this.curLR=null;
			return;
		}
		
		Advertise leftAd=null;
		Advertise midAd=null;
		Advertise rightAd=null;
		
		adList.add(0,null);
		adList.add(null);
		boolean flag=false;
		for(Advertise ad:adList){
			leftAd=midAd;
			midAd=rightAd;
			rightAd=ad;
			
			if(midAd!=null&&midAd.getId().equals(this.adId)){
				flag=true;
				break;
			}
		}
		
		if(!flag){
			if(eventAdvertise!=null){
				this.advertise=eventAdvertise;
				this.curLR=this.curLR_LEFT;
			}else{
				//向左翻页
				if(this.direct_LEFT.equals(this.direct)){
					this.advertise=adList.get(adList.size()-2);
					this.curLR=this.curLR_RIGHT;
				//向右翻页	
				}else{
					this.advertise=adList.get(1);
					this.curLR=this.curLR_LEFT;
				}
			}
		}else{
			//向左翻页
			if(this.direct_LEFT.equals(this.direct)){
				//当前展示的是广告的左半页
				if(this.curLR_LEFT.equals(this.curLR)){
					this.advertise=leftAd;
					this.curLR=this.curLR_RIGHT;
				//当前展示的是广告的右半页	
				}else if(this.curLR_RIGHT.equals(this.curLR)){
					this.advertise=midAd;
					this.curLR=this.curLR_LEFT;
				}else{
					this.advertise=midAd;
					this.curLR=this.curLR_RIGHT;
				}
			//向右翻页		
			}else{
				//当前展示的是广告的左半页
				if(this.curLR_LEFT.equals(this.curLR)){
					this.advertise=midAd;
					this.curLR=this.curLR_RIGHT;
				//当前展示的是广告的右半页	
				}else if(this.curLR_RIGHT.equals(this.curLR)){
					this.advertise=rightAd;
					this.curLR=this.curLR_LEFT;
				}else{
					this.advertise=midAd;
					this.curLR=this.curLR_LEFT;
				}
			}
		}
	}
	/**
	 * 或得需要返回的页面，如果这个参数不正确，则返回iphone版
	 * @return
	 */
	private String getPage(){
		if(StringUtils.equalsIgnoreCase(this.device, "ipad")){
			return IPAD_READ_PAGE;
		}else if(StringUtils.equalsIgnoreCase(this.device, "iphone")){
			return IPHONE_READ_PAGE;
		}else if(StringUtils.equalsIgnoreCase(this.device, "android")){
			return ANDROID_PHONE_READ_PAGE;
		}
		return IPHONE_READ_PAGE;
	}
	


	public FpageEvent getFpageEvent() {
		return fpageEvent;
	}

	public void setFpageEvent(FpageEvent fpageEvent) {
		this.fpageEvent = fpageEvent;
	}

	public Integer getLastPageNo() {
		return lastPageNo;
	}

	public Integer getStartPageNo() {
		return startPageNo;
	}

	public TextPageDto getTextPage() {
		return textPage;
	}

	public void setTextPage(TextPageDto textPage) {
		this.textPage = textPage;
	}

	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getDevice() {
		return device;
	}
	
	
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
    

    public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	/**
	 * 如果设置的参数值不合法，则默认为iphone
	 * @param device
	 */
	public void setDevice(String device) {
		//如果device没有通过get或post方式的参数传递过来,则试图从header里面取
		if(StringUtil.isBlank(device)){
			this.device=ServletActionContext.getRequest().getHeader("device");
		}			
		if(!StringUtils.equalsIgnoreCase(this.device, "ipad")
				&& !StringUtils.equalsIgnoreCase(this.device, "iphone") 
				&& !StringUtils.equalsIgnoreCase(this.device, "android")){
			this.device="iphone";
			return;
		}
		this.device = device;
	}
	
	
}
