package cn.magme.web.action.sns;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.Article;
import cn.magme.pojo.CreativeArticleRel;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.CreativeCategoryRel;
import cn.magme.pojo.CreativeNeteaseCategoryRel;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.sns.ArticleEx;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.sns.ArticleExService;
import cn.magme.service.sns.ArticleService;
import cn.magme.service.sns.CreativeArticleRelService;
import cn.magme.service.sns.CreativeCategoryRelService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.sns.CreativeNeteaseCategoryRelService;
import cn.magme.service.sns.CreativeService;
import cn.magme.util.StringUtil;
import cn.magme.util.UploadPictureUtil;
import cn.magme.web.action.BaseAction;

import com.danga.MemCached.MemCachedClient;
import com.opensymphony.xwork2.ActionContext;

 /**
  * 作品发布
  * @author fredy
  * @since 2012-12-10
  */
@Results({@Result(name="success",location="/WEB-INF/pages/sns/articlePub.ftl"),
	@Result(name="preview",location="/WEB-INF/pages/sns/articlePubPreview.ftl"),
	@Result(name = "index", location = "/WEB-INF/pages/index.ftl")})
public class ArticlePubAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1836324182356335820L;
	@Resource
	private CreativeService creativeService;
	@Resource
	private CreativeCategoryService creativeCategoryService;
	//@Resource
	private MemCachedClient memCachedClient;
	@Resource
	private CreativeCategoryRelService creativeCategoryRelService;
	@Resource
	private ArticleService articleService;
	@Resource
	private CreativeArticleRelService creativeArticleRelService;
	@Resource
	private ArticleExService articleExService; 
	
	@Resource
	private IssueService issueService;
	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private CreativeNeteaseCategoryRelService creativeNeteaseCategoryRelService;
	
	private static final String IMAG_PREFIX="/snsimg";
	
	private static final Logger log=Logger.getLogger(ArticlePubAction.class);
	public static final String TYPE_SET_SUCCESS="typeset_success";
	private final String operate="works";
	
	/**
	 * 模板选择图片分界线
	 */
	public static final int TEMPLATE_PIC_COUNT=10;
	
	/**
	 * 模板选择字数分界线
	 */
	public static final int TEMPLATE_WORD_COUNT=50;
	
	/**
	 * 模板选择字数分界线
	 */
	public static final int TAG_MAX_LENGTH=16;
	/**
	 * 文字字数模板的key
	 */
	public static final String WORD_COUNT_TEMP="wordCountTemp";
	/**
	 * 图片数量模板的key
	 */
	public static final String IMG_COUNT_TEMP="imgCountTemp";
	/**
	 * 
	 * 发布作品的初始页面
	 */
	public String execute(){
		this.initData();
		this.id=null;//发布作品，设置id为null
		this.creativeId=null;//发布作品，设置id为null
		//golf用户
		if(this.getSessionUser().getUserLevel()==3){
			creativeCategoryTree=creativeCategoryService.queryCategoryGolfTree();
		}else if(this.getSessionUser().getUserLevel()==1){
			//认证编辑
			creativeCategoryTree=creativeCategoryService.queryCategoryTree();
		}
		neteaseCcList=this.creativeCategoryService.queryByType(2);
		
		return SUCCESS;
	}
	
	/**
	 * 修改作品
	 * @return
	 */
	public String edit(){
		Long tempCreativeId=this.creativeId;
        this.initData();//初始化数据
		
		//20130111认证编辑默认仅自己可见
		if(this.userLevel==1){
			this.ploy=1;
		}
		//为了兼容按照creativeId编辑和articleId编辑的通用性
		if(tempCreativeId!=null){
			this.creativeId=null;
			List<Article> tempCreativeList=this.articleService.queryByCreativeId(tempCreativeId);
			if(tempCreativeList==null || tempCreativeList.size()<=0){
				this.jsonResult=JsonResult.getFailure();
				this.jsonResult.setMessage("作品没有对应的文章,creativeId:"+tempCreativeId);
				return JSON;
			}else{
				id=tempCreativeList.get(0).getId();
			}
		}
		
		
		this.initData();
        this.creativeId=null;
        //修改
        if(id!=null){
        	if(id<=0){
        		throw new RuntimeException("文章id不合法:id"+id);
        	}else{
        		this.article=this.articleService.queryById(id);
        		//非正常作品是不能编辑的
        		if(article==null || this.article.getStatus()!=PojoConstant.ARTICLE.STATUS_OK ){
        			log.error("文章处于无效状态，或者文章不存在:id"+id);
        			return "index";
        		}
        		if(this.getSessionUserId().longValue()!=this.article.getUserId()){
        			log.error("不能编辑别人的文章,文章id:"+id+",userid:"+this.getSessionUserId());
        			return "index";
        		}
        		CreativeArticleRel creativeArticleRel=this.creativeArticleRelService.queryByArticleId(id);
        		if(creativeArticleRel!=null){
        			tempCreative=this.creativeService.queryById(creativeArticleRel.getCreativeId());
            		if(tempCreative==null || tempCreative.getUserId()==null || tempCreative.getUserId().longValue()!=this.getSessionUserId()){
            			log.error("编辑文章失败，只能编辑自己的文章,articleId;"+this.id);
            			return "index";
            		}
        		}else{
        			log.error("编辑文章失败，作品文章映射关系不存在,articleId;"+this.id);
        			return "index";
        		}
        		this.articleInCreative=this.articleService.queryByCreativeId(creativeArticleRel.getCreativeId());
        		neteaseCcList=this.creativeCategoryService.queryByType(2);
        		neteaseCncrList = this.creativeNeteaseCategoryRelService.queryByCid(creativeArticleRel.getCreativeId());
        	}
        }
		return SUCCESS;
	}
	/**
	 * 给作品增加文章
	 * @return
	 */
	public String addActicle(){
		this.initData();
		this.id=null;
		if(this.getSessionUser().getUserLevel()==null || this.getSessionUser().getUserLevel()!=1){
			throw new RuntimeException("只有认证编辑能够给作品增加文章");
		}
		if(this.creativeId==null || this.creativeId<=0){
			throw new RuntimeException("作品id不合法:id");
		}
		
		tempCreative=this.creativeService.queryById(creativeId);
		if(tempCreative==null || tempCreative.getStatus()==null  || tempCreative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK){
			throw new RuntimeException("作品处于无效状态，或者作品不存在,不能给作品增加文章:作品id"+creativeId);
		}
		
		if(tempCreative.getUserId()==null || tempCreative.getUserId().longValue()!=this.getSessionUserId()){
			throw new RuntimeException("作品不属于这个作者，不能给这个作品增加文章:作品id"+creativeId+",userid:"+this.getSessionUserId());
		}
		
		List<Article> articleList=this.articleService.queryByCreativeId(creativeId);
		if(articleList==null || articleList.size()<=0){
			throw new RuntimeException("作品至少得有一个文章，没有，因此不能给作品增加文章:作品id"+creativeId);
		}
		this.articleInCreative=this.articleService.queryByCreativeId(creativeId);
		return SUCCESS;
	}
	
	
	/**
	 * 发布作品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String pub(){
		this.jsonResult=JsonResult.getFailure();
		this.userLevel=this.getSessionUser().getUserLevel();
		// 从缓存或者session中读取数据
		List<ArticleEx> removeExList=null;
		if(this.memCachedClient!=null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, CacheConstants.CACHE_ONE_HOUR);//1小时后失效
			String tempId=this.getSessionJsessionid()+this.getSessionUserId();
			article=(Article)this.memCachedClient.get(CacheConstants.ARTICLE_MAKE_CACHE_PREFIX+tempId);
			articleExList=(List<ArticleEx>)this.memCachedClient.get(CacheConstants.ARTICLE_EX_MAKE_CACHE_PREFIX+tempId);
			categoryIds=(List<Long>)this.memCachedClient.get(CacheConstants.ARTICLE_MAKE_CATEGORY_CACHE_PREFIX+tempId);
			neteaseCategoryIds=(String)this.memCachedClient.get(CacheConstants.ARTICLE_NETEASE_CACHE_PREFIX+tempId);
			picCollectionIds=(List<Long>)this.memCachedClient.get(CacheConstants.ARTICLE_MAKE_CATEGORY_PIC_CACHE_PREFIX+tempId);
			creative=(Creative)memCachedClient.get(CacheConstants.ARTICLE_MAKE_CACHE_CREATIVE_PREFIX+tempId);
			removeExList=(List<ArticleEx>)this.memCachedClient.get(CacheConstants.REMOVE_ARTICLE_EX_MAKE_CACHE_PREFIX+tempId);
			tempCreative=(Creative)memCachedClient.get(CacheConstants.ARTICLE_MAKE_CACHE_CREATIVE_EDIT_SE_PREFIX+tempId);
		}else{
        	article=(Article)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE+this.getSessionUserId());
        	articleExList=(List<ArticleEx>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX+this.getSessionUserId());
        	categoryIds=(List<Long>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS+this.getSessionUserId());
        	neteaseCategoryIds=(String)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_NETEASE_CATEGORYIDS+this.getSessionUserId());
        	picCollectionIds=(List<Long>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS+this.getSessionUserId());
        	creative=(Creative)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_CACHE_CREATIVE+this.getSessionUserId());
        	tempCreative=(Creative)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_CACHE_CREATIVE_EDIT_SE+this.getSessionUserId());
        	removeExList=(List<ArticleEx>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX_REMOVE+this.getSessionUserId());
        }
        
		if(this.article==null){
			this.jsonResult.setMessage("发布出错，重复发布");
			return JSON;
		}
		
        if(StringUtil.isNotBlank(this.template)){
        	this.article.setTemplate(this.template);
        }
		//this.jsonResult=this.creativeService.pubCreative(creative, creativeExList, categoryIds, picCollectionIds);
        this.jsonResult=this.articleService.pubCreative(article, creative, articleExList, categoryIds, picCollectionIds,this.userLevel,removeExList,tempCreative,this.neteaseCategoryIds);
        this.deleteCacheOrSession();
		return JSON;
	}
	
	
	
	
	//获取作品内容
	//TODO 
	public String getEditArticleEx(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		CreativeArticleRel rel=this.creativeArticleRelService.queryByArticleId(id);
		Creative c=this.creativeService.queryById(rel.getCreativeId());
		List<ArticleEx> list = articleExService.getArticleEx(id);
		this.creativeCategoryRelList=creativeCategoryRelService.queryByCid(this.creativeId);
		this.jsonResult.put("list", list);
		this.jsonResult.put("creativeCategoryRelList", creativeCategoryRelList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 作品制作，把作品数据保存在session中，这就意味着一次一个用户只能制作一个作品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String makeJson(){
		//默认失败
		this.jsonResult=JsonResult.getFailure();
		this.initData();//初始化数据
		
		//20130111认证编辑默认仅自己可见
		if(this.userLevel==1){
			this.ploy=1;
		}
		//校验数据
		//title
		//认证编辑可以在发布非第一篇文章的时候不填标题
		if(this.userLevel==1){
			if(intent==0 && StringUtil.isNotBlank(this.title) && this.title.length()>PojoConstant.ARTICLE.MAX_TITLE_LENGHT){
				this.jsonResult.setMessage("标题长度大于最大长度:"+PojoConstant.ARTICLE.MAX_TITLE_LENGHT);
				return JSON;
			}
		}else if(StringUtil.isBlank(this.title) || this.title.length()>PojoConstant.ARTICLE.MAX_TITLE_LENGHT){
			this.jsonResult.setMessage("标题长度为空或者大于最大长度:"+PojoConstant.ARTICLE.MAX_TITLE_LENGHT);
			return JSON;
		}
		
		//过滤html
		if(StringUtil.isBlank(this.title)){
			this.title=null;
		}else{
			this.title=StringUtil.escapeHtml(this.title);	
		}
		
		//content和conList不能同时为空
		if(StringUtil.isBlank(this.content) && (this.conList==null || this.conList.size()<=0)){
			this.jsonResult.setMessage("内容和(图片，视频，音乐)不能同时为空");
			return JSON;
		}
		
		if(StringUtil.isNotBlank(this.secondTitle)){
			if(this.secondTitle.length()>PojoConstant.ARTICLE.MAX_SECOND_TITLE_LENGHT){
				this.jsonResult.setMessage("显示标题长度为空或者大于最大长度:"+PojoConstant.ARTICLE.MAX_TITLE_LENGHT);
				return JSON;
			}
			this.secondTitle=StringUtil.escapeHtml(this.secondTitle);
		}else{
			this.secondTitle=this.title;
		}
		//校验content
		List<Map<String,String>> contentImgUrls=null;
		if(StringUtil.isNotBlank(this.content)){
			Map<String,Object> backStr=StringUtil.HtmlTextByEdit(this.content, this.systemProp.getCreativeImgUrl());
			if(backStr.get("content")==null || StringUtil.isBlank(backStr.get("content").toString()) 
					|| backStr.get("content").toString().length()>PojoConstant.ARTICLE.MAX_CONTENT_LENGHT){
				this.jsonResult.setMessage("内容长度为空或者大于最大长度:"+PojoConstant.ARTICLE.MAX_CONTENT_LENGHT);
				return JSON;
			}
			this.content=backStr.get("content").toString();
			//内容中的图片
			contentImgUrls=(List<Map<String,String>>)backStr.get("pathImgList");
		}else{
			if(this.userLevel==1){
				this.jsonResult.setMessage("认证编辑必须选择填写内容");
				return JSON;
			}
		}
		//描述
		if(StringUtil.isNotBlank(this.secondDescription)){
			if(this.secondDescription.length()>PojoConstant.ARTICLE.MAX_SECOND_DESCRIPTION_LENGHT){
				this.jsonResult.setMessage("显示描述长度为空或者大于最大长度:"+PojoConstant.ARTICLE.MAX_SECOND_DESCRIPTION_LENGHT);
				return JSON;
			}
		}else if(StringUtil.isNotBlank(this.content)){
			String tmpDesc=StringUtil.HtmlTextByEditContentPreview(this.content);
			if(StringUtil.isNotBlank(tmpDesc) && tmpDesc.length()>PojoConstant.ARTICLE.MAX_SECOND_DESCRIPTION_LENGHT){
				this.secondDescription=tmpDesc.substring(0,PojoConstant.ARTICLE.MAX_SECOND_DESCRIPTION_LENGHT);
				this.secondDescription=StringUtil.HtmlTextByEditContentPreview(this.secondDescription+"...");
			}else{
				this.secondDescription=tmpDesc;
			}
		}
		//校验杂志链接
		if(!validateMagazineUrl()){
			this.jsonResult.setMessage("抱歉，杂志链接错误并且只支持本站中杂志链接");
			return JSON;
		}
		
		//校验 magazineUrl,publicationid,issueid,magazineName这些都是来自于magazineUrl，因此以它为准
		//认证编辑发作品(系列)的时候才能输入杂志url
		//显示描述为空的时候，使用内容的前20个字代替显示描述
		if(!(this.userLevel==1) ){
			this.magazineUrl=null;
			this.publicationid=null;
			this.issueid=null;
			this.magazineName=null;
		}		
		
		//分类
		if((categoryIds==null || categoryIds.size()<=0) && (this.userLevel==1 || this.userLevel==3 ) && this.creativeFlag==1){
			this.jsonResult.setMessage("认证编辑和golf用户创建作品必须选择分类");
			return JSON;
		}
		//认证编辑和golf之外一律不许增加类目和图集
		if(this.userLevel!=1 && this.userLevel!=3){
			categoryIds=null;
			this.picCollectionIds=null;
		}
		
		//作品标题和作品描述
		if(StringUtil.isBlank(this.creativeTitle)){
			this.creativeTitle=this.secondTitle;
		}
		if(StringUtil.isBlank(this.creativeDescription)){
			this.creativeDescription=this.secondDescription;
		}else{
			this.creativeDescription=StringUtil.HtmlTextByEditContentPreview(creativeDescription);
		}
		if(this.creativeFlag==1){
			if(StringUtil.isBlank(this.creativeTitle) || StringUtil.isBlank(this.creativeDescription)){
				this.jsonResult.setMessage("发布系列(作品)，系列标题和系列描述不能为空");
				return JSON;
			}
		}
		if(id!=null){
			this.article=this.articleService.queryById(id);
			if(article==null || this.article.getStatus()!=PojoConstant.ARTICLE.STATUS_OK ){
    			log.error("文章处于无效状态，或者文章不存在:id"+id);
    			this.jsonResult.setMessage("文章处于无效状态，或者文章不存在:id"+id);
    			return JSON;
    		}
			CreativeArticleRel caRel=this.creativeArticleRelService.queryByArticleId(id);
			if(caRel==null){
				this.jsonResult.setMessage("编辑文章，但是找不到文章对应的作品，文章id："+id);
    			return JSON;
			}
			//第一篇文章title不能为空
			if(this.article.getSortOrder()==0){
				if(StringUtil.isBlank(this.title)){
					this.jsonResult.setMessage("系列第一篇文章标题不能为空");
	    			return JSON;
				}
			}
			this.creativeId=caRel.getCreativeId();
			Creative tmpCreative=this.creativeService.queryById(this.creativeId);
			if(tmpCreative==null || tmpCreative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK ){
    			log.error("作品处于无效状态，或者作品不存在:id"+id);
    			this.jsonResult.setMessage("作品处于无效状态，或者作品不存在:id"+id);
    			return JSON;
    		}
			//TODO 校验是否是自己的作品
		}else{
			this.article=new Article();
		}
		
		if(this.userLevel==2 && this.intent==0 && (this.tags==null  || this.tags.size()<=0)){
			this.jsonResult.setMessage("seoer必须输入标签!");
			return JSON;
		}
		
		if(this.tags!=null && this.tags.size()>0){
			boolean isBigger=false;
			for(String tag:tags){
				if(tag!=null && tag.length()>TAG_MAX_LENGTH){
					isBigger=true;
					break;
				}
			}
			if(isBigger){
				this.jsonResult.setMessage("标签不能大于16字");
    			return JSON;
			}
		}
		
		//图片链接保存
		Map<String,List<ArticleEx>> exres=this.parseConList();
		articleExList=exres.get("exl");
		List<ArticleEx> removeEx=exres.get("removeExl");
		
		//将编辑器中的图片链接全部转换成creativeex
		if(contentImgUrls!=null && contentImgUrls.size()>0){
			List<ArticleEx> edImgArticleExList=this.parseContentImgList(contentImgUrls);
			if(edImgArticleExList!=null && edImgArticleExList.size()>0){
				articleExList.addAll(edImgArticleExList);
			}
		}
		
		
		this.article.setTitle(this.title);
		this.article.setUserId(this.getSessionUserId());
		this.article.setContent(this.content);
		this.article.setStatus(PojoConstant.ARTICLE.STATUS_OK);
		this.article.setArticleType(this.userLevel);
		
		Map<String,Object> temp=this.choosePatternTemplateName();
		this.article.setWordCountTemp((String)temp.get(WORD_COUNT_TEMP));
		this.article.setImgCountTemp((Integer)temp.get(IMG_COUNT_TEMP));
		if(articleExList!=null && articleExList.size()>0){
			if(this.firstArticleEx!=null && this.firstArticleEx.getImgType()!=null && this.firstArticleEx.getImgType()==PojoConstant.ARTICLE_EX.IMG_TYPE_COVER){
				this.article.setImgCount(articleExList.size()-1);	
			}else{
				this.article.setImgCount(articleExList.size());
			}
			
		}else{
			this.article.setImgCount(0);
		}
		//增加图片信息
		if(this.articleExList!=null && this.articleExList.size()>0 && this.firstArticleEx!=null){
			article.setWidth(firstArticleEx.getW());
			article.setHeight(firstArticleEx.getH());
			article.setImagePath(firstArticleEx.getImgPath());
		}else if(this.articleExList==null || this.firstArticleEx==null || this.articleExList.size()<=0 ){
			article.setWidth(null);
			article.setHeight(null);
			article.setImagePath(null);
		}
		
		if(id==null){
			this.article.setSortOrder(0);
		}
		this.article.setBigWordCount(bigWordCount);
		
		if(StringUtil.isBlank(secondDescription)){
			secondDescription=null;
		}
		if(StringUtil.isBlank(creativeDescription)){
			creativeDescription=null;
		}
		
		//增加文章，需要添加creative对象
		if(this.intent==0){
			
			creative=new Creative();
			creative.setId(creativeId);
			creative.setCreativeType(this.userLevel);
			creative.setcType(PojoConstant.CREATIVE.CREATIVE_C_TYPE);
			creative.setIshome(PojoConstant.CREATIVE.IS_HOME_WAIT_AUDIT);
			creative.setIsrecommend(PojoConstant.CREATIVE.IS_RECOMMEND_NO);
			creative.setIssueid(issueid);
			creative.setMagazineName(magazineName);
			creative.setMagazineUrl(magazineUrl);
			creative.setTitle(creativeTitle);
			creative.setDescribed(creativeDescription);
			creative.setPloy(ploy);
			creative.setPublicationid(publicationid);
			creative.setMagazineUrl(magazineUrl);
			creative.setStatus(PojoConstant.CREATIVE.STATUS_OK);
			creative.setShowType(0);
			creative.setUserId(this.getSessionUserId());
			creative.setWeight(0);
			creative.setContent(this.content);
			creative.setSecondTitle(this.secondTitle);
			creative.setSecondDesc(this.secondDescription);
			creative.setFirstTitle(this.title);
			creative.setIsrecommendChannel(0);//设置频道推荐默认值为0
			creative.setOrigin(origin);
			creative.setOriginUrl(originUrl);
			
			//增加图片信息
			if(this.articleExList!=null && this.articleExList.size()>0 && this.firstArticleEx!=null){
				creative.setWidth(firstArticleEx.getW());
				creative.setHigh(firstArticleEx.getH());
				creative.setImagePath(firstArticleEx.getImgPath());
			}
			if(this.tags!=null && this.tags.size()>0){
				creative.setTagStrList(this.tags);
			}
		}else if(this.intent==1){
			creative=this.creativeService.queryById(creativeId);
		}
		
		if(this.intent!=0 && this.userLevel==1){
			this.tempCreative=this.creativeService.queryById(creativeId);
			tempCreative.setIssueid(issueid);
			tempCreative.setMagazineName(magazineName);
			tempCreative.setMagazineUrl(magazineUrl);
			tempCreative.setPublicationid(publicationid);
			tempCreative.setSecondDesc(secondDescription);
			tempCreative.setSecondTitle(secondTitle);
		}
		
		// 数据放入mem或session中存放
		if(this.memCachedClient!=null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, CacheConstants.CACHE_ONE_HOUR);//1小时后失效
			String tempId=this.getSessionJsessionid()+this.getSessionUserId();
			this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CACHE_PREFIX+tempId, this.article, cal.getTime());
			if(removeEx!=null && removeEx.size()>0){
				this.memCachedClient.set(CacheConstants.ARTICLE_EX_MAKE_CACHE_PREFIX+tempId, removeEx, cal.getTime());
			}
			if(articleExList!=null && articleExList.size()>0){
				this.memCachedClient.set(CacheConstants.REMOVE_ARTICLE_EX_MAKE_CACHE_PREFIX+tempId, articleExList, cal.getTime());
			}
			if(this.categoryIds!=null && this.categoryIds.size()>0){
				this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CATEGORY_CACHE_PREFIX+tempId,this.categoryIds,cal.getTime());
			}
			if(this.picCollectionIds!=null && picCollectionIds.size()>0){
				this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CATEGORY_PIC_CACHE_PREFIX+tempId,this.picCollectionIds,cal.getTime());	
			}
			if(this.tempCreative!=null){
				this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CACHE_CREATIVE_EDIT_SE_PREFIX+tempId,this.tempCreative,cal.getTime());
			}
			if(this.creative!=null){
				this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CACHE_CREATIVE_PREFIX+tempId,this.creative,cal.getTime());
				this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CACHE_SERIA_CREATIVE_PREFIX+tempId,this.creative,cal.getTime());
			}else{
				//这种情况肯定是编辑文章
				Creative tempCreative2=this.creativeService.queryById(article.getId());
				this.memCachedClient.set(CacheConstants.ARTICLE_MAKE_CACHE_SERIA_CREATIVE_PREFIX+tempId,tempCreative2,cal.getTime());
			}
			if(StringUtil.isNotBlank(this.neteaseCategoryIds)){
				this.memCachedClient.set(CacheConstants.ARTICLE_NETEASE_CACHE_PREFIX+tempId,neteaseCategoryIds,cal.getTime());
			}
			this.memCachedClient.set(CacheConstants.CREATIVE_FLAG+tempId,this.creativeFlag,cal.getTime());
		}else{
			ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE+this.getSessionUserId(), this.article);
			if(articleExList!=null && articleExList.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX+this.getSessionUserId(), articleExList);
			}
			if(removeEx!=null && removeEx.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX_REMOVE+this.getSessionUserId(), removeEx);
			}
			if(this.categoryIds!=null && this.categoryIds.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS+this.getSessionUserId(), categoryIds);
			}
			if(this.picCollectionIds!=null && picCollectionIds.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS+this.getSessionUserId(), picCollectionIds);	
			}
			if(this.tempCreative!=null){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE_CACHE_CREATIVE_EDIT_SE+this.getSessionUserId(), tempCreative);
			}
			if(this.creative!=null){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE_CACHE_CREATIVE+this.getSessionUserId(), creative);
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE_SERIAL_CACHE_CREATIVE+this.getSessionUserId(), creative);
			}else{
				//这种情况肯定是编辑文章
				CreativeArticleRel rel=this.creativeArticleRelService.queryByArticleId(article.getId());
				Creative tempCreative=this.creativeService.queryById(rel.getCreativeId());
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_ARTICLE_SERIAL_CACHE_CREATIVE+this.getSessionUserId(), tempCreative);
			}
			if(StringUtil.isNotBlank(this.neteaseCategoryIds)){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_NETEASE_CATEGORYIDS+this.getSessionUserId(), neteaseCategoryIds);
			}
			ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_CREATIVE_FLAG+this.getSessionUserId(), this.creativeFlag);
			
		}
		
		//String template=this.getCachedTemplate(templateName);
		//String templateContent=this.completeTemplate(template, creative, creativeExList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		//this.jsonResult.put("templateContent", templateContent);
		return JSON;
	}
	
	@SuppressWarnings("unchecked")
	public String preview(){
		this.userLevel=this.getSessionUser().getUserLevel();
		if(this.memCachedClient!=null){
			String tempId=this.getSessionJsessionid()+this.getSessionUserId();
			this.article=(Article)this.memCachedClient.get(CacheConstants.ARTICLE_MAKE_CACHE_PREFIX+tempId);
			this.articleExList=(List<ArticleEx>)this.memCachedClient.get(CacheConstants.ARTICLE_EX_MAKE_CACHE_PREFIX+tempId);
			this.seriaCreative=(Creative)this.memCachedClient.get(CacheConstants.ARTICLE_MAKE_CACHE_SERIA_CREATIVE_PREFIX+tempId);
			this.creativeFlag=(Integer)this.memCachedClient.get(CacheConstants.CREATIVE_FLAG+tempId,this.creativeFlag);
			this.tempCreative=(Creative)this.memCachedClient.get(CacheConstants.ARTICLE_MAKE_CACHE_CREATIVE_EDIT_SE_PREFIX+tempId);
		}else{
			this.article=(Article)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE+this.getSessionUserId());
			this.articleExList=(List<ArticleEx>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX+this.getSessionUserId());
			this.seriaCreative=(Creative)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_SERIAL_CACHE_CREATIVE+this.getSessionUserId());
			this.creativeFlag=(Integer)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CREATIVE_FLAG+this.getSessionUserId());
			tempCreative=(Creative)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_ARTICLE_CACHE_CREATIVE_EDIT_SE+this.getSessionUserId());
		}
		if(this.article!=null && this.article.getId()!=null && this.article.getId()>0){
			this.editArticle=this.articleService.queryById(this.article.getId());
		}
		if(tempCreative!=null){
			seriaCreative=tempCreative;
		}
	    //this.tempPattern=this.choosePatternTemplateName();
	    //this.imgCount=this.tempPattern.substring(0,tempPattern.length()-1);
		return "preview";
	}
	/**
	 * 选择模板的算法
	 * 模板名：0a,0b,0c,1a,1b,1c=========10a,10b,10c
     * 图片数量，0-10
     * 文字100以下a,200以下b,200+c
	 * @return
	 */
	private Map<String,Object>  choosePatternTemplateName(){
		Map<String,Object> res=new HashMap<String,Object>();
		Integer imgCountTemp=0;//默认值
		String wordCountTemp="a";
		if(this.articleExList!=null && articleExList.size()>0){
			int size=articleExList.size();
			if(size>=1 && firstArticleEx!=null && firstArticleEx.getImgType()!=null && firstArticleEx.getImgType()==PojoConstant.ARTICLE_EX.IMG_TYPE_COVER){
				size=size-1;
			}
			int integerPart=size/TEMPLATE_PIC_COUNT;
			int otherPart=size%TEMPLATE_PIC_COUNT;
			if(integerPart>0){
				imgCountTemp=10;
			}else{
				imgCountTemp=otherPart;
			}
		}
		
		if(StringUtil.isNotBlank(this.article.getContent())){
			int wordLenght=this.article.getContent().length();
			if(wordLenght<=TEMPLATE_WORD_COUNT){
				wordCountTemp="a";
			}else if(wordLenght<=200){
				wordCountTemp="b";
			}else{
				wordCountTemp="c";
			}
		}
		res.put(WORD_COUNT_TEMP, wordCountTemp);
		res.put(IMG_COUNT_TEMP, imgCountTemp);
		//this.imgCount=String.valueOf("1");
		//TODO 填充模板
		return res;
		//TODO 为了测试，现在固定返回1a
		//this.tempPattern=wordCountTemp;
		//this.imgCount=String.valueOf(picCountTemp);
		//return picCountTemp+wordCountTemp;
	}
	
    private boolean validateMagazineUrl(){
    	//这三个值不相信客户端
    	this.magazineName=null;
		this.issueid=null;
		this.publicationid=null;
    	if(StringUtil.isBlank(magazineUrl)){
			this.magazineUrl=null;
    		return true;
    	}
    	if(magazineUrl!=null && magazineUrl.indexOf("action?id=")>-1){//杂志url判断
			String idstr=magazineUrl.substring(magazineUrl.lastIndexOf("id")+3);
			Long id=Long.valueOf(idstr);
			Issue issue = issueService.queryById(id);
			if(issue!=null){
				this.magazineName=issue.getPublicationName();
				this.issueid=issue.getId();
				this.publicationid=issue.getPublicationId();
				return true;
			}
		}else if(magazineUrl!=null && magazineUrl.indexOf("action?eventId=")>-1){//事件判断
			String idstr=magazineUrl.substring(magazineUrl.lastIndexOf("eventId=")+8);
			Long eventId=Long.valueOf(idstr);
			FpageEvent fpageEvent = fpageEventService.getFpageEventById(eventId);
			 if (fpageEvent != null && !fpageEvent.getStatus().equals(PojoConstant.FPAGEEVENT.STATUS_DELETE)) {
				 this.magazineName=fpageEvent.getPublicationName();
				 this.issueid=fpageEvent.getIssueId();
				 this.publicationid=fpageEvent.getPublicationId();
				 return true;
			 }
		}else if(magazineUrl!=null && magazineUrl.indexOf("&Id=")>-1){//阅读器获取链接判断
			String idstr=magazineUrl.substring(magazineUrl.lastIndexOf("&Id=")+4);
			Long id=Long.valueOf(idstr);
			Issue issue = issueService.queryById(id);
			if(issue!=null){
				this.magazineName=issue.getPublicationName();
				this.issueid=issue.getId();
				this.publicationid=issue.getPublicationId();
				return true;
			}
		}
    	return false;
    }
	/**
	 * 将编辑器中图片信息转换成ArticleEx
	 * @param contentImgUrls
	 * @return
	 */
	private List<ArticleEx> parseContentImgList(List<Map<String,String>> contentImgUrls){
		if(contentImgUrls==null || contentImgUrls.size()<=0){
			return null;
		}
		List<ArticleEx> articleList=new ArrayList<ArticleEx>();
		//没有上传图片，视频，音乐
		if(this.maxPosition==0){
			this.maxPosition=-1;
		}
		for(Map<String,String> contentImgUrl:contentImgUrls){
			ArticleEx ex = new ArticleEx();
			ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
			ex.setContent(null);
			ex.setArticleId(article.getId());
			ex.setImgPath(IMAG_PREFIX+contentImgUrl.get("path"));
			ex.setPosition(++this.maxPosition);
			ex.setStatus(PojoConstant.ARTICLE_EX.STATUS_OK);
			ex.setW(Integer.valueOf(contentImgUrl.get("width")));
			ex.setH(Integer.valueOf(contentImgUrl.get("high")));
			ex.setImgType(PojoConstant.ARTICLE_EX.IMG_TYPE_NORMAL);
			//记录第一条数据，为之后的计算方便
			if(ex.getW()>100 || ex.getH()>100){//宽和高都小于100的图片丢弃
				if(maxPosition==0){
					firstArticleEx=ex;
				}
				articleList.add(ex);
			}
		}
		return articleList;
	}
	
	/**
	 * 解析音乐，图片和视频
	 * @return
	 */
	private Map<String,List<ArticleEx>> parseConList(){
		Map<String,List<ArticleEx>> res=new HashMap<String,List<ArticleEx>>();
		List<ArticleEx> exl = new ArrayList<ArticleEx>();
		List<ArticleEx> articleExList =new ArrayList<ArticleEx>(); 
		if(id!=null && id>0){
			articleExList=this.articleExService.getArticleEx(this.article.getId());
		}
		try{
			//解析作品中的 图片、音乐、视频
			if(null!=conList && conList.size()>0 && article!=null){
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					boolean wflag=true;
					//修改作品
					for (int j = 0; j < articleExList.size(); j++) {
						ArticleEx cEx = articleExList.get(j);
						if(list[0].equals("video") && (cEx.getImgPath().equals(list[1]) || list[3].equals(cEx.getPath()))){
							int eid=Integer.valueOf(list[5]);
							if(cEx.getId().intValue()==eid && article.getId().longValue()==cEx.getArticleId().longValue()){
								wflag=false;
								if(list[2]!=null && !list[2].equalsIgnoreCase("null")){
									cEx.setContent(list[2]);
								}
								//修改了封面图片
								if(!cEx.getImgPath().equals(list[1])){
									cEx.setImgPath(list[1].replace(this.systemProp.getStaticServerUrl(), ""));
								}
								int position=Integer.valueOf(list[4]);
								this.maxPosition=this.maxPosition<position?position:this.maxPosition;
								cEx.setPosition(position);
								cEx.setImgType(PojoConstant.ARTICLE_EX.IMG_TYPE_NORMAL);
								exl.add(cEx);
								//记录第一条数据，为之后的计算方便
								if(position==0){
									firstArticleEx=cEx;
								}
								//snsCreativeExService.updateCreativeEx(w);
								articleExList.remove(j);
								break;
							}
						}else if(list[0].equals("music") && (cEx.getImgPath().equals(list[1]) || list[3].equals(cEx.getPath()))){
							int eid=Integer.valueOf(list[5]);
							if(cEx.getId().intValue()==eid && article.getId().intValue()==cEx.getArticleId().intValue()){
								wflag=false;
								if(list[2]!=null && !list[2].equalsIgnoreCase("null")){
									cEx.setContent(list[2]);
								}
								int position=Integer.valueOf(list[4]);
								this.maxPosition=this.maxPosition<position?position:this.maxPosition;
								cEx.setPosition(position);
								cEx.setImgType(PojoConstant.ARTICLE_EX.IMG_TYPE_NORMAL);
								exl.add(cEx);
								if(position==0){
									firstArticleEx=cEx;
								}
								//snsCreativeExService.updateCreativeEx(w);
								articleExList.remove(j);
								break;
							}
						}else if(cEx.getImgPath().equals(list[1].replace(systemProp.getStaticServerUrl(), ""))){
							int eid=Integer.valueOf(list[4]);
							if(cEx.getId().intValue()==eid && article.getId().intValue()==cEx.getArticleId().intValue()){
								wflag=false;
								if(list[2]!=null && !list[2].equalsIgnoreCase("null")){
									cEx.setContent(list[2]);
								}
								int position=Integer.valueOf(list[3]);
								this.maxPosition=this.maxPosition<position?position:this.maxPosition;
								cEx.setPosition(position);
								Integer imgType=Integer.valueOf(list[5]);
								cEx.setImgType(imgType);
								exl.add(cEx);
								if(position==0){
									firstArticleEx=cEx;
								}
								//如果有只设置首页，这个值有限，覆盖上面一行的值
								if(imgType==PojoConstant.ARTICLE_EX.IMG_TYPE_COVER){
									firstArticleEx=cEx;
								}
								//snsCreativeExService.updateCreativeEx(w);
								articleExList.remove(j);
								break;
							}
						}
					}
					//新增作品
					if(wflag){
						ArticleEx ex = new ArticleEx();
						ex.setArticleId(this.article.getId());
						ex.setStatus(PojoConstant.CREATIVE_EX.STATUS_OK);
						if(list[0].equals("video")){
							ex.setConType(SNSConstant.SNS_CREATIVE_VIDEO);
							if(list[2]!=null && !list[2].equalsIgnoreCase("null")){
								ex.setContent(list[2]);
							}
							ex.setPath(list[3]);
							int position=Integer.valueOf(list[4]);
							ex.setPosition(position);
							ex.setImgType(PojoConstant.ARTICLE_EX.IMG_TYPE_NORMAL);
							this.maxPosition=this.maxPosition<position?position:this.maxPosition;
							//已经替换成了本站的图片
							if(list[1].startsWith(this.systemProp.getStaticServerUrl())){
								ex.setImgPath(list[1].replace(this.systemProp.getStaticServerUrl(), ""));
								BufferedImage imageResize = ImageIO.read(new File(this.systemProp.getStaticLocalUrl()+ex.getImgPath()));
								ex.setW(imageResize.getWidth());
								ex.setH(imageResize.getHeight());
							}else{
								Map<String,String> uploadInfo=UploadPictureUtil.uploadPicPubUrl(list[1], this.systemProp.getCreativeImgUrl());
								ex.setImgPath(IMAG_PREFIX+uploadInfo.get("path"));
								ex.setW(Integer.valueOf(uploadInfo.get("picwidth")));
								ex.setH(Integer.valueOf(uploadInfo.get("pichigh")));
							}
							if(position==0){
								firstArticleEx=ex;
							}
						}else if(list[0].equals("music")){
							ex.setConType(SNSConstant.SNS_CREATIVE_MUSIC);
							ex.setImgPath(list[1]);
							if(list[2]!=null && !list[2].equalsIgnoreCase("null")){
								ex.setContent(list[2]);
							}
							ex.setPath(list[3]);
							int position=Integer.valueOf(list[4]);
							ex.setPosition(position);
							this.maxPosition=this.maxPosition<position?position:this.maxPosition;
							
							Map<String,String> uploadInfo=UploadPictureUtil.uploadPicPubUrl(list[1], this.systemProp.getCreativeImgUrl());
							ex.setImgPath(IMAG_PREFIX+uploadInfo.get("path"));
							ex.setW(Integer.valueOf(uploadInfo.get("picwidth")));
							ex.setH(Integer.valueOf(uploadInfo.get("pichigh")));
							ex.setImgType(PojoConstant.ARTICLE_EX.IMG_TYPE_NORMAL);
							if(position==0){
								firstArticleEx=ex;
							}
						}else{
							ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
							ex.setImgPath(list[1].replace(systemProp.getStaticServerUrl(), ""));
							if(list[2]!=null && !list[2].equalsIgnoreCase("null")){
								ex.setContent(list[2]);
							}
							int position=Integer.valueOf(list[3]);
							ex.setPosition(position);
							//记录最大位置
							this.maxPosition=this.maxPosition<position?position:this.maxPosition;
							ex.setW(Integer.valueOf(list[4]));
							ex.setH(Integer.valueOf(list[5]));
							Integer imgType=Integer.valueOf(list[6]);
							ex.setImgType(imgType);
							
							if(position==0){
								firstArticleEx=ex;
							}
							//如果有只设置首页，这个值有限，覆盖上面一行的值
							if(imgType==PojoConstant.ARTICLE_EX.IMG_TYPE_COVER){
								firstArticleEx=ex;
							}
						}
						exl.add(ex);
//						snsCreativeExService.insertCreativeEx(ex);
					}
				}
				/*for(ArticleEx ex:articleExList){
					ex.setStatus(PojoConstant.CREATIVE_EX.STATUS_INVALID);
					exl.add(ex);
					//snsCreativeExService.updateCreativeEx(w);
				}*/
			}
		}catch (Exception e) {
			log.error("", e);
		}
		res.put("exl", exl);
		if(articleExList!=null && articleExList.size()>0){
			res.put("removeExl",articleExList);
		}
		return res;
	
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		this.deleteCacheOrSession();
		this.userLevel=this.getSessionUser().getUserLevel();
		this.intent=this.judgeIntent();
		if(this.userLevel!=1){
			this.secondTitle=null;
			this.secondDescription=null;
		}
	}
	
	/**
	 * 删除缓存数据
	 */
	public String deleteCacheOrSession(){
		if(this.jsonResult==null){
			this.jsonResult=JsonResult.getSuccess();
		}
		// 数据放入mem或session中存放
		try {
			if(this.memCachedClient!=null){
				String tempId=this.getSessionJsessionid()+this.getSessionUserId();
				this.memCachedClient.delete(CacheConstants.ARTICLE_MAKE_CACHE_PREFIX+tempId);
				this.memCachedClient.delete(CacheConstants.ARTICLE_EX_MAKE_CACHE_PREFIX+tempId);
				this.memCachedClient.delete(CacheConstants.REMOVE_ARTICLE_EX_MAKE_CACHE_PREFIX+tempId);
				this.memCachedClient.delete(CacheConstants.ARTICLE_MAKE_CATEGORY_CACHE_PREFIX+tempId);
				this.memCachedClient.delete(CacheConstants.ARTICLE_NETEASE_CACHE_PREFIX+tempId);
				this.memCachedClient.delete(CacheConstants.ARTICLE_MAKE_CATEGORY_PIC_CACHE_PREFIX+tempId);
				this.memCachedClient.delete(CacheConstants.ARTICLE_MAKE_CACHE_PREFILE+tempId);
				this.memCachedClient.delete(CacheConstants.ARTICLE_MAKE_CACHE_CREATIVE_EDIT_SE_PREFIX+tempId);
			}else{
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_ARTICLE+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_ARTICLE_EX_REMOVE+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_NETEASE_CATEGORYIDS+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_ARTICLE+this.getSessionUserId());
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_ARTICLE_CACHE_CREATIVE_EDIT_SE+this.getSessionUserId());
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	
	/**
	 * 类目树
	 */
	private List<CreativeCategory> creativeCategoryTree;
	
	//作品
	private Creative creative;
	
	//作品扩展
	private List<ArticleEx> articleExList;
	
	private int maxPosition=0;
	
	/**
	 *临时作品 
	 */
	private Creative tempCreative;
	
	
	/**
	 * 发布作品(系列)：1     否则：0; 只有编辑能够设置为1,默认0
	 */
	private int creativeFlag=0;
	
	//作品id，编辑的时候必须传
	private Long id;

	//默认
	private Integer isHome=PojoConstant.CREATIVE.IS_HOME_WAIT_AUDIT;
	
	private String title;
	
	private String content;
	
	private String magazineUrl;
	
	private String magazineName;
	
	private List<String>  tags;
	
	private Integer ploy;
	
	private Long issueid;
	
	private Long publicationid;
	
	private String secondTitle;
	
	private String secondDescription;
	
	/**
	 * 图片url
	 */
	private List<String> conList;
	
	/**
	 * 用户选择的类目id
	 */
	private List<Long> categoryIds;
	
	/**
	 * 用户选择的类目id
	 */
	private String neteaseCategoryIds;
	
	/**
	 * 用户选择的图集
	 */
	private List<Long> picCollectionIds;
	
	
	private List<CreativeCategory> chooseCategoryList;
	
	private List<CreativeCategoryRel> creativeCategoryRelList; 
	
	/**
	 * 字数
	 */
	private Long wordCount;
	
	/**
	 * 模板最后的样式
	 */
	private String template;
	
	/**
	 * 文章
	 */
	private Article article;
	/**
	 * 用户等级，普通用户0,认证编辑 1,SEO用户2,3，golf
	 */
	private Integer userLevel=0;
	
	/**
	 * 作品(系列)标题
	 */
	private String creativeTitle;
	/**
	 * 作品(系列)描述
	 */
	private String creativeDescription;
	
	/**
	 * 作品id
	 */
	private Long creativeId;
	/**
	 * 用户意图，0 增加文章，1 给作品添加文章，2 修改文章
	 */
	private int intent=0;
	/**
	 * 第0个文章扩展
	 */
	private ArticleEx firstArticleEx;
	
	/**
	 * 作品系列
	 */
	private Creative seriaCreative;
	
	private List<Article> articleInCreative;
	
	private Article editArticle;
	/**
	 * 网易云阅读类目
	 */
	List<CreativeCategory> neteaseCcList;
	/**
	 * 网易云阅读类目,作品包含的类目
	 */
	List<CreativeNeteaseCategoryRel> neteaseCncrList;
	
	
	
	
	
	public Creative getTempCreative() {
		return tempCreative;
	}

	public void setTempCreative(Creative tempCreative) {
		this.tempCreative = tempCreative;
	}

	public Article getEditArticle() {
		return editArticle;
	}

	public void setEditArticle(Article editArticle) {
		this.editArticle = editArticle;
	}

	public List<Article> getArticleInCreative() {
		return articleInCreative;
	}

	public Creative getSeriaCreative() {
		return seriaCreative;
	}

	/**
	 * 有id认为是修改，有creativeId认为是给作品增加文章，都没有认为是增加文章
	 * @return
	 */
	public int judgeIntent(){
		//保证creativeFlag和creativeId的一致性
		//勾选创建系列,则作品id必须为空
		//试图判断用户意图
		this.intent=0;
		if(id==null && this.creativeId==null){//增加文章
			this.intent=0;
			this.creative=null;
			this.article=null;
			if(userLevel==1){
				this.creativeFlag=1;
			}
		}else if(id==null && this.creativeId!=null){//给作品添加文章
			this.creative=this.creativeService.queryById(this.creativeId);
			this.article=null;
			this.creative=null;
			this.intent=1;
		}else if(id!=null){//修改文章
			this.article=this.articleService.queryById(id);
			this.creativeId=null;
			this.creative=null;
			this.intent=2;
		}
		return this.intent;
	}
	
	
	
	public List<ArticleEx> getArticleExList() {
		return articleExList;
	}

	public void setArticleExList(List<ArticleEx> articleExList) {
		this.articleExList = articleExList;
	}

	public int getIntent() {
		return intent;
	}

	public Long getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(Long creativeId) {
		this.creativeId = creativeId;
	}

	public int getCreativeFlag() {
		return creativeFlag;
	}

	public void setCreativeFlag(int creativeFlag) {
		this.creativeFlag = creativeFlag;
	}

	
	public String getCreativeTitle() {
		return creativeTitle;
	}

	public void setCreativeTitle(String creativeTitle) {
		this.creativeTitle = creativeTitle;
	}

	public String getCreativeDescription() {
		return creativeDescription;
	}

	public void setCreativeDescription(String creativeDescription) {
		this.creativeDescription = creativeDescription;
	}


	public Integer getUserLevel() {
		return userLevel;
	}


	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}



	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount(Long wordCount) {
		this.wordCount = wordCount;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<CreativeCategoryRel> getCreativeCategoryRelList() {
		return creativeCategoryRelList;
	}

	public List<CreativeCategory> getChooseCategoryList() {
		return chooseCategoryList;
	}

	public void setChooseCategoryList(List<CreativeCategory> chooseCategoryList) {
		this.chooseCategoryList = chooseCategoryList;
	}

	public List<CreativeCategory> getCreativeCategoryTree() {
		return creativeCategoryTree;
	}

	public void setCreativeCategoryTree(List<CreativeCategory> creativeCategoryTree) {
		this.creativeCategoryTree = creativeCategoryTree;
	}
	
	

	public List<Long> getPicCollectionIds() {
		return picCollectionIds;
	}

	public void setPicCollectionIds(List<Long> picCollectionIds) {
		this.picCollectionIds = picCollectionIds;
	}

	public Integer getIsHome() {
		return isHome;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setIsHome(Integer isHome) {
		this.isHome = isHome;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMagazineUrl() {
		return magazineUrl;
	}

	public void setMagazineUrl(String magazineUrl) {
		this.magazineUrl = magazineUrl;
	}

	public String getMagazineName() {
		return magazineName;
	}

	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer getPloy() {
		return ploy;
	}

	public void setPloy(Integer ploy) {
		this.ploy = ploy;
	}

	public Long getIssueid() {
		return issueid;
	}

	public void setIssueid(Long issueid) {
		this.issueid = issueid;
	}

	public Long getPublicationid() {
		return publicationid;
	}

	public void setPublicationid(Long publicationid) {
		this.publicationid = publicationid;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public String getSecondDescription() {
		return secondDescription;
	}

	public void setSecondDescription(String secondDescription) {
		this.secondDescription = secondDescription;
	}

	public List<String> getConList() {
		return conList;
	}

	public void setConList(List<String> conList) {
		this.conList = conList;
	}

	/*public void setCreative(Creative creative) {
		this.creative = creative;
	}*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Creative getCreative() {
		return creative;
	}

	public  String getOperate() {
		return operate;
	}
	
	
	private String origin;
	
	private Integer bigWordCount;
	
	
	
	public Integer getBigWordCount() {
		return bigWordCount;
	}

	public void setBigWordCount(Integer bigWordCount) {
		this.bigWordCount = bigWordCount;
	}


	private String originUrl;
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public List<CreativeCategory> getNeteaseCcList() {
		return neteaseCcList;
	}

	public void setNeteaseCcList(List<CreativeCategory> neteaseCcList) {
		this.neteaseCcList = neteaseCcList;
	}

	public List<CreativeNeteaseCategoryRel> getNeteaseCncrList() {
		return neteaseCncrList;
	}

	public void setNeteaseCncrList(List<CreativeNeteaseCategoryRel> neteaseCncrList) {
		this.neteaseCncrList = neteaseCncrList;
	}

	public String getNeteaseCategoryIds() {
		return neteaseCategoryIds;
	}

	public void setNeteaseCategoryIds(String neteaseCategoryIds) {
		this.neteaseCategoryIds = neteaseCategoryIds;
	}

	
	
	

}
