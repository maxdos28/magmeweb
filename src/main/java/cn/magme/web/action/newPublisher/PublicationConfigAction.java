package cn.magme.web.action.newPublisher;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Admin;
import cn.magme.pojo.Category;
import cn.magme.pojo.Publication;
import cn.magme.pojo.PublicationCategoryRel;
import cn.magme.pojo.Publisher;
import cn.magme.service.CategoryService;
import cn.magme.service.PublicationCategoryRelService;
import cn.magme.service.PublicationService;
import cn.magme.util.StringUtil;

import com.opensymphony.xwork2.ActionContext;

@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/publicationConfig.ftl"),
		@Result(name="upload_json",type="json",params={"root","jsonResult","contentType","text/html"})})

public class PublicationConfigAction extends PublisherBaseAction {
	
	private static final long serialVersionUID = 867457713150515125L;
	
	@Resource
	private PublicationService publicationService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private PublicationCategoryRelService publicationCategoryRelService;

	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		Publisher publisher=this.getSessionPublisher();
		Admin admin= this.getSessionAdmin();
		categoryList = categoryService.queryAllChildCategories();
		if(publisher!=null
				&&(publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_0)
				||publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_1))){
			this.publicationList=publicationService.getListByNameAndPublisherId(null, publisher.getId(), null, null,-1);
			return "config";
		}else if(admin!=null){
			this.publicationList=publicationService.getListByNameAndPublisherId(null, null, null, null,-1);
			return "config";
		}else{
			return "deny";
		}
	}
	
	/**
	 * 新建或编辑杂志信息
	 * @return
	 */
	public String doJson(){
		Publisher publisher=this.getSessionPublisher();
		publication.setPublisherId(publisher.getId());//出版商id
		
		this.jsonResult=publicationService.savePublication(publication,this.getSessionPublisherId(), publisher.getLevel());
		//获取对应的杂质集合
		if(publisher!=null
				&&(publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_0)
				||publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_1))){
			this.publicationList=publicationService.getListByNameAndPublisherId(null, publisher.getId(), null, null,-1);
		}else if(publisher!=null
				&&publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_2)){
			this.publicationList=publicationService.getListByNameAndPublisherId(null, null, null, null,-1);
		}
		Map<String,Object> objMap = new HashMap<String,Object>();
		objMap.put("publicationList", this.publicationList);
		this.jsonResult.setData(objMap);
		
		return JSON;
	}
	
	/**
	 * 检测英文名称是否存在
	 * @return
	 */
	public String checkEnglishName(){
		String tempEnglishName = publication.getEnglishname();
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(StringUtil.isNotBlank(tempEnglishName)){
			long countNum = publicationService.queryByEnglishName(tempEnglishName);
			if(countNum==0){
				//可以使用
				this.jsonResult.put("checkStatus", "1");
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}else{
				//已经有重复名称
				this.jsonResult.put("checkStatus", "2");
			}
		}else{
			this.jsonResult.put("checkStatus", "3");//名称不能为空
		}
		return JSON;
	}
	
	/**
	 * 图片上传
	 * @return
	 */
	public String uploadLogoJson() {
		Publisher publisher=this.getSessionPublisher();
		try {
			this.jsonResult=this.publicationService.uploadPublicationLogo(publisher.getId(),publicationId, logoFile, logoFileContentType, logoFileFileName);

			this.jsonResult.put("logoFileName", this.jsonResult.get("avatarFileName"));
//			publication.setLogo((String)this.jsonResult
//					.get("tempAvatarUrl"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "upload_json";
	}
	
	/**
	 * 更新杂志logo
	 * @return
	 */
	public String updatePublicationLogo(){
		Publisher publisher=this.getSessionPublisher();
		this.publication=publicationService.queryById(publicationId);
		publication.setLogo("/"+publisher.getId()+"/"+publicationId+"/"+logoFileName);//杂志logo
		publicationService.updateByIdAndPublisherId(publication);
		//将文件拷贝到url
		this.jsonResult = publicationService.savePublicationLogo(publisher.getId(), publicationId,  this.logoFileName, x, y, width, height);
		
		return JSON;
	}
	
	public String doPojoJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		//如果是出版商检验其权利 begin
				Publisher pu = this.getSessionPublisher();
				boolean checkUser = false;
				if(pu!=null){//出版商用户
					List<Publication> tpList = publicationService.queryAuditByPublisherId(pu.getId());
					if(tpList!=null){
						for (Publication puc : tpList) {
							String tempS = puc.getId()+"";
							if(tempS.equals(publicationId+"")){
								checkUser = true;
							}
						}
					}
					
					if(!checkUser){
						this.jsonResult.setMessage("没有权限");
						return JSON;
					}
				}
				
				//如果是出版商检验其权利 end
		this.publication=publicationService.queryById(publicationId);
		pcRel = publicationCategoryRelService.getList(publicationId);
//		this.generateJsonResult(JsonResult.CODE.SUCCESS, 
//                JsonResult.MESSAGE.SUCCESS, "publication", this.publication);
		
		
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		Map<String,Object> objMap = new HashMap<String,Object>();
		objMap.put("publication", this.publication);
		objMap.put("pcRel", pcRel);
		this.jsonResult.setData(objMap);
		return JSON;
	}
	
	private Long publicationId;
	private Publication publication;
	private File logoFile;
	private String logoFileFileName;
	private String logoFileContentType;
	private List<Publication> publicationList;
	private List<Category> categoryList;
	private List<PublicationCategoryRel> pcRel;
	
	private String logoFileName;
	private Float x;
	private Float y;
	private Float width;
	private Float height;
	

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public File getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(File logoFile) {
		this.logoFile = logoFile;
	}

	public String getLogoFileFileName() {
		return logoFileFileName;
	}

	public void setLogoFileFileName(String logoFileFileName) {
		this.logoFileFileName = logoFileFileName;
	}

	public String getLogoFileContentType() {
		return logoFileContentType;
	}

	public void setLogoFileContentType(String logoFileContentType) {
		this.logoFileContentType = logoFileContentType;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<PublicationCategoryRel> getPcRel() {
		return pcRel;
	}

	public void setPcRel(List<PublicationCategoryRel> pcRel) {
		this.pcRel = pcRel;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}
	
	
}
