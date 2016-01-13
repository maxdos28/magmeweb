package cn.magme.web.action.newPublisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.PageD;
import cn.magme.pojo.QueryJson;
import cn.magme.pojo.Tag;
import cn.magme.pojo.sns.Creative;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.PageDService;
import cn.magme.service.TagService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * @author songjie
 * @date 2012-08-09
 *
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/newPublisher/releaseaudit.ftl"),
	@Result(name = "upload_json", type = "json", params = { "root","result"})
})
public class ReleaseAuditAction extends BaseAction{
	private static final long serialVersionUID = -2342532569570436292L;
	private final Integer size=10;
	
	@Resource
	private SnsPublicService snsPublicService;
	@Resource
	private SnsCreativeService snsCreativeService;
	@Resource
	private PageDService pageDService;
	@Resource
	private TagService tagService;
	
	private Integer pageCount;
	private Integer pageCurrent;
	private Integer status;
	private String title;
	private String described;
	private Integer pagedid;
	private List<PublicResult>  prList;
	private Long prid;
	private PublicResult creativepojo;
	private List<PageD>  pagedList;
	private Integer isrecommend;
	private Integer weight; 
	
	private String idStr;
	
	
	public String execute(){
		listM1Json();
		return "success";
	}
	
	public String listM1Json(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		PublicResult pr = new PublicResult();
		pr.setIshome(status);
		if(StringUtil.isNotBlank(title)){
			pr.setTitle(title);
		}
		PageD tempPD = pageDService.queryByName(idStr);
		if(tempPD!=null){
			try{
			this.pagedid = Integer.parseInt(tempPD.getId().toString());
			}catch (Exception e) {
				this.pagedid=0;
			}
		}else{
			if(StringUtil.isNotBlank(idStr)){
				this.pagedid=0;
			}
		}
		pr.setPagedid(pagedid);
		if(pageCurrent==null || pageCurrent<=0){
			pageCurrent=1;
		}
		int dbListCount = snsPublicService.getCheckM1ListCount(pr);
		pageCount= (dbListCount+size-1)/size;
		if(pageCount<=0){
			pageCount=1;
		}
		prList = snsPublicService.getCheckM1List(pr, (pageCurrent-1)*size, size);
		if(prList!=null){//去小图片
			for (PublicResult prPojo : prList) {
				String imagepath = prPojo.getImagepath();
				if(imagepath!=null && imagepath.lastIndexOf("/")>0){
					String tempStr = imagepath.substring(0,imagepath.lastIndexOf("/")+1)+"max_800_"+imagepath.substring(imagepath.lastIndexOf("/")+1);
					prPojo.setImagepath(tempStr);
				}
			}
		}
		this.jsonResult.put("pageCount", pageCount);
		this.jsonResult.put("prList", prList);
		this.jsonResult.put("pageCurrent", pageCurrent);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String editM1Json(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		PublicResult pr = new PublicResult();
		pr.setCid(prid);
		List<PublicResult> tempList = snsPublicService.getCheckM1List(pr, -1,-1);
		if(tempList!=null && tempList.size()>0){
			creativepojo = tempList.get(0);
		}
//		creativepojo = snsCreativeService.getEditCreative(prid);
		if(creativepojo!=null && creativepojo.getPagedid()!=null){//根据pagedid查找对应的百科专辑名称
			PageD pd = pageDService.queryById(Long.valueOf(creativepojo.getPagedid()+""));
			if(pd!=null)creativepojo.setReserve(pd.getName());//临时存放对应的专辑名称
		}
		Tag tag = new Tag();
		tag.setType(PojoConstant.TAG.TYPE_CREATIVE);
		tag.setStatus(PojoConstant.TAG.STATUS_OK);
		tag.setObjectId(prid);
		List<Tag> dbTag = tagService.getByTag(tag);
		
		this.jsonResult.put("tagList", dbTag);
		this.jsonResult.put("creativepojo", creativepojo);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public String updateM1Json(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		Creative creative = new Creative();
		creative.setId(prid);
//		if(StringUtil.isNotBlank(described)){
//			creative.setContent(described);
//		}else{
//			this.jsonResult.setMessage("内容不能为空");
//			return JSON;
//		}
//		creative.setWeight(weight);
				
		try{
			if(StringUtil.isNotBlank(title)){
				PageD tempPD = pageDService.queryByName(title);
				if(tempPD!=null){
					try{
					this.pagedid = Integer.parseInt(tempPD.getId().toString());
					}catch (Exception e) {
						this.pagedid=0;
					}
				}else{
					if(StringUtil.isNotBlank(idStr)){
						this.pagedid=0;
					}
				}
				Long tempPageDID = Long.parseLong(pagedid+"");
				creative.setPageDId(tempPageDID);
			}else{
				creative.setPageDId(0L);
			}
			snsCreativeService.updateCreative(creative);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}catch (Exception e) {
		}
		//creative.setIshome(2);//修改后设置为未通过
		
		return JSON;
	}
	
	/**
	 * 支持批量更新状态
	 * @return
	 */
	public String updateStatusM1Json(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		String[] idStrArray = idStr.split(",");
		if(idStrArray!=null&& idStrArray.length>0){
			try{
				for (String cid : idStrArray) {
					Creative creative = new Creative();
					if(cid!=null&&cid.length()>0){
						creative.setId(Long.parseLong(cid));
						creative.setIshome(status);
						snsCreativeService.updateCreative(creative);
					}
				}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}catch (Exception e) {
				return JSON;
			}
		}
		return JSON;
	}
	
	/**
	 * 支持批量更新  是否推荐
	 * @return
	 */
	public String updateIsRecommendM1Json(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		String[] idStrArray = idStr.split(",");
		if(idStrArray!=null&& idStrArray.length>0){
			try{
				for (String cid : idStrArray) {
					Creative creative = new Creative();
					if(cid!=null&&cid.length()>0){
						creative.setId(Long.parseLong(cid));
						creative.setIsrecommend(status);
						snsCreativeService.updateCreative(creative);
					}
				}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}catch (Exception e) {
				return JSON;
			}
		}
		return JSON;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPageDByName(){
		Map map = new HashMap();
		map.put("status", "1");
		map.put("name", title);
		pagedList = pageDService.queryByCondition(map);
		List<QueryJson> bjList = new Vector<QueryJson>();
		for (PageD plist : pagedList) {
			QueryJson bj = new QueryJson();
			bj.setKeyword(plist.getName());
			bjList.add(bj);
		}
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		
		this.jsonResult.put("result", bjList);
		return JSON;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPagedid() {
		return pagedid;
	}

	public void setPagedid(Integer pagedid) {
		this.pagedid = pagedid;
	}

	public List<PublicResult> getPrList() {
		return prList;
	}

	public void setPrList(List<PublicResult> prList) {
		this.prList = prList;
	}

	public Long getPrid() {
		return prid;
	}

	public void setPrid(Long prid) {
		this.prid = prid;
	}

	public PublicResult getCreativepojo() {
		return creativepojo;
	}

	public void setCreativepojo(PublicResult creativepojo) {
		this.creativepojo = creativepojo;
	}

	public String getDescribed() {
		return described;
	}

	public void setDescribed(String described) {
		this.described = described;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public List<PageD> getPagedList() {
		return pagedList;
	}

	public void setPagedList(List<PageD> pagedList) {
		this.pagedList = pagedList;
	}

	public Integer getIsrecommend() {
		return isrecommend;
	}

	public void setIsrecommend(Integer isrecommend) {
		this.isrecommend = isrecommend;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	

}
