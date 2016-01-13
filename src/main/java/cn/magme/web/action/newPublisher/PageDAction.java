package cn.magme.web.action.newPublisher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Category;
import cn.magme.pojo.PageD;
import cn.magme.service.CategoryService;
import cn.magme.service.PageDService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.StringUtil;
import cn.magme.util.UploadPictureUtil;
import cn.magme.web.action.BaseAction;
/**
 * D类页的action
 * @author fredy
 * @since 20120807
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisher/pagedlist.ftl"),
	@Result(name = "upload_json", type = "json", params = { "root","jsonResult", "contentType", "text/html" })
})
public class PageDAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5132992672629882439L;
	
	@Resource
	private PageDService pageDService;
	@Resource
	private CategoryService categoryService;

	private Integer height;

	private Integer width;
	
	
	private static final Logger log=Logger.getLogger(PageDAction.class);
	
	public String execute(){
		categoryId=2L;//页面初始化
		categoryList = categoryService.queryAllChildCategories();
		this.pageDJson();
		return SUCCESS;
	}
	
	
	/**
	 * @return
	 */
	public String getPageD(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			PageD pageD=this.pageDService.queryById(id);
			if(pageD!=null){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("paged", pageD);
			}
		} catch (Exception e) {
			log.error("", e);
			return JSON;
		}
		return JSON;
	}
	
	public String pageDJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			ExtPageInfo extpage = new ExtPageInfo();
			if(pageCurrent<=0){
				pageCurrent=1;
			}
			extpage.setStart((pageCurrent-1)*pageSize);
			extpage.setLimit(pageSize);
			extpage.setCurPage(pageCurrent);
			extpage=this.pageDService.queryByCidAndName(extpage, categoryId, name);
			if(extpage!=null){
				if(extpage.getTotal()!=0){
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
					this.jsonResult.put("page", extpage);
					pageCount = extpage.getTotalPage();
				}else{
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
					extpage.setTotal(1);
					this.jsonResult.put("page", extpage);
					
					pageCount = 1;
				}
			}else{
				extpage = new ExtPageInfo();
				extpage.setCurPage(pageCurrent);
				extpage.setTotal(1L);
			}
		} catch (Exception e) {
			log.error("PageDAction -->pageDJson.action",e);
			return JSON;
		}
		return JSON;
	}
	
	public String addPagedJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		PageD paged = new PageD();
		paged.setId(id);
		paged.setCategoryId(categoryId);
		paged.setFirstLetter(firstLetter);
		paged.setDescription(description);
		paged.setHeaderDesc(headerDesc);
		paged.setIndexDesc(indexDesc);
		paged.setKeyWord(keyWord);
		paged.setName(name);//
		paged.setTags(tags);//
		paged.setTitle(title);//
		paged.setPicUrl(url);
		paged.setShortTitle(shortTitle);
		paged.setIsHot(isHot);
		if(width != null) paged.setWidth(width);
		if(height != null) paged.setHeight(height);
		if(StringUtil.isBlank(name)){this.jsonResult.setMessage("名称不能为空");return "upload_json";}
		if(StringUtil.isBlank(title)){this.jsonResult.setMessage("索引文字标题不能为空");return "upload_json";}
		if(isHot>0 && StringUtil.isBlank(shortTitle)){this.jsonResult.setMessage("选择热点专题时缩略标题不能为空");return "upload_json";}
		
		PageD checkName= pageDService.queryByName(name);
		if(checkName!=null){
			if(!checkName.getName().equals(name)){
			this.jsonResult.setMessage("名称重复");
			return "upload_json";
			}
		}
		
		if(id!=null){//更新
			if(StringUtil.isNotBlank(tags)){
				String[] tagsArray = tags.split(",");
				boolean nameCheck = false;
				if(tagsArray!=null){
					for (int i = 0; i < tagsArray.length; i++) {
						String temp =tagsArray[i];
						if(name.equals(temp)){
							nameCheck = true;
							break;
						}
					}
					if(!nameCheck){
						paged.setTags(name+","+tags);
					}
				}
			}else{
				paged.setTags(name);
			}
			
			if(this.pageDService.updateById(paged)!=null){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		}else{//添加
			paged.setStatus(1);
			//将名称默认为标签之一。
			if(StringUtil.isNotBlank(tags)){
				paged.setTags(name+","+tags);
			}else{
				paged.setTags(name);
			}
			if(this.pageDService.addPaged(paged)!=null){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		}
		return JSON;
	}
	
	public String updateImageJson(){
		this.jsonResult = JsonResult.getFailure();
		if(pic!=null){
			Map<String,String> upPicMap = UploadPictureUtil.uploadPicPub(pic, picContentType, picFileName, systemProp.getPageDLocalUrl());
			if(upPicMap!=null&&upPicMap.get("status")!=null){
				if(upPicMap.get("status").equals("1")){//上传成功
					this.jsonResult.put("imgpath", upPicMap.get("path"));
					try {
//						BufferedImage img = ImageIO.read(new File("E:\\usr\\local\\magmecn\\static\\paged" + upPicMap.get("path")));
//						URL url = new URL("http://static.magme.com/paged" + upPicMap.get("path"));
//						BufferedImage img = ImageIO.read(url);
						File f = new File(systemProp.getPageDLocalUrl() + upPicMap.get("path"));
						BufferedImage img = ImageIO.read(f);
						this.width = img.getWidth();
						this.height = img.getHeight();
						this.jsonResult.put("width", width);
						this.jsonResult.put("height", height);
						this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
						this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
					} catch (IOException e) {
						this.jsonResult.setMessage("获取图片尺寸失败！");
						log.error("read image width & height error", e);
						e.printStackTrace();
					}
				}else{//上传失败
					this.jsonResult.setMessage(upPicMap.get("message"));
					return "upload_json";
				}
			}
		}else{
			//没有上传图片
			this.jsonResult.put("imgpath", "");
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "upload_json";
	}
	
	public String editPagedJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		PageD paged = new PageD();
		paged.setId(id);
		paged.setStatus(status);
		if(this.pageDService.updateById(paged)!=null){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	private Long categoryId;
	
	private String name;
	
	private Integer pageCount=1;
	private Integer pageCurrent=1;
	
	private Integer pageSize=10;
	
	private Long id;
	
	private List<PageD> pagedList;
	
	private List<Category> categoryList;
	
	private String description;
	private String indexDesc;
	private String tags;
	private String headerDesc;
	private String keyWord;
	private String title;
	private String shortTitle;
	private Integer isHot;
	private Integer status;
	
	private File pic;
	private String picContentType;
	private String picFileName;
	
	private String firstLetter;
	private String url;
	
	public String getFirstLetter() {
		return firstLetter;
	}


	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	

	public String getShortTitle() {
		return shortTitle;
	}


	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}


	public Integer getIsHot() {
		return isHot;
	}


	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getIndexDesc() {
		return indexDesc;
	}


	public void setIndexDesc(String indexDesc) {
		this.indexDesc = indexDesc;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getHeaderDesc() {
		return headerDesc;
	}


	public void setHeaderDesc(String headerDesc) {
		this.headerDesc = headerDesc;
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public List<PageD> getPagedList() {
		return pagedList;
	}


	public void setPagedList(List<PageD> pagedList) {
		this.pagedList = pagedList;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Category> getCategoryList() {
		return categoryList;
	}


	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
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


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public void setHeight(Integer height) {
		this.height = height;
	}


	public Integer getHeight() {
		return height;
	}


	public void setWidth(Integer width) {
		this.width = width;
	}


	public Integer getWidth() {
		return width;
	}
	
	
}
