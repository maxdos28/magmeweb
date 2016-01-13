package cn.magme.web.action.phoenix;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jsoup.helper.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.magme.common.JsonResult;
import cn.magme.common.SystemProp;
import cn.magme.constants.WebConstant;
import cn.magme.constants.phoenix.PhoenixConstants;
import cn.magme.pojo.phoenix.PhoenixAd;
import cn.magme.pojo.phoenix.PhoenixArticle;
import cn.magme.pojo.phoenix.PhoenixCategory;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.result.phoenix.PhoenixArticleResult;
import cn.magme.service.IosPushService;
import cn.magme.service.phoenix.PhoenixAdService;
import cn.magme.service.phoenix.PhoenixArticleService;
import cn.magme.service.phoenix.PhoenixCategoryService;
import cn.magme.service.phoenix.PhoenixOrderService;
import cn.magme.util.FileOperate;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 新闻 Action
 * @author xiaochen
 * @since 2013-05-13
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/contentManager.ftl")
,@Result(name="articleUploadJson",type="json",params={"root","jsonResult","contentType","text/html;"})
,@Result(name="down_success",type="stream",params={"contentType","application/vnd.android.package-archive","bufferSize","2048",
		"inputName","inputStream","contentDisposition","attachment;filename=\"phoenix.apk\""})
,@Result(name="download_redirect",type="redirect",location="http://")})
public class PhoenixArticleAction extends BaseAction{
	private static final long serialVersionUID = 3818255961847376016L;
	private static final Logger log = Logger.getLogger(PhoenixArticleAction.class);
	
	@Resource
	private PhoenixArticleService phoenixArticleService;
	
	@Resource
	private  PhoenixOrderService phoenixOrderService;
	@Resource
	private  PhoenixCategoryService phoenixCategoryService;
	@Resource
	private SystemProp systemProp;
	@Resource
	private PhoenixAdService phoenixAdService;
	@Resource
	private IosPushService iosPushService;
	
	private ByteArrayInputStream inputStream;

	private Long id;
	private Long appId;
	private Long categoryId;
	private Integer isFree;
	private Integer orientation;
	private Integer begin;
	private Integer size;
	private long currentPage;
    private long pageCount;
    private long rowCount;
	private final Long pageSize = 10L;
	private Integer isCoverStory;
	private Integer coverStorySort;
	//文章查询条件
	private Integer status;
	private String fromDate;
	private String endDate;
	
	
	/**
	 * 设备号
	 */
	private String deviceNo;
	
	private String title;
	private String description;
	
	private File articleZip;
	private File articleImg;
    //上传文件名.
    private String articleZipFileName;
    private String articleImgFileName;
    //上传文件类型.
    private String articleZipFileContentType;
    private String articleImgFileContentType;
    
	
	public String content(){
		
		return SUCCESS;
	}
	
	public String download(){
		String appIdStr=(this.appId==null || this.appId<=0)?"901":String.valueOf(this.appId);
		String downloadxml=this.systemProp.getStaticLocalUrl()+File.separator+"phoenix"
		+File.separator+"apk"+File.separator+appIdStr+File.separator+"version.xml";
		try {
			DocumentBuilderFactory fac=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=fac.newDocumentBuilder();
			Document doc=db.parse(downloadxml);
			Element elem=doc.getDocumentElement();
			NodeList nodes=elem.getElementsByTagName("url");
			String url=null;
			//其实只有一个url元素
			for(int i=0;i<nodes.getLength();i++){
				Node d=nodes.item(i);
				url=d.getTextContent();
			}
			if(url!=null){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/vnd.android.package-archive");
				response.sendRedirect(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		//download();
	}
	
	/*public String download(){
		try {
			InputStream f=new FileInputStream(this.systemProp.getStaticLocalUrl()+File.separator+"download"+File.separator+"phoenix.apk");
			byte [] b=new byte[f.available()];
			f.read(b);
			this.inputStream=new ByteArrayInputStream(b);;
		} catch (Exception e) {
			log.error("", e);
		}
		return "down_success";
	}*/
	
	@Override
	//频道新闻接口
	public String execute()  {
		this.jsonResult=JsonResult.getFailure();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		if(StringUtil.isBlank(this.deviceNo)){
			log.error("deviceNo不能为空");
			this.jsonResult.setMessage("deviceNo不能为空");
			return JSON;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("appId", appId);
		map.put("categoryId", categoryId);
		map.put("isFree", isFree);
		map.put("status", 1);
		map.put("orientation", orientation);
		map.put("begin", begin);
		map.put("size", size);
		map.put("isCoverStory", this.isCoverStory);
		try{
			List<PhoenixArticle> list =  phoenixArticleService.query(map);
			list=this.addPaidInfo(list, appId, deviceNo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("list", list);
		}catch (Exception e) {
			log.error("PhoenixArticleAction.class", e);
		}
		
		return JSON;
	}
	
	//查询设置封面故事的文章,包括广告,1文章+1广告方式返回
	public String queryCoverArticle()
	{
		this.jsonResult=JsonResult.getFailure();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("appId", appId);
		map.put("status", 1);
		map.put("isCoverStory", 1);
		try{
			List<PhoenixArticle> list =  phoenixArticleService.query(map);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			List<PhoenixAd> adList = this.phoenixAdService.getAllPhoenixAd(appId);
			List<Map> l = new ArrayList();
			//只有广告情况
			if(list==null||list.size()==0)
			{
				if(adList!=null&&adList.size()>0)
				{
					for(int i=0;i<adList.size();i++)
					{
						PhoenixAd ad = adList.get(i);
						Map m2 = new HashMap();
						m2.put("title", ad.getTitle());
						m2.put("imgUrl", ad.getPicPath());
						m2.put("link", ad.getLink());
						m2.put("type", "ad");
						l.add(m2);
					}
				}
				this.jsonResult.put("list", l);
				return JSON;
			}
			for(int i=0;i<list.size();i++)
			{
				PhoenixArticle a = list.get(i);
				Map m = new HashMap();
				m.put("title", a.getTitle());
				m.put("imgUrl", a.getImgUrl());
				m.put("id", a.getId());
				m.put("type", "article");
				l.add(m);
				//加入广告
				if(adList!=null&&adList.size()>0&&i<adList.size())
				{
					PhoenixAd ad = adList.get(i);
					Map m2 = new HashMap();
					m2.put("title", ad.getTitle());
					m2.put("imgUrl", ad.getPicPath());
					m2.put("link", ad.getLink());
					m2.put("type", "ad");
					l.add(m2);
				}
			}
			//处理广告更多的情况
			if(adList!=null&&adList.size()>0&&5<adList.size())
			{
				for(int i=5;i<adList.size();i++)
				{
					PhoenixAd ad = adList.get(i);
					Map m2 = new HashMap();
					m2.put("title", ad.getTitle());
					m2.put("imgUrl", ad.getPicPath());
					m2.put("link", ad.getLink());
					m2.put("type", "ad");
					l.add(m2);
				}
			}
			
			this.jsonResult.put("list", l);
		}catch (Exception e) {
			log.error("PhoenixArticleAction.class", e);
		}
		
		return JSON;
	}
	
	/**
	 * 按照条件查询文章，返回结果只有id
	 * @return
	 */
	public String queryJustIdJson(){
		this.jsonResult=JsonResult.getFailure();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		if(StringUtil.isBlank(this.deviceNo)){
			log.error("deviceNo不能为空");
			this.jsonResult.setMessage("deviceNo不能为空");
			return JSON;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("appId", appId);
		map.put("categoryId", categoryId);
		map.put("isFree", isFree);
		map.put("status", 1);
		map.put("orientation", orientation);
		map.put("begin", begin);
		map.put("size", size);
		map.put("isCoverStory", this.isCoverStory);
		try{
			List<Map> list =  phoenixArticleService.queryJustId(map);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			if(list==null||list.size()==0)
			{
				return JSON;
			}
			List<Long> idList = new ArrayList();
			List<Integer> pageNumList = new ArrayList();
			for(Map m:list)
			{
				idList.add((Long)m.get("id"));
				pageNumList.add((Integer)m.get("pageNum"));
			}
			this.jsonResult.put("list", idList);
			this.jsonResult.put("page", pageNumList);
		}catch (Exception e) {
			log.error("PhoenixArticleAction.class", e);
		}
		return JSON;
	}
	
	//后台查询内容
	public String searchArticle()
	{
		this.jsonResult=JsonResult.getFailure();
		getSessionAppId();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("categoryId", categoryId);
		map.put("isFree", isFree);
		if(status!=null&&status>0)
			map.put("status", status);
		else
			map.put("auditedStatus", 1);
		map.put("fromDate", fromDate);
		map.put("endDate", endDate);
		map.put("title", title);
		map.put("orientation", orientation);
		rowCount = phoenixArticleService.count(map);
		countPage() ;
		map.put("begin", (currentPage-1)*pageSize);
		map.put("size", pageSize);
		try{
			List<PhoenixArticle> list =  phoenixArticleService.query(map);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("list", list);
			this.jsonResult.put("currentPage", currentPage);
			this.jsonResult.put("pageCount", pageCount);
		}catch (Exception e) {
			log.error("PhoenixArticleAction.class", e);
			e.printStackTrace();
		}
		
		return JSON;
	}
	

	private void countPage() {
		if (rowCount % this.pageSize == 0)
			pageCount = rowCount / this.pageSize;
		else
			pageCount = rowCount / this.pageSize + 1;// 总页数
		if(currentPage>pageCount)
			currentPage = pageCount;
		if(currentPage<=0)
			currentPage = 1;
	}
	//得到session的appId
	private void getSessionAppId() {
		PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
		appId = phoenixUser.getAppId();
	}
	
	public String index(){
		this.jsonResult=JsonResult.getFailure();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		if(StringUtil.isBlank(this.deviceNo)){
			log.error("deviceNo不能为空");
			this.jsonResult.setMessage("deviceNo不能为空");
			return JSON;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("isFree", isFree);
		try{
			List<PhoenixArticleResult> list =  phoenixArticleService.queryGroupCategory(map);
			list=this.addPaidInfoByPhoenixArticleResult(list, appId, deviceNo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("list", list);
		}catch (Exception e) {
			log.error("PhoenixArticleAction.class", e);
			e.printStackTrace();
		}
		return JSON;
	}
	
	/**
	 * 增加文章接口
	 * @author fredy
	 * @return
	 */
	public String addArticleJson(){

		this.jsonResult=JsonResult.getFailure();
		getSessionAppId();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return "articleUploadJson";
		}
		if(cn.magme.util.StringUtil.isBlank(title)){
			log.warn("标题为空");
			this.jsonResult.setMessage("标题为空");
			return "articleUploadJson";
		}
		if(cn.magme.util.StringUtil.isBlank(description)){
			log.warn("描述为空");
			this.jsonResult.setMessage("描述为空");
			return "articleUploadJson";
		}
		if(categoryId == null || categoryId <= 0){
			log.warn("栏目id(categoryId)小于0");
			this.jsonResult.setMessage("栏目id(categoryId)小于0");
			return "articleUploadJson";
		}
		PhoenixCategory pc = phoenixCategoryService.queryById(categoryId);
		if(pc==null)
		{
			log.warn("栏目没找到");
			this.jsonResult.setMessage("栏目没找到");
			return "articleUploadJson";
		}
		if(articleZip == null){
			log.warn("zip包为空");
			this.jsonResult.setMessage("zip包为空");
			return "articleUploadJson";
		}
		if(cn.magme.util.StringUtil.isBlank(articleZipFileName))
		{
			log.warn("zip包名称为空");
			this.jsonResult.setMessage("zip包名称为空");
			return "articleUploadJson";
		}
		if(!(articleZipFileName.toLowerCase().endsWith(".zip")||articleZipFileName.toLowerCase().endsWith(".mpres")))
		{
			log.warn("zip包文件类型不正确");
			this.jsonResult.setMessage("zip包文件类型不正确");
			return "articleUploadJson";
		}

		if(articleImg==null)
		{
			log.warn("请上传图片");
			this.jsonResult.setMessage("请上传图片");
			return "articleUploadJson";
		}
		if(articleImg!=null&&articleImgFileName!=null&&!articleImgFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("图片类型不正确");
			this.jsonResult.setMessage("图片类型不正确");
			return "articleUploadJson";
		}
		//保存文章
		PhoenixArticle pa = new PhoenixArticle();
		pa.setAppId(appId);
		pa.setCategoryId(categoryId);
		pa.setDescription(description);
		pa.setTitle(title);
		pa.setIsFree(pc.getIsFree());
		pa.setIsPush(0);
		//默认2
		pa.setIsCoverStory(0);
		pa.setCoverStorySort(0);
		//pa.setStatus(1);
		//2013-6-7 jasper 默认为2，表示未审核
		pa.setStatus(2);
		pa.setTotalPages(1);
		Long articleId = phoenixArticleService.insert(pa);
		if(articleId == null||articleId<=0){
			log.warn("保存失败");
			this.jsonResult.setMessage("保存失败");
			return "articleUploadJson";
		}
		id = articleId;
		pa.setId(articleId);
		//保存ZIP和图片
		String staticPath = systemProp.getStaticLocalUrl();
		String relativePath = "/appprofile/" +"app"+this.appId + File.separator + articleId
				+ File.separator;
		//旧路径
//		String staticPath = systemProp.getPhoenixFilePath();
//		String relativePath = this.appId + File.separator + articleId
//				+ File.separator;
		File dir = new File(staticPath + relativePath);
		if (!dir.exists())
			dir.mkdirs();
        //图片文件保存
        if(articleImg!=null)
        {
        	//文章图片ID，文章ID+－+时间
        	String fileName = relativePath+articleId+"-"+System.nanoTime()+".jpg";
        	File imgFile = new File(staticPath+fileName);
            FileOperate.copyFile(articleImg.getAbsolutePath(),imgFile.getAbsolutePath());
            pa.setImgUrl(fileName.replaceAll("\\\\", "/"));
    		//旧路径
            //pa.setImgUrl("/phoenix/article/"+fileName.replaceAll("\\\\", "/"));
            
        }
		//zip文件保存
        int p = unzip(staticPath + relativePath, dir);
		if(p==0)
			return "articleUploadJson";
		// 如果是新增则直接解压缩
		pa.setSharePath(this.systemProp.getStaticServerUrl()+"/appprofile/" + relativePath.replaceAll("\\\\", "/")+"1/phone.html");
		pa.setDownPath(this.systemProp.getStaticServerUrl()+"/appprofile/" + relativePath.replaceAll("\\\\", "/")+ articleId + ".mpres");
		pa.setSize("" + this.articleZip.length());
		pa.setPageNum(p);
		//更新文章
        phoenixArticleService.update(pa);
        this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
        this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return "articleUploadJson";
	}
	/**
	 * 删除文章接口
	 * @author fredy
	 * @return
	 */
	public String delArticleJson(){
		this.jsonResult=JsonResult.getFailure();
		if(id==null || id<=0){
			this.jsonResult.setMessage("id必须大于0");
			return JSON;
		}
		if(this.phoenixArticleService.delById(id)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	/**
	 * 给文章增加付款信息
	 * @return
	 */
	private List<PhoenixArticle> addPaidInfo(List<PhoenixArticle> articleList,Long appId,String deviceNo){
		if(articleList==null || articleList.size()<=0){
			return articleList;
		}
		PhoenixUser u=this.getSessionPhoenixUser();
		if(u!=null && u.getType()==PhoenixConstants.PHOENIX_USER.TYPE_SPECIAL_USER){//超级账号，所有都是可以读写的
			for(PhoenixArticle article:articleList){
				article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_YES);
			}
		}else{
			Set<Long> paidCategory=phoenixOrderService.queryPaidCateSet(appId, deviceNo);
			boolean purchaseLessOne=(paidCategory!=null && paidCategory.size()>0);
			for(PhoenixArticle article:articleList){
				//只要有购买记录，那么会赠送一个封面故事
				if(article.getIsFree()==PhoenixConstants.PHOENIX_ARTICLE.IS_FREE_NO){
					//赠送类目,如果已经付费了购买了，那么赠送收费的5w类目
					if(purchaseLessOne && article.getRecommend()==PhoenixConstants.PHOENIX_ARTICLE.RECOMMEND_YES){
						article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_YES);
					//已经付费的类目
					}else if(purchaseLessOne && paidCategory.contains(article.getCategoryId())){
						article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_YES);	
					}else{
				    //收费类目，但是没有付费
						article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_NO);
					}
				}else{
					//在免费模式下，是否购买是没有意义的
					article.setIsPurchase(null);
				}
			}
		}
		return articleList;
	}
	/**
	 * 给频道增加付款信息
	 * @param phoenixArticleResultList
	 * @param appId
	 * @param deviceNo
	 * @return
	 */
	private List<PhoenixArticleResult> addPaidInfoByPhoenixArticleResult(List<PhoenixArticleResult> phoenixArticleResultList,Long appId,String deviceNo){
		if(phoenixArticleResultList==null || phoenixArticleResultList.size()<=0){
			return phoenixArticleResultList;
		}
		PhoenixUser u=this.getSessionPhoenixUser();
		if(u!=null && u.getType()==PhoenixConstants.PHOENIX_USER.TYPE_SPECIAL_USER){//超级账号，所有都是可以读写的
			for(PhoenixArticleResult articleRes:phoenixArticleResultList){
				articleRes.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_YES);
			}
		}else{
			Set<Long> paidCategory=phoenixOrderService.queryPaidCateSet(appId, deviceNo);
			boolean purchaseLessOne=(paidCategory!=null && paidCategory.size()>0);
			for(PhoenixArticleResult article:phoenixArticleResultList){
				//只要有购买记录，那么会赠送一个封面故事
				if(article.getIsFree()==PhoenixConstants.PHOENIX_ARTICLE.IS_FREE_NO){
					if(purchaseLessOne && article.getRecommend()==PhoenixConstants.PHOENIX_ARTICLE.RECOMMEND_YES){
						article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_YES);	
					}else if(purchaseLessOne && paidCategory.contains(article.getCategoryId())){
						article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_YES);	
					}else{
						article.setIsPurchase(PhoenixConstants.PHOENIX_ARTICLE.IS_PURCHASE_NO);
					}
				}else{
					//在免费模式下，是否购买是没有意义的
					article.setIsPurchase(null);
				}
			}
			
		}
		return phoenixArticleResultList;
	}
	
	
	
	/**
	 * 修改文章接口
	 * @return
	 */
	public String updateArticleJson(){

		this.jsonResult=JsonResult.getFailure();
		getSessionAppId();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return "articleUploadJson";
		}
		if(id==null||id<=0)
		{
			log.warn("文章ID为空");
			this.jsonResult.setMessage("文章ID为空");
			return "articleUploadJson";
		}
		if(articleZip!=null&&!(articleZipFileName.toLowerCase().endsWith(".zip")||articleZipFileName.toLowerCase().endsWith(".mpres")))
		{
			log.warn("zip包文件类型不正确");
			this.jsonResult.setMessage("zip包文件类型不正确");
			return "articleUploadJson";
		}
		if(articleImg!=null&&articleImgFileName!=null&&!articleImgFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("图片类型不正确");
			this.jsonResult.setMessage("图片类型不正确");
			return "articleUploadJson";
		}
		PhoenixCategory pc = phoenixCategoryService.queryById(categoryId);
		Map param = new HashMap();
		param.put("id", id);
		PhoenixArticle pa = phoenixArticleService.queryById(param);
		pa.setTitle(title);
		pa.setCategoryId(categoryId);
		if(pc!=null)
			pa.setIsFree(pc.getIsFree());
		pa.setDescription(description);
		if(isCoverStory!=null)
		pa.setIsCoverStory(isCoverStory);
		if(coverStorySort!=null)
		pa.setCoverStorySort(coverStorySort);
		//保存ZIP和图片
		//String staticPath = systemProp.getMagicEditorPath();
		String staticPath = systemProp.getPhoenixFilePath();
		//String relativePath = "app"+this.appId + File.separator + id
		//		+ File.separator;
		String relativePath = this.appId + File.separator + id
						+ File.separator;
		File dir = new File(staticPath + relativePath);
		if (!dir.exists())
			dir.mkdirs();
		if(articleImg!=null)
		{
			//删除旧图片
			String imgUrl = pa.getImgUrl();
			if(imgUrl!=null)
			{
				File oldFile = new File(staticPath + relativePath+imgUrl.substring(imgUrl.lastIndexOf("/")+1));
				if(oldFile.exists())
					oldFile.delete();
			}
        	//文章图片ID，文章ID+－+时间
        	String fileName = relativePath+id+"-"+System.nanoTime();
			File imgFile = new File(staticPath + fileName+".jpg");
            FileOperate.copyFile(articleImg.getAbsolutePath(),imgFile.getAbsolutePath());
            //pa.setImgUrl("/appprofile/" +fileName.replaceAll("\\\\", "/") + ".jpg");

            pa.setImgUrl("/phoenix/article/"+fileName.replaceAll("\\\\", "/"));
		}
		if(articleZip!=null)
		{
			int p = unzip(staticPath + relativePath, dir);
			if(p==0)
				return "articleUploadJson";
			// 如果是新增则直接解压缩
			pa.setSharePath(this.systemProp.getStaticServerUrl()+"/appprofile/" + relativePath.replaceAll("\\\\", "/")+"1/phone.html");
			pa.setDownPath(this.systemProp.getStaticServerUrl()+"/appprofile/" + relativePath.replaceAll("\\\\", "/")+ id + ".mpres");
			pa.setSize("" + this.articleZip.length());
			pa.setPageNum(p);
		}
		
		//更新文章
        phoenixArticleService.update(pa);
        this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
        this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return "articleUploadJson";
	}
	
	/**
	 * 审核文章
	 * @return
	 */
	public String auditedArticleJson(){

		this.jsonResult=JsonResult.getFailure();
		getSessionAppId();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		if(id==null||id<=0)
		{
			log.warn("文章ID为空");
			this.jsonResult.setMessage("文章ID为空");
			return JSON;
		}
		
		PhoenixArticle pa = new PhoenixArticle();
		pa.setId(id);
		pa.setStatus(1);		
		//更新文章状态
        phoenixArticleService.update(pa);
        this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
        this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 推送文章消息
	 * @return
	 */
	public String pushMessageJson(){

		this.jsonResult=JsonResult.getFailure();
		getSessionAppId();
		if(appId == null || appId <= 0){
			log.warn("应用id(appId)小于0");
			this.jsonResult.setMessage("应用id(appId)小于0");
			return JSON;
		}
		if(id==null||id<=0)
		{
			log.warn("文章ID为空");
			this.jsonResult.setMessage("文章ID为空");
			return JSON;
		}
		Map pm = new HashMap();
		pm.put("id", id);
		PhoenixArticle pa = this.phoenixArticleService.queryById(pm);
		if(pa==null)
		{
			log.warn("找不到文章");
			this.jsonResult.setMessage("找不到文章");
			return JSON;
		}
		List<String> args = new ArrayList();
		args.add(""+id);
		args.add(""+pa.getPageNum());
//		args.add(""+pa.getCategoryId());
//		args.add(""+pa.getTitle());
		int k = iosPushService.iosPushMessage(0,this.getSessionPhoenixUser().getId(), pa.getTitle(),args,2,new Long[]{appId});
		if(k==0){
			this.jsonResult.setMessage("发送失败");
			return JSON;
		}
		//更新推送状态
		PhoenixArticle pa2 = new PhoenixArticle();
		pa2.setId(id);
		pa2.setIsPush(1);
		this.phoenixArticleService.update(pa2);
        this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
        this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	//保存ZIP并解压
	private int unzip(String path, File dir) {
		int pageSize = 0;// 总页数
		//zip文件保存
		File zipFile = new File(path+id+".zip");
		FileOperate.copyFile(articleZip.getAbsolutePath(),zipFile.getAbsolutePath());
		//解压ZIP
		try {
			//解压根zip包
			FileOperate.unzipFile(articleZip.getAbsolutePath(), path,false);
			//解压解压后的资源文件
			File [] fList=dir.listFiles();
		    for(File ff:fList){
		    	if(ff.isDirectory()||!ff.getName().toLowerCase().endsWith(".zip"))
					continue;
			   if(ff.getName().indexOf("assets")>0){
				   FileOperate.unzipFile(ff.getAbsolutePath(),path,true);
					pageSize++;
			   }
			 //把手机的html页面解压出来,作为分享的路径
				//由于pubassets路径问题,解压出来的路径要放到magic.editor.path下,和互动杂志使用相同的
				if (ff.getName().toLowerCase().indexOf("phone") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
				}
				if (ff.getName().toLowerCase().indexOf("pad") > 0) {
					FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
				}
				//旧的ZIP格式也要解压缩
				if (ff.getName().equals("1.zip")) {
					FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
				}
		    }
		    //重命名解压后的资源文件
		    File [] assetsFolderList=dir.listFiles();
		    int assetsIndex=0;
		    for(File ff:assetsFolderList){
		    	assetsIndex=ff.getName().indexOf("assets");
		    	if(ff.isDirectory() && assetsIndex>0){
		    		FileOperate.moveFolder(ff.getAbsolutePath(), path+ff.getName().substring(0,assetsIndex));
		    	}
		    }
		    //zipFile.delete();
		} catch (IOException e) {
			log.error("", e);
			this.jsonResult.setMessage("unzip file error");
			return 0;
		}
		return pageSize;
	}
	
	 public void addActionError(String anErrorMessage){
		   String s=anErrorMessage;
		   System.out.println(s);
		  }
		  public void addActionMessage(String aMessage){
		   String s=aMessage;
		   System.out.println(s);
		  
		  }
		  public void addFieldError(String fieldName, String errorMessage){
		   String s=errorMessage;
		   String f=fieldName;
		   System.out.println(s);
		   System.out.println(f);
		  
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPhoenixArticleService(PhoenixArticleService phoenixArticleService) {
		this.phoenixArticleService = phoenixArticleService;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public File getArticleZip() {
		return articleZip;
	}

	public void setArticleZip(File articleZip) {
		this.articleZip = articleZip;
	}

	public File getArticleImg() {
		return articleImg;
	}

	public void setArticleImg(File articleImg) {
		this.articleImg = articleImg;
	}

	public String getArticleZipFileName() {
		return articleZipFileName;
	}

	public void setArticleZipFileName(String articleZipFileName) {
		this.articleZipFileName = articleZipFileName;
	}

	public String getArticleImgFileName() {
		return articleImgFileName;
	}

	public void setArticleImgFileName(String articleImgFileName) {
		this.articleImgFileName = articleImgFileName;
	}

	public String getArticleZipFileContentType() {
		return articleZipFileContentType;
	}

	public void setArticleZipFileContentType(String articleZipFileContentType) {
		this.articleZipFileContentType = articleZipFileContentType;
	}

	public String getArticleImgFileContentType() {
		return articleImgFileContentType;
	}

	public void setArticleImgFileContentType(String articleImgFileContentType) {
		this.articleImgFileContentType = articleImgFileContentType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCoverStorySort(Integer coverStorySort) {
		this.coverStorySort = coverStorySort;
	}

	public void setIsCoverStory(Integer isCoverStory) {
		this.isCoverStory = isCoverStory;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	

	
}
