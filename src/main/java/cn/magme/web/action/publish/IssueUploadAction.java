/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.charge.SaleContentService;
import cn.magme.util.FileOperate;
import cn.magme.util.MagPdfChop;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;

/**
 * @author fredy.liu
 * @date 2011-5-26
 * @version $id$
 */
@Results({@Result(name="center_success",location="/WEB-INF/pages/publish/center.ftl"),
	@Result(name="issueUploadJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class IssueUploadAction  extends BaseAction{
	private static final long serialVersionUID = -8690744069871555246L;
	@Resource
	private SystemProp systemProp;
	//@Resource
	//private IssueProcessService issueProcessService;
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private IssueService issueService;
	@Resource
	private SaleContentService saleContentService;
	
	private static final String ISSUE_NUM_SEP="/";
	
	private static final Logger log=Logger.getLogger(IssueUploadAction.class);
	
	private static final String issueUploadJson="issueUploadJson";

	/**
	 * 上传文件信息保存，使用线程实现
	 * @return
	 */
	public String addIssueProcess(){
		Publisher publisher =getSessionPublisher();
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		//Publisher publisher = this.publisherService.queryById(1039L);
       /* HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");*/
        //没有上传文件时，返回本页面.
       if(StringUtil.isBlank(fileName) || fileName.toLowerCase().lastIndexOf(".pdf")!=fileName.length()-4){
    	   jsonResult.setCode(JsonResult.CODE.FAILURE);
           jsonResult.setMessage("上传文件类型必需为pdf文件");
           //this.writeMsg();
    	   return issueUploadJson;
        }
        fileName=fileName.substring(fileName.toLowerCase().lastIndexOf("\\")+1);
        
        if (issueFile == null) {
        	jsonResult.setCode(JsonResult.CODE.FAILURE);
        	jsonResult.setMessage("上传文件为空");
        	//this.writeMsg();
            return issueUploadJson;
        }
        
        if(publisher==null){
        	jsonResult.setCode(JsonResult.CODE.FAILURE);
        	jsonResult.setMessage("没有出版商登录");
        	//this.writeMsg();
        	return issueUploadJson;
        }
        run();
        
        //this.writeMsg();
	    return issueUploadJson;
	}
	
	/*private void writeMsg(){
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charaset=utf-8");
        try {
			response.getWriter().write("<textarea>"+"{code:"+this.jsonResult.getCode()+",data:null,message:"+this.jsonResult.getMessage()+"}"+"</textarea>");
		} catch (IOException e) {
			log.error("write repsponse error", e);
		}
	}*/
	
	
	private void run() {
		    jsonResult=new JsonResult();
	        //上传前的逻辑
	        //查询publiaction
	        Publication publication=publicationService.queryById(publicationId);
	        
	        //为啥需要去掉文件名中的空格
	        chop=publication.getWhratio()==null?0:publication.getWhratio();
	        Integer needCompress=(publication.getNeedCompress()==null || publication.getNeedCompress()!=1)?0:publication.getNeedCompress();
	        String fileNameExt=(publication.getFrontChop()==null || publication.getFrontChop()!=1)?".pdf":".pdf.bak";
	        String newFileName = System.currentTimeMillis()+"_"+chop+"_"+needCompress+fileNameExt;

	        Issue issue = new Issue();
	        issue.setIssueType(PojoConstant.ISSUE.ISSUETYPE.NORMAL.getCode());
	        issue.setDescription(description);
	        issue.setFileName(newFileName);
	        issueNumber=year.substring(2,4)+ISSUE_NUM_SEP+month;
	        if(StringUtil.isNotBlank(day)){
	        	issueNumber+=ISSUE_NUM_SEP+day;
	        }
	        issue.setIssueNumber(issueNumber);
	        issue.setKeyword(keyword);
	        issue.setIsFree(isFree);
	        //issue.setProcessId(processId);
	        issue.setPublicationId(publicationId);
	        issue.setPublicationName(publication.getName());
	        issue.setPublishDate(publishDate);
	        issue.setStatus(PojoConstant.ISSUE.STATUS.WAIT_PROCESS.getCode());
	        issue.setIssueCode(0L);
	        issueService.insert(issue);
	        //设置生成时的id为issueCode，重新生成issue时，此值不变
	        issue.setIssueCode(issue.getId());
	        issueService.updateById(issue);
	        String toPathFileName=null;
	        String rootPath=(publication.getNeedPrint()==null || publication.getNeedPrint()==1)?this.systemProp.getMagLocalPdf():this.systemProp.getNoPrintPdf();
	        
	        String path=rootPath + File.separator+publicationId+File.separator+issue.getId();
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            
            toPathFileName = path+File.separator + newFileName;
            if (new File(toPathFileName).exists()) {
            	//如果文件存在，则删除
            	new File(toPathFileName).delete();
            }
            jsonResult.setCode(JsonResult.CODE.SUCCESS);
        	jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
        	
	        //TODO 先要设置好当前上传的Issue是否收费！！！！！！！！！！！
        	saleContentService.autoAddByIssue(issue);
        	
	        //移动文件
	        try {
	            FileOperate.moveFile(issueFile.getAbsolutePath(), toPathFileName);
	            //为了使用pdf打印功能,把文件保存在pdf打印目录
	            /*printPdf=this.systemProp.getBeforePrintPdf()+File.separator
	            				+publicationId+"_"+issue.getId()+"_"+toPathFileName.substring(toPathFileName.lastIndexOf(File.separator)+1);
	            File printPdfParent=new File(printPdf).getParentFile();
	            //目录不存在
	            if(!printPdfParent.exists()){
	            	printPdfParent.mkdirs();
	            }
	            printPdfBak=printPdf+".bak";
	            FileOperate.copyFile(toPathFileName, printPdfBak);*/
	            
	            FileOperate.delFile(issueFile.getAbsolutePath());
	            if(issue.getId()!=null && issue.getId()>0){
	            	newFileName = unLockPdfFile(toPathFileName.substring(toPathFileName.lastIndexOf(File.separator)+1),path);
	            }
	        } catch (Exception e) {
	        	log.error("", e);
	        }
	        
	        //处理切割
	        if(publication.getFrontChop()!=null && publication.getFrontChop()==1 && StringUtil.isNotBlank(toPathFileName)){
		        try {
					new ChopThread(new File(toPathFileName)).start();
				} catch (Exception e) {
					log.error("", e);
				}
	        }

	}
	public class ChopThread extends Thread{
		private Logger log=Logger.getLogger(ChopThread.class);
		private File fromPdf;
		public ChopThread(File fromPdf){
			this.fromPdf=fromPdf;
		}
		@Override
		public void run() {
			try {
				File newFileName=MagPdfChop.pdfChop(fromPdf);
				String destFile=newFileName.getParentFile()+File.separator+newFileName.getName().substring(5,newFileName.getName().length()-4);
				//把切割好的文件由chop_**.pdf.bak改名为**.pdf
				//FileOperate.moveFile(newFileName.getAbsolutePath(), destFile);
				newFileName.renameTo(new File(destFile));
				//删除原来的bak文件
				FileOperate.delFile(newFileName.getParentFile()+File.separator+newFileName.getName().substring(5));
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
	}
	
	private String unLockPdfFile(String fileName,String path) {
		log.info("begin unlockfile");
        String ret = fileName;
        PdfStamper stamper = null;
        try {
            String oldPath = path + File.separator + fileName;
            PdfReader reader = new PdfReader(new RandomAccessFileOrArray(oldPath),null);
            if (reader.isEncrypted()) {
                String newFileName = fileName+".bak";
                String newPath = path + File.separator + newFileName;
                stamper = new PdfStamper(reader, new FileOutputStream(newPath));
                stamper.setEncryption(null, null, PdfWriter.AllowPrinting | PdfWriter.AllowCopy | PdfWriter.AllowScreenReaders,
                        PdfWriter.STRENGTH128BITS);
                stamper.close();
                FileOperate.delFile(oldPath);
                File newPathFile=new File(newPath);
                newPathFile.renameTo(new File(path + File.separator+fileName));
            }
            reader.close();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Exception e) {
                    log.error("", e);
                }

            }
        }
        log.info("end unlockfile");
        return ret;
    }
	
	//上传的文件.
    private File issueFile;
    //上传文件名.
    private String fileName;
    //上传文件类型.
    private String fileContentType;
    
    private String year;
    
    private String month;
    
    private String day;
    
    
    
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public File getIssueFile() {
		return issueFile;
	}
	public void setIssueFile(File issueFile) {
		this.issueFile = issueFile;
	}
	private String keyword;
	private Long publicationId;
	private String issueNumber;
	private String description;
	private Date publishDate;
	private Integer chop;
	private Integer isFree=1;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
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
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getChop() {
		return chop;
	}
	public void setChop(Integer chop) {
		this.chop = chop;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	
	public static void main(String[] args) {
		String f="18147.pdf";
		String d="e:\\";
		IssueUploadAction a=new IssueUploadAction();
		a.unLockPdfFile(f, d);
	}
}
