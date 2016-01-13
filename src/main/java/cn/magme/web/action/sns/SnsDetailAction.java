package cn.magme.web.action.sns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Article;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.CreativeCategoryRel;
import cn.magme.pojo.User;
import cn.magme.pojo.look.LooArticle;
import cn.magme.pojo.look.LooItem;
import cn.magme.pojo.sns.ArticleEx;
import cn.magme.pojo.sns.Creative;
import cn.magme.result.sns.CreativeCommentResult;
import cn.magme.service.DetailPageService;
import cn.magme.service.HomePageItemService;
import cn.magme.service.UserService;
import cn.magme.service.look.LookHomePageService;
import cn.magme.service.sns.ArticleExService;
import cn.magme.service.sns.ArticleService;
import cn.magme.service.sns.CreativeCategoryRelService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.sns.CreativeService;
import cn.magme.util.DateUtil;
import cn.magme.web.action.BaseAction;
/**
 * 
 * @author fredy
 * @since 2012-12-14
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sns/snsDetail.ftl"),
	@Result(name = "index",type="redirect", location = "/"),
	@Result(name="empty_success",location="/WEB-INF/pages/sns/emptySnsDetail.ftl")})
public class SnsDetailAction extends BaseAction {
	
	@Resource
	private CreativeService creativeService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleExService articleExService;
	@Resource
	private CreativeCategoryRelService creativeCategoryRelService;
	@Resource
	private DetailPageService detailPageService;
	@Resource
	private CreativeCategoryService creativeCategoryService;
	@Resource
	private UserService userService;

	private static final long serialVersionUID = -6097406186826322036L;
	
	private static final Logger log=Logger.getLogger(SnsDetailAction.class);
	@Resource
	private HomePageItemService homePageItemService;
    @Resource
    private LookHomePageService lookHomePageService;
	private List<LooItem> looItemList;
	
	private LooArticle looArticle;
	private List<Map> sameLooArticleList;
	/**
	 * 修改为查询LOO客资讯
	 */
	public String execute(){
        //改为查询LOO客栏目
        this.looItemList = this.lookHomePageService.looItemList();
		if(creativeId==null || this.creativeId<=0){
			log.error("作品id，creativeId必须大于0，跳转到首页");
			return "index";
		}
		this.looArticle=lookHomePageService.looArticle(creativeId);
		if(this.looArticle==null || this.looArticle.getStatus()==null || !this.looArticle.getStatus().equals(new Byte(PojoConstant.Look.STATUS_ON))){
			log.error("作品不存在或作品被删除，跳转到首页,creativeId:"+this.creativeId);
			return "index";
		}
		//得到文章的栏目
		Long looItemId = this.lookHomePageService.looArticleItem(creativeId);
		if(looItemId==null||looItemId<=0)
			return SUCCESS;
		String publishDate = DateUtil.format(this.looArticle.getPublishDate(), "yyyy-MM-dd HH:mm:ss");
		//得到前一篇文章
		Map pa = this.lookHomePageService.quaryPreviousArticle(looItemId, creativeId, publishDate);
		if(pa!=null)
			this.prevArticleId = (Long)pa.get("articleId");
		//得到后一篇文章和推荐
		this.sameLooArticleList = this.lookHomePageService.quaryNextArticle(looItemId, creativeId, publishDate);
		if(this.sameLooArticleList!=null&&this.sameLooArticleList.size()>0)
		{
			this.nextArticleId = (Long)this.sameLooArticleList.get(0).get("articleId");
		}
		return SUCCESS;
	}
	
	public String execute_old(){
		creativeCategoryList = creativeCategoryService.queryCategoryTree();
		if(creativeId==null || this.creativeId<=0){
			log.error("作品id，creativeId必须大于0，跳转到首页");
			return "index";
		}
		this.creative=creativeService.queryById(this.creativeId);
		if(this.creative==null || this.creative.getStatus()==null || this.creative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK){
			log.error("作品不存在或作品被删除，跳转到首页,creativeId:"+this.creativeId);
			return "index";
		}
		user=this.userService.getUserById(this.creative.getUserId());
		List<Article> articleList=this.articleService.queryByCreativeId(creativeId);
		
		if(user==null || user.getStatus()!=PojoConstant.USER.STATUS_ACTIVE.intValue()){
			log.error("这个作品下所对应的作者状态异常,跳转至首页,userId:"+this.creative.getUserId()+",creativeId:"+creativeId);
			return "index";
		}
		if(articleList==null || articleList.size()<=0){
			log.error("这个作品下没有文章,跳转至首页,creativeId:"+this.creativeId);
			return "index";
		}
		totalCount=articleList.size();
		//未传入了文章id
		if(this.articleId==null || this.articleId<=0){
			//由于默认使用排序的倒序排列，因此使用最后一个
			this.article=articleList.get(0);
			//下一个
			if(totalCount>1){
				this.nextArticleId=articleList.get(1).getId();
				this.nextCreativeId=this.creativeId;
			}
		}else{
			//传入了文章id
			this.article=this.articleService.queryById(this.articleId);
			if(this.article==null || this.article.getStatus()==null || this.article.getStatus()!=PojoConstant.ARTICLE.STATUS_OK){
				log.error("通过文章id查询不到文章或者文章已经被删除，跳转到首页,articleId:"+this.articleId);
				return "index";
			}
			
			int currentArticleIndex=-1;
			for(int i=0;i<totalCount;i++){
				if(articleId==articleList.get(i).getId().longValue()){
					currentArticleIndex=i;
					break;
				}
			}
			if(currentArticleIndex==-1){
				log.error("作品(creativeId:"+this.creativeId+")下无这个文章(articleId:"+this.articleId+")");
				return "index";
			}
			int preIndex=currentArticleIndex-1;
			int nextIndex=currentArticleIndex+1;
			if(preIndex>=0 && preIndex<totalCount){
				this.prevArticleId=articleList.get(preIndex).getId();
				this.prevCreativeId=this.creativeId;
			}
			if(nextIndex>=0 && nextIndex<totalCount){
				this.nextArticleId=articleList.get(nextIndex).getId();
				this.nextCreativeId=this.creativeId;
			}
		}
		//计算推荐阅读
		this.recommend();
		//给点击图片拼接内容
		this.picContent();
		//查找评论
		commentLst = detailPageService.selCommentLst(this.creativeId, PojoConstant.SORT.TYPE_CREATIVE, 0, 10);
		//非认证编辑没有下一页上一页按钮
		if(creative.getCreativeType()==null || (creative.getCreativeType()!=1)){
			this.nextArticleId=null;
			this.nextCreativeId=null;
			this.prevArticleId=null;
			this.prevCreativeId=null;
			return SUCCESS;
		}
		List<CreativeCategoryRel> relList=this.creativeCategoryRelService.queryByCid(creativeId);
		//目前认为一个作品没有多分类，因此一律使用第一个分类
		if(relList!=null && relList.size()>0 && relList.get(0)!=null){
			CreativeCategory c=creativeCategoryService.queryById(relList.get(0).getCategoryId());
			this.nextAndPrevCreative(c.getParentId());
		}
		return SUCCESS;
	}
	
	public String empty(){
		creativeCategoryList = creativeCategoryService.queryCategoryTree();
		return "empty_success";
	}
	
	/**
	 * 下一篇作品和上一篇作品
	 * @param 频道id
	 */
	private void nextAndPrevCreative(Long parentCategoryId){
		if(parentCategoryId==null || parentCategoryId<=0){
			return ;
		}
		//时间倒序排列
		List<Creative> creativeList=this.homePageItemService.snsDetailCreativeByParentCId(parentCategoryId, true);
		int matchIndex=-1;
		int totalCreativeSize=creativeList.size();
		if(creativeList!=null && creativeList.size()>0){
			for(int i=0;i<totalCreativeSize;i++){
				Creative c=creativeList.get(i);
				if(this.creativeId.longValue()==c.getId()){
					matchIndex=i;
					break;
				}
			}
		}
		//没有匹配
		if(matchIndex==-1 || totalCreativeSize<=1 || matchIndex>totalCreativeSize-1){
			return;
		}
		int prev=matchIndex-1;
		int next=matchIndex+1;
		
		if(prev>=0 && prev<totalCreativeSize){
			if(this.prevCreativeId==null){
				this.prevCreativeId=creativeList.get(prev).getId();
			}
			this.litterPrevCreativeId=creativeList.get(prev).getId();
		}
		
		if(next>=0 && next<totalCreativeSize){
			if(this.nextCreativeId==null){
				this.nextCreativeId=creativeList.get(next).getId();
			}
			this.litterNextCreativeId=creativeList.get(next).getId();
		}
	}
		
	/**
	 * 图片链接拼接
	 */
	private void picContent(){
		this.articleExList=this.articleExService.getByArticleIdAndImgType(this.article.getId(),PojoConstant.ARTICLE_EX.IMG_TYPE_NORMAL);
		StringBuilder sb=new StringBuilder("");
		if(articleExList!=null && articleExList.size()>0){
			for(ArticleEx articleEx:articleExList){
				if(articleEx.getConType()==PojoConstant.ARTICLE_EX.CTYPE_IMAGE){
					sb.append("<img src=\"").append(this.systemProp.getStaticServerUrl()).append(articleEx.getImgPath()).append("\" />");
				}else if(articleEx.getConType()==PojoConstant.ARTICLE_EX.CTYPE_MUSIC){
					sb.append(articleEx.getPath());
				}else if(articleEx.getConType()==PojoConstant.ARTICLE_EX.CTYPE_VIDEO){
					sb.append("<embed src=\"").append(articleEx.getPath()).append("\" allowFullScreen=\"true\" quality=\"high\" width=\"800\" height=\"500\" align=\"middle\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\"></embed>");
				}
			}
		}
		this.imgContent=sb.toString();
	}
	/**
	 * 推荐阅读
	 */
	private void recommend(){
		//推荐-----------------------------------------start
		//同类推荐
		List<Creative> tempSameList=this.creativeService.querySamePIdCreativeList(creativeId,0,10);
		//同标签文章
		if(tempSameList==null || tempSameList.size()<=0){
			tempSameList=this.creativeService.querySameTagCreativeByCId(creativeId,0,10);
		}
		if(tempSameList==null || tempSameList.size()<=6){
			this.sameCreativeList=tempSameList;
		}else{
			//从10个中随机选择6个
			this.sameCreativeList=new ArrayList<Creative>();
			int k=tempSameList.size()-1;
			for(;sameCreativeList.size()<=5;k--){
				int index=(int)(Math.random()*k);
				this.sameCreativeList.add(tempSameList.get(index));
				tempSameList.remove(index);
			}
		}
		//推荐-----------------------------------------end
	}
	
	
	/*public String execute(){
		if(creativeId==null || this.creativeId<=0){
			log.error("作品id，creativeId必须大于0，跳转到首页");
			return "index";
		}
		this.creative=creativeService.queryById(this.creativeId);
		if(this.creative==null || this.creative.getStatus()==null || this.creative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK){
			log.error("作品不存在或作品被删除，跳转到首页,creativeId:"+this.creativeId);
			return "index";
		}
		user=this.userService.getUserById(this.creative.getUserId());
		List<Article> articleList=this.articleService.queryByCreativeId(creativeId);
		
		if(user==null || user.getStatus()!=PojoConstant.USER.STATUS_ACTIVE.intValue()){
			log.error("这个作品下所对应的作者状态异常,跳转至首页,userId:"+this.creative.getUserId()+",creativeId:"+creativeId);
			return "index";
		}
		if(articleList==null || articleList.size()<=0){
			log.error("这个作品下没有文章,跳转至首页,creativeId:"+this.creativeId);
			return "index";
		}
		List<CreativeCategoryRel> relList=this.creativeCategoryRelService.queryByCid(creativeId);
		CreativeCategoryRel rel=null;
		Date maxRelDate=null;
		Date minRelDate=null;
		if(relList!=null && relList.size()>0){
			rel=relList.get(0);
			maxRelDate=relList.get(0).getUpdateTime();
			if(relList.size()>1){
				minRelDate=relList.get(relList.size()-1).getUpdateTime();
			}else{
				minRelDate=maxRelDate;
			}
		}
		totalCount=articleList.size();
			
		//未传入了文章id
		if(this.articleId==null || this.articleId<=0){
			//由于默认使用排序的倒序排列，因此使用最后一个
			this.article=articleList.get(0);
			//下一个
			if(totalCount>1){
				this.nextArticleId=articleList.get(1).getId();
				this.nextCreativeId=this.creativeId;
			}
		}else{
			//传入了文章id
			this.article=this.articleService.queryById(this.articleId);
			if(this.article==null || this.article.getStatus()==null || this.article.getStatus()!=PojoConstant.ARTICLE.STATUS_OK){
				log.error("通过文章id查询不到文章或者文章已经被删除，跳转到首页,articleId:"+this.articleId);
				return "index";
			}
			
			int currentArticleIndex=-1;
			for(int i=0;i<totalCount;i++){
				if(articleId==articleList.get(i).getId().longValue()){
					currentArticleIndex=i;
					break;
				}
			}
			if(currentArticleIndex==-1){
				log.error("作品(creativeId:"+this.creativeId+")下无这个文章(articleId:"+this.articleId+")");
				return "index";
			}
			int preIndex=currentArticleIndex-1;
			int nextIndex=currentArticleIndex+1;
			if(preIndex>=0 && preIndex<totalCount){
				this.prevArticleId=articleList.get(preIndex).getId();
				this.prevCreativeId=this.creativeId;
			}
			if(nextIndex>=0 && nextIndex<totalCount){
				this.nextArticleId=articleList.get(nextIndex).getId();
				this.nextCreativeId=this.creativeId;
			}
		}
		//推荐阅读
		this.recommend();
		//给点击图片拼接内容
		this.picContent();
		//查找评论
		commentLst = detailPageService.selCommentLst(this.creativeId, PojoConstant.SORT.TYPE_CREATIVE, 0, 10);
		//非认证编辑没有下一页上一页按钮
		if(creative.getCreativeType()==null || (creative.getCreativeType()!=1)){
			this.nextArticleId=null;
			this.nextCreativeId=null;
			this.prevArticleId=null;
			this.prevCreativeId=null;
			return SUCCESS;
		}
		
		//下一篇作品
		this.nextCreative();
		//上一篇作品
		this.prevCreative();
		return SUCCESS;
	}*/
	
	/*
	 * 下 一篇文章，作品
	 */
	/*private void nextCreative(Date maxRelDate,CreativeCategoryRel rel){
		if(nextCreativeId!=null && litterNextCreativeId!=null){
			return ;
		}
		//下一篇文章------------------------------------start
		List<CreativeCategoryRel> nextCategoryList=this.creativeCategoryRelService.queryOtherCreativeInSameCateogyrByCId(creativeId,
				maxRelDate,false);
		
		//查看同类的下一篇文章
		if( nextCategoryList!=null && nextCategoryList.size()>0){
			CreativeCategoryRel ccr=nextCategoryList.get(0);
			if(ccr!=null && ccr.getCreativeId()!=null && ccr.getCreativeId()>0){
				if(this.nextCreativeId==null){
					this.nextCreativeId=ccr.getCreativeId();
				}
				this.litterNextCreativeId=ccr.getCreativeId();
			}
		}else{
		//查看其它类别的作品
		    Creative c=null;
			CreativeCategory cc=this.creativeCategoryService.queryById(rel.getCategoryId());
			for(long i=cc.getParentId();i<=MAX_PARENT_CATEGORY_ID;i++){
				c=this.creativeService.queryParentCategoryFirstC(i);
				//不能是自己
				if(c!=null && c.getId()==this.creativeId.longValue()){
					c=null;
				}
				if(c!=null){
					break;
				}
			}
			if(c==null){
				c=this.creativeService.queryParentCategoryFirstC(1L);
			}
			if(c!=null){
				if(nextCreativeId==null){
					nextCreativeId=c.getId();
				}
				litterNextCreativeId=c.getId();
			}
			
		}
		
		//查看同标签的下一遍文章
		if(litterNextCreativeId==null || this.nextCreativeId==null){
			List<Creative> nextTagList=this.creativeService.querySameTagCreativeByCId(creativeId,maxRelDate,true);
			if(nextTagList!=null && nextTagList.size()>0){
				Creative c=nextTagList.get(0);
				if(this.nextCreativeId==null){
					this.nextCreativeId=c.getId();
				}
				if(litterNextCreativeId==null){
					this.litterNextCreativeId=c.getId();
				}
			}
		}
		
		//查看时间轴的下一个作品
		if(litterNextCreativeId==null ||  this.nextCreativeId==null){
			Creative c=this.creativeService.queryNeighbourByCreativeId(creativeId, true);
			if(c!=null){
				if(this.nextCreativeId==null){
					this.nextCreativeId=c.getId();
				}
				if(litterNextCreativeId==null){
					this.litterNextCreativeId=c.getId();
				}
			}
		}
		
		//下一篇文章------------------------------------end
		
	}*/
	
	/*
	 * 上 一篇文章，作品
	 */
	/*private void prevCreative(Date minRelDate,CreativeCategoryRel rel){
		if(prevCreativeId!=null && litterPrevCreativeId!=null){
			return;
		}
		
		//上一篇文章--------------------------------------start
		List<CreativeCategoryRel> prevCategoryList=this.creativeCategoryRelService.queryOtherCreativeInSameCateogyrByCId(creativeId,
				minRelDate,true);
		//查看同类的上一篇文章
		if( prevCategoryList!=null && prevCategoryList.size()>0){
			CreativeCategoryRel ccr=prevCategoryList.get(0);
			if(ccr!=null && ccr.getCreativeId()!=null && ccr.getCreativeId()>0){
				if(this.prevCreativeId==null){
					this.prevCreativeId=ccr.getCreativeId();
				}
				this.litterPrevCreativeId=ccr.getCreativeId();
			}
		}else{
			//查看其它类别的作品
			Creative c=null;
			CreativeCategory cc=this.creativeCategoryService.queryById(rel.getCategoryId());
			for(long i=cc.getParentId();i>=1;i--){
				c=this.creativeService.queryParentCategoryFirstC(i);
				//不能是自己
				if(c!=null && c.getId()==this.creativeId.longValue()){
					c=null;
				}
				if(c!=null){
					break;
				}
			}
			if(c==null){
				c=this.creativeService.queryParentCategoryFirstC(MAX_PARENT_CATEGORY_ID);
			}
			if(c!=null){
				if(prevCreativeId==null){
					prevCreativeId=c.getId();
				}
				litterPrevCreativeId=c.getId();
			}
		}
		
		
		//查看同标签的上一遍文章
		if(litterPrevCreativeId==null ||  this.prevCreativeId==null){
			List<Creative> prevTagList=this.creativeService.querySameTagCreativeByCId(creativeId,minRelDate,false);
			if(prevTagList!=null && prevTagList.size()>0){
				Creative c=prevTagList.get(0);
				if(this.prevCreativeId==null){
					this.prevCreativeId=c.getId();
				}
				if(litterPrevCreativeId==null){
					this.litterPrevCreativeId=c.getId();
				}
			}
		}
		
		//查看时间轴的上一个作品
		 if(litterPrevCreativeId==null ||  this.prevCreativeId==null){
			Creative c=this.creativeService.queryNeighbourByCreativeId(creativeId, false);
			if(c!=null){
				if(this.prevCreativeId==null){
					this.prevCreativeId=c.getId();
				}
				if(litterPrevCreativeId==null){
					this.litterPrevCreativeId=c.getId();
				}
			}
		}
		//上一篇文章--------------------------------------end
	}*/
	
	
	/**
	 * 作品id
	 */
	private Long creativeId;
	
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 作品
	 */
	private Creative creative;
	/**
	 * 文章
	 */
	private Article article;
	/**
	 * 文章图片
	 */
	private List<ArticleEx> articleExList;
	/**
	 * 对应的用户
	 */
	private User user;
	
	
	
	/**
	 * 上一篇文章id
	 */
	private Long prevArticleId;
	
	/**
	 * 下一篇文章id
	 */
	private Long nextArticleId;
	
	/**
	 * 上一篇作品id
	 */
	private Long prevCreativeId;
	
	/**
	 * 下一篇作品id
	 */
	private Long nextCreativeId;
	
	/**
	 * 同类作品
	 */
	private List<CreativeCategoryRel> ccrList;
	
	/**
	 * 同类标签或同类类目对应的作品
	 */
	private List<Creative> sameCreativeList;
	
	private List<CreativeCommentResult> commentLst;
	
	/**
	 * 下一篇作品
	 */
	private Long litterNextCreativeId;
	/**
	 * 上一篇作品
	 */
	private Long litterPrevCreativeId;
	
	/**
	 * 图片内容
	 */
	private String imgContent;
	
	/**
     * 新版首页一级和二级分类的集合
     */
    private List<CreativeCategory> creativeCategoryList;
	
	
    
	public List<CreativeCategory> getCreativeCategoryList() {
		return creativeCategoryList;
	}

	public void setCreativeCategoryList(List<CreativeCategory> creativeCategoryList) {
		this.creativeCategoryList = creativeCategoryList;
	}

	public String getImgContent() {
		return imgContent;
	}
	private int totalCount;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Long getLitterNextCreativeId() {
		return litterNextCreativeId;
	}

	public void setLitterNextCreativeId(Long litterNextCreativeId) {
		this.litterNextCreativeId = litterNextCreativeId;
	}

	public Long getLitterPrevCreativeId() {
		return litterPrevCreativeId;
	}

	public void setLitterPrevCreativeId(Long litterPrevCreativeId) {
		this.litterPrevCreativeId = litterPrevCreativeId;
	}

	public List<CreativeCommentResult> getCommentLst() {
		return commentLst;
	}

	public void setCommentLst(List<CreativeCommentResult> commentLst) {
		this.commentLst = commentLst;
	}

	public List<Creative> getSameCreativeList() {
		return sameCreativeList;
	}

	public void setSameCreativeList(List<Creative> sameCreativeList) {
		this.sameCreativeList = sameCreativeList;
	}

	public List<CreativeCategoryRel> getCcrList() {
		return ccrList;
	}

	public void setCcrList(List<CreativeCategoryRel> ccrList) {
		this.ccrList = ccrList;
	}

	public Long getPrevCreativeId() {
		return prevCreativeId;
	}

	public Long getNextCreativeId() {
		return nextCreativeId;
	}

	public Long getPrevArticleId() {
		return prevArticleId;
	}

	public Long getNextArticleId() {
		return nextArticleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(Long creativeId) {
		this.creativeId = creativeId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Creative getCreative() {
		return creative;
	}

	public void setCreative(Creative creative) {
		this.creative = creative;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<ArticleEx> getArticleExList() {
		return articleExList;
	}

	public void setArticleExList(List<ArticleEx> articleExList) {
		this.articleExList = articleExList;
	}

	public List<LooItem> getLooItemList() {
		return looItemList;
	}

	public void setLooItemList(List<LooItem> looItemList) {
		this.looItemList = looItemList;
	}

	public LooArticle getLooArticle() {
		return looArticle;
	}

	public void setLooArticle(LooArticle looArticle) {
		this.looArticle = looArticle;
	}

	public List<Map> getSameLooArticleList() {
		return sameLooArticleList;
	}

	public void setSameLooArticleList(List<Map> sameLooArticleList) {
		this.sameLooArticleList = sameLooArticleList;
	}
}
