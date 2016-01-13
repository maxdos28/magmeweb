package cn.magme.web.action.newPublisher;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Admin;
import cn.magme.pojo.CreativeRecommend;
import cn.magme.service.CreativeRecommendService;
import cn.magme.web.action.BaseAction;

/**
 * @author songjie
 * @date 2013-03-13
 *
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/newPublisher/creativeRecommed.ftl"),
	@Result(name = "upload_json", type = "json", params = { "root","jsonResult", "contentType", "text/html" })
})
public class CreativeRecommedAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	private final Integer size = 5;
	@Resource
	private CreativeRecommendService creativeRecommendService;
	
	public String execute(){
		listJson();
		return "success";
	}
	
	public String updateJson(){
		Admin admin= this.getSessionAdmin();
		if(admin==null){
			this.jsonResult = JsonResult.getNotLogin();
			return JSON;
		}
		CreativeRecommend aa = new CreativeRecommend();
		aa.setStatus(status);
		aa.setType(type);
		aa.setAlt(alt);
		aa.setTitle(title);
		aa.setUrl(url);
		aa.setUserId(admin.getId());
		aa.setSortVal(weights);
		aa.setId(aaid);
		this.jsonResult = this.creativeRecommendService.savePojo(aa, pic, picContentType, picFileName);
		return "upload_json";
	}
	
	/**
	 * 修改状态
	 * @return
	 * 
	 */
	public String updateStatusJson(){
		Admin admin= this.getSessionAdmin();
		if(admin==null){
			this.jsonResult = JsonResult.getNotLogin();
			return JSON;
		}
		CreativeRecommend aa = new CreativeRecommend();
		aa.setStatus(status);
		aa.setUserId(admin.getId());
		aa.setId(aaid);
		this.jsonResult = new JsonResult();
		try{
			if(PojoConstant.ACTIVITYALBUM.STATUS_UP==status){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",status);
				int listCount = creativeRecommendService.listCreativeRecommendCount(map);
					if(listCount>=5){//大于5条
						this.jsonResult.setCode(JsonResult.CODE.FAILURE);
						this.jsonResult.setMessage("已发布了 5条记录");
						return JSON;
					}
			}
			this.creativeRecommendService.updateCreativeRecommend(aa);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}catch (Exception e) {
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			return JSON;
		}
		return JSON;
	}
	
	public String getPojoJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.put("activityAlbum", this.creativeRecommendService.queryById(aaid));
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public  String listJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		
		if(status==null || status==12){
			map.put("status", PojoConstant.CRETIVE_RECOMMED.STATUS_UP);
			map.put("status2", PojoConstant.CRETIVE_RECOMMED.STATUS_WAIT);
		}
		else{map.put("status", status);}		

		int listCount = creativeRecommendService.listCreativeRecommendCount(map);
		if(this.pageCount==null || this.pageCount<=0){
			
			this.pageCount = (listCount+size-1)/size;
			this.pageCurrent=1;
		}else{
			this.pageCount = (listCount+size-1)/size;
		}
		map.put("begin", (pageCurrent-1)*size);
		map.put("size", size);
		setListaa(creativeRecommendService.listCreativeRecommend(map));
		this.jsonResult.put("listaa", getListaa());
		this.jsonResult.put("pageCurrent", pageCurrent);
		this.jsonResult.put("pageCount", pageCount);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	
	
	private List<CreativeRecommend> listaa;
	private Integer pageCurrent;
	private Integer pageCount;
	private File pic;
	private String picContentType;
	private String picFileName;
	
	private Long aaid;
	private Long creativeId;
	private String title;
	private String image;
	private String alt;
	private String url;
	private Integer weights;
	private Integer type;
	private Integer status;
	private Integer status2;
	
	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Long getAaid() {
		return aaid;
	}

	public void setAaid(Long aaid) {
		this.aaid = aaid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getStatus2() {
		return status2;
	}

	public void setStatus2(Integer status2) {
		this.status2 = status2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicContentType() {
		return picContentType;
	}

	public void setPicContentType(String picContentType) {
		this.picContentType = picContentType;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public List<CreativeRecommend> getListaa() {
		return listaa;
	}

	public void setListaa(List<CreativeRecommend> listaa) {
		this.listaa = listaa;
	}

	public Long getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(Long creativeId) {
		this.creativeId = creativeId;
	}

	
}
