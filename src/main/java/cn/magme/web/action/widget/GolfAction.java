package cn.magme.web.action.widget;

import java.util.List;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Article;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.sns.ArticleEx;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.UserEnjoyService;
import cn.magme.service.sns.ArticleExService;
import cn.magme.service.sns.ArticleService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.sns.CreativeService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class GolfAction extends BaseAction {
	@Resource
	private CreativeCategoryService creativeCategoryService;
	@Resource
	private CreativeService creativeService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleExService articleExService;
	@Resource
	private UserEnjoyService userEnjoyService;
	
	private long fid;//一级分类
	private long sid;//二级分类
	private long cid;//作品id
	private long aid;//文章id
	
	private Integer begin;
	private Integer size;
	
	public String firstType(){
		this.jsonResult= JsonResult.getFailure();
		List<CreativeCategory> firstList = creativeCategoryService.queryCategoryFirstGolf();
		if(firstList!=null){
			this.jsonResult.put("firstList", firstList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	public String secondType(){
		this.jsonResult= JsonResult.getFailure();
		CreativeCategory fc = creativeCategoryService.queryByIdByGolf(fid);
		List<CreativeCategory> secondList = creativeCategoryService.queryCategoryFirstGolf(fid);
		if(secondList!=null){
			for (CreativeCategory creativeCategory : secondList) {
				Long sceondId = creativeCategory.getId();
				String imgPath = articleService.imgBySecondCategory(sceondId);
				creativeCategory.setSeoTitle(imgPath);
			}
			//增加父分类在分类列表的起始位置
			if(fc!=null)
				secondList.add(0, fc);
			this.jsonResult.put("secondList", secondList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	public String queryByCCategoryId(){
		this.jsonResult= JsonResult.getFailure();
		List<Creative> cList = creativeService.queryByCCategoryId(sid,5,begin,size);
		if(cList!=null){
			this.jsonResult.put("creativeList", cList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	public String queryByCreativeId(){
		this.jsonResult= JsonResult.getFailure();
		List<Article> aList = articleService.queryByCreativeId(cid);
		if(aList!=null){
			for (Article article : aList) {
				List<ArticleEx> tmpList = articleExService.queryByCreativeId(cid);
				if(tmpList!=null&&tmpList.size()>0){
					for (ArticleEx articleEx : tmpList) {
						if(articleEx!=null){
							Integer xType = articleEx.getConType();
							if(xType==4){//视频
								articleEx.setPath(pathChange(articleEx.getPath()));
							}
						}
					}
				}
				article.setArticleExList(tmpList);
			}
			this.jsonResult.put("articleList", aList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	public String enjoyByCreativeId(){
		  this.jsonResult=userEnjoyService.change(this.getSessionUserId(),cid, PojoConstant.USERENJOY.TYPE_GOLF);
		  return JSON;
	}
	
	public String enjoyCountByCreativeId(){
		  this.jsonResult=userEnjoyService.enjoyCountByCreativeId(this.getSessionUserId(),cid, PojoConstant.USERENJOY.TYPE_GOLF);
		  return JSON;
	}
	
	
	private String pathChange(String path){
		String backPath = path;
		//String tudouPath="http://www.tudou.com/listplay/9K9jNdHX8vY/";
		String youkuPath="http://v.youku.com/v_show/id_";
		String keyStr ="";
//		if(path.contains("tudou")){//土豆视频
//			keyStr = pathKeyGet(path);
//			if(keyStr!=null){
//				backPath = tudouPath + keyStr +".html";
//			}
//		}
		if(path.contains("youku")){//优酷视频
			keyStr = pathKeyGet(path);
			if(keyStr!=null){
				backPath = youkuPath + keyStr +".html";
			}
		}
		return backPath;
	}
	
	private String pathKeyGet(String path){
		String backStr = null;
		try{
			if(StringUtil.isNotBlank(path)&&path.lastIndexOf("/")>-1){
				String tmpPath = path.substring(0,path.lastIndexOf("/"));
				if(StringUtil.isNotBlank(tmpPath)&&tmpPath.lastIndexOf("/")>-1){
					backStr = tmpPath.substring(tmpPath.lastIndexOf("/")+1);
				}
			}
			}catch(Exception ex){return null;}
		return backStr;
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	

}
