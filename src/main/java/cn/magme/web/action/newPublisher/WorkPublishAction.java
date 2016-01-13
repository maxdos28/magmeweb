package cn.magme.web.action.newPublisher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Admin;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.CreativeCategoryRel;
import cn.magme.pojo.CreativeNeteaseCategoryRel;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Tag;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.HomePageItemService;
import cn.magme.service.TagService;
import cn.magme.service.sns.CreativeCategoryRelService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.sns.CreativeNeteaseCategoryRelService;
import cn.magme.service.sns.CreativeService;
import cn.magme.util.DateUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * 作品发布
 * @author devin.song
 * @date 2012-12-05
 *
 */
/**
 * @author uu
 *
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/newPublisher/workrelease.ftl"),
	@Result(name="audited",location="/WEB-INF/pages/newPublisher/workaudited.ftl"),
	@Result(name="published_admin",location="/WEB-INF/pages/newPublisher/workauditedadmin.ftl"),
	@Result(name="published",location="/WEB-INF/pages/newPublisher/workpublished.ftl"),
	@Result(name="worknoeditor",location="/WEB-INF/pages/newPublisher/worknoeditor.ftl")
})
public class WorkPublishAction extends BaseAction{
	private static final long serialVersionUID = -2342532569570436292L;
	private Integer pageSize = 50;
	
	@Resource
	private CreativeService creativeService;
	@Resource
	private CreativeCategoryService creativeCategoryService;
	@Resource
	private CreativeCategoryRelService creativeCategoryRelService;
	@Resource
	private CreativeNeteaseCategoryRelService creativeNeteaseCategoryRelService;
	@Resource
	private HomePageItemService homePageItemService;
	@Resource
	private TagService tagService;
	
	private Long sortId;
	private Long secondSortId;
	private Integer begin = 0;
	private Integer currentPage = 1;
	private Integer pageNo;
	private String title;
	private Integer ishome;
	private String userName;
	private Integer isrecommend;//是否为在首页中推荐的作品（0：否，1：是）
	private Integer isrecommendChannel;//是否为在频道分页中推荐的作品（0：否，1：是）
	private Integer weight;//权重
	private String sortIdStr;
	private String time;
	private Tag tag;
	private String described;//描述
	private String relStr;//关系id
	private String picStr;//是否图集
	
	/**
	 * 作品的集合
	 */
	private List<Creative> creativeList;
	 /**
     * 新版首页一级和二级分类的集合
     */
    private List<CreativeCategory> creativeCategoryList;
	
	/**
	 * 未审核
	 * @return
	 */
	public String execute(){
		ishome=2;//未审核
		creativeCategoryList = creativeCategoryService.queryCategoryTree();
		if(creativeCategoryList!=null){
			for (CreativeCategory cc : creativeCategoryList) {
				int tempFirst = creativeCategoryRelService.creativeNumByFirstType(cc.getId(),ishome,1);
				cc.setCreativeCount(tempFirst);
				if(cc.getChildCreativeList()!=null){
					for (CreativeCategory ccs : cc.getChildCreativeList()) {
						int tempSecond = creativeCategoryRelService.creativeNumBySecondType(ccs.getId(),ishome,1);
						ccs.setCreativeCount(tempSecond);
					}
				}
			}
		}
		listM1Json();
		return "success";
	}
	
	public String noEditorPage(){
		this.ishome=7;
		listNoEditorJson();
		return "worknoeditor";
	}
	
	/**
	 * 删除标签,id标签id，类型固定成4
	 * @return
	 */
	public String deleteTagJson(){
		this.jsonResult=JsonResult.getFailure();
		if(id==null || id<=0){
			this.jsonResult.setMessage("删除标签失败，id不正确");
			return JSON;
		}
		if(this.tagService.deleteById(id)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage("删除成功");
		}
    	return JSON;
    }
	/**
	 * 删除作品
	 * @return
	 */
	public String deleteCreative(){
		this.jsonResult=JsonResult.getFailure();
		if(id==null || id<=0){
			this.jsonResult.setMessage("删除作品失败，id不正确");
			return JSON;
		}
		if(this.creativeService.deleteById(id)>0){
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}
		return JSON;
	}
	
	public String listNoEditorJson(){
		this.jsonResult = JsonResult.getFailure();
		if(id!=null){
			Creative c=this.creativeService.queryById(id);
			currentPage=1;
			pageNo=1;
			creativeList=new ArrayList<Creative>();
			if(c!=null && c.getStatus()==PojoConstant.CREATIVE.STATUS_OK){
				creativeList.add(c);
			}
		}else{
			begin = (getCurrentPage()-1)*pageSize;
			int [] creativeTypes=new int[]{0,2,3};
			int listCount = creativeService.queryCountByCrativeTypes(creativeTypes);
			pageNo = (listCount+pageSize-1)/pageSize;
			creativeList = creativeService.queryByCrativeTypes(creativeTypes, begin, pageSize);
		}
		
		this.jsonResult.put("currentPage", getCurrentPage());
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.put("pageSize", pageSize);
		this.jsonResult.put("creativeList", creativeList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 记录总数
	 * @return
	 */
	public String listNoEditorCountJson(){
		this.jsonResult = JsonResult.getFailure();
		int listCount=0;
		if(id!=null){
			Creative c=this.creativeService.queryById(id);
			if(c!=null && c.getStatus()==PojoConstant.CREATIVE.STATUS_OK){
				listCount=1;
			}
			pageNo=1;
		}else{
			int [] creativeTypes=new int[]{0,2,3};
			listCount = creativeService.queryCountByCrativeTypes(creativeTypes);
			pageNo = (listCount+pageSize-1)/pageSize;
		}
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	public String listM1Json(){
		this.jsonResult = JsonResult.getFailure();
		begin = (getCurrentPage()-1)*pageSize;
		int listCount = creativeService.creativeListByIndexControlCount(sortId, secondSortId, title, userName, ishome);
		pageNo = (listCount+pageSize-1)/pageSize;
		creativeList = creativeService.creativeListByIndexControl(sortId, secondSortId,title,userName,ishome, begin, pageSize);
		this.jsonResult.put("currentPage", getCurrentPage());
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.put("pageSize", pageSize);
		this.jsonResult.put("creativeList", creativeList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 记录总数
	 * @return
	 */
	public String listM1CountJson(){
		this.jsonResult = JsonResult.getFailure();
		int listCount = creativeService.creativeListByIndexControlCount(sortId, secondSortId, title, userName, ishome);
		pageNo = (listCount+pageSize-1)/pageSize;
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 立即发布前的一个步骤  把状态为4的发布时间清空 并将4状态改为：5(通过审核)
	 * @return
	 */
	public String savePublishTime(){
		//start_time = now();
		this.jsonResult = JsonResult.getFailure();
		if(sortIdStr!=null){
			Long[] tmpSortId = StringUtil.splitToLongArray(sortIdStr);
			if(tmpSortId!=null && tmpSortId.length>0){
				for (Long sId : tmpSortId) {
					Creative c = this.creativeService.queryById(sId);
						if(c!=null){
							Integer tmpIsHome = c.getIshome();
							if(tmpIsHome==4){
								c.setStartTime(new Date());
							}
							this.creativeService.updateById(c);
							this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
							this.jsonResult.setMessage("状态更改成功");
						}else{
							this.jsonResult.setCode(JsonResult.CODE.FAILURE);
							this.jsonResult.setMessage("状态更改失败");
							break;
						}
				}
			}
		}
		return JSON;
	}
	
	/**
	 * 延迟发布
	 * @return
	 */
	public String delayedRelease(){
		//修改ishome=4
		//start_time = now();
		this.jsonResult = JsonResult.getFailure();
		if(sortIdStr!=null){
			Long[] tmpSortId = StringUtil.splitToLongArray(sortIdStr);
			if(tmpSortId!=null && tmpSortId.length>0){
				for (Long sId : tmpSortId) {
					Creative c = this.creativeService.queryById(sId);
						if(c!=null){
							Integer tmpIsHome = c.getIshome();
							if(tmpIsHome==5 || tmpIsHome==4){
								Date tTime = DateUtil.parse(time);
								Long tempTime = DateUtil.dateDifference(tTime,new Date());
								if(tempTime!=null && tempTime>=0){
									c.setIshome(4);//延迟发布
									c.setStartTime(tTime);//用户传的时间
								}else{
									this.jsonResult.setCode(JsonResult.CODE.FAILURE);
									this.jsonResult.setMessage("延迟发布时间必须大于当前时间");
									break;
								}
							}
							this.creativeService.updateById(c);
							this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
							this.jsonResult.setMessage("状态更改成功");
						}else{
							this.jsonResult.setCode(JsonResult.CODE.FAILURE);
							this.jsonResult.setMessage("状态更改失败");
							break;
						}
				}
			}
		}
		return JSON;
	}
	
	/**
	 * 选择的作品 立即发布 
	 * @return
	 */
	public String atOncePublish(){
		//修改ishome=1 -->状态5或者4直接修改
		//ploy=0 公开
		//刷新首页的缓存
		this.jsonResult = JsonResult.getFailure();
		if(sortIdStr!=null){
			Long[] tmpSortId = StringUtil.splitToLongArray(sortIdStr);
			if(tmpSortId!=null && tmpSortId.length>0){
				for (Long sId : tmpSortId) {
					Creative c = this.creativeService.queryById(sId);
						if(c!=null){
							Integer tmpIsHome = c.getIshome();
							if(tmpIsHome==4){
//								Date d2 = c.getStartTime();
//								if(d2!=null){
//									Long flagTemp = DateUtil.dateDifference(new Date(), d2);
//									if(flagTemp!=null && flagTemp>=0){
										c.setIshome(1);//在首页显示
										c.setPloy(0);//公开
										c.setStartTime(new Date());
//									}
//								}
							}
							if(tmpIsHome==5){
								c.setIshome(1);//在首页显示
								c.setPloy(0);//公开
								c.setStartTime(new Date());
							}
							this.creativeService.updateById(c);
							this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
							this.jsonResult.setMessage("状态更改成功");
						}else{
							this.jsonResult.setCode(JsonResult.CODE.FAILURE);
							this.jsonResult.setMessage("状态更改失败");
							break;
						}
				}
				//刷新首页的缓存
				//homePageItemService.updateByStartTime();
				homePageItemService.saveCreativeToCache();
			}
		}
		return JSON;
	}
	
	/**
	 * 已审核
	 * @return
	 */
	public String auditedPage(){
		ishome=5;//已审核
		creativeCategoryList = creativeCategoryService.queryCategoryTree();
		if(creativeCategoryList!=null){
			for (CreativeCategory cc : creativeCategoryList) {
				int tempFirst = creativeCategoryRelService.creativeNumByFirstType(cc.getId(),ishome,1);
				cc.setCreativeCount(tempFirst);
				if(cc.getChildCreativeList()!=null){
					for (CreativeCategory ccs : cc.getChildCreativeList()) {
						int tempSecond = creativeCategoryRelService.creativeNumBySecondType(ccs.getId(),ishome,1);
						ccs.setCreativeCount(tempSecond);
					}
				}
			}
		}
		listM1Json();
		return "audited";
	}
	
	/**
	 * 已发布
	 * @return
	 */
	public String publishedPage(){
		ishome=1;//已发布
		listM1Json();
		return "published";
	}
	
	/**
	 * 测试方便查看
	 * @return
	 */
	public String publishedPageAdmin(){
		ishome=1;//已发布
		listM1Json();
		return "published_admin";
	}
	
	/**
	 * 更改ishome的状态
	 * @return
	 */
	public String ishomeValChange(){
		this.jsonResult = JsonResult.getFailure();
		if(sortIdStr!=null){
			Long[] tmpSortId = StringUtil.splitToLongArray(sortIdStr);
			if(tmpSortId!=null && tmpSortId.length>0){
				for (Long sId : tmpSortId) {
					Creative c = this.creativeService.queryById(sId);
					if(ishome!=null && (ishome==2 || ishome==4 || ishome==5 || ishome==1)){
						if(c!=null){
							Integer tmpIsHome = c.getIshome();
							if(tmpIsHome!=null){
								if(ishome==2 && (tmpIsHome==4 || tmpIsHome==5)){//在已审核状态 后退到待审核状态时
									c.setIsrecommend(0);//不推荐
									c.setIsrecommendChannel(0);//不推荐
									c.setWeight(0);//权重
								}
								if(ishome==5 && (tmpIsHome==1)){//在发布状态 后退到已审核状态时
									c.setIsrecommend(0);//不推荐
									c.setIsrecommendChannel(0);//不推荐
									c.setWeight(0);//权重
								}
								if(ishome==5 && (tmpIsHome==2)){//在待审核状态 到已审核状态时
									//记录审核人
									 Admin admin = this.getSessionAdmin();
									 if(admin!=null){
										 c.setAuditUserId(admin.getId());
									 }else{
										 this.jsonResult.setMessage("请用admin用户登录");
										 return JSON;
									 }
								}
							}
							c.setIshome(ishome);
							this.creativeService.updateById(c);
							this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
							this.jsonResult.setMessage("状态更改成功");
						}else{
							this.jsonResult.setMessage("状态更改失败");
						}
					}
				}
			}
		}
		return JSON;
	}
	
	/**
	 * 根据id修改权重和是否推荐
	 * @return
	 */
	public String creativeValChange(){
		this.jsonResult = JsonResult.getFailure();
		Creative c = this.creativeService.queryById(sortId);
		if(c!=null){
			if(isrecommend!=null && (isrecommend==0 || isrecommend==1)){
				c.setIsrecommend(isrecommend);
			}
			
			if(isrecommendChannel!=null && (isrecommendChannel==0 || isrecommendChannel==1)){
				c.setIsrecommendChannel(isrecommendChannel);
			}
			
			
			if(weight!=null && (weight<=9999)){
				c.setWeight(weight);
			}else if(weight!=null && weight>9999){
				this.jsonResult.setMessage("权重最大值是9999");
				return JSON;
			}
			this.creativeService.updateById(c);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage("更改成功");
		}else{
			this.jsonResult.setMessage("对象无效");
		}
		return JSON;
	}
	
	/**
	 * 编辑前的数据准备
	 * @return
	 */
	public String editCreativePre(){
		this.jsonResult = JsonResult.getFailure();
		if(sortId==null || sortId<0) return JSON;
		Creative c = this.creativeService.queryById(sortId);
		List<CreativeCategoryRel> ccr = this.creativeCategoryRelService.queryByCid(sortId);
		tag = new Tag();
		tag.setObjectId(sortId);
		List<Tag> tagList = getTags();
		List<CreativeCategory> ccList=this.creativeCategoryService.queryByType(2);
		List<CreativeNeteaseCategoryRel> cncrList = this.creativeNeteaseCategoryRelService.queryByCid(c.getId());
		this.jsonResult.put("creative", c);
		this.jsonResult.put("creativeCategoryRel", ccr);
		this.jsonResult.put("tagList", tagList);
		this.jsonResult.put("ccList", ccList);
		this.jsonResult.put("cncrList", cncrList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 保持修改的内容
	 * @return
	 */
	public String editCreative(){
		this.jsonResult = JsonResult.getFailure();
		if(sortId==null || sortId<0) {
			this.jsonResult.setMessage("获取对象失败");
			return JSON;
		}
		if(StringUtil.isBlank(title)){
			this.jsonResult.setMessage("标题不能为空");
			return JSON;
		}else{
			if(title.length()>20){
				this.jsonResult.setMessage("标题长度超过20");
				return JSON;
			}
		}
		if(StringUtil.isBlank(described)){
			this.jsonResult.setMessage("描述不能为空");
			return JSON;
		}else{
			if(described.length()>75){
				this.jsonResult.setMessage("描述内容超过75");
				return JSON;
			}
		}
		if(StringUtil.isBlank(relStr)){
			this.jsonResult.setMessage("请选择对应的分类");
			return JSON;
		}
		
		Creative c = this.creativeService.queryById(sortId);
		c.setSecondTitle(title);
		c.setSecondDesc(described);
		this.creativeCategoryRelService.deleteByCreativeId(sortId);
		Long[] relLong = StringUtil.splitToLongArray(relStr);
		Long[] picLong = StringUtil.splitToLongArray(picStr);
		for (int i = 0; i < relLong.length; i++) {
			CreativeCategoryRel ccr = new CreativeCategoryRel();
			ccr.setCreativeId(sortId);
			ccr.setCategoryId(relLong[i]);
			ccr.setStatus(PojoConstant.CREATIVE_CATEGORY_REL.STATUS_OK);
			ccr.setPicCollection(Integer.parseInt(picLong[i]+""));
			this.creativeCategoryRelService.insert(ccr);
		}
//		tag = new Tag();
//		tag.setObjectId(sortId);
//		tag.setType(PojoConstant.TAG.TYPE_CREATIVE);
		addTagJson();
		creativeService.updateById(c);
		this.creativeNeteaseCategoryRelService.deleteByCreativeId(c.getId());
		if(StringUtils.isNotBlank(categoryIds)){
			String [] categoryIdArr=categoryIds.split(",");
			if(categoryIdArr!=null && categoryIdArr.length>0){
				for(String categoryId:categoryIdArr){
					CreativeNeteaseCategoryRel rel=new CreativeNeteaseCategoryRel();
					rel.setCategoryId(Long.parseLong(categoryId));
					rel.setCreativeId(c.getId());
					rel.setStatus(1);
					this.creativeNeteaseCategoryRelService.insert(rel);
				}
			}
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 添加标签
	 * @return
	 */
	public String addTagJson(){
		this.jsonResult = JsonResult.getFailure();
		if(tag != null){
			Publisher publisher = this.getSessionPublisher();
			Admin admin = this.getSessionAdmin();
			if(publisher != null){
				tag.setCreatedBy(publisher.getId());
				this.jsonResult = tagService.insert2(tag);
			}else if(admin != null){
				tag.setCreatedBy(admin.getId());
				this.jsonResult = tagService.insert2(tag);
			}
		}
		return JSON;
	}
	
	/**
	 * 获取对应的作品分类
	 * @return
	 */
	protected List<Tag> getTags() {
		tag.setType(PojoConstant.TAG.TYPE_CREATIVE);
		tag.setStatus(PojoConstant.TAG.STATUS_OK);
		List<Tag> tags = tagService.getByTag(tag);
		return tags;
	}
	public String queryData(){
		this.jsonResult = JsonResult.getFailure();
		
		return JSON;
	}

	private Long id;
	
	private String categoryIds;
	
	
	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Long getSecondSortId() {
		return secondSortId;
	}

	public void setSecondSortId(Long secondSortId) {
		this.secondSortId = secondSortId;
	}

	public List<Creative> getCreativeList() {
		return creativeList;
	}

	public void setCreativeList(List<Creative> creativeList) {
		this.creativeList = creativeList;
	}

	public List<CreativeCategory> getCreativeCategoryList() {
		return creativeCategoryList;
	}

	public void setCreativeCategoryList(List<CreativeCategory> creativeCategoryList) {
		this.creativeCategoryList = creativeCategoryList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getIshome() {
		return ishome;
	}

	public void setIshome(Integer ishome) {
		this.ishome = ishome;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
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

	public String getSortIdStr() {
		return sortIdStr;
	}

	public void setSortIdStr(String sortIdStr) {
		this.sortIdStr = sortIdStr;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescribed() {
		return described;
	}

	public void setDescribed(String described) {
		this.described = described;
	}

	public String getRelStr() {
		return relStr;
	}

	public void setRelStr(String relStr) {
		this.relStr = relStr;
	}

	public String getPicStr() {
		return picStr;
	}

	public void setPicStr(String picStr) {
		this.picStr = picStr;
	}
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Integer getIsrecommendChannel() {
		return isrecommendChannel;
	}

	public void setIsrecommendChannel(Integer isrecommendChannel) {
		this.isrecommendChannel = isrecommendChannel;
	}
	
	
}
