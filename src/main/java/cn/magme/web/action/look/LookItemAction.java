package cn.magme.web.action.look;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Publication;
import cn.magme.pojo.look.LooItem;
import cn.magme.pojo.phoenix.PhoenixCategory;
import cn.magme.service.PublicationService;
import cn.magme.service.look.LookItemService;
import cn.magme.service.phoenix.PhoenixCategoryService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK栏目管理
 * 
 * @author jasper
 * @date 2013.10.29
 * 
 */
@Results({
		@Result(name = "success", location = "/WEB-INF/pages/looker/admin/itemManager.ftl"),
		@Result(name = "fileUploadJson", type = "json", params = { "root",
				"jsonResult", "contentType", "text/html" }) })
public class LookItemAction extends BaseAction {
	@Resource
	private LookItemService lookItemService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private PhoenixCategoryService phoenixCategoryService;
	private static final Logger log = Logger.getLogger(LookItemAction.class);

	private String title;
	private Integer currentPage = 1;

	private Integer isRecommend;
	private Integer itemType;
	private Long categoryId;

	private Long id;
	private Long parentId;
	private String memo;
	private String color;
	private Integer sortOrder;
	private Integer isRe;
	private Integer isNew;
	private Integer type;
	private Integer isDelete;
	private Integer isHot;
	private String itemPicFileName;
	private File itemPic;
	private Long publicationId;

	private Long appId = 903L;

	public String execute() {
		return SUCCESS;
	}

	// 查询栏目
	public String searchItemJson() {
		this.jsonResult = JsonResult.getFailure();
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		Page p = lookItemService.searchLooItem(isRecommend, itemType,
				categoryId, title, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}

	// 保存栏目
	public String saveItemJson() {
		this.jsonResult = JsonResult.getFailure();

		if (StringUtil.isBlank(this.title)) {
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if (this.type == null) {
			log.warn("类型必须填写");
			this.jsonResult.setMessage("类型必须填写");
			return "fileUploadJson";
		}

		if (this.type == 2) {
			if (this.publicationId == null || this.publicationId <= 0) {
				log.warn("杂志必须填写");
				this.jsonResult.setMessage("杂志必须填写");
				return "fileUploadJson";
			}
		} else {

			if ((id == null || id <= 0)
					&& (this.parentId != null && this.parentId > 0)
					&& this.itemPic == null) {
				log.warn("图片必须上传");
				this.jsonResult.setMessage("图片必须上传");
				return "fileUploadJson";
			}
		}
		if (StringUtil.isBlank(this.memo)) {
			log.warn("描述必须填写");
			this.jsonResult.setMessage("描述必须填写");
			return "fileUploadJson";
		}
		if (this.sortOrder == null || this.sortOrder <= 0) {
			log.warn("排序必须填写");
			this.jsonResult.setMessage("排序必须填写");
			return "fileUploadJson";
		}
		if ((this.parentId == null || this.parentId <= 0)
				&& (this.categoryId == null || this.categoryId <= 0)) {
			log.warn("分类必须填写");
			this.jsonResult.setMessage("分类必须填写");
			return "fileUploadJson";
		}

		if (itemPic != null && itemPicFileName != null
				&& !itemPicFileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		if (itemPic != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff = ImageIO.read(itemPic);
				if (buff.getHeight() < 472 || buff.getWidth() < 472) {
					log.warn("图片尺寸为472*472");
					this.jsonResult.setMessage("图片尺寸为472*472");
					return "fileUploadJson";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.parentId != null && this.parentId > 0 && id != null && id > 0
				&& this.parentId.longValue() == this.id.longValue()) {
			log.warn("父栏目不能选择自身");
			this.jsonResult.setMessage("父栏目不能选择自身");
			return "fileUploadJson";
		}
		LooItem looItem = new LooItem();
		ObjDataCopy.copy(this, looItem);
		looItem.setType(new Byte("" + type));
		//是否在前端可删除
		if(this.isDelete!=null&&this.isDelete==1)
			looItem.setIsDelete(new Byte("1"));
		else
			looItem.setIsDelete(new Byte("0"));
		//是否为新栏目
		if(this.isNew!=null&&this.isNew==1)
			looItem.setIsNew(new Byte("1"));
		else
			looItem.setIsNew(new Byte("0"));
		//是否为热点栏目
		if(this.isHot!=null&&this.isHot==1)
			looItem.setIsHot(new Byte("1"));
		else
			looItem.setIsHot(new Byte("0"));
		if (id != null && id > 0)
			looItem.setId(id);
		else {
			id = this.lookItemService.inserLooItem(looItem);
			looItem.setId(id);
		}
		// 上传图片
		if (this.itemPic != null) {

			String staticPath = systemProp.getStaticLocalUrl();
			String relativePath = File.separator + "look" + File.separator
					+ this.appId + File.separator + "item" + File.separator
					+ id + File.separator;
			File dir = new File(staticPath + relativePath);
			if (!dir.exists())
				dir.mkdirs();
			// 图片尺寸转换 pad 472*472 phone 360*360
			// 文章图片ID，文章ID+－+时间
			String fileName = relativePath + "item_" + id + "-"
					+ System.nanoTime();

			FileOperate.copyFile(itemPic.getAbsolutePath(), staticPath
					+ fileName + ".jpg");
			ImageUtil.smallerByWidthAndHeight(itemPic.getAbsolutePath(),
					staticPath + fileName + "_pad.jpg", 472, 472);
			ImageUtil.smallerByWidthAndHeight(itemPic.getAbsolutePath(),
					staticPath + fileName + "_phone.jpg", 360, 360);
			looItem.setPicPath(fileName.replaceAll("\\\\", "/") + ".jpg");
		}
		int r = lookItemService.saveLooItem(looItem, publicationId, categoryId,
				sortOrder);
		if (r <= 0) {
			this.jsonResult.setMessage("保存失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}

	// 栏目信息
	public String itemInfoJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		Map item = this.lookItemService.getLooItemInfo(id);
		if (item == null) {
			this.jsonResult.setMessage("删除失败");
		} else {
			this.jsonResult.put("itemInfo", item);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 删除栏目
	public String deleteItemJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookItemService.deleteLooItem(id);
		if (r <= 0) {
			this.jsonResult.setMessage("删除失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 上架和下架
	public String changeStatusItemJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookItemService.changeLooItemStatus(id);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 推荐
	public String recommendItemJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookItemService.changeLooItemRecommendStatus(id);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 得到杂志列表
	public String publicationList() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			log.warn("APP ID为空");
			this.jsonResult.setMessage("APP ID为空");
			return JSON;
		}
		List<Publication> pubList = publicationService.queryByAppId(appId,
				PojoConstant.PUBLICATION.PUBTYPE.ACTIVE_MAGAZINE.getCode());
		if (pubList == null) {
			this.jsonResult.setMessage("得到杂志列表失败");
		} else {
			this.jsonResult.put("publicationList", pubList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 全部栏目列表
	public String allItemJson() {
		this.jsonResult = JsonResult.getFailure();
		List<LooItem> itemList = this.lookItemService.getAllLooItem();
		List<Map> liList = new ArrayList();
		// 给有子栏目的栏目做标记
		if (itemList != null && itemList.size() > 0) {
			Map<Long, Long> pm = new HashMap();// 保存有子栏目的父栏目
			for (LooItem li : itemList) {
				Map m = new HashMap();
				m.put("id", li.getId());
				m.put("isRe", li.getIsRe());
				m.put("memo", li.getMemo());
				m.put("parentId", li.getParentId());
				m.put("picPath", li.getPicPath());
				m.put("sortOrder", li.getSortOrder());
				m.put("status", li.getStatus());
				m.put("title", li.getTitle());
				m.put("type", li.getType());
				liList.add(m);
				if (li.getParentId() != null && li.getParentId() > 0)
					pm.put(li.getParentId(), li.getParentId());
			}
			for (Map m : liList) {
				if (pm.get(m.get("id")) != null)
					m.put("isParent", 1);
				else
					m.put("isParent", 0);
			}
		}
		this.jsonResult.put("itemList", liList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	// 分类下栏目列表
	public String getItemByCategoryIdJson() {
		this.jsonResult = JsonResult.getFailure();
		if (categoryId == null || categoryId <= 0) {
			log.warn("分类ID为空");
			this.jsonResult.setMessage("分类ID为空");
			return JSON;
		}
		List<Map> itemList = this.lookItemService
				.getItemByCategoryId(categoryId);
		this.jsonResult.put("itemList", itemList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	// 凤凰栏目列表
	public String getPhoenixItemByAppIdJson() {
		this.jsonResult = JsonResult.getFailure();
		if (appId == null || appId <= 0) {
			log.warn("APP ID为空");
			this.jsonResult.setMessage("APP ID为空");
			return JSON;
		}
		List<PhoenixCategory> phoenixCategoryList = phoenixCategoryService
				.queryByCondition(appId, null, null, null, null);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("phoenixCategoryList", phoenixCategoryList);
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

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public Integer getIsRe() {
		return isRe;
	}

	public void setIsRe(Integer isRe) {
		this.isRe = isRe;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getItemPicFileName() {
		return itemPicFileName;
	}

	public void setItemPicFileName(String itemPicFileName) {
		this.itemPicFileName = itemPicFileName;
	}

	public File getItemPic() {
		return itemPic;
	}

	public void setItemPic(File itemPic) {
		this.itemPic = itemPic;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

}
