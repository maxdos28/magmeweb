package cn.magme.web.action.look;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.IssueContents;
import cn.magme.pojo.look.LooAdminUser;
import cn.magme.pojo.look.LooArticle;
import cn.magme.pojo.look.LooManyLook;
import cn.magme.pojo.ma.MaAdLocation;
import cn.magme.service.look.LookArticleService;
import cn.magme.service.ma.MaAdLocationService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * LOOK文章管理
 * 
 * @author jasper
 * @date 2013.10.30
 * 
 */
@Results({
		@Result(name = "success", location = "/WEB-INF/pages/looker/admin/articleManager.ftl"),
		@Result(name = "view_edit_article", location = "/WEB-INF/pages/looker/admin/articleEdit.ftl"),
		@Result(name = "fileUploadJson", type = "json", params = { "root",
				"jsonResult", "contentType", "text/html" }) })
public class LookArticleAction extends BaseAction {
	@Resource
	private LookArticleService lookArticleService;
	@Resource
	private MaAdLocationService maAdLocationService;
	private static final Logger log = Logger
			.getLogger(LookCategoryAction.class);

	private String title;
	private String title2;
	private Integer currentPage = 1;
	private Integer status;
	private Long itemId;
	private String itemIds;
	private Long categoryId;

	private Long id;
	private String ids;
	private String memo;
	private File smallPic1;
	private String smallPic1FileName;
	private File smallPic2;
	private String smallPic2FileName;
	private File articleZip;
	private String articleZipFileName;

	private Long manyLookId;
	private String manyTitle;
	private String manyUrl;
	private Integer sortOrder;
	private Integer manyType;
	private String isTop;
	private String cuser;
	private String createTime;
	private String publishDate;

	private Long appId = 903L;// 默认APP ID

	public String execute() {
		return SUCCESS;
	}
	
	public String viewEditArticle() {
		return "view_edit_article";
	}

	// 查询文章
	public String searchArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		Page p = lookArticleService.searchLooArticle(status, itemId,
				categoryId, title,cuser,createTime,publishDate, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooArticle> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}

	// 保存文章
	public String saveArticleJson() {
		this.jsonResult = JsonResult.getFailure();

		if (StringUtil.isBlank(this.title)) {
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if (StringUtil.isBlank(this.memo)) {
			log.warn("描述必须填写");
			this.jsonResult.setMessage("描述必须填写");
			return "fileUploadJson";
		}
		if (itemIds == null || itemIds.length() == 0) {
			log.warn("栏目必须填写");
			this.jsonResult.setMessage("栏目必须填写");
			return "fileUploadJson";
		}
		if (this.id == null || this.id <= 0) {
//			if (this.smallPic1 == null) {
//				log.warn("必须上传图片");
//				this.jsonResult.setMessage("必须上传图片");
//				return "fileUploadJson";
//			}
//			if (this.smallPic2 == null) {
//				log.warn("必须上传图片");
//				this.jsonResult.setMessage("必须上传图片");
//				return "fileUploadJson";
//			}
			if (this.articleZip == null) {
				log.warn("必须上传文件");
				this.jsonResult.setMessage("必须上传文件");
				return "fileUploadJson";
			}
		}

		if ((this.smallPic1 != null || this.smallPic2 != null)
				&& (this.smallPic1 == null || this.smallPic2 == null)) {
			log.warn("2张图片必须同时上传");
			this.jsonResult.setMessage("2张图片必须同时上传");
			return "fileUploadJson";
		}
		if (smallPic1 != null && smallPic1FileName != null
				&& !smallPic1FileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		if (smallPic2 != null && smallPic2FileName != null
				&& !smallPic2FileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		// 图片尺寸检查
		if (smallPic1 != null) {
			try {
				BufferedImage buff = ImageIO.read(smallPic1);
				if (buff.getHeight() != 472 || buff.getWidth() != 472) {
					log.warn("图片尺寸为472*472");
					this.jsonResult.setMessage("图片尺寸为472*472");
					return "fileUploadJson";
				}
			} catch (IOException e) {
				this.jsonResult.setMessage(e.getMessage());
				return "fileUploadJson";
			}
		}
		if (smallPic2 != null && smallPic2FileName != null
				&& !smallPic2FileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		// 图片尺寸检查
		if (smallPic2 != null) {
			try {
				BufferedImage buff = ImageIO.read(smallPic2);
				if (buff.getHeight() != 360 || buff.getWidth() != 360) {
					log.warn("图片尺寸为360*360");
					this.jsonResult.setMessage("图片尺寸为360*360");
					return "fileUploadJson";
				}
			} catch (IOException e) {
				this.jsonResult.setMessage(e.getMessage());
				return "fileUploadJson";
			}
		}
		if (articleZip != null
				&& articleZipFileName != null
				&& !(articleZipFileName.toLowerCase().endsWith(".mpres") || articleZipFileName
						.toLowerCase().endsWith(".zip"))) {
			log.warn("文件类型必须是mpres");
			this.jsonResult.setMessage("文件类型必须是mpres或zip");
			return "fileUploadJson";
		}
		LooArticle looArticle = new LooArticle();
		looArticle.setTitle(title);
		//正文
		if(title2!=null&&title2.trim().length()>0)
			looArticle.setTitle2(title2);
		else
			looArticle.setTitle2("");
		looArticle.setMemo(memo);
		looArticle.setIsTop(new Byte(isTop));
		LooAdminUser user = (LooAdminUser) ActionContext.getContext()
				.getSession().get(WebConstant.SESSION.LOOK_USER);
		if (id != null && id > 0) {
			looArticle.setId(id);
			looArticle.setMuser(user.getUserNo());
		} else {
			looArticle.setPageNum(0);
			id = this.lookArticleService.inserLooArticle(looArticle);
			looArticle.setId(id);
			looArticle.setCuser(user.getUserNo());
		}
		// 文章使用和互动杂志相同路径
		String staticPath = systemProp.getMagicEditorPath();
		String relativePath = "app" + this.appId + File.separator + id
				+ File.separator;
		File dir = new File(staticPath + relativePath);
		if (!dir.exists())
			dir.mkdirs();
		String fileName = File.separator + relativePath + "article_" + id + "-"
				+ System.nanoTime();
		// 上传文件
		if (this.articleZip != null) {
			// 解压文件
			int p = unzip(staticPath + relativePath, dir, looArticle);
			if (p == 0)
				return "fileUploadJson";
			// 如果是新增则直接解压缩
			looArticle.setSharePath(this.systemProp.getStaticServerUrl()
					+ "/appprofile/" + relativePath.replaceAll("\\\\", "/")
					+ "1/phone.html");
			looArticle.setDownPath(this.systemProp.getStaticServerUrl()
					+ "/appprofile/" + relativePath.replaceAll("\\\\", "/")
					+ id + ".mpres");
			looArticle.setSize("" + this.articleZip.length());
			looArticle.setPageNum(p);
		}
		// 上传图片
		if (this.smallPic1 != null) {
			// FileInputStream fis = new FileInputStream(smallPic);
			try {

				BufferedImage buff = ImageIO.read(smallPic1);
				// 图片尺寸转换 pad 470*470 phone 360*360
				// 文章图片ID，文章ID+－+时间

				FileOperate.copyFile(smallPic1.getAbsolutePath(), staticPath
						+ fileName + ".jpg");
				if (buff.getHeight() == 472 && buff.getWidth() == 472) {
					FileOperate.copyFile(smallPic2.getAbsolutePath(),
							staticPath + fileName + "_pad.jpg");
				} else {
					ImageUtil.smallerByWidthAndHeight(
							smallPic1.getAbsolutePath(), staticPath + fileName
									+ "_pad.jpg", 472, 472);
				}
				looArticle.setSmallPic("/appprofile"
						+ fileName.replaceAll("\\\\", "/") + ".jpg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (this.smallPic2 != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {

				BufferedImage buff = ImageIO.read(smallPic1);
				// 图片尺寸转换 pad 470*470 phone 360*360
				// 文章图片ID，文章ID+－+时间

				if (buff.getHeight() == 360 && buff.getWidth() == 360) {
					FileOperate.copyFile(smallPic2.getAbsolutePath(),
							staticPath + fileName + "_phone.jpg");
				} else {
					ImageUtil.smallerByWidthAndHeight(
							smallPic2.getAbsolutePath(), staticPath + fileName
									+ "_phone.jpg", 360, 360);
				}
			} catch (IOException e) {
				this.jsonResult.setMessage(e.getMessage());
				return "fileUploadJson";
			}
		}

		int r = lookArticleService.saveLooArticle(looArticle, itemIds);
		if (r <= 0) {
			this.jsonResult.setMessage("保存失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}

	// 保存ZIP并解压
	private int unzip(String path, File dir, LooArticle looArticle) {
		int pageSize = 0;// 总页数
		// zip文件保存
		File zipFile = new File(path + id + ".mpres");
		FileOperate.copyFile(articleZip.getAbsolutePath(),
				zipFile.getAbsolutePath());
		// 解压ZIP
		try {
			// 解压根zip包
			FileOperate.unzipFile(articleZip.getAbsolutePath(), path, false);
			// 解压解压后的资源文件
			File[] fList = dir.listFiles();
			for (File ff : fList) {
				if (ff.isDirectory()
						|| !ff.getName().toLowerCase().endsWith(".zip"))
					continue;
				if (ff.getName().toLowerCase().indexOf("assets") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
					pageSize++;
				}

				// 把手机的html页面解压出来,作为分享的路径
				// 由于pubassets路径问题,解压出来的路径要放到magic.editor.path下,和互动杂志使用相同的
				if (ff.getName().toLowerCase().indexOf("phone") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
				}
				if (ff.getName().toLowerCase().indexOf("pad") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
				}
			}
			// 重命名解压后的资源文件
			File[] assetsFolderList = dir.listFiles();
			int assetsIndex = 0;
			for (File ff : assetsFolderList) {
				assetsIndex = ff.getName().indexOf("assets");
				if (ff.isDirectory() && assetsIndex > 0) {
					FileOperate.moveFolder(ff.getAbsolutePath(), path
							+ ff.getName().substring(0, assetsIndex));
				}
			}
			// zipFile.delete();
			// 读取ZIP中的配置文件
			File configFile = new File(path + "config.xml");
			if (configFile.exists()) {
				// 文章的页记数,KPI使用
				Map<String, Object> config = this.parseConfig(configFile);
				Integer totalPages = (Integer) config.get("pageSumCount");
				if (totalPages == null || totalPages == 0) {
					totalPages = (Integer) config.get("pageCount");
				}
				looArticle.setCountPage(totalPages);
				//取配置文件中的页数,否则在更新文章且文章页数变少的情况下总页数不正确
				if(config.get("pageCount")!=null)
				pageSize = (Integer) config.get("pageCount");
				//文章封面图
				String coverPad = (String) config.get("coverPad");
				String coverPhone = (String) config.get("coverPhone");
				if(StringUtil.isNotBlank(coverPad)&&StringUtil.isNotBlank(coverPhone))
				{
					File coverPadFile = new File(path+coverPad);
					File coverPhoneFile = new File(path+coverPhone);
					//文件需要重新命名,以_pad和_phone结尾
					if(coverPadFile.exists()&&coverPhoneFile.exists())
					{
						FileOperate.copyFile(coverPadFile.getAbsolutePath(), path+"article_" + id+coverPad.substring(coverPad.lastIndexOf(".")));
						FileOperate.copyFile(coverPadFile.getAbsolutePath(), path+"article_" + id+"_pad"+coverPad.substring(coverPad.lastIndexOf(".")));
						FileOperate.copyFile(coverPhoneFile.getAbsolutePath(), path+"article_" + id+"_phone"+coverPhone.substring(coverPhone.lastIndexOf(".")));
						String fileName = "/appprofile/app" + this.appId + File.separator + id
								+ File.separator+"article_" + id+coverPad.substring(coverPad.lastIndexOf("."));
						looArticle.setSmallPic(fileName.replaceAll("\\\\", "/"));
					}
				}
			}

		} catch (IOException e) {
			log.error("", e);
			this.jsonResult.setMessage("unzip file error");
			return 0;
		}
		return pageSize;
	}

	/**
	 * 解析xml配置文件
	 * 
	 * @return
	 */
	private Map<String, Object> parseConfig(File configFile) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<IssueContents> issueContentList = new ArrayList<IssueContents>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			InputStream in = new FileInputStream(configFile);
			Document doc = builder.parse(in);

			Element root = doc.getDocumentElement();
			if (root == null)
				return null;

			NodeList collegeNodes = root.getChildNodes();
			if (collegeNodes == null)
				return null;

			Node pageCountNode = root.getElementsByTagName("pageCount").item(0);
			map.put("pageCount", Integer.parseInt(pageCountNode.getFirstChild()
					.getNodeValue()));
			Node pageSumCountNode = root.getElementsByTagName("pageSumCount")
					.item(0);
			map.put("pageSumCount", Integer.parseInt(pageSumCountNode
					.getFirstChild().getNodeValue()));

			// 方向，横竖屏
			Node orientationNode = root.getElementsByTagName("orientation")
					.item(0);
			map.put("orientation", Integer.parseInt(orientationNode
					.getFirstChild().getNodeValue()));
            //封面图片
            Node coverPadNode=root.getElementsByTagName("coverPad").item(0);
            map.put("coverPad", coverPadNode.getFirstChild().getNodeValue());
            Node coverPhoneNode=root.getElementsByTagName("coverPhone").item(0);
            map.put("coverPhone", coverPhoneNode.getFirstChild().getNodeValue());
			// 页码,标题，描述
			NodeList pageNodeList = root.getElementsByTagName("page");
			for (int i = 0; i < pageNodeList.getLength(); i++) {
				Node page = pageNodeList.item(i);
				NodeList pageDetailNodeList = page.getChildNodes();
				IssueContents issueContent = new IssueContents();
				for (int j = 0; j < pageDetailNodeList.getLength(); j++) {
					Node pageDetail = pageDetailNodeList.item(j);
					if (pageDetail.getNodeName().equalsIgnoreCase("pageNo")) {
						issueContent.setPageno(Integer.parseInt(pageDetail
								.getFirstChild().getNodeValue()));
					}
					if (pageDetail.getNodeName().equalsIgnoreCase("title")) {
						issueContent.setTitle(pageDetail.getFirstChild()
								.getNodeValue());
					}
					if (pageDetail.getNodeName()
							.equalsIgnoreCase("description")) {
						issueContent.setDescription(pageDetail.getFirstChild()
								.getNodeValue());
					}
				}
				// issueContent.setIssueid(issueId);
				issueContent.setStatus(PojoConstant.ISSUECONTENTS.STATUS.SHOW
						.getCode());
				issueContentList.add(issueContent);
			}
            //广告信息
            NodeList adNodeList=root.getElementsByTagName("ad");
            if(adNodeList!=null&&adNodeList.getLength()>0)
            {
            	Date date = new Date();
            	for(int i = 0; i < adNodeList.getLength(); i++) {
                	MaAdLocation mal = new MaAdLocation();
                	mal.setType(new Byte("1"));
                	mal.setArticleId(id);
                	String keywords = null;
                    Node ad = adNodeList.item(i);
                    NodeList adDetailNodeList=ad.getChildNodes();
                    for(int j=0; j<adDetailNodeList.getLength();j++){
                    	Node adDetail = adDetailNodeList.item(j);
                    	
                    	//广告ID
                    	if(adDetail.getNodeName().equalsIgnoreCase("id")){
                    		mal.setAdId(adDetail.getFirstChild().getNodeValue());                    		
                    	}
                    	//页码
    					if(adDetail.getNodeName().equalsIgnoreCase("pageNo")){      
    						mal.setPageNo(new Integer(adDetail.getFirstChild().getNodeValue()));  
    					}
                    	//广告尺码
    					if(adDetail.getNodeName().equalsIgnoreCase("size")){
    						mal.setAdSize(adDetail.getFirstChild().getNodeValue());  
    					}
    					//广告关键字
    					if(adDetail.getNodeName().equalsIgnoreCase("keyword")){
    						keywords = adDetail.getFirstChild().getNodeValue();  
    					}
    					//设备区分
    					if(adDetail.getNodeName().equalsIgnoreCase("device")){
    						if(adDetail.getFirstChild().getNodeValue().equals("pad"))
    							mal.setDevice(new Byte("1"));  
    						else
    							mal.setDevice(new Byte("2")); 
    					}
                    }
                    this.maAdLocationService.saveAdLocation(mal, keywords);
                }
            }
		} catch (Exception e) {
			log.error("parse config file error", e);
		}

		map.put("content", issueContentList);
		return map;

	}

	// 文章信息
	public String articleInfoJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		Map article = this.lookArticleService.getLooArticleInfo(id);
		if (article == null) {
			this.jsonResult.setMessage("获取文章信息失败");
		} else {
			this.jsonResult.put("articleInfo", article);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 删除文章
	public String deleteArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.deleteLooArticle(id);
		if (r <= 0) {
			this.jsonResult.setMessage("删除失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 上架
	public String statusOnArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.changeLooArticleStatus(id,
				PojoConstant.Look.STATUS_ON);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	// 批量上架
	public String batStatusOnArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (StringUtil.isBlank(ids)) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.batChangeLooArticleStatus(ids,
				PojoConstant.Look.STATUS_ON);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 下架
	public String statusOffArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.changeLooArticleStatus(id,
				PojoConstant.Look.STATUS_OFF);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 审核通过
//	public String statusAcceptArticleJson() {
//		this.jsonResult = JsonResult.getFailure();
//		if (id == null || id <= 0) {
//			log.warn("ID为空");
//			this.jsonResult.setMessage("ID为空");
//			return JSON;
//		}
//		int r = this.lookArticleService.changeLooArticleStatus(id,
//				PojoConstant.Look.STATUS_ARTICLE2);
//		if (r <= 0) {
//			this.jsonResult.setMessage("操作失败");
//		} else {
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		}
//		return JSON;
//	}
	// 批量审核通过
//	public String batStatusAcceptArticleJson() {
//		this.jsonResult = JsonResult.getFailure();
//		if (StringUtil.isBlank(ids)) {
//			log.warn("ID为空");
//			this.jsonResult.setMessage("ID为空");
//			return JSON;
//		}
//		int r = this.lookArticleService.batChangeLooArticleStatus(ids,
//				PojoConstant.Look.STATUS_ARTICLE2);
//		if (r <= 0) {
//			this.jsonResult.setMessage("操作失败");
//		} else {
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		}
//		return JSON;
//	}

	// 审核未通过
//	public String statusRefuseArticleJson() {
//		this.jsonResult = JsonResult.getFailure();
//		if (id == null || id <= 0) {
//			log.warn("ID为空");
//			this.jsonResult.setMessage("ID为空");
//			return JSON;
//		}
//		int r = this.lookArticleService.changeLooArticleStatus(id,
//				PojoConstant.Look.STATUS_ARTICLE3);
//		if (r <= 0) {
//			this.jsonResult.setMessage("操作失败");
//		} else {
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		}
//		return JSON;
//	}

	// 置顶
	public String topArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.topArticle(id, 1);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 取消置顶
	public String cancelTopArticleJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.topArticle(id, 0);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 取消定时上架
	public String cancelPlanJson() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		this.jsonResult = this.lookArticleService.cancelPlan(id);
		return JSON;
	}
	// 得到文章的多看看
	public String getManyLook() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		List<LooManyLook> l = this.lookArticleService.getLooManyLookList(id);
		this.jsonResult.put("manyLookList", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	// 保存多看看
	public String saveManyLook() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null || id <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (sortOrder == null || sortOrder <= 0) {
			log.warn("排序为空");
			this.jsonResult.setMessage("排序为空");
			return JSON;
		}
		if (manyType == null || manyType <= 0) {
			log.warn("类型为空");
			this.jsonResult.setMessage("类型为空");
			return JSON;
		}
		if (StringUtil.isBlank(manyTitle)) {
			log.warn("名称为空");
			this.jsonResult.setMessage("名称为空");
			return JSON;
		}
		if (StringUtil.isBlank(manyUrl)) {
			log.warn("url为空");
			this.jsonResult.setMessage("url为空");
			return JSON;
		}
		LooManyLook ml = new LooManyLook();

		ml.setArticleId(id);
		if (manyLookId != null && manyLookId > 0)
			ml.setId(manyLookId);
		ml.setSortOrder(sortOrder);
		ml.setTitle(manyTitle);
		ml.setUrl(manyUrl);
		ml.setType(new Byte("" + manyType));
		int r = this.lookArticleService.saveLooManyLook(ml);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			if (manyLookId == null || manyLookId <= 0)
				this.jsonResult.put("manyLookId", r);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 删除多看看
	public String deleteManyLook() {
		this.jsonResult = JsonResult.getFailure();
		if (manyLookId == null || manyLookId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.deleteLooManyLook(manyLookId);
		if (r <= 0) {
			this.jsonResult.setMessage("删除失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 多看看上架下架
	public String changeStatusManyLook() {
		this.jsonResult = JsonResult.getFailure();
		if (manyLookId == null || manyLookId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookArticleService.changeLooManyLookStatus(manyLookId);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public File getSmallPic1() {
		return smallPic1;
	}

	public void setSmallPic1(File smallPic1) {
		this.smallPic1 = smallPic1;
	}

	public String getSmallPic1FileName() {
		return smallPic1FileName;
	}

	public void setSmallPic1FileName(String smallPic1FileName) {
		this.smallPic1FileName = smallPic1FileName;
	}

	public File getSmallPic2() {
		return smallPic2;
	}

	public void setSmallPic2(File smallPic2) {
		this.smallPic2 = smallPic2;
	}

	public String getSmallPic2FileName() {
		return smallPic2FileName;
	}

	public void setSmallPic2FileName(String smallPic2FileName) {
		this.smallPic2FileName = smallPic2FileName;
	}

	public File getArticleZip() {
		return articleZip;
	}

	public void setArticleZip(File articleZip) {
		this.articleZip = articleZip;
	}

	public String getArticleZipFileName() {
		return articleZipFileName;
	}

	public void setArticleZipFileName(String articleZipFileName) {
		this.articleZipFileName = articleZipFileName;
	}

	public Long getManyLookId() {
		return manyLookId;
	}

	public void setManyLookId(Long manyLookId) {
		this.manyLookId = manyLookId;
	}

	public String getManyTitle() {
		return manyTitle;
	}

	public void setManyTitle(String manyTitle) {
		this.manyTitle = manyTitle;
	}

	public String getManyUrl() {
		return manyUrl;
	}

	public void setManyUrl(String manyUrl) {
		this.manyUrl = manyUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getManyType() {
		return manyType;
	}

	public void setManyType(Integer manyType) {
		this.manyType = manyType;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}
}
