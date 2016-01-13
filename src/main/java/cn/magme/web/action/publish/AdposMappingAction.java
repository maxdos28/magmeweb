/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdPosition;
import cn.magme.pojo.AdposMapping;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdPositionService;
import cn.magme.service.AdposMappingService;
import cn.magme.service.AdvertiseService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.entity.AdposAndAdDO;

/**
 * @author fredy.liu
 * @date 2011-6-7
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/adManager.ftl"),@Result(name="adpre",location="/WEB-INF/pages/publish/adpre.ftl")})
public class AdposMappingAction extends BaseAction {
	
	private static final String AD_PREVIEW="adpre";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8452637035779521176L;
	
	private static final Logger log=Logger.getLogger(AdposMappingAction.class);
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private AdPositionService adPositionService;
	
	@Resource
	private AdvertiseService advertiseService;
	
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private AdposMappingService adposMappingService;
	
	
	/**
	 * 审核映射
	 * @return
	 */
	public String auditMappingJson(){
		this.jsonResult=new JsonResult();
		//默认失败
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			if(this.mappingId==null || this.mappingId<=0 ){
				this.jsonResult.setMessage("参数错误");
				return JSON;
			}
			AdposMapping admapping=this.adposMappingService.queryById(this.mappingId);
			boolean succ=this.adposMappingService.updateStatusByIdAndOriStatus(admapping.getId(), PojoConstant.ADPOSMAPPING.STATUS.PASS.getCode(), admapping.getStatus());
			if(succ){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}else{
				this.jsonResult.setMessage("更新错误");
			}
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setMessage("审核失败");
		}
		return JSON;
	}
	
	public String queryAdPosJson2(){
		this.jsonResult=new JsonResult();
		if(totalRow<=0 || startRow<0 || (NumberUtil.isLessThan0(publicationId) && NumberUtil.isLessThan0(issueId))){
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage("传入参数错误");
			return JSON;
		}
		try {
			Publisher publisher=this.getSessionPublisher();
			
			publicationList=publicationService.queryNormalByPublisherId(publisher.getId());
			//查询期刊列表
			issueList=issueService.queryAllIssuesByPublisherIdAndPublisherId(publicationId, publisher.getId());
			
			if(issueId==null){
				//所有的广告位
				adPosList=adPositionService.queryAdPosByPublicationId(publicationId,startRow,totalRow);
			}else{
				//需要传会publicationId，用于显示图片
				this.publicationId=issueService.queryById(issueId).getPublicationId();
				//指定期刊的广告位
				adPosList=adPositionService.queryAdPosByIssueId(issueId,startRow,totalRow);
				
			}
			
			//查询这个用户创建的所有广告
			adList=advertiseService.queryAllNomalAdsByUserId(publisher.getId(), PojoConstant.ADVERTISE.STATUS.VALID.getCode());
			
			//广告map
			advertiseMap=new HashMap<String,Advertise>();
			this.adposmappingMap=new HashMap<String,AdposMapping>();
			for(AdPosition adPos:adPosList){
				Advertise ad=advertiseService.queryAdByPosId(adPos.getId());
				AdposMapping adposmapping=this.adposMappingService.queryMappingByAdposId(adPos.getId());
				if(ad!=null){
					advertiseMap.put(String.valueOf(adPos.getId()),ad);
				}
				if(adposmapping!=null){
					this.adposmappingMap.put(String.valueOf(adPos.getId()), adposmapping);
				}
			}
			this.jsonResult.put("publicationId", publicationId);
			this.jsonResult.put("advertiseMap", advertiseMap);
			this.jsonResult.put("adPosList", adPosList);
			this.jsonResult.put("issueList", issueList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		
		//使用非json返回 
		return SUCCESS;
		
	}

	/**
	 * 删除映射
	 * @return
	 */
	public String delMappingJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.put("adPosId", adPosId);
		//默认错误
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(NumberUtil.isLessThan0(adPosId) || NumberUtil.isLessThan0(adId)){
			return JSON;
		}
		try {
			if(adposMappingService.delMappingByAdposIdAndAdId(adPosId, adId)){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		} catch (Exception e) {
			log.error("eeeee",e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage("删除错误");
		}
		return JSON;
	}

	
	private List<AdposAndAdDO>  queryAds(Long issueId,Long pageNo, Long[] statusArr){
		List<AdposMapping> adposMapList=this.adposMappingService.queryByIssueIdPageNoStatus(issueId, pageNo, statusArr);
		List<AdposAndAdDO> adposAndAdList=new ArrayList<AdposAndAdDO>(); 
		try {
			if(adposMapList!=null && adposMapList.size()>0){
				for(AdposMapping adposMap:adposMapList){
					Advertise ad=this.advertiseService.queryById(adposMap.getAdId());
					AdPosition adpos=this.adPositionService.queryById(adposMap.getPosId());
					AdposAndAdDO adposAndAd=new AdposAndAdDO();
					adposAndAd.setAdId(ad.getId());
					adposAndAd.setAdmappingId(adposMap.getId());
					adposAndAd.setAdposId(adpos.getId());
					adposAndAd.setDescription(ad.getDescription());
					//adposAndAd.setHeight(adpos.getHeight());
					adposAndAd.setIaType(ad.getAdType());
					adposAndAd.setImgurl(ad.getImgurl());
					adposAndAd.setIssueId(adpos.getIssueId());
					adposAndAd.setLinkurl(ad.getLinkurl());
					adposAndAd.setMediaurl(ad.getMediaurl());
					adposAndAd.setName(ad.getTitle());
					adposAndAd.setPageNo(adpos.getPageNo());
					//adposAndAd.setWidth(adpos.getWidth());
					/*adposAndAd.setxOffset(adpos.getPosx());
					adposAndAd.setyOffset(adpos.getPosy());*/
					adposAndAd.setStatus(adposMap.getStatus());
					adposAndAdList.add(adposAndAd);
				}
			}
		} catch (Exception e) {
			log.error("查询广告信息出错，请检查数据是否正确", e);
		}
		return adposAndAdList;
	}
	
	
	
	/**
	 * 出版商阅读器的广告信息
	 * @return
	 */
	public String queryPublisherAdJson(){
		this.queryReaderAdJson();
		return JSON;
	}
	
	/**
	 * 读者阅读器的广告信息
	 * @return
	 */
	public String queryReaderAdJson(){
		this.jsonResult=new JsonResult();
		//默认失败
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		//没有传入页码，默认为1
		if(this.pageNo==null || this.pageNo<=0){
			this.pageNo=1L;
		}
		Long [] statusArr=StringUtil.splitToLongArray(status);
		if(statusArr==null || statusArr.length<=0 || this.issueId==null || this.issueId<=0){
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage("查询读者阅读器错误,参数错误,请检查issueId,status");
			return JSON;
		}
		try {
			List<AdposAndAdDO> adposAndAdList=this.queryAds(issueId, pageNo, statusArr);
			if(adposAndAdList!=null && adposAndAdList.size()>0){
				this.jsonResult.put("ad", adposAndAdList);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}else{
				//this.jsonResult.setCode(code)
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("没有广告信息");
			}
		} catch (Exception e) {
			log.error("查询广告失败", e);
		}
		return JSON;
	}
	
	/**
	 * 预览广告的action
	 * @return
	 */
	public String previewAd(){
		if(NumberUtil.isLessThan0(this.adPosId) || NumberUtil.isLessThan0(this.adId)){
			return null;
		}
		
		try {
			AdposMapping mapping=this.adposMappingService.queryMappingByAdposIdAndAdId(adPosId, adId);
			AdPosition adposition = this.adPositionService.queryById(mapping.getPosId());
			//Advertise advertise= this.advertiseService.queryById(mapping.getAdId());
			this.issueId=adposition.getIssueId();
			this.pageNo = adposition.getPageNo().longValue();
		} catch (Exception e) {
			log.error("", e);
		}
		return AD_PREVIEW;
	}
	/**
	 * 映射id
	 */
	private Long mappingId;
	
	/**
	 * 广告位置id
	 */
	private Long adPosId;
	
	/**
	 * 广告id
	 */
	private Long adId;
	
	/**
	 * 杂志列表
	 */
	private List<Publication> publicationList;
	
	/**
	 * 期刊列表
	 */
	private List<Issue> issueList;
	
	/**
	 * 广告位列表
	 */
	private List<AdPosition> adPosList;
	
	/**
	 * 广告位与广告映射，key 广告位id，value 广告
	 */
	private Map<String,Advertise> advertiseMap;
	/**
	 * 广告
	 */
	private List<Advertise> adList;
	
	/**
	 * 映射
	 */
	private Map<String,AdposMapping> adposmappingMap;
	
	/**
	 * 开始条数,数据从0开始
	 */
	private int startRow=0;
	
	/**
	 * 总的需要条数
	 */
	private int totalRow=20;
	
	/**
	 * 杂志id
	 */
	private Long publicationId;
	
	/**
	 * 期刊id
	 */
	private Long issueId;
	
	private String status;
	
	/**
	 * 页码
	 */
	private Long pageNo;
	
	
	
	
	
	public Map<String, AdposMapping> getAdposmappingMap() {
		return adposmappingMap;
	}

	public void setAdposmappingMap(Map<String, AdposMapping> adposmappingMap) {
		this.adposmappingMap = adposmappingMap;
	}

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAdPosId() {
		return adPosId;
	}

	public void setAdPosId(Long adPosId) {
		this.adPosId = adPosId;
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}


	public Long getPublicationId() {
		return publicationId;
	}


	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}


	public List<AdPosition> getAdPosList() {
		return adPosList;
	}


	public void setAdPosList(List<AdPosition> adPosList) {
		this.adPosList = adPosList;
	}


	public int getStartRow() {
		return startRow;
	}


	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	public int getTotalRow() {
		return totalRow;
	}


	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}


	public Map<String, Advertise> getAdvertiseMap() {
		return advertiseMap;
	}


	public void setAdvertiseMap(Map<String, Advertise> advertiseMap) {
		this.advertiseMap = advertiseMap;
	}

	public List<Advertise> getAdList() {
		return adList;
	}

	public void setAdList(List<Advertise> adList) {
		this.adList = adList;
	}
	
	

}
