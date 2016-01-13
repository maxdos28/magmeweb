/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.util.FileOperate;
import cn.magme.util.ObjectUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-5-26
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/center.ftl"),
	@Result(name="test",location="/WEB-INF/pages/publish/test.ftl")})
public class IssueAction extends BaseAction{
	
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	private static final long serialVersionUID = 438247918283941073L;

	
	private static final Logger log=Logger.getLogger(IssueAction.class);
	
	@Resource
	private IssueService issueService; 
	
	@Resource
	private PublicationService publicationService;

	public static final String SEMI_COMMA=";";
	//更新索引
	//@Resource
	//private LuceneService luceneService;
	
	/**
	 * 通过issueId查询期刊信息，包括总页数，swf文件基本路径，jpg文件基本路径, 杂志名称
	 * @return
	 */
	public String queryIssueInfo(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			if(id==null || id<=0){
				this.jsonResult.setMessage("传入的id参数有误，id为空或<=0");
				return JSON;
			}
			Issue issue=issueService.queryById(id);
			if(issue==null){
				this.jsonResult.setMessage("期刊不存在");
				return JSON;
			}
			//首页长宽
			if(StringUtil.isNotBlank(issue.getP0Size())){
				String [] p0size=issue.getP0Size().split(SEMI_COMMA);
				this.jsonResult.put("p0width", p0size[0]);
				this.jsonResult.put("p0height", p0size[1]);
			}
			//次页长宽
			if(StringUtil.isNotBlank(issue.getP1Size())){
				String [] p1size=issue.getP1Size().split(SEMI_COMMA);
				this.jsonResult.put("p1width", p1size[0]);
				this.jsonResult.put("p1height", p1size[1]);
			}
			
			Publication pub=publicationService.queryById(issue.getPublicationId());
			if(issue!=null && pub!=null){
				this.jsonResult.put("issueId", issue.getId());
				this.jsonResult.put("swfPath", issue.getSwfPath());
				this.jsonResult.put("jpgPath", issue.getJpgPath());
				this.jsonResult.put("totalPages", issue.getTotalPages());
				this.jsonResult.put("pubName", issue.getPublicationName());
				this.jsonResult.put("categoryId", pub.getCategoryId());
				this.jsonResult.put("SORTNAME", pub.getDomain());
				this.jsonResult.put("publicationId", pub.getId());
				this.jsonResult.put("status", issue.getStatus());
				this.jsonResult.put("issueType", issue.getIssueType());
				this.jsonResult.put("publishDate", sdf.format(issue.getPublishDate()));
				this.jsonResult.put("secondDomainUrl", "http://"+pub.getDomain()+".magme.cn/"+pub.getEnglishname()+"/"+issue.getId()+".html");
				this.jsonResult.put("issuePojo", issue);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}else{
				this.jsonResult.setMessage("杂志不存在");
			}
		} catch (Exception e) {
			log.error("", e);
			
		}
		return JSON;
	}
	
	public String queryPublicationInfoJson(){
		int [] statuses=new int[]{PojoConstant.ISSUE.STATUS.ON_SALE.getCode()};
		Publication pub =null;
		try{
			if(this.publicationId!=null){
				issueList=issueService.queryByPubIdAndStatuses(this.publicationId, statuses,-1);
			}else{
				this.jsonResult=this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE);
			}
			this.jsonResult=this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("issueList", issueList);
			pub = publicationService.queryById(this.publicationId);
			if(pub!=null && pub.getDomain()!=null)
				this.jsonResult.put("SORTNAME", pub.getDomain());
		}catch (Exception e) {
			this.jsonResult=this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE);
		}
		
		return JSON;
	}
	
	/**
	 * 上架期刊
	 * @return
	 */
	public String upShelfJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Issue issue=this.issueService.queryById(id);
			issue.setStatus(PojoConstant.ISSUE.STATUS.ON_SALE.getCode());
			Publication pub=this.publicationService.queryById(issue.getPublicationId());
			if(pub==null || pub.getStatus()!=PojoConstant.PUBLICATION.STATUS.ON_SHELF.getCode()){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("期刊对应的杂志不存在，或者已经下架");
				return JSON;
			}
			if(this.issueService.updateById(issue)>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
			//luceneService.updateIndex(issue);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	/**
	 * 下架期刊
	 * @return
	 */
	public String downShelfJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Issue issue=this.issueService.queryById(id);
			issue.setStatus(PojoConstant.ISSUE.STATUS.DOWN_SHELF.getCode());
			if(this.issueService.updateById(issue)>0){
				//删除收藏
				//userFavoriteService.delUserFavoriteByIssueId(id);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	
	public String delIssue(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Issue issue=this.issueService.queryById(id);
			issue.setStatus(PojoConstant.ISSUE.STATUS.ADMIN_DOWN_SHELF.getCode());
			if(this.issueService.updateById(issue)>0){
				//删除收藏
				//userFavoriteService.delUserFavoriteByIssueId(id);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
			//luceneService.deleteIndex(issue);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
		
	}
	/**
	 * 由id查询issue所有信息
	 * @return
	 */
	public String getIssueById(){
		try {
			Issue issue = this.issueService.queryById(id);
			this.jsonResult = new JsonResult();
			this.jsonResult.put("issue", issue);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		} catch (Exception e) {
			if(this.jsonResult == null){
				this.jsonResult = new JsonResult();
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			}
		}
		return JSON;
	}
	
	/**
	 * 根据期刊id获取对应的杂志对象
	 * @return
	 */
	public String queryPublicationByIssueId(){
		this.jsonResult = JsonResult.getSuccess();
		Publication pub = publicationService.queryPubByIssueId(id);
		this.jsonResult.put("publicationPro", pub);
		return JSON;
	}
	
	/**
	 * 更新issue
	 * @return
	 */
	public String updateIssue(){
		try {
			Issue issue = this.issueService.queryById(id);
			issue.setKeyword(keyword);
			//issue.setFileName(fileName);
			issue.setPublishDate(publishDate);
			issue.setIssueNumber(issueNumber);
			issue.setDescription(description);
			//issue.setPublicationId(publicationId);
			this.issueService.updateById(issue);
			this.jsonResult = new JsonResult();
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			//luceneService.updateIndex(issue);
		} catch (Exception e) {
			if(this.jsonResult == null){
				this.jsonResult = new JsonResult();
			}
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			e.printStackTrace();
		}
		return JSON;
	}
	
	public String getByPubId(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			issueList=this.issueService.queryByPubIdAndStatuses(publicationId,new int[]{ PojoConstant.ISSUE.STATUS.ON_SALE.getCode()},-1);
			if(issueList==null || issueList.size()<=0){
				this.jsonResult.setMessage("no data");
			}else{
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("issueList", issueList);
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return JSON;
	}
	
	public String reTransferJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Issue issue=this.issueService.queryById(id);
			Issue newIssue=new Issue();
			ObjectUtil.copyProperty(issue, newIssue);
			newIssue.setId(null);
			newIssue.setStatus(PojoConstant.ISSUE.STATUS.RE_PROCESSING.getCode());
			newIssue.setId(this.issueService.insert(newIssue));
			//更新一些信息 包括swfpath和jpgpath 
			newIssue.setSwfPath(newIssue.getPublicationId()+"/"+newIssue.getId());
			newIssue.setJpgPath(newIssue.getPublicationId()+"/"+newIssue.getId());
			this.issueService.updateById(newIssue);
			issue.setStatus(PojoConstant.ISSUE.STATUS.RE_PROCESSING_DROP.getCode());
			this.issueService.updateById(issue);
			String sourcePdf=this.systemProp.getMagLocalPdf()+File.separator+issue.getPublicationId()+File.separator+issue.getId();
			File sourcePdfFile=new File(sourcePdf).listFiles()[0];
			String destPdf=this.systemProp.getNoPrintPdf()+File.separator+newIssue.getPublicationId()+File.separator+newIssue.getId();
			File destPdfPath=new File(destPdf);
			destPdfPath.mkdirs();
			File destPdfFile=new File(destPdf+File.separator+sourcePdfFile.getName());
			FileOperate.copyFile(sourcePdfFile.getAbsolutePath(), destPdf+File.separator+sourcePdfFile.getName());
			destPdfFile.renameTo(new File(destPdf+File.separator+sourcePdfFile.getName().replace(".bak", "")));
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage("已经提交重新转换");
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setMessage("提交重新转换请求失败");
		}
		
		return JSON;
	}
	
	/*public String reTransferJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Issue issue=this.issueService.queryById(id);
			Publication publication=this.publicationService.queryById(issue.getPublicationId());
			//只有被压缩过的，待审核的期刊才能再转一次
			if(issue!=null && publication!=null 
					&& (issue.getStatus()==PojoConstant.ISSUE.STATUS.WAIT_AUDIT.getCode()
					|| issue.getStatus()==PojoConstant.ISSUE.STATUS.PROCESS_FAIL.getCode())){
				String path=this.systemProp.getMagLocalPdf()+File.separator+issue.getPublicationId()+File.separator+issue.getId()+File.separator;
				//原来的文件名：1321237244337_0_0.pdf截断后为1321237244337_0_
				String fileName=issue.getFileName();
				String srcFile=null;
				if(fileName.lastIndexOf(".pdf.bak")>0){
					fileName=issue.getFileName().substring(0, issue.getFileName().length()-9);
					srcFile=path+issue.getFileName();
				}else{
					fileName=issue.getFileName().substring(0, issue.getFileName().length()-5);
					srcFile=path+issue.getFileName()+".bak";
				}
				//判断文件是否存在
				File srcFileOri=new File(srcFile);
				if(!srcFileOri.exists()){
					//1321237244337_0_0.pdf.fork.bak
					//fileName=issue.getFileName().substring(0, issue.getFileName().length()-14);
					srcFile=path+fileName+"1.pdf.fork.bak";
				}
				
				//add by liufosheng in 2012-04-17,重新转换，生成一个新的id，
				Issue newIssue=new Issue();
				ObjectUtil.copyProperty(issue, newIssue);
				newIssue.setId(null);
				newIssue.setStatus(PojoConstant.ISSUE.STATUS.RE_PROCESSING.getCode());
				newIssue.setId(this.issueService.insert(newIssue));
				//更新一些信息 包括swfpath和jpgpath 
				newIssue.setSwfPath(newIssue.getPublicationId()+"/"+newIssue.getId());
				newIssue.setJpgPath(newIssue.getPublicationId()+"/"+newIssue.getId());
				this.issueService.updateById(newIssue);
				issue.setStatus(PojoConstant.ISSUE.STATUS.RE_PROCESSING_DROP.getCode());
				this.issueService.updateById(issue);
				String newPath=this.systemProp.getMagLocalPdf()+File.separator+issue.getPublicationId()+File.separator+newIssue.getId()+File.separator;
				File newPathFile=new File(newPath);
				if(!newPathFile.exists()){
					newPathFile.mkdirs();
				}
				FileOperate fileOprate=new FileOperate();
				fileOprate.copyFile(srcFile,  newPath+fileName+"1.pdf.fork.bak");
				File forkFile=new File(newPath+fileName+"1.pdf.fork.bak");
				forkFile.renameTo(new File( newPath+fileName+"1.pdf.fork"));
				//fileOprate.(newPath+fileName+"1.pdf.fork.bak", newPath+fileName+"1.pdf.fork");
				//将压缩处理过的文件改成不压缩处理
				//fileOprate.moveFile(srcFile, path+fileName+"1.pdf.fork");
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setMessage("提交重新转换请求失败");
		}
		return JSON;
	}*/
	
	public String test(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String[] issueIds=request.getParameterValues("issueId");
		if(issueIds!=null){
			this.issueList=new ArrayList<Issue>();
			for(String issueId:issueIds){
				if(issueId!=null&&issueId.matches("^\\d+$")){
					Issue issue=issueService.queryById(Long.parseLong(issueId));
					if(issue!=null){
						issueList.add(issue);
					}
				}
			}
		}
		
		return "test";
	}
	
	public static void main(String[] args) {
		//filename=""
		Date date=new Date();
		System.out.println(date);
		System.out.println(sdf.format(date));
	}
	/**
	 * 期刊id
	 */
	private Long id;
	private String keyword;
	private Date publishDate;
	private String issueNumber;
	private String description;
	private String fileName;
	private Long publicationId;
	private List<Issue> issueList;
	
	//private Long languageId;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}
}
