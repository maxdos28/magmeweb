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
import cn.magme.pojo.FpageEventDto;
import cn.magme.service.FpageEventService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-11-29
 * @version $id$
 */
@Results({@Result(name="phone_index",location="/WEB-INF/pages/mobile/phone.ftl")
	,@Result(name="ipad_index",location="/WEB-INF/pages/mobile/ipad.ftl")})
public class MobileIndexAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2645718052268662577L;
	
	private static final Logger log=Logger.getLogger(MobileIndexAction.class);
	
	@Resource
	private FpageEventService fpageEventService;
	
	private static final String IPAD_PAGE="ipad_index";
	
	private static final String IPHONE_PAGE="phone_index";
	
	private static final String ANDROID_PHONE_PAGE="phone_index";
	
	/**
	 * 设备类型，默认为iphone
	 * device=pc
	 * device=ipad
	 * device=iphone
	 * device=android
	 */
	private String device="iphone";
	
	/**
	 * 默认页面大小
	 */
	private static final int DEFAULT_PAGE_SIEZ=15;
	
	private static final int DEFAULT_IPAD_PAGE_SIZE=20;
	
	/**
	 * 每页大小
	 */
	private int size=DEFAULT_PAGE_SIEZ;
	
	/**
	 * 开始记录
	 */
	private int begin=0;
	
	/**
	 * 类目id
	 */
	private Long sortId;
	
	
	private List<FpageEventDto> fpageEventList;
	
	
	
	public String execute(){
		String returnpage=this.getPageAndSetDefaultSize();
		this.getEventJson();
		return returnpage;
	}
	
	public String getEventJson(){
		//TODO false改为true
		String returnpage=this.getPageAndSetDefaultSize();
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<FpageEventDto> tmpfpageEventList=this.fpageEventService.getHomeEventList(sortId, null, "mobile", true, begin, size);
			int realSize=tmpfpageEventList==null?0:tmpfpageEventList.size();
			//过滤结果集,对于非ipad版本需要过滤
			if(!StringUtils.equalsIgnoreCase(this.device, "ipad") && tmpfpageEventList!=null && tmpfpageEventList.size()>0){
				fpageEventList=new ArrayList<FpageEventDto>();
				for(FpageEventDto dto:tmpfpageEventList){
					if(!StringUtils.equalsIgnoreCase(dto.getEventClass(),"22")){
						//手机版，图片参数要改变
						dto.setImgFile(dto.getPhoneImgFile());
						fpageEventList.add(dto);
					}
				}
			}else{
				fpageEventList=tmpfpageEventList;
			}
			if(fpageEventList!=null && fpageEventList.size()>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("fpageEventList", fpageEventList);
				this.jsonResult.put("eventBegin", begin+realSize);
			}else{
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage("no data");
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}

	/**
	 * 或得需要返回的页面，如果这个参数不正确，则返回iphone版
	 * @return
	 */
	private String getPageAndSetDefaultSize(){
		if(StringUtils.equalsIgnoreCase(this.device, "ipad")){
			this.size=DEFAULT_IPAD_PAGE_SIZE;
			return IPAD_PAGE;
		}else if(StringUtils.equalsIgnoreCase(this.device, "iphone")){
			return IPHONE_PAGE;
		}else if(StringUtils.equalsIgnoreCase(this.device, "android")){
			return ANDROID_PHONE_PAGE;
		}
		return IPHONE_PAGE;
	}
	public String getDevice() {
		return device;
	}
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
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}

	

	

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public List<FpageEventDto> getFpageEventList() {
		return fpageEventList;
	}
	
	
}
