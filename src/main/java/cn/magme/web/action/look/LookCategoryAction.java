package cn.magme.web.action.look;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.look.LooCategory;
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookCategoryService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK分类管理
 * @author jasper
 * @date 2013.10.29
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/categoryManager.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookCategoryAction extends BaseAction {
	@Resource
	private LookCategoryService lookCategoryService;
	private static final Logger log=Logger.getLogger(LookCategoryAction.class);
	
	private String title;
	private Integer currentPage = 1;
	
	private Long id;
	private String memo;
	private Integer sortOrder;
	private File categoryPic1;
	private String categoryPic1FileName;
	private File categoryPic2;
	private String categoryPic2FileName;
	private File categoryPic3;
	private String categoryPic3FileName;
	private File categoryPic4;
	private String categoryPic4FileName;
	
	private String color;

	private Long appId = 903L;
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询分类
	public String searchCategoryJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookCategoryService.searchLookCategory(title, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooCategory> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存分类
	public String saveCategoryJson()
	{
		this.jsonResult=JsonResult.getFailure();
			
		if(StringUtil.isBlank(this.title))
		{
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.color))
		{
			log.warn("颜色必须填写");
			this.jsonResult.setMessage("颜色必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.memo))
		{
			log.warn("描述必须填写");
			this.jsonResult.setMessage("描述必须填写");
			return "fileUploadJson";
		}
		if(this.sortOrder==null)
		{
			log.warn("排序必须填写");
			this.jsonResult.setMessage("排序必须填写");
			return "fileUploadJson";
		}
		if((id==null||id<=0)&&(categoryPic1==null||categoryPic2==null||categoryPic3==null||categoryPic4==null))
		{
			log.warn("必须上传图片");
			this.jsonResult.setMessage("必须上传图片");
			return "fileUploadJson";
		}
		if((categoryPic1!=null||categoryPic2!=null||categoryPic3!=null||categoryPic4!=null)&&(categoryPic1==null||categoryPic2==null||categoryPic3==null||categoryPic4==null))
		{
			log.warn("必须上传全部图片");
			this.jsonResult.setMessage("必须上传全部图片");
			return "fileUploadJson";
		}
		if(categoryPic1!=null&&categoryPic1FileName!=null&&!categoryPic1FileName.toLowerCase().endsWith(".png"))
		{
			log.warn("文件类型必须是png");
			this.jsonResult.setMessage("文件类型必须是png");
			return "fileUploadJson";
		}
		if(categoryPic2!=null&&categoryPic2FileName!=null&&!categoryPic2FileName.toLowerCase().endsWith(".png"))
		{
			log.warn("文件类型必须是png");
			this.jsonResult.setMessage("文件类型必须是png");
			return "fileUploadJson";
		}
		if(categoryPic3!=null&&categoryPic3FileName!=null&&!categoryPic3FileName.toLowerCase().endsWith(".png"))
		{
			log.warn("文件类型必须是png");
			this.jsonResult.setMessage("文件类型必须是png");
			return "fileUploadJson";
		}
		if(categoryPic4!=null&&categoryPic4FileName!=null&&!categoryPic4FileName.toLowerCase().endsWith(".png"))
		{
			log.warn("文件类型必须是png");
			this.jsonResult.setMessage("文件类型必须是png");
			return "fileUploadJson";
		}
		if(categoryPic1!=null)
		{
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff1 = ImageIO.read(categoryPic1);
				if (buff1.getHeight() != 144 || buff1.getWidth() != 144) {
					log.warn("图片尺寸为144*144");
					this.jsonResult.setMessage("图片尺寸为144*144");
					return "fileUploadJson";
				}
				BufferedImage buff2 = ImageIO.read(categoryPic2);
				if (buff2.getHeight() != 96 || buff2.getWidth() != 96) {
					log.warn("图片尺寸为96*96");
					this.jsonResult.setMessage("图片尺寸为96*96");
					return "fileUploadJson";
				}
				BufferedImage buff3 = ImageIO.read(categoryPic3);
				if (buff3.getHeight() != 72 || buff3.getWidth() != 72) {
					log.warn("图片尺寸为72*72");
					this.jsonResult.setMessage("图片尺寸为72*72");
					return "fileUploadJson";
				}
				BufferedImage buff4 = ImageIO.read(categoryPic4);
				if (buff4.getHeight() != 48 || buff4.getWidth() != 48) {
					log.warn("图片尺寸为48*48");
					this.jsonResult.setMessage("图片尺寸为48*48");
					return "fileUploadJson";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LooCategory looCategory = new LooCategory();
		ObjDataCopy.copy(this, looCategory);
		if (id != null && id > 0)
			looCategory.setId(id);
		else {
			int rid = this.lookCategoryService.saveLooCategory(looCategory);
			id = new Long(rid);
			looCategory.setId(id);
		}
		// 上传图片
		if (this.categoryPic1 != null) {
					
					String staticPath = systemProp.getStaticLocalUrl();
					String relativePath = File.separator + "look" + File.separator
							+ this.appId + File.separator + "category" + File.separator + id
							+ File.separator;
					File dir = new File(staticPath + relativePath);
					if (!dir.exists())
						dir.mkdirs();
					// 图片尺寸转换 pad 472*472 phone 360*360
					// 文章图片ID，文章ID+－+时间
					String fileName = relativePath + "cate_" + id + "-"
							+ System.nanoTime();

					FileOperate.copyFile(categoryPic1.getAbsolutePath(), staticPath+fileName+".png");

					FileOperate.copyFile(categoryPic1.getAbsolutePath(), staticPath+fileName+"_1.png");
					FileOperate.copyFile(categoryPic2.getAbsolutePath(), staticPath+fileName+"_2.png");
					FileOperate.copyFile(categoryPic3.getAbsolutePath(), staticPath+fileName+"_3.png");
					FileOperate.copyFile(categoryPic4.getAbsolutePath(), staticPath+fileName+"_4.png");
					looCategory.setPicPath(fileName.replaceAll("\\\\", "/") + ".png");
				}
		int r = lookCategoryService.saveLooCategory(looCategory);
		
		if(r<=0)
		{
			this.jsonResult.setMessage("保存失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}
	//获得分类信息
	public String categoryInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		LooCategory c = this.lookCategoryService.getLooCategoryInfo(id);
		if(c==null)
		{
			this.jsonResult.setMessage("获得分类信息失败");
		}
		else
		{
			this.jsonResult.put("categoryInfo", c);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//删除分类
	public String deleteCategoryJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookCategoryService.deleteLooCategory(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("删除失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//上架和下架
	public String changeStatusCategoryJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookCategoryService.changeLooCategoryStatus(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//全部分类列表
	public String allCategoryJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<LooCategory> categoryList = this.lookCategoryService.getAllLooCategory();
		this.jsonResult.put("categoryList", categoryList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public File getCategoryPic1() {
		return categoryPic1;
	}

	public void setCategoryPic1(File categoryPic1) {
		this.categoryPic1 = categoryPic1;
	}

	public String getCategoryPic1FileName() {
		return categoryPic1FileName;
	}

	public void setCategoryPic1FileName(String categoryPic1FileName) {
		this.categoryPic1FileName = categoryPic1FileName;
	}

	public File getCategoryPic2() {
		return categoryPic2;
	}

	public void setCategoryPic2(File categoryPic2) {
		this.categoryPic2 = categoryPic2;
	}

	public String getCategoryPic2FileName() {
		return categoryPic2FileName;
	}

	public void setCategoryPic2FileName(String categoryPic2FileName) {
		this.categoryPic2FileName = categoryPic2FileName;
	}

	public File getCategoryPic3() {
		return categoryPic3;
	}

	public void setCategoryPic3(File categoryPic3) {
		this.categoryPic3 = categoryPic3;
	}

	public String getCategoryPic3FileName() {
		return categoryPic3FileName;
	}

	public void setCategoryPic3FileName(String categoryPic3FileName) {
		this.categoryPic3FileName = categoryPic3FileName;
	}

	public File getCategoryPic4() {
		return categoryPic4;
	}

	public void setCategoryPic4(File categoryPic4) {
		this.categoryPic4 = categoryPic4;
	}

	public String getCategoryPic4FileName() {
		return categoryPic4FileName;
	}

	public void setCategoryPic4FileName(String categoryPic4FileName) {
		this.categoryPic4FileName = categoryPic4FileName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
