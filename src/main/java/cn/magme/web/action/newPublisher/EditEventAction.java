package cn.magme.web.action.newPublisher;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.FileOperate;
import cn.magme.common.JsonResult;
import cn.magme.common.JsonResult.CODE;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Admin;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Sort;
import cn.magme.pojo.Tag;
import cn.magme.pojo.TextPage;
import cn.magme.service.AdminService;
import cn.magme.service.FpageEventService;
import cn.magme.service.SortService;
import cn.magme.service.TagService;
import cn.magme.service.TextPageService;
import cn.magme.util.ConvertPojoToMap;
import cn.magme.util.DateUtil;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ImageUtil;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;




/**
 * 编辑=编辑事件
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({
		@Result(name = "upload_json", type = "json", params = { "root", "jsonResult", "contentType", "text/html" }),
		@Result(name = "config",location="/WEB-INF/pages/newPublisher/magmeEvent.ftl"),
		@Result(name = "getListAjax", location = "/WEB-INF/pages/ad/magmeEventPageTable_ajax.ftl"),
		@Result(name = "editAjax", location = "/WEB-INF/pages/dialog/editEvent.ftl") 
		})
public class EditEventAction extends BaseAction {
	private static final int NUM_PER_PAGE = 30;
	private Long issueId;
	private Integer pageNum;
	
	@Resource
	private TextPageService textPageService;
	@Resource
	private TagService tagService;
    @Resource
    private FpageEventService fpageEventService;    
    @Resource
    private SortService sortService;
    @Resource
    private AdminService adminService;

    //add by billy
    private ExtPageInfo page;
    private Integer start;
    private Integer limit;
    private Integer isRecommend;
    private Integer isSuitMobile;
    private Integer status;
    private String createdDate;
    private Long publicationId;
    private String reserved1;
    private Long adId;
    private String createdTimeStart;
    private String createdTimeEnd;
    private Integer sidebar;
    
    private List<FpageEvent> fpageEventlist;
    
    private Long eventId;
    private FpageEvent fpageEvent;

	private Integer weight;
	private String title;
	private String description;
	private Integer pageNo;
	private Integer endPageNo;
	private File imgFile;
	private String imgFileName;
	private String channel;
	
	private Tag tag;
	private List<Sort> sortList;
	
	private List<Admin> userList;
	private Integer userId;
	private Admin adminPo;
    
	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		start = 0;
		limit = limit == null ? NUM_PER_PAGE : limit;
		page = new ExtPageInfo();
		page.setStart(start);
		page.setLimit(limit);
		ExtPageInfo aPage = new ExtPageInfo();
		aPage.setStart(0);
		aPage.setLimit(50);
		ExtPageInfo adminPage = adminService.getPage(aPage);
		userList = adminPage.getData();
		//一级分类
		sortList = sortService.getLstWithLatestEvent(SORT.NEW_SORT_TYPE);
//		getListAjax();
		return "config";
	}

	/**
	 * 跳转到修改event的页面
	 * 
	 * @return
	 */
	public String editAjax() {
		this.fpageEvent = getById(eventId);
		tag = new Tag();
		tag.setObjectId(eventId);
		List<Tag> tags = getTags();
		fpageEvent.setTagList(tags);
		adminPo = adminService.getById(fpageEvent.getUserid());
		return "editAjax";
	}

	@SuppressWarnings("unchecked")
	public String getListAjax() {
		page = new ExtPageInfo();
		limit = limit == null ? NUM_PER_PAGE : limit;
		pageNum = (pageNum == null || pageNum < 1) ? 1 : pageNum;
		start = (pageNum - 1) * limit;
		page.setStart(start);
		page.setLimit(limit);
		
		FpageEvent event = new FpageEvent();
		event.setIsRecommend(isRecommend);
		event.setIsSuitMobile(isSuitMobile);
		event.setStatus(status);
		event.setPublicationId(publicationId);
		event.setIssueId(issueId);
		event.setTitle(title);
		event.setDescription(description);
		event.setReserved1(reserved1);
		event.setCreatedDate(createdDate);
		event.setAdId(adId);
		event.setUserid(userId);
		event.setId(eventId);
		event.setSidebar(sidebar);
		
		Map<String, Object> map = ConvertPojoToMap.convert(event);
//		map.put("createdTimeStart", createdTimeStart);
		map.put("tag", channel);
		
		
		page = fpageEventService.getByPageToDesigner(page, map);
		
		pageNum = page.getCurPage();
		fpageEventlist = page.getData();
		return "getListAjax";
	}
	private FpageEvent getById(long id){
		ExtPageInfo page = new ExtPageInfo();
		page.setStart(0);
		page.setLimit(1);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		page = fpageEventService.getByPageToDesigner(page, map);
		
		if(page.getData().size() > 0)
			return (FpageEvent) page.getData().get(0);
		return null;
	}

	public String saveJson(){
		this.jsonResult = JsonResult.getFailure();
		if(tag != null){
			addTagJson();
			if(this.jsonResult.getCode() == CODE.FAILURE)
		        return "upload_json";
		}
        try{
    		fpageEvent = getById(eventId);
    		fpageEvent.setWeight(weight);
    		fpageEvent.setTitle(title);
            fpageEvent.setDescription(description);
            fpageEvent.setPageNo(pageNo);
            fpageEvent.setEndPageNo(endPageNo);
//            fpageEvent.setImgFile(imgFile);
            fpageEvent.setStatus(status);
            fpageEvent.setIsRecommend(isRecommend);
            fpageEvent.setIsSuitMobile(isSuitMobile);
            if(sidebar==null)sidebar=0;
            fpageEvent.setSidebar(sidebar);
            BufferedInputStream inputStream = null;
            ActionContext ctx = ActionContext.getContext();
            if(imgFile != null){
            	try{
	            	boolean upSuccess = upload();
	            	if(!upSuccess){
	                    this.generateJsonResult(JsonResult.CODE.FAILURE, "您上传的图片大小不符合规格,请重新上传");
	                    return "upload_json";
	            	}
            	}catch(Exception e){
            		this.generateJsonResult(JsonResult.CODE.EXCEPTION, "您上传的图片失败,请重新上传");
            		return "upload_json";
            	}
            }
            String res1 = fpageEvent.getReserved1();
            fpageEvent.setReserved1(null);
            this.jsonResult = fpageEventService.saveJson(fpageEvent, inputStream, (Admin) ctx.getSession().get(WebConstant.SESSION.ADMIN));
            fpageEvent.setReserved1(res1);
        }catch(Exception e){
            this.generateJsonResult(JsonResult.CODE.EXCEPTION, "服务器内部错误");
        }
        return "upload_json";
	}

    /**
     * 图片上传
     * @return 
     */
    public boolean upload() {
	        //目前path临时用fpageimage
        FileOperate fileOp = new FileOperate();
        String rootPath = systemProp.getFpageLocalUrl() + File.separator + "event";
        if (!new File(rootPath).exists()) {
            fileOp.newFolder(rootPath);
        }

        String currentDate = DateUtil.format(new Date(), "yyyyMMdd");
        String path = rootPath + File.separator + currentDate;
        if (!new File(path).exists()) {
            fileOp.newFolder(path);
        }
        String newImgFileName = System.currentTimeMillis()
                + imgFileName.substring(imgFileName.lastIndexOf(".")).toLowerCase();
        String newImgFilePath = path + File.separator + newImgFileName;

        fileOp.moveFile(imgFile.getAbsolutePath(), newImgFilePath);

        ImageUtil.smallerByRatio(newImgFilePath, path + File.separator + "0.7_" + newImgFileName, 0.7f);
        ImageUtil.smallerTag(newImgFilePath,path + File.separator + "68_" + newImgFileName, 68, 68, 3);
        ImageUtil.smaller(newImgFilePath,path + File.separator + "210_" + newImgFileName, 210, 0, 4);


        //改版后，图片尺寸不限制
        Integer imgWidth = ImageUtil.getWidth(newImgFilePath);
        Integer imgHeight = ImageUtil.getHeight(newImgFilePath);
        fpageEvent.setWidth((float)imgWidth);
        fpageEvent.setHeight((float)imgHeight);
//        if ((imgWidth + 0.0 != fpageEvent.getWidth()) || (imgHeight + 0.0 != fpageEvent.getHeight())) {
//            return false;
//        } else {
        	fpageEvent.setImgFile("/" + currentDate + "/" + newImgFileName);
        	return true;
//        }
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
	  * 标签列表
	 * @return
	 */
	public String listTagJson(){
		 this.jsonResult= JsonResult.getFailure();
		 if(tag!=null){
			 this.jsonResult = JsonResult.getSuccess();
			 this.jsonResult.put("tagList", getTags());
			 this.jsonResult.put("sortList", sortList);
		 }
		 return JSON;
	 }

	private List<Tag> getTags() {
		tag.setType(PojoConstant.TAG.TYPE_EVENT);
		tag.setStatus(PojoConstant.TAG.STATUS_OK);
		List<Tag> tags = tagService.getByTag(tag);

		//一级分类
		sortList = sortService.getLstWithLatestEvent(SORT.NEW_SORT_TYPE);
		for (Sort s : sortList) {
			s.setIsDefault(0);
		}
		List<Tag> list = new ArrayList<Tag>();
		for (Tag t : tags) {
			boolean found = false;
			for (Sort s : sortList) {
				if(t.getName().equals(s.getName())){
					found = true;
					s.setIsDefault(1);
					break;
				}
			}
			if(!found) 
				list.add(t);
		}
		return list;
	}
	/**
	 * 相应阅读器的事件调用
	 * @return
	 */
	public String eventFlexRequest(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		//判断身份
		if(this.getSessionAdmin()==null) 
			{
				this.jsonResult.setMessage("非编辑用户不可以调用该接口");
				return JSON;
			}
		if(issueId==null || issueId<=0) return JSON;
		if(pageNum==null || pageNum<0) return JSON;
		try{
			TextPage tp = textPageService.getByIssueIdAndPageNo(issueId, pageNum);
			
			//-------------------------根据期刊id获取对应的所有的页码：2,3,5,6,7,9
			StringBuffer sbStr = new StringBuffer();
			List<FpageEvent> issueIdAllEventList = fpageEventService.getFpageEventListByIssueId(issueId,-1,-1);
			int ik=0;
			int tempListSize = issueIdAllEventList.size();
			for (FpageEvent fpageEvent : issueIdAllEventList) {
				if(ik>=(tempListSize-1)){
					sbStr.append(NumberUtil.fillNum(fpageEvent.getPageNo(), fpageEvent.getEndPageNo()));
				}else{
					sbStr.append(NumberUtil.fillNum(fpageEvent.getPageNo(), fpageEvent.getEndPageNo()));
					sbStr.append(",");
				}
				ik++;
			}
			//-------------------------
			
			Tag tempTag = new Tag();
				tempTag.setObjectId(issueId);
				tempTag.setPageNum(pageNum);
				tempTag.setType(PojoConstant.TAG.TYPE_ISSUE);
				tempTag.setStatus(PojoConstant.TAG.STATUS_OK);
				if(tp!=null){
				//tp.setContent(HtmlUtils.htmlEscape(tp.getContent()));
				if(tp.getContent()!=null){
					//tp.setContent(java.net.URLEncoder.encode(tp.getContent()));
					tp.setContent(StringUtil.HtmlText(tp.getContent()));
				}
				if(tp.getTitle()!=null){
					tp.setTitle(StringUtil.HtmlText(tp.getTitle()));
				}
				}
			List<Tag> listTag =	tagService.getByTag(tempTag);
			if(listTag!=null&&listTag.size()>0){
				for(Tag t:listTag){
					if(t.getName()!=null){
						t.setName(StringUtil.HtmlText(t.getName()));
					}
				}
			}
			this.jsonResult.put("eventAllPageNo", sbStr.toString());
			this.jsonResult.put("textpage", tp);
			this.jsonResult.put("tag", listTag);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}catch (Exception e) {
		}
		return JSON;
	}
	

	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getIsSuitMobile() {
		return isSuitMobile;
	}

	public void setIsSuitMobile(Integer isSuitMobile) {
		this.isSuitMobile = isSuitMobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setFpageEventlist(List<FpageEvent> fpageEventlist) {
		this.fpageEventlist = fpageEventlist;
	}

	public List<FpageEvent> getFpageEventlist() {
		return fpageEventlist;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getStart() {
		return start;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setFpageEvent(FpageEvent fpageEvent) {
		this.fpageEvent = fpageEvent;
	}

	public FpageEvent getFpageEvent() {
		return fpageEvent;
	}


	public Integer getWeight() {
		return weight;
	}


	public void setWeight(Integer weight) {
		this.weight = weight;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getPageNo() {
		return pageNo;
	}


	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}


	public Integer getEndPageNo() {
		return endPageNo;
	}


	public void setEndPageNo(Integer endPageNo) {
		this.endPageNo = endPageNo;
	}


	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}


	public File getImgFile() {
		return imgFile;
	}


	public void setPage(ExtPageInfo page) {
		this.page = page;
	}


	public ExtPageInfo getPage() {
		return page;
	}


	public void setCreatedTimeStart(String createdTimeStart) {
		this.createdTimeStart = createdTimeStart;
	}


	public String getCreatedTimeStart() {
		return createdTimeStart;
	}


	public void setCreatedTimeEnd(String createdTimeEnd) {
		this.createdTimeEnd = createdTimeEnd;
	}


	public String getCreatedTimeEnd() {
		return createdTimeEnd;
	}


	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}


	public Long getPublicationId() {
		return publicationId;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Long getAdId() {
		return adId;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Tag getTag() {
		return tag;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}

	public List<Sort> getSortList() {
		return sortList;
	}

	public List<Admin> getUserList() {
		return userList;
	}

	public void setUserList(List<Admin> userList) {
		this.userList = userList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Admin getAdminPo() {
		return adminPo;
	}

	public void setAdminPo(Admin adminPo) {
		this.adminPo = adminPo;
	}

	public Integer getSidebar() {
		return sidebar;
	}

	public void setSidebar(Integer sidebar) {
		this.sidebar = sidebar;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
