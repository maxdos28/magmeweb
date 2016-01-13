package cn.magme.web.action.look;

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

import com.danga.MemCached.MemCachedClient;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.CacheConstants;
import cn.magme.pojo.look.LooCategoryRecommend;
import cn.magme.pojo.look.LooGift;
import cn.magme.service.look.LookCategoryRecommendService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK分类广告(推荐)管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/adManagerByCategory.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookAdManagerByCategoryAction extends BaseAction {
	@Resource
	private LookCategoryRecommendService lookCategoryRecommendService;
	@Resource 
	private MemCachedClient memCachedClient;
	private static final Logger log=Logger.getLogger(LookAdManagerByCategoryAction.class);
	
	private Integer type;
	private Long categoryId;
	private Long itemId;
	private String title;
	private Integer currentPage = 1;
	
	private Long categoryRecommendId;
	private File recommendPic;
	private String recommendPicFileName;
	private String link;
	private Long articleId;
	private Byte size;
	private Integer sortOrder;
	private Long appId = 903L;//默认APP
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询分类广告
	public String searchCategoryAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookCategoryRecommendService.searchLooCategoryRecommend(type, categoryId, itemId, title, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存分类广告
	public String saveCategoryAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
			
		if(StringUtil.isBlank(this.title))
		{
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if(this.sortOrder==null||this.sortOrder<0)
		{
			log.warn("排序必须填写");
			this.jsonResult.setMessage("排序必须填写");
			return "fileUploadJson";
		}
		if(this.type==null||this.type<0)
		{
			log.warn("类型必须填写");
			this.jsonResult.setMessage("类型必须填写");
			return "fileUploadJson";
		}
		if(this.categoryId==null||this.categoryId<=0)
		{
			log.warn("分类必须填写");
			this.jsonResult.setMessage("分类必须填写");
			return "fileUploadJson";
		}
		if(this.type!=null&&this.type==1&&(this.itemId==null||this.itemId<=0))
		{
			log.warn("栏目必须填写");
			this.jsonResult.setMessage("栏目必须填写");
			return "fileUploadJson";
		}
		if(this.type!=null&&this.type==0&&StringUtil.isBlank(this.link))
		{
			log.warn("链接必须填写");
			this.jsonResult.setMessage("链接必须填写");
			return "fileUploadJson";
		}
		if((this.categoryRecommendId==null||this.categoryRecommendId<=0)&&this.recommendPic==null)
		{
			log.warn("需要上传图片");
			this.jsonResult.setMessage("需要上传图片");
			return "fileUploadJson";
		}
		if(recommendPic!=null&&recommendPicFileName!=null&&!recommendPicFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		LooCategoryRecommend cr = new LooCategoryRecommend();
		ObjDataCopy.copy(this, cr);
		if(cr.getItemId()==null)
			cr.setItemId(0L);
		cr.setType(new Byte(""+type));
		if(categoryRecommendId!=null&&categoryRecommendId>0)
			cr.setId(categoryRecommendId);
		else
		{

			this.jsonResult = lookCategoryRecommendService.saveLooCategoryRecommend(cr);
			if(this.jsonResult.getCode()!=200)
				return JSON;
			categoryRecommendId = new Long(this.jsonResult.get("id").toString());
			cr.setId(categoryRecommendId);
		}
		
		String staticPath=systemProp.getStaticLocalUrl();
		if(this.recommendPic!=null&&this.recommendPic!=null)
		{
			String relativePath = File.separator +"look"+File.separator+this.appId+File.separator+"ad"+File.separator;
			File f=new File(staticPath+relativePath);
			if(!f.exists()){
				f.mkdirs();
			}
			try {
				BufferedImage buff = ImageIO.read(recommendPic);
				if (buff.getHeight() < 380 || buff.getWidth() < 1256) {
					log.warn("图片尺寸为1256*380");
					this.jsonResult.setMessage("图片尺寸为1256*380");
					return "fileUploadJson";
				}
				String fileName=relativePath+"adcate_"+categoryRecommendId+"_"+System.nanoTime();

				FileOperate.copyFile(recommendPic.getAbsolutePath(), staticPath+fileName+".jpg");
				ImageUtil.smallerByWidthAndHeight(recommendPic.getAbsolutePath(),
						staticPath + fileName + "_pad.jpg", 1256, 380);
				ImageUtil.smallerByWidthAndHeight(recommendPic.getAbsolutePath(),
						staticPath + fileName + "_phone.jpg", 640, 194);

				cr.setPicPath(fileName.replaceAll("\\\\", "/")+".jpg");
			} catch (IOException e) {
				this.jsonResult.setMessage(e.getMessage());
				return "fileUploadJson";
			}
		}
		this.jsonResult = lookCategoryRecommendService.saveLooCategoryRecommend(cr);
		if(this.jsonResult.getCode()==200)
		{
			memCachedClient.delete(CacheConstants.LOOK_GIFT_CACHE);
		}
		return "fileUploadJson";
	}
	//获得分类广告信息
	public String categoryAdInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(categoryRecommendId==null||categoryRecommendId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		LooCategoryRecommend cr = this.lookCategoryRecommendService.getLooCategoryRecommendInfo(categoryRecommendId);
		if(cr==null)
		{
			this.jsonResult.setMessage("获得礼品信息失败");
		}
		else
		{
			this.jsonResult.put("categoryRecommendInfo", cr);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//删除分类广告
	public String deleteCategoryAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(categoryRecommendId==null||categoryRecommendId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookCategoryRecommendService.deleteLooCategoryRecommend(categoryRecommendId);
		if(r<=0)
		{
			this.jsonResult.setMessage("删除失败");
		}
		else
		{
			//memCachedClient.delete(CacheConstants.LOOK_GIFT_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//分类广告上架和下架
	public String changeStatusCategoryAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(categoryRecommendId==null||categoryRecommendId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookCategoryRecommendService.changeLooCategoryRecommendStatus(categoryRecommendId);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
		}
		else
		{
			//memCachedClient.delete(CacheConstants.LOOK_GIFT_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCategoryRecommendId() {
		return categoryRecommendId;
	}

	public void setCategoryRecommendId(Long categoryRecommendId) {
		this.categoryRecommendId = categoryRecommendId;
	}

	public File getRecommendPic() {
		return recommendPic;
	}

	public void setRecommendPic(File recommendPic) {
		this.recommendPic = recommendPic;
	}

	public String getRecommendPicFileName() {
		return recommendPicFileName;
	}

	public void setRecommendPicFileName(String recommendPicFileName) {
		this.recommendPicFileName = recommendPicFileName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Byte getSize() {
		return size;
	}

	public void setSize(Byte size) {
		this.size = size;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

}
