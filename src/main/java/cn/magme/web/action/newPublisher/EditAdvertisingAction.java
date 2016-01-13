package cn.magme.web.action.newPublisher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.JSONResult;


import cn.magme.common.JsonResult;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdvertiseService;
import cn.magme.service.InteractiveContentService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;




/**
 * 编辑=广告管理
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/magmeAdvertising.ftl"),
	@Result(name="success",location="/WEB-INF/pages/dialog/editAdvertise.ftl")})
public class EditAdvertisingAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private AdvertiseService advertiseService;
	@Resource
	private InteractiveContentService interactiveContentService;
	
	private final Long adSize = 10L;

	/**
	 * 广告设置的起始页 互动广告
	 * @return
	 */
	public String to(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("begin", 0);//初始化
		map.put("size", adSize);
		map.put("adType", new String[]{"1","2","3"});//默认互动广告
		this.adList = advertiseService.getListPublicatonLevel(map);
		int listcount = advertiseService.getListPublicatonLevelCount(map);
		this.adPageNo= (listcount+adSize-1)/adSize;
		this.adCurrentPage = 1L;
		return "config";
	}
	/**
	 * 广告的查询 翻页
	 * @return
	 */
	public String doJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.advertise!=null){
			Map<String,Object> map = this.queryMap(this.advertise);
			int statusTemp = advertise.getStatus()==null?99:advertise.getStatus();
			int adTypeTemp = advertise.getAdType()==null?1:advertise.getAdType();
	 		
	 		if(this.adCurrentPage==null || this.adCurrentPage<=0)this.adCurrentPage=1L;
			map.put("begin",this.adSize*(this.adCurrentPage-1));
			map.put("size", this.adSize);
	 		if(adTypeTemp==1 || adTypeTemp==3){//互动广告和插页广告
	 			if(statusTemp==1){//已审核
	 				map.put("status", new String[]{"2","5","6","8"});
	 			}else if(statusTemp==2){//待审核
	 				map.put("status", new String[]{"1"});
	 			}
	 			if(adTypeTemp==1){//互动广告 
			 		map.put("adType", new String[]{"1","2","3"});
			 		this.adList = advertiseService.getListPublicatonLevel(map);
					int listcount = advertiseService.getListPublicatonLevelCount(map);
					this.adPageNo= (listcount+adSize-1)/adSize;
					this.jsonResult.put("adList", this.adList);
					this.jsonResult.put("adPageNo", adPageNo);
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		 		}else{//插页广告
		 			map.put("adType", new String[]{"5"});
		 			this.adList = advertiseService.getListPublicatonLevel(map);
					int listcount = advertiseService.getListPublicatonLevelCount(map);
					this.adPageNo= (listcount+adSize-1)/adSize;
					this.jsonResult.put("adList", this.adList);
					this.jsonResult.put("adPageNo", adPageNo);
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		 		}
	 			
	 		}else{//互动内容
	 			if(statusTemp==2){//待审核
	 				map.put("status", 0);
	 			}else if(statusTemp==1){//已审核
	 				map.put("status",1);
	 			}
				int listcount = interactiveContentService.queryPageCount(map);
				this.adPageNo= (listcount+adSize-1)/adSize;
				this.jsonResult.put("adList", interactiveContentService.queryPage(map));
				this.jsonResult.put("adPageNo", adPageNo);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
	 		}
		}
		return JSON;
	}
	
	/**
	 * @return
	 */
	public String doEditTo(){
		if(this.advertise!=null){
			this.advertise = advertiseService.getById(advertise.getId());
		}
		return "success";
	}
	/**
	 * 互动内容的修改
	 * @return
	 */
	public String doSaveAdJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.advertise!=null&&this.advertise.getId()!=null){
			if(this.advertiseService.updateById(advertise)){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		}
		return JSON;
	}
	
	protected Map<String,Object> queryMap(Advertise advertise) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(advertise!=null){
			if(advertise.getLevel()!=null&&advertise.getLevel()>0)
			{
				if(advertise.getLevel()==2){//出版商等级
					map.put("level", 0);
				}else{
					map.put("level", advertise.getLevel());
				}
			}
			if(advertise.getStartTime()!=null)map.put("startTime", advertise.getStartTime());//结束时间的开始值
			if(advertise.getEndTime()!=null)map.put("endTime", advertise.getEndTime());//结束时间的结束值
			if(advertise.getPublicationName()!=null)map.put("publicationName", advertise.getPublicationName());//杂志名称
			if(advertise.getTitle()!=null)map.put("title", advertise.getTitle());//广告名称
		}
		return map;
	}
	
	private Long adPageNo;
	private Long adCurrentPage;
	private List<Advertise> adList;
	private Advertise advertise;

	public Long getAdPageNo() {
		return adPageNo;
	}
	public void setAdPageNo(Long adPageNo) {
		this.adPageNo = adPageNo;
	}
	public Long getAdCurrentPage() {
		return adCurrentPage;
	}
	public void setAdCurrentPage(Long adCurrentPage) {
		this.adCurrentPage = adCurrentPage;
	}
	public List<Advertise> getAdList() {
		return adList;
	}
	public void setAdList(List<Advertise> adList) {
		this.adList = adList;
	}
	public Advertise getAdvertise() {
		return advertise;
	}
	public void setAdvertise(Advertise advertise) {
		this.advertise = advertise;
	}
	
	
}
