package cn.magme.web.action.newPublisher;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ActivityAlbum;
import cn.magme.pojo.Sort;
import cn.magme.service.ActivityAlbumService;
import cn.magme.service.SortService;
import cn.magme.web.action.BaseAction;

/**
 * @author songjie
 * @date 2012-08-07
 *
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/newPublisher/activityalbum.ftl"),
	@Result(name="listAA",location="/WEB-INF/pages/newPublisher/activityalbumlist.ftl"),
	@Result(name = "upload_json", type = "json", params = { "root","jsonResult", "contentType", "text/html" })
})
public class ActivityAlbumAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	private final Integer size = 5;
	
	@Resource
	private ActivityAlbumService activityAlbumService;
	@Resource
	private SortService sortService;
	
	public String execute(){
		this.type=0;
		this.sortList = sortService.getLstWithLatestEvent(PojoConstant.SORT.NEW_SORT_TYPE);
		listJson();
		return "success";
	}
	
	public String appPicStart(){
		this.type=9999;//麦米阅读app开机图片管理
		listJson();
		return "success";
	}
	
	public String updateJson(){
		ActivityAlbum aa = new ActivityAlbum();
		aa.setStatus(status);
		aa.setType(type);
		aa.setAlt(alt);
		aa.setTitle(title);
		aa.setUrl(url);
		aa.setWeights(weights);
		aa.setId(aaid);
		this.jsonResult = this.activityAlbumService.savePojo(aa, pic, picContentType, picFileName);
		return "upload_json";
	}
	
	/**
	 * 修改状态
	 * @return
	 * 
	 */
	public String updateStatusJson(){
		ActivityAlbum aa = new ActivityAlbum();
		aa.setStatus(status);
		aa.setId(aaid);
		this.jsonResult = new JsonResult();
		try{
			if(PojoConstant.ACTIVITYALBUM.STATUS_UP==status){
				ActivityAlbum tempAA = new ActivityAlbum();
				int temp_type = this.activityAlbumService.queryById(aaid).getType();
				tempAA.setType(temp_type);
				tempAA.setStatus(status);
				int listCount = activityAlbumService.queryActivityAlbumListCount(tempAA);
				if(temp_type!=9999){
					if(listCount>3){//大于4条
						this.jsonResult.setCode(JsonResult.CODE.FAILURE);
						this.jsonResult.setMessage("已发布了 4 条记录");
						return JSON;
					}
				}else{
					if(listCount>4){//大于5条
						this.jsonResult.setCode(JsonResult.CODE.FAILURE);
						this.jsonResult.setMessage("已发布了 5条记录");
						return JSON;
					}
				}
			}
			this.activityAlbumService.updatePojo(aa);
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
		this.jsonResult.put("activityAlbum", this.activityAlbumService.queryById(aaid));
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public  String listJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		ActivityAlbum aa = new ActivityAlbum();
		aa.setType(type);
		
		if(status==null || status==12){
			aa.setStatus(PojoConstant.ACTIVITYALBUM.STATUS_UP);
			aa.setStatus2(PojoConstant.ACTIVITYALBUM.STATUS_WAIT);
		}
		else{ aa.setStatus(status);}		

		
		if(this.pageCount==null || this.pageCount<=0){
			int listCount = activityAlbumService.queryActivityAlbumListCount(aa);
			this.pageCount = (listCount+size-1)/size;
			this.pageCurrent=1;
		}else{
			int listCount = activityAlbumService.queryActivityAlbumListCount(aa);
			this.pageCount = (listCount+size-1)/size;
		}
		listaa = activityAlbumService.queryActivityAlbumList(aa, (pageCurrent-1)*size, size);
		this.jsonResult.put("listaa", listaa);
		this.jsonResult.put("pageCurrent", pageCurrent);
		this.jsonResult.put("pageCount", pageCount);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	
	
	private List<ActivityAlbum> listaa;
	private Integer pageCurrent;
	private Integer pageCount;
	private File pic;
	private String picContentType;
	private String picFileName;
	private List<Sort> sortList;
	
	private Integer aaid;
	private String title;
	private String image;
	private String alt;
	private String url;
	private Integer weights;
	private Integer type;
	private Integer status;
	private Integer status2;
	public List<ActivityAlbum> getListaa() {
		return listaa;
	}

	public void setListaa(List<ActivityAlbum> listaa) {
		this.listaa = listaa;
	}
	
	
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

	public Integer getAaid() {
		return aaid;
	}

	public void setAaid(Integer aaid) {
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

	public List<Sort> getSortList() {
		return sortList;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}
	
	
}
