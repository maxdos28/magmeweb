/*package cn.magme.web.action.sns;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.CreativeCategoryRel;
import cn.magme.pojo.sns.Creative;
import cn.magme.pojo.sns.CreativeEx;
import cn.magme.service.sns.CreativeCategoryRelService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.sns.CreativeService;
import cn.magme.service.sns.SnsCreativeExService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.util.StringUtil;
import cn.magme.util.UploadPictureUtil;
import cn.magme.web.action.BaseAction;

import com.danga.MemCached.MemCachedClient;
import com.opensymphony.xwork2.ActionContext;

 *//**
  * 作品发布
  * @author fredy
  * @since 2012-12-3
  *//*
@Results({@Result(name="success",location="/WEB-INF/pages/sns/creativePub.ftl"),
	@Result(name="preview",location="/WEB-INF/pages/sns/creativePubPreview.ftl")})
public class CreativePubAction extends BaseAction {
	private static final long serialVersionUID = 5778448612254476187L;
	@Resource
	private CreativeService creativeService;
	@Resource
	private CreativeCategoryService creativeCategoryService;
	@Resource
	private SnsCreativeExService snsCreativeExService;
	//@Resource
	private MemCachedClient memCachedClient;
	@Resource
	private CreativeCategoryRelService creativeCategoryRelService;
	@Resource
	private SnsCreativeService snsCreativeService;
	
	private static final String IMAG_PREFIX="/snsimg";
	
	private static final Logger log=Logger.getLogger(CreativePubAction.class);
	public static final String TYPE_SET_SUCCESS="typeset_success";
	private final String operate="works";
	
	优化代码,模板使用直接输出的方式,去除这段代码
	 * private static final String TEMPLATE_TITLE="<h1>.*</h1>"; 
	
	private static final String TEMPLATE_CONTENT="<div class=\"source\">.*</div><imaplacehold>";
	
	private static final String TEMPLATE_IMG="<a href=\"#\" class=\"pic p1\"></a>";
	
	private static final String TEMPLATE_IMG_PLACEHOLD="<imaplacehold>";
	
	private static final String TEMPLATE_REMOVE_HEADER=".*<body>";
	
	private static final String TEMPLATE_REMOVE_FOOTER="<imaplacehold>.*";
	
	private static final String TEMPLATE_ADD_HEADER="<script src='systemProp.staticServerUrl/v3/template/js/jquery-ui-1.8.21.custom.min.js'></script>\n"
                                                    +"<script src='systemProp.staticServerUrl/v3/template/js/jquery.mCustomScrollbar.js'></script>\n"
                                                    +"<script src='systemProp.staticServerUrl/v3/template/js/tpl.js'></script>"
                                                    +"<link rel='stylesheet' type='text/css' href='systemProp.staticServerUrl/v3/template/style/jquery.mCustomScrollbar.css' />\n"
                                                    +"<link rel='stylesheet' type='text/css' href='systemProp.staticServerUrl/v3/template/style/tpl.css' />\n";
                                                    //+"<script>$(function(){fnGenerate();});</script>";
	
	private static final String TEMPLATE_ADD_FOOTER="<imaplacehold></div>";
	
	
	
	*//**
	 * 模板选择图片分界线
	 *//*
	public static final int TEMPLATE_PIC_COUNT=10;
	
	*//**
	 * 模板选择字数分界线
	 *//*
	public static final int TEMPLATE_WORD_COUNT=100;
	*//**
	 * 发布作品的初始页面
	 *//*
	public String execute(){
		this.deleteCacheOrSession();
		this.id=null;//发布作品，设置id为null
		creativeCategoryTree=creativeCategoryService.queryCategoryTree();
		return SUCCESS;
	}
	
	*//**
	 * 制作好页面后，执行排版链接跳转
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public String typeSetting(){
		this.jsonResult=JsonResult.getFailure();
		try {
			if(this.memCachedClient!=null){
				this.creative=(Creative)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
				this.creativeExList=(List<CreativeEx>)this.memCachedClient.get(CacheConstants.CREATIVE_EX_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
				
				this.categoryIds=(List<Long>)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CATEGORY_CACHE_PREFIX+this.getSessionJsessionid());
				this.picCollectionIds=(List<Long>)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CATEGORY_PIC_CACHE_PREFIX+this.getSessionJsessionid());
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(this.creative==null ){
			this.creative=(Creative)ActionContext.getContext().getSession().get("creative");
		}
		if(this.creativeExList==null || this.creativeExList.size()<=0){
			this.creativeExList=(List<CreativeEx>)ActionContext.getContext().getSession().get("creativeExList");
		}
		//0 普通用户，1 认证编辑
		int userlevel=(this.getSessionUser().getReserve1()!=null && this.getSessionUser().getReserve1().equalsIgnoreCase(PojoConstant.CREATIVE.AUDIT_EDITOR) )?1:0;
		if(userlevel==1){
			if(this.categoryIds==null || this.categoryIds.size()<=0){
				this.categoryIds=(List<Long>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS);
			}
			
			if(this.picCollectionIds==null || this.picCollectionIds.size()<=0){
				this.picCollectionIds=(List<Long>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS);
			}
		}
		
		
		if(StringUtil.isNotBlank(this.tempPattern)){
			//TODO 校验模板合法性
			//this.creative.setTempPattern(this.tempPattern);
		}
		
		this.jsonResult=this.creativeService.pubCreative(creative, creativeExList, categoryIds, picCollectionIds);
		
		return TYPE_SET_SUCCESS;
	}
	
	*//**
	 * 发布作品
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public String pub(){
		this.jsonResult=JsonResult.getFailure();
		// 从缓存或者session中读取数据
		if(this.memCachedClient!=null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, CacheConstants.CACHE_ONE_HOUR);//1小时后失效
			creative=(Creative)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
			creativeExList=(List<CreativeEx>)this.memCachedClient.get(CacheConstants.CREATIVE_EX_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
			categoryIds=(List<Long>)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CATEGORY_CACHE_PREFIX+this.getSessionJsessionid());
			picCollectionIds=(List<Long>)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CATEGORY_PIC_CACHE_PREFIX+this.getSessionJsessionid());	
		}
        if(creative==null){
        	creative=(Creative)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CREATIVE);
        	creativeExList=(List<CreativeEx>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CREATIVE_EX);
        	categoryIds=(List<Long>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS);
        	picCollectionIds=(List<Long>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS);
        }
			
		if(StringUtil.isNotBlank(this.tempPattern)){
			//creative.setTempPattern(tempPattern);
		}
		this.jsonResult=this.creativeService.pubCreative(creative, creativeExList, categoryIds, picCollectionIds);
		return JSON;
	}
	
	*//**
	 * 编辑作品
	 * @return
	 *//*
	public String edit(){
		this.deleteCacheOrSession();
		this.edit=1;
        creativeCategoryTree=creativeCategoryService.queryCategoryTree();
        //编辑
        if(id!=null){
        	if(id<=0){
        		throw new RuntimeException("作品id不合法:id"+id);
        	}else{
        		this.creative=this.creativeService.queryById(id);
        		//非正常作品是不能编辑的
        		if(creative==null || this.creative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK ){
        			throw new RuntimeException("作品处于无效状态，或者作品不存在:id"+id);
        		}
        		//TODO 不能编辑自己的作品，为了测试方便，暂时注释
        		if(this.getSessionUserId().longValue()!=this.creative.getUserId()){
        			throw new RuntimeException("不能编辑别人的作品"+id);
        		}
        		
        	}
        }
		return SUCCESS;
	}
	
	
	//获取作品内容
	public String getEditCreativeEx(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		List<CreativeEx> list = snsCreativeExService.getCreativeEx(id);
		this.creativeCategoryRelList=creativeCategoryRelService.queryByCid(id);
		this.jsonResult.put("list", list);
		this.jsonResult.put("creativeCategoryRelList", creativeCategoryRelList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	*//**
	 * 作品制作，把作品数据保存在session中，这就意味着一次一个用户只能制作一个作品
	 * @return
	 *//*
	public String makeJson(){
		//默认失败
		this.jsonResult=JsonResult.getFailure();
		//校验数据
		//title
		if(StringUtil.isBlank(this.title) || this.title.length()>PojoConstant.CREATIVE.MAX_TITLE_LENGHT){
			this.jsonResult.setMessage("标题长度为空或者大于最大长度:"+PojoConstant.CREATIVE.MAX_TITLE_LENGHT);
			return JSON;
		}
		//过滤html
		this.title=StringUtil.escapeHtml(this.title);
		//content和conList不能同时为空
		if(StringUtil.isBlank(this.content) && (this.conList==null || this.conList.size()<=0)){
			this.jsonResult.setMessage("内容和(图片，视频，音乐)不能同时为空");
			return JSON;
		}
		//0 普通用户，1 认证编辑
		int userlevel=(this.getSessionUser().getReserve1()!=null && this.getSessionUser().getReserve1().equalsIgnoreCase(PojoConstant.CREATIVE.AUDIT_EDITOR) )?1:0;
		List<Map<String,String>> contentImgUrls=null;
		if(userlevel==1){
			if(StringUtil.isNotBlank(this.secondTitle)){
				if(this.secondTitle.length()>PojoConstant.CREATIVE.MAX_SECOND_TITLE_LENGHT){
					this.jsonResult.setMessage("显示标题长度为空或者大于最大长度:"+PojoConstant.CREATIVE.MAX_TITLE_LENGHT);
					return JSON;
				}
				this.secondTitle=StringUtil.escapeHtml(this.secondTitle);
			}else{
				this.secondTitle=this.title;
			}
			if(StringUtil.isNotBlank(this.secondDescription)){
				if(this.secondDescription.length()>PojoConstant.CREATIVE.MAX_SECOND_DESCRIPTION_LENGHT){
					this.jsonResult.setMessage("显示描述长度为空或者大于最大长度:"+PojoConstant.CREATIVE.MAX_SECOND_DESCRIPTION_LENGHT);
					return JSON;
				}
				this.secondDescription=StringUtil.escapeHtml(secondDescription);
			}
			//TODO 校验 magazineUrl,publicationid,issueid,magazineName这些都是来自于magazineUrl，因此以它为准
			if(StringUtil.isBlank(this.magazineUrl)){
				this.magazineUrl=null;
				this.publicationid=null;
				this.issueid=null;
				this.magazineName=null;
			}
			
			//分类
			if(categoryIds==null || categoryIds.size()<=0){
				this.jsonResult.setMessage("认证编辑必须选择分类");
				return JSON;
			}else{
				//校验分类
				List<CreativeCategory> ccList=this.creativeCategoryService.queryByIds(categoryIds);
				if(ccList==null || ccList.size()!=categoryIds.size()){
					this.jsonResult.setMessage("有分类id不合法,categoryIds:"+ToStringBuilder.reflectionToString(categoryIds));
					return JSON;
				}
			}
			
			//TODO 校验图片
			
			//校验content
			if(StringUtil.isNotBlank(this.content)){
				Map<String,Object> backStr=StringUtil.HtmlTextByEdit(this.content, this.systemProp.getCreativeImgUrl());
				if(backStr.get("content")==null || StringUtil.isBlank(backStr.get("content").toString()) 
						|| backStr.get("content").toString().length()>PojoConstant.CREATIVE.MAX_CONTENT_LENGHT){
					this.jsonResult.setMessage("内容长度为空或者大于最大长度:"+PojoConstant.CREATIVE.MAX_CONTENT_LENGHT);
					return JSON;
				}
				this.content=backStr.get("content").toString();
				//内容中的图片
				contentImgUrls=(List<Map<String,String>>)backStr.get("pathImgList");
			}
			
			
		}else{
			//普通用户，这些字段直接标识为空，防止用户使用技术手段提交这些字段
			this.secondTitle=this.title;
			this.secondDescription=null;
			this.categoryIds=null;
			this.magazineUrl=null;
			this.publicationid=null;
			this.issueid=null;
			this.magazineName=null;
		}
		
		//自动填充
		//显示描述为空的时候，使用内容的前20个字代替显示描述
		if( StringUtil.isBlank(this.secondDescription) && StringUtil.isNotBlank(this.content) ){
			String tmpDesc=StringUtil.escapeHtml(this.content);
			if(StringUtil.isNotBlank(tmpDesc) && tmpDesc.length()>PojoConstant.CREATIVE.MAX_SECOND_DESCRIPTION_LENGHT){
				this.secondDescription=tmpDesc.substring(0,PojoConstant.CREATIVE.MAX_SECOND_DESCRIPTION_LENGHT);
			}else{
				this.secondDescription=tmpDesc;
			}
			
		}
		
		if(id!=null){
			creative=this.creativeService.queryById(id);
			if(creative==null || this.creative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK ){
    			log.error("作品处于无效状态，或者作品不存在:id"+id);
    			this.jsonResult.setMessage("作品处于无效状态，或者作品不存在:id"+id);
    			return JSON;
    		}
			//TODO 校验是否是自己的作品
		}else{
			creative=new Creative();
		}
		
		creative.setId(id);
		creative.setContent(this.content);
		creative.setcType(PojoConstant.CREATIVE.CREATIVE_C_TYPE);
		creative.setIshome(isHome);
		creative.setIsrecommend(PojoConstant.CREATIVE.IS_RECOMMEND_NO);
		creative.setIssueid(issueid);
		creative.setMagazineName(magazineName);
		creative.setMagazineUrl(magazineUrl);
		creative.setPloy(ploy);
		creative.setPublicationid(publicationid);
		//creative.setSecondDescription(secondDescription);
		creative.setMagazineUrl(magazineUrl);
		//creative.setSecondTitle(secondTitle);
		creative.setStatus(PojoConstant.CREATIVE.STATUS_OK);
		creative.setTitle(title);
		creative.setShowType(0);
		creative.setUserId(this.getSessionUserId());
		
		//图片链接保存
		creativeExList=this.parseConList();
		
		//将编辑器中的图片链接全部转换成creativeex
		if(contentImgUrls!=null && contentImgUrls.size()>0){
			List<CreativeEx> edImgCreativeExList=this.parseContentImgList(contentImgUrls);
			if(edImgCreativeExList!=null && edImgCreativeExList.size()>0){
				creativeExList.addAll(edImgCreativeExList);
			}
		}
		//模板设置
		//creative.setTempPattern(this.choosePatternTemplateName());
		
		// 数据放入mem或session中存放
		if(this.memCachedClient!=null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, CacheConstants.CACHE_ONE_HOUR);//1小时后失效
			this.memCachedClient.set(CacheConstants.CREATIVE_MAKE_CACHE_PREFIX+this.getSessionJsessionid(), creative, cal.getTime());
			if(creativeExList!=null && creativeExList.size()>0){
				this.memCachedClient.set(CacheConstants.CREATIVE_EX_MAKE_CACHE_PREFIX+this.getSessionJsessionid(), creativeExList, cal.getTime());
			}
			if(this.categoryIds!=null && this.categoryIds.size()>0){
				this.memCachedClient.set(CacheConstants.CREATIVE_MAKE_CATEGORY_CACHE_PREFIX+this.getSessionJsessionid(),this.categoryIds,cal.getTime());
			}
			if(this.picCollectionIds!=null && picCollectionIds.size()>0){
				this.memCachedClient.set(CacheConstants.CREATIVE_MAKE_CATEGORY_PIC_CACHE_PREFIX+this.getSessionJsessionid(),this.picCollectionIds,cal.getTime());	
			}
			
		}else{
			ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_CREATIVE, creative);
			if(creativeExList!=null && creativeExList.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_CREATIVE_EX, creativeExList);
			}
			if(this.categoryIds!=null && this.categoryIds.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS, categoryIds);
			}
			if(this.picCollectionIds!=null && picCollectionIds.size()>0){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS, picCollectionIds);	
			}
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
		if(this.memCachedClient!=null){
			this.creative=(Creative)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
			this.creativeExList=(List<CreativeEx>)this.memCachedClient.get(CacheConstants.CREATIVE_EX_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
		}else{
			this.creative=(Creative)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CREATIVE);
			this.creativeExList=(List<CreativeEx>)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_KEY_CREATIVE_EX);
		}
	    this.tempPattern=this.choosePatternTemplateName();
	    this.imgCount=this.tempPattern.substring(0,tempPattern.length()-1);
		return "preview";
	}
	*//**
	 * 选择模板的算法
	 * 模板名：0a,0b,0c,1a,1b,1c=========10a,10b,10c
     * 图片数量，0-10
     * 文字100以下a,200以下b,200+c
	 * @return
	 *//*
	private String  choosePatternTemplateName(){
		Integer picCountTemp=0;//默认值
		String wordCountTemp="a";
		if(this.creativeExList!=null && creativeExList.size()>0){
			int size=creativeExList.size();
			int integerPart=size/TEMPLATE_PIC_COUNT;
			int otherPart=size%TEMPLATE_PIC_COUNT;
			if(integerPart>0){
				picCountTemp=10;
			}else{
				picCountTemp=otherPart;
			}
		}
		
		if(StringUtil.isNotBlank(this.creative.getContent())){
			int wordLenght=this.creative.getContent().length();
			if(wordLenght<=TEMPLATE_WORD_COUNT){
				wordCountTemp="a";
			}else if(wordLenght<=TEMPLATE_WORD_COUNT*2){
				wordCountTemp="b";
			}else{
				wordCountTemp="c";
			}
		}
		this.tempPattern="a";
		this.imgCount=String.valueOf("1");
		//TODO 填充模板
		return "1a";
		//TODO 为了测试，现在固定返回1a
		//this.tempPattern=wordCountTemp;
		//this.imgCount=String.valueOf(picCountTemp);
		//return picCountTemp+wordCountTemp;
	}
	
	@SuppressWarnings("unchecked")
	public String changeTempJson(){
		this.jsonResult=JsonResult.getFailure();
		try {
			if(this.memCachedClient!=null){
				this.creative=(Creative)this.memCachedClient.get(CacheConstants.CREATIVE_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
				this.creativeExList=(List<CreativeEx>)this.memCachedClient.get(CacheConstants.CREATIVE_EX_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(this.creative==null ){
			this.creative=(Creative)ActionContext.getContext().getSession().get("creative");
		}
		if(this.creativeExList==null || this.creativeExList.size()<=0){
			this.creativeExList=(List<CreativeEx>)ActionContext.getContext().getSession().get("creativeExList");
		}
		String templateName=this.choosePatternTemplateName();
		String template=this.getCachedTemplate(templateName);
		String templateContent=this.completeTemplate(template, creative, creativeExList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("temp", templateName);
		this.jsonResult.put("templateContent", templateContent);
		return JSON;
	}
	
	public String test(){
		this.changeTempJson();
		this.content=(String)this.jsonResult.get("templateContent");
		return "test";
	}
	
	*//**
	 * 将编辑器中图片信息转换成creativeex
	 * @param contentImgUrls
	 * @return
	 *//*
	private List<CreativeEx> parseContentImgList(List<Map<String,String>> contentImgUrls){
		if(contentImgUrls==null || contentImgUrls.size()<=0){
			return null;
		}
		List<CreativeEx> exList=new ArrayList<CreativeEx>();
		//没有上传图片，视频，音乐
		if(this.maxPosition==0){
			this.maxPosition=-1;
		}
		for(Map<String,String> contentImgUrl:contentImgUrls){
			CreativeEx ex = new CreativeEx();
			ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
			ex.setContent(null);
			ex.setCreativeId(creative.getId());
			ex.setImgPath(contentImgUrl.get("path"));
			ex.setPosition(++this.maxPosition);
			ex.setStatus(PojoConstant.CREATIVE_EX.STATUS_OK);
			ex.setW(Integer.valueOf(contentImgUrl.get("width")));
			ex.setH(Integer.valueOf(contentImgUrl.get("high")));
			exList.add(ex);
		}
		return exList;
	}
	
	*//**
	 * 解析音乐，图片和视频
	 * @return
	 *//*
	private List<CreativeEx> parseConList(){
		List<CreativeEx> exl = new ArrayList<CreativeEx>();
		List<CreativeEx> exList =new ArrayList<CreativeEx>(); 
		if(id!=null && id>0){
			exList=snsCreativeExService.getCreativeEx(creative.getId());
		}
		try{
			//解析作品中的 图片、音乐、视频
			if(null!=conList && conList.size()>0 && creative!=null){
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					boolean wflag=true;
					//修改作品
					for (int j = 0; j < exList.size(); j++) {
						CreativeEx cEx = exList.get(j);
						if(list[0].equals("video") && (cEx.getImgPath().equals(list[1]) || list[3].equals(cEx.getPath()))){
							int eid=Integer.valueOf(list[5]);
							if(cEx.getId().intValue()==eid && creative.getId().intValue()==cEx.getCreativeId().intValue()){
								wflag=false;
								cEx.setContent(list[2]);
								int position=Integer.valueOf(list[4]);
								this.maxPosition=this.maxPosition<position?position:this.maxPosition;
								cEx.setPosition(position);
								exl.add(cEx);
								
								//snsCreativeExService.updateCreativeEx(w);
								exList.remove(j);
								break;
							}
						}else if(list[0].equals("music") && (cEx.getImgPath().equals(list[1]) || list[3].equals(cEx.getPath()))){
							int eid=Integer.valueOf(list[5]);
							if(cEx.getId().intValue()==eid && creative.getId().intValue()==cEx.getCreativeId().intValue()){
								wflag=false;
								cEx.setContent(list[2]);
								int position=Integer.valueOf(list[4]);
								this.maxPosition=this.maxPosition<position?position:this.maxPosition;
								cEx.setPosition(position);
								
								exl.add(cEx);
								
								//snsCreativeExService.updateCreativeEx(w);
								exList.remove(j);
								break;
							}
						}else if(cEx.getImgPath().equals(list[1].replace(systemProp.getStaticServerUrl(), ""))){
							int eid=Integer.valueOf(list[4]);
							if(cEx.getId().intValue()==eid && creative.getId().intValue()==cEx.getCreativeId().intValue()){
								wflag=false;
								cEx.setContent(list[2]);
								int position=Integer.valueOf(list[3]);
								this.maxPosition=this.maxPosition<position?position:this.maxPosition;
								cEx.setPosition(position);
								
								exl.add(cEx);
								
								//snsCreativeExService.updateCreativeEx(w);
								exList.remove(j);
								break;
							}
						}
					}
					//新增作品
					if(wflag){
						CreativeEx ex = new CreativeEx();
						ex.setCreativeId(creative.getId());
						ex.setStatus(PojoConstant.CREATIVE_EX.STATUS_OK);
						if(list[0].equals("video")){
							ex.setConType(SNSConstant.SNS_CREATIVE_VIDEO);
							ex.setContent(list[2]);
							ex.setPath(list[3]);
							int position=Integer.valueOf(list[4]);
							ex.setPosition(position);
							this.maxPosition=this.maxPosition<position?position:this.maxPosition;
							Map<String,String> uploadInfo=UploadPictureUtil.uploadPicPubUrl(list[1], this.systemProp.getCreativeImgUrl());
							ex.setImgPath(IMAG_PREFIX+uploadInfo.get("path"));
							ex.setW(Integer.valueOf(uploadInfo.get("picwidth")));
							ex.setH(Integer.valueOf(uploadInfo.get("pichigh")));
						}else if(list[0].equals("music")){
							ex.setConType(SNSConstant.SNS_CREATIVE_MUSIC);
							ex.setImgPath(list[1]);
							ex.setContent(list[2]);
							ex.setPath(list[3]);
							int position=Integer.valueOf(list[4]);
							ex.setPosition(position);
							this.maxPosition=this.maxPosition<position?position:this.maxPosition;
							
							Map<String,String> uploadInfo=UploadPictureUtil.uploadPicPubUrl(list[1], this.systemProp.getCreativeImgUrl());
							ex.setImgPath(IMAG_PREFIX+uploadInfo.get("path"));
							ex.setW(Integer.valueOf(uploadInfo.get("picwidth")));
							ex.setH(Integer.valueOf(uploadInfo.get("pichigh")));
						}else{
							ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
							ex.setImgPath(IMAG_PREFIX+list[1].replace(systemProp.getStaticServerUrl(), ""));
							ex.setContent(list[2]);
							int position=Integer.valueOf(list[3]);
							ex.setPosition(position);
							//记录最大位置
							this.maxPosition=this.maxPosition<position?position:this.maxPosition;
							ex.setW(Integer.valueOf(list[4]));
							ex.setH(Integer.valueOf(list[5]));
						}
						exl.add(ex);
//						snsCreativeExService.insertCreativeEx(ex);
					}
				}
				for(CreativeEx ex:exList){
					ex.setStatus(PojoConstant.CREATIVE_EX.STATUS_INVALID);
					exl.add(ex);
					//snsCreativeExService.updateCreativeEx(w);
				}
			}
		}catch (Exception e) {
			log.error("", e);
		}
		
		return exl;
	
	}
	
	*//**
	 * 删除缓存数据
	 *//*
	private void deleteCacheOrSession(){
		// 数据放入mem或session中存放
		try {
			if(this.memCachedClient!=null){
				this.memCachedClient.delete(CacheConstants.CREATIVE_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
				this.memCachedClient.delete(CacheConstants.CREATIVE_EX_MAKE_CACHE_PREFIX+this.getSessionJsessionid());
				this.memCachedClient.delete(CacheConstants.CREATIVE_MAKE_CATEGORY_CACHE_PREFIX+this.getSessionJsessionid());
				this.memCachedClient.delete(CacheConstants.CREATIVE_MAKE_CATEGORY_PIC_CACHE_PREFIX+this.getSessionJsessionid());
			}else{
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_CREATIVE);
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_CREATIVE_EX);
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_CATEGORYIDS);
				ActionContext.getContext().getSession().remove(WebConstant.SESSION.SESSION_KEY_PIC_COLLECTIONS);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	*//**
	 * 查找模板
	 * @param templatePattern
	 * @return
	 *//*
	private String getCachedTemplate(String templatePattern){
		if(StringUtil.isBlank(templatePattern)){
			return null;
		}
		String template=null;
		if(this.memCachedClient!=null){
			template=(String)this.memCachedClient.get(CacheConstants.CREATIVE_TEMPLATE_CACHE_PREFIX+templatePattern);
		}
		if(StringUtil.isBlank(template)){
			File f=new File(this.systemProp.getStaticLocalUrl()+File.separator+"v3"+File.separator+"template"+File.separator+"p"+templatePattern+".html");
			if(!f.exists()){
				log.error("模板不存在");
				return null;
			}
			try {
				StringBuilder tmpStr=new StringBuilder("");
				BufferedReader br=new BufferedReader(new FileReader(f));
				String readline=br.readLine();
				while(readline!=null){
					tmpStr.append(readline);
					readline=br.readLine();
				}
				br.close();
				template=tmpStr.toString();
				template=template.replaceAll(TEMPLATE_REMOVE_HEADER, TEMPLATE_ADD_HEADER);//替换头
				template=template.replaceAll(TEMPLATE_REMOVE_FOOTER, TEMPLATE_ADD_FOOTER);//替换尾
				template=template.replaceAll("systemProp.staticServerUrl",this.systemProp.getStaticServerUrl());
				if(this.memCachedClient!=null){
					this.memCachedClient.set(CacheConstants.CREATIVE_TEMPLATE_CACHE_PREFIX+templatePattern, template);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return template;
		
	}
	
	private String completeTemplate(String template,Creative c,List<CreativeEx> cExList){
		if(StringUtil.isBlank(template)){
			return null;
		}
		//替换标题
		template=template.replaceAll(TEMPLATE_TITLE, "<h1>"+c.getSecondTitle()+"</h1>");
		String content=c.getContent();
		content = content.replaceAll("\r\n", "<br/>");
		content = content.replaceAll("\n", "<br/>");
		//增加用户头像之类的东西
		//<a href="#"><img src="http://static.magme.com/profiles/22924/22924_1343808424710.jpg" /></a><strong>Daniel</strong><span>5分钟前
		StringBuilder userInfo=new StringBuilder("");
		userInfo.append("<a href=\"javascript:void(0)\"><img src=\"");
		userInfo.append(this.systemProp.getProfileServerUrl()+this.getSessionUser().getAvatar60()).append("\"/></a><strong>");
		userInfo.append(this.getSessionUser().getNickName()).append("</strong><span>").append("5分钟前</span></div>");
		content=userInfo.toString()+content;
		template=template.replaceAll(TEMPLATE_CONTENT, "<div class=\"source\">"+content+"</div><imaplacehold>");
		if(cExList!=null && cExList.size()>0){
			StringBuilder exStr=new StringBuilder("");
			for(CreativeEx cEx:cExList ){
				String href=StringUtil.isNotBlank(cEx.getPath())?cEx.getPath():"#";
				exStr.append("<a href=\"javascript:void(0)\"").append("class=\"pic p1\">");
				exStr.append(" <img src=\"").append(cEx.getImgPath()).append("\" ");
				exStr.append("width=\"").append(cEx.getW()).append("\" ");
				exStr.append("height=\"").append(cEx.getH()).append("\" /> ");
				if(StringUtil.isNotBlank(cEx.getContent())){
					exStr.append("<span>").append(cEx.getContent()).append("</span>");
				}
				exStr.append("</a> ");
			}
			template=template.replaceAll(TEMPLATE_IMG_PLACEHOLD, exStr.toString());
		}else{
			template=template.replaceAll(TEMPLATE_IMG_PLACEHOLD, "");
		}
		
		return template;
	}
	private List<CreativeCategory> creativeCategoryTree;
	
	//作品
	private Creative creative;
	
	//作品扩展
	private List<CreativeEx> creativeExList;
	
	private int maxPosition=0;
	
	*//**
	 * 新增作品 edit=0,修改作品edit=1
	 *//*
	private int edit=0;
	
	
	
	public int getEdit() {
		return edit;
	}

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
	
	*//**
	 * 图片url
	 *//*
	private List<String> conList;
	
	*//**
	 * 用户选择的类目id
	 *//*
	private List<Long> categoryIds;
	
	*//**
	 * 用户选择的图集
	 *//*
	private List<Long> picCollectionIds;
	
	
	private List<CreativeCategory> chooseCategoryList;
	
	private List<CreativeCategoryRel> creativeCategoryRelList; 
	
	private String tempPattern;
	
	private String imgCount;
	
	
	
	
	
	public String getImgCount() {
		return imgCount;
	}

	public void setImgCount(String imgCount) {
		this.imgCount = imgCount;
	}

	public String getTempPattern() {
		return tempPattern;
	}

	public void setTempPattern(String tempPattern) {
		this.tempPattern = tempPattern;
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
	
	public List<CreativeEx> getCreativeExList() {
		return creativeExList;
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

	public void setCreative(Creative creative) {
		this.creative = creative;
	}

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
	
	
	
	
	
	
	
	

}
*/