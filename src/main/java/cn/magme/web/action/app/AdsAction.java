package cn.magme.web.action.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import cn.magme.common.JsonResult;
import cn.magme.pojo.Advertise;
import cn.magme.service.AdvertiseService;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class AdsAction extends BaseAction{
    private static final int MAX_ISSUETOTALPAGES = 639;//最大的期刊页码
	@Resource
	private AdvertiseService advertiseService;

	private Long issueId;
	
	@SuppressWarnings("unchecked")
	public String query(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.issueId==null || this.issueId<=0){
			return JSON;
		}
		
		try {
			Map map = new HashMap();
			map.put("issueId", issueId);
			map.put("status", new int[]{2,5,6,8});
			map.put("adType", new int[]{2,3});
			
			List<Advertise> adList = this.advertiseService.getMagemeAdListByMap(map);
			if(adList != null && adList.size() > 0){
				for (Advertise ad : adList) {
					Long pageNo = ad.getPageNo();
					if(pageNo > MAX_ISSUETOTALPAGES){//是插页广告上的广告，pageNo实际为插页广告id
		            	Advertise insertAd=advertiseService.queryById(pageNo);
		          		ad.setPageNo(insertAd.getPageNo());
					}
				}
			}
			this.jsonResult.put("adList", adList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}
	
	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
}
